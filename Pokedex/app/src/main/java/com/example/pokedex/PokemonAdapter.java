package com.example.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataset;
    private Context context;

    public PokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poke, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        holder.nomPokemonTextView.setText(p.getName());

        Glide.with(context)
                .load("https://cdn.traction.one/pokedex/pokemon/" + p.getNumber() + ".png")
                .into(holder.pokemonImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void ajouterPokemon(ArrayList<Pokemon> listePokemon) {
        dataset.addAll(listePokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView pokemonImageView;
        private TextView nomPokemonTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            pokemonImageView = (ImageView) itemView.findViewById(R.id.pokemonImageView);
            nomPokemonTextView = (TextView) itemView.findViewById(R.id.nomPokemonTextView);
        }
    }
}
