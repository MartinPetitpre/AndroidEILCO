package com.example.pokedex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.example.pokedex.ListePokemon;


public interface PokedexApiService {

    public static final String ENDPOINT = "https://pokeapi.co/api/v2";

    @GET("pokemon")
    Call<ListePokemon> getListePokemon(@Query("limit") int limit, @Query("offset") int offset);
}
