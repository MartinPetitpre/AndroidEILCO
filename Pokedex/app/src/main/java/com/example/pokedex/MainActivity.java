package com.example.pokedex;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.pokedex.Pokemon;
import com.example.pokedex.ListePokemon;
import com.example.pokedex.PokedexApiService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";

    // Tableau contenant les ID des boutons de génération des pokémons
    private static final int[] idsBoutonGenerationPokemon = {
        R.id.boutonGeneration1,
        R.id.boutonGeneration2,
        R.id.boutonGeneration3,
        R.id.boutonGeneration4,
        R.id.boutonGeneration5,
        R.id.boutonGeneration6,
        R.id.boutonGeneration7,
        R.id.boutonGeneration8
    };

    // Correspondance génération -> plage d'Id : Gen1=[1, 151], Gen2=[152, 251], etc
    private static final int[] idsGenerationPokemonMin = {   1, 152, 252, 387, 494, 650, 722, 810 };
    private static final int[] idsGenerationPokemonMax = { 151, 251, 386, 493, 649, 721, 809, 898 };

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;

    public void onClickGeneration(View view) {
        // 1. Réinitialiser le fond des boutons
        for (int idBouton : idsBoutonGenerationPokemon) {
            Button bouton = (Button)findViewById(idBouton);
            bouton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pokeball_non, getTheme()));
        }
        // 2. Récupérer la génération séléctionnée (depuis la propriété "Tag" dans le layout)
        // changer le fond du bouton sur lequel on a cliqué, changer le titre, charger les données.
        Button bouton = (Button)(view);
        bouton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pokeball_oui, getTheme()));
        int generation = Integer.parseInt((String)bouton.getTag());
        setTitle(String.format("%d%s génération de Pokémon", generation, (generation <= 1) ? "ère" : "ème"));
        chargerGeneration(generation);
    }

    private void chargerGeneration(int generation) {
        // Le paramètre generation commence à 1.
        // 1. Récupération de la plage selon la génération.
        generation = generation - 1; // Commencer à l'indice 0 pour les tableaux
        if (generation < 0) { generation = 0; }
        if (generation >= idsGenerationPokemonMax.length) { generation = idsGenerationPokemonMax.length - 1; }
        int idMin = idsGenerationPokemonMin[generation];
        int idMax = idsGenerationPokemonMax[generation];
        // 2. Récupération des pokémons et mise à jour de l'adaptateur du recycler view
        try {
            List<Pokemon> pokemons = fetchPokemonsByIdRange(idMin, idMax);
            this.pokemonAdapter.effacer();
            this.pokemonAdapter.ajouterPokemon(pokemons);
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, String.format("Erreur lors de la récupération des pokémons (IndexOutOfBounds). (idMin, idMax) = (%d, %d).", idMin, idMax), e);
        } catch (NullPointerException e) {
            Log.e(TAG, String.format("Erreur lors de la récupération des pokémons (NullPointer). (idMin, idMax) = (%d, %d).", idMin, idMax), e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        this.pokemonAdapter = new PokemonAdapter(this);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView.setAdapter(this.pokemonAdapter);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(layoutManager);
        this.retrofit = new Retrofit.Builder()
            .baseUrl(PokedexApiService.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        // Simuler un clic sur le bouton 1ere génération
        onClickGeneration((View)findViewById(R.id.boutonGeneration1));
    }

    private void insertPokemonDb(List<Pokemon> listePokemon) {
        AppDatabase appDb = AppDatabase.getInstance(this);
        List<Pokemon> listePokemonDb = appDb.pokemonDao().getAll();
        // On verifie si le pokemon existe en base
        for (int i = 0; i < listePokemon.size(); i++) {
            boolean existeDeja = false;
            for (int j = 0; j < listePokemonDb.size(); j++) {
                if(listePokemonDb.get(j).getNumber() == listePokemon.get(i).getNumber()) {
                    existeDeja = true;
                    break;
                }
            }
            if(!existeDeja) {
                Log.i(TAG,listePokemon.get(i).getName() );
                appDb.pokemonDao().insertPokemon(listePokemon.get(i));
            }
        }
    }

    private List<Pokemon> fetchPokemonsByIdRange(int idMin, int idMax) throws IndexOutOfBoundsException, NullPointerException {
        if (idMin > idMax) {
            throw new IndexOutOfBoundsException();
        }
        // 1. Récupération des pokémons dans la base de données
        AppDatabase appDb = AppDatabase.getInstance(this);
        List<Pokemon> pokemons = appDb.pokemonDao().getByIdRange(idMin, idMax);
        // 2. La liste retournée devrait en pratique avoir autant de pokémons que spécifiés avec la plage passée en paramètres
        // Sinon, cela signifie que la liste est incomplète et que l'on doit la mettre à jour grâce aux appels vers l'api
        int requiredPokemonCountInDatabase = idMax - idMin + 1;
        if (pokemons.size() < requiredPokemonCountInDatabase) {
            // 2.1. Si ce n'est pas le cas, la liste est incomplète. On doit récupérer les pokémons manquants via l'api
            // puis les ajouter dans la base de données. On doit avant ça effacer la liste, incomplète, récupérée précédemment.
            pokemons.clear();
            // Les pokémons retournés on un id *supérieur* à l'offset.
            // Par exemple, un offset de 0 va renvoyer les pokémons à partir de l'id 1, ...
            // On soustrait 1 pour inclure le pokémon avec l'"idMin"
            int apiOffset = idMin - 1;
            // Le calcul est le même que précédemment, mais pour la clarté des choses on peut utiliser le même calcul sur une autre variable
            // La limite est le nombre de pokémons à retourner, donc le nombre d'id dans la plage renseignée en paramètres
            int apiLimit = idMax - idMin + 1;
            this.retrofit.create(PokedexApiService.class).getListePokemon(apiLimit, apiOffset).enqueue(new Callback<ListePokemon>() {
                @Override
                public void onResponse(Call<ListePokemon> call, Response<ListePokemon> response) {
                    if (response.isSuccessful()) {
                        // On prie pour que les données soient récupérées :), et on les ajoute au cache.
                        pokemons.addAll(response.body().getResults());
                        insertPokemonDb(pokemons);
                    } else {
                        Log.e(TAG, String.format("Erreur lors de la récupération d'un pokémon. onResponse : %s.", response.errorBody()));
                    }
                }
                @Override
                public void onFailure(Call<ListePokemon> call, Throwable t) {
                    Log.e(TAG, String.format("Erreur lors de la récupération d'un pokémon. onFailure : %s.", t.getMessage()), t);
                }
            });
        }
        // 3. La liste est complète
        return pokemons;
    }
}
