package com.example.pokedex;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;

    private int offset;

    private boolean chargementDispo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pokemonAdapter = new PokemonAdapter(this);

        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (chargementDispo) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Fin");

                            chargementDispo = false;
                            offset += 20;
                            recupererDonnees(offset);
                        }
                    }
                }
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chargementDispo = true;
        offset = 0;
        recupererDonnees(offset);
    }

    private void recupererDonnees(int offset) {
        PokedexApiService service = retrofit.create(PokedexApiService.class);
        Call<ListePokemon> listePokemonCall = service.getListePokemon(20, offset);

        listePokemonCall.enqueue(new Callback<ListePokemon>() {
            @Override
            public void onResponse(Call<ListePokemon> call, Response<ListePokemon> response) {
                chargementDispo = true;
                if (response.isSuccessful()) {

                    ListePokemon listePokemon = response.body();
                    ArrayList<Pokemon> listePokemon2 = listePokemon.getResults();

                    pokemonAdapter.ajouterPokemon(listePokemon2);

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ListePokemon> call, Throwable t) {
                chargementDispo = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
}
