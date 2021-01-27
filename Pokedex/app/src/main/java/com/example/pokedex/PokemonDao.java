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

    @Query("SELECT * FROM pokemon WHERE number >= :idMin AND number <= :idMax")
    List<Pokemon> getByIdRange(int idMin, int idMax);

    @Insert
    void insertPokemon(Pokemon pokemon);

    @Delete
    void deletePokemon(Pokemon pokemon);
}