package com.example.pokedex;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

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

    private PokemonAdapter pokemonAdapter;

    public void onClickGeneration(View view) {
        // 1. Réinitialiser le fond des boutons, icone de chargement
        this.findViewById(R.id.recyclerView).setVisibility(View.GONE);
        for (int idBouton : idsBoutonGenerationPokemon) {
            Button bouton = (Button)findViewById(idBouton);
            bouton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pokeball_non, getTheme()));
        }
        // 2. Récupérer la génération séléctionnée (depuis la propriété "Tag" dans le layout)
        // changer le fond du bouton sur lequel on a cliqué, changer le titre, charger les données.
        Button bouton = (Button)(view);
        bouton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pokeball_oui, getTheme()));
        int generation = Integer.parseInt((String)bouton.getTag());
        setTitle(String.format(Locale.ENGLISH, "%d%s génération de Pokémon", generation, (generation == 1) ? "ère" : "ème"));
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
            PokemonFacade.fetchPokemonsByIdRange(this, idMin, idMax, (pokemons) -> {
                this.pokemonAdapter.effacer();
                this.pokemonAdapter.ajouterPokemon(pokemons);
                this.findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
            });
        } catch (IndexOutOfBoundsException e) {
            Log.e(Constantes.TAG, String.format(Locale.ENGLISH, "Erreur lors de la récupération des pokémons (IndexOutOfBounds). (idMin, idMax) = (%d, %d).", idMin, idMax), e);
        } catch (NullPointerException e) {
            Log.e(Constantes.TAG, String.format(Locale.ENGLISH, "Erreur lors de la récupération des pokémons (NullPointer). (idMin, idMax) = (%d, %d).", idMin, idMax), e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        this.pokemonAdapter = new PokemonAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(this.pokemonAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        // Simuler un clic sur le bouton 1ere génération
        onClickGeneration((View)findViewById(R.id.boutonGeneration1));
    }
}
