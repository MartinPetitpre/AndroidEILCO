package com.example.pokedex;

import java.util.List;

public class MonCallback {
    // Pour les opérations asynchrones de retrofit
    public interface Generique {
        public void executer();
    };
    // Pour la récupération des pokemons dans PokemonFacade
    public interface PassantEnParametreUneListeDePokemons {
        public void executer(List<PokemonEntity> pokemons);
    }
};
