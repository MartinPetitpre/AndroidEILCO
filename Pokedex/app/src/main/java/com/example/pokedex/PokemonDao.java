package com.example.pokedex;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    List<Pokemon> getAll();

    @Insert
    void insertPokemon(Pokemon pokemon);

    @Delete
    void deletePokemon(Pokemon pokemon);
}