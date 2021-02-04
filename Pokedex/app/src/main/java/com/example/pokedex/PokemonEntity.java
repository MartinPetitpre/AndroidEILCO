package com.example.pokedex;

import android.content.Context;
import android.widget.ImageView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Locale;

@Entity
public class PokemonEntity {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public Integer id;

    @ColumnInfo(name = "nom")
    public String nom;

    @ColumnInfo(name = "detailsDisponibles")
    public Boolean detailsDisponibles;

    @ColumnInfo(name = "detailDescription")
    public String detailDescription;

    @ColumnInfo(name = "detailEvolutions")
    public String detailEvolutions; // Noms de pokémon séparés par ;

    @ColumnInfo(name = "detailTaille")
    public String detailTaille;

    @ColumnInfo(name = "detailPoids")
    public String detailPoids;

    @ColumnInfo(name = "detailType")
    public String detailTypes; // Types séparés par ;

    public void loadImageIntoImageView(Context context, ImageView imageView) {
        Glide.with(context)
            .load(String.format(Locale.ENGLISH, "https://cdn.traction.one/pokedex/pokemon/%d.png", this.id))
            .apply(new RequestOptions()
                .placeholder(R.drawable.pokeball_neutre))
            .into(imageView);
    }
};
