package com.example.pokedex;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private ArrayList<PokemonEntity> dataset;
    private Context context;

    public PokemonAdapter(Context context) {
        this.context = context;
        this.dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poke, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PokemonEntity pokemon = this.dataset.get(position);
        holder.nomPokemonTextView.setText(pokemon.nom);
        pokemon.loadImageIntoImageView(this.context, holder.pokemonImageView);
        holder.pokemonImageView.setOnClickListener(view -> {
            Intent intent = new Intent(this.context, PokemonActivity.class);
            intent.putExtra(Constantes.EXTRA_POKEMON_ID, pokemon.id);
            this.context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void effacer() {
        this.dataset.clear();
    }

    public void ajouterPokemon(List<PokemonEntity> listePokemon) {
        this.dataset.addAll(listePokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView pokemonImageView;
        public TextView nomPokemonTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.pokemonImageView = (ImageView) itemView.findViewById(R.id.pokemonImageView);
            this.nomPokemonTextView = (TextView) itemView.findViewById(R.id.nomPokemonTextView);
        }
    }
}
