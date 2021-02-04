package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PokemonActivity extends AppCompatActivity {

    // Petits helpers, permet de simplifier un peu le code et d'éviter d'avoir un cast du type ((TextView)this.findViewById(...)).setText(...)
    // mais plutot this.findTextView(...).setText(...)
    TextView findTextView(int id) { return (TextView)this.findViewById(id); }
    ImageView findImageView(int id) { return (ImageView)this.findViewById(id); }
    Button findButton(int id) { return (Button)this.findViewById(id); }
    CardView findCardView(int id) { return (CardView)this.findViewById(id); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        this.findViewById(R.id.vueDetails).setVisibility(View.GONE);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        PokemonAdapter pokemonAdapter = new PokemonAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEvolutions);
        int idPokemon = this.getIntent().getIntExtra(Constantes.EXTRA_POKEMON_ID, 0);

        this.findTextView(R.id.texteIdPokemon).setText(String.format(Locale.ENGLISH, "#%03d", idPokemon));
        this.findButton(R.id.boutonRetourPokemon).setOnClickListener(v -> this.finish());
        this.findCardView(R.id.carteFond).setCardBackgroundColor(Color.parseColor("#3B3B3B"));
        this.findButton(R.id.boutonDescription).setOnClickListener(v -> {
            this.findButton(R.id.boutonDescription).setAlpha(1.00f);
            this.findButton(R.id.boutonEvolutions).setAlpha(0.50f);
            this.findViewById(R.id.layoutDescription).setVisibility(View.VISIBLE);
            this.findViewById(R.id.recyclerViewEvolutions).setVisibility(View.GONE);
            this.findViewById(R.id.texteDescriptionPokemon).setVisibility(View.VISIBLE);
        });
        this.findButton(R.id.boutonEvolutions).setOnClickListener(v -> {
            this.findButton(R.id.boutonDescription).setAlpha(0.50f);
            this.findButton(R.id.boutonEvolutions).setAlpha(1.00f);
            this.findViewById(R.id.layoutDescription).setVisibility(View.GONE);
            this.findViewById(R.id.recyclerViewEvolutions).setVisibility(View.VISIBLE);
            this.findViewById(R.id.texteDescriptionPokemon).setVisibility(View.GONE);
        });

        // Simuler l'appui sur description
        this.findButton(R.id.boutonDescription).callOnClick();

        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        try {
            // 1. Récupérer les données relatives à ce pokémon
            PokemonFacade.fetchPokemonsByIdRange(this, idPokemon, idPokemon, (pokemons) -> {
                PokemonEntity pokemon = pokemons.get(0);
                PokemonFacade.fetchPokemonDetails(this, pokemon, () -> {
                    // 2. Injecter les détails sur l'interface
                    this.findTextView(R.id.texteNomPokemon).setText(pokemon.nom.toUpperCase());
                    pokemon.loadImageIntoImageView(this, this.findImageView(R.id.imagePokemon));
                    this.findTextView(R.id.texteDescriptionPokemon).setText(pokemon.detailDescription);
                    this.findTextView(R.id.textePoidsPokemon).setText(pokemon.detailPoids);
                    this.findTextView(R.id.texteTaillePokemon).setText(pokemon.detailTaille);
                    this.findTextView(R.id.texteTypesPokemon).setText(pokemon.detailTypes.replace(";", ", "));
                    Integer color;
                    switch (pokemon.detailTypes.split(";")[0].toLowerCase()) {
                        case "bug":      color = Color.parseColor("#A0C888"); break;
                        case "dark":     color = Color.parseColor("#908888"); break;
                        case "dragon":   color = Color.parseColor("#6898F8"); break;
                        case "electric": color = Color.parseColor("#E0E000"); break;
                        case "fairy":    color = Color.parseColor("#FF65D5"); break;
                        case "fighting": color = Color.parseColor("#F87070"); break;
                        case "fire":     color = Color.parseColor("#F89030"); break;
                        case "flying":   color = Color.parseColor("#58C8F0"); break;
                        case "ghost":    color = Color.parseColor("#A870F8"); break;
                        case "grass":    color = Color.parseColor("#90E880"); break;
                        case "ground":   color = Color.parseColor("#6E3C0B"); break;
                        case "ice":      color = Color.parseColor("#30D8CF"); break;
                        case "normal":   color = Color.parseColor("#B8B8A8"); break;
                        case "poison":   color = Color.parseColor("#E090F8"); break;
                        case "psychic":  color = Color.parseColor("#F838A8"); break;
                        case "rock":     color = Color.parseColor("#C8A048"); break;
                        case "steel":    color = Color.parseColor("#B8B8D0"); break;
                        case "water":    color = Color.parseColor("#6898F7"); break;
                        default:         color = Color.parseColor("#3B3B3B"); break;
                    };
                    this.findCardView(R.id.carteFond).setCardBackgroundColor(color);
                    this.findViewById(R.id.layoutNavigation).setBackgroundColor(color);
                    // 3. Récupérer les évolutions, enlever le pokémon actuel et les ajouter au recycler view des évolutions
                    String[] evolutionArray = pokemon.detailEvolutions.split(";");
                    List<String> evolutions = new ArrayList<>();
                    for (String evolution : evolutionArray) {
                        if (!evolution.toLowerCase().equals(pokemon.nom.toLowerCase())) {
                            evolutions.add(evolution.toLowerCase());
                        }
                    }
                    PokemonFacade.fetchPokemonsByNames(this, evolutions, pokemonsByName -> {
                        pokemonAdapter.ajouterPokemon(pokemonsByName);
                        this.findViewById(R.id.vueDetails).setVisibility(View.VISIBLE);
                    });
                });
            });
        } catch (IndexOutOfBoundsException e) {
            Log.e(Constantes.TAG, String.format(Locale.ENGLISH, "Erreur lors de la récupération du pokémon (IndexOutOfBounds). (id) = (%d).", idPokemon), e);
            this.finish();
        }
    }
}