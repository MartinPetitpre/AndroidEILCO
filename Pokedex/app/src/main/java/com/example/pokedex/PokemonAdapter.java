package com.example.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataset;
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
        Pokemon p = this.dataset.get(position);
        holder.nomPokemonTextView.setText(p.getName());
        Glide.with(context)
            .load("https://cdn.traction.one/pokedex/pokemon/" + p.getNumber() + ".png")
            .apply(new RequestOptions()
                .placeholder(R.drawable.pokeball_neutre))
            .into(holder.pokemonImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void effacer() {
        this.dataset.clear();
    }

    public void ajouterPokemon(List<Pokemon> listePokemon) {
        this.dataset.addAll(listePokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pokemonImageView;
        private TextView nomPokemonTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.pokemonImageView = (ImageView) itemView.findViewById(R.id.pokemonImageView);
            this.nomPokemonTextView = (TextView) itemView.findViewById(R.id.nomPokemonTextView);
        }
    }
}
