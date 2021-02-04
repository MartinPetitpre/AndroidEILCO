package com.example.pokedex;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonFacade {

    // --------------------------------------------------------------------------------------------
    //  ____   _____   _____
    // |  _ \ |  __ \ |  __ \
    // | |_) || |  | || |  | |
    // |  _ < | |  | || |  | |
    // | |_) || |__| || |__| |
    // |____/ |_____/ |_____/
    // --------------------------------------------------------------------------------------------

    @androidx.room.Database(entities = {PokemonEntity.class}, version = 1, exportSchema = false)
    public static abstract class AppDatabase extends RoomDatabase {
        public abstract PokemonDao pokemonDao();

        public static synchronized AppDatabase getInstance(Context context) {
            AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "pokemon").allowMainThreadQueries().build();
            return db;
        }
    };

    @androidx.room.Dao
    public interface PokemonDao {
        @androidx.room.Query("SELECT * FROM pokemonentity WHERE id >= :idMin AND id <= :idMax")
        List<PokemonEntity> getByIdRange(Integer idMin, Integer idMax);

        @androidx.room.Query("SELECT * FROM pokemonentity WHERE nom IN (:noms)")
        List<PokemonEntity> getByNames(List<String> noms);

        @androidx.room.Insert
        void insert(PokemonEntity pokemon);

        @androidx.room.Update
        void update(PokemonEntity pokemon);
    };

    // --------------------------------------------------------------------------------------------
    //            _____   _____
    //     /\    |  __ \ |_   _|
    //    /  \   | |__) |  | |
    //   / /\ \  |  ___/   | |
    //  / ____ \ | |      _| |_
    // /_/    \_\|_|     |_____|
    // --------------------------------------------------------------------------------------------

    public interface PokeapiApiService {
        public static final String ENDPOINT = "https://pokeapi.co/api/v2/";

        @retrofit2.http.GET("pokemon/")
        public Call<PokeapiApi.ResponseList> getPokemonList(@retrofit2.http.Query("limit") Integer limit, @retrofit2.http.Query("offset") Integer offset);

        @retrofit2.http.GET("pokemon/{name}")
        public Call<PokeapiApi.Response> getPokemonDetailsFromName(@retrofit2.http.Path("name") String name);
    };

    public static class PokeapiApi {
        // Lors de l'appel https://pokeapi.co/api/v2/pokemon/?offset=xxx&limit=xxx

        public static class ResponseList {
            public List<Response> results;
        }

        public static class Response {
            public String url;
            public String name;
        }
    }

    public interface PokedexApiService {
        public static final String ENDPOINT = "https://pokeapi.glitch.me/v1/";

        @retrofit2.http.GET("/pokemon/{id}")
        public Call<List<PokedexApi.Response>> getPokemonDetailedResponse(@retrofit2.http.Path("id") String id);
    };

    public static class PokedexApi {
        // Lors de l'appel https://pokeapi.glitch.me/v1/pokemon/xxx

        public static class Response {
            public Integer number;
            public String name;
            public String height;
            public String weight;
            public List<String> types;
            public Family family;
            public String description;
        }

        public static class Family {
            public List<String> evolutionLine;
        }
    }

    // --------------------------------------------------------------------------------------------
    //  _____  __  __  _____   _
    // |_   _||  \/  ||  __ \ | |
    //   | |  | \  / || |__) || |
    //   | |  | |\/| ||  ___/ | |
    //  _| |_ | |  | || |     | |____  _
    // |_____||_|  |_||_|     |______|(_)
    // --------------------------------------------------------------------------------------------

    private static PokemonEntity makePokemon(Integer id, String name) {
        PokemonEntity pokemon = new PokemonEntity();
        pokemon.id = id;
        pokemon.nom = name.toLowerCase();
        // Quand on crée un pokémon, on ne cherche pas tout de suite ses détails.
        // Ces détails sont récupérés en temps voulu par l'utilisateur lorsqu'il cliquera sur un pokémon pour en afficher les détails
        pokemon.detailsDisponibles = false;
        pokemon.detailDescription = "";
        pokemon.detailEvolutions = "";
        pokemon.detailPoids = "";
        pokemon.detailTaille = "";
        pokemon.detailTypes = "";
        return pokemon;
    }

    private static Retrofit makePokeapiRetrofit() {
        return new Retrofit.Builder()
            .baseUrl(PokeapiApiService.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    private static Retrofit makePokedexRetrofit() {
        return new Retrofit.Builder()
            .baseUrl(PokedexApiService.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public static void fetchPokemonDetails(Context context, PokemonEntity pokemon, MonCallback.Generique callback) {
        // On veut récupérer les détails du pokémon. Si les détails ne sont pas disponibles, on les récupère via l'api et on les stocke en base
        if (pokemon.detailsDisponibles) {
            // Ce pokemon est déjà complet !
            callback.executer();
        } else {
            // Il n'est pas complet. On fait appel à l'api et on met à jour la base de données.
            PokemonDao dao = AppDatabase.getInstance(context).pokemonDao();
            Retrofit retrofit = makePokedexRetrofit();
            retrofit.create(PokedexApiService.class).getPokemonDetailedResponse(pokemon.id.toString()).enqueue(new Callback<List<PokedexApi.Response>>() {
                @Override
                public void onResponse(Call<List<PokedexApi.Response>> call, Response<List<PokedexApi.Response>> response) {
                    if (response.isSuccessful()) {
                        PokedexApi.Response detailedResponse = response.body().get(0);
                        pokemon.detailsDisponibles = true;
                        pokemon.detailTaille = detailedResponse.height;
                        pokemon.detailPoids = detailedResponse.weight;
                        pokemon.detailEvolutions = String.join(";", detailedResponse.family.evolutionLine);
                        pokemon.detailDescription = detailedResponse.description;
                        pokemon.detailTypes = String.join(";", detailedResponse.types).toLowerCase();
                        dao.update(pokemon);
                        AppDatabase.getInstance(context).close();
                        callback.executer();
                    } else {
                        Log.e(Constantes.TAG, String.format("Erreur lors de la récupération des détails d'un pokémon. onResponse : %s.", response.errorBody()));
                    }
                }
                @Override
                public void onFailure(Call<List<PokedexApi.Response>> call, Throwable t) {
                    Log.e(Constantes.TAG, String.format("Erreur lors de la récupération des détails d'un pokémon. onFailure : %s.", t.getMessage()), t);
                }
            });
        }
    }

    public static void fetchPokemonsByIdRange(Context context, int idMin, int idMax, MonCallback.PassantEnParametreUneListeDePokemons callback) throws IndexOutOfBoundsException, NullPointerException {
        if (idMin > idMax) {
            throw new IndexOutOfBoundsException();
        }
        // 1. Récupération des pokémons dans la base de données
        PokemonDao dao = AppDatabase.getInstance(context).pokemonDao();
        AppDatabase.getInstance(context).close();
        List<PokemonEntity> pokemonsInDatabase = dao.getByIdRange(idMin, idMax);
        // 2. La liste retournée devrait en pratique avoir autant de pokémons que spécifiés avec la plage passée en paramètres
        // Sinon, cela signifie que la liste est incomplète et que l'on doit la mettre à jour grâce aux appels vers l'api
        int requiredPokemonCountInDatabase = idMax - idMin + 1;
        if (pokemonsInDatabase.size() < requiredPokemonCountInDatabase) {
            // 2.1. Si ce n'est pas le cas, la liste est incomplète. On doit récupérer les pokémons manquants via l'api
            // puis les ajouter dans la base de données.
            List<PokemonEntity> pokemons = new ArrayList<>(); // On part d'une liste vide
            // Les pokémons retournés on un id *supérieur* à l'offset.
            // Par exemple, un offset de 0 va renvoyer les pokémons à partir de l'id 1, ...
            // On soustrait 1 pour inclure le pokémon avec l'"idMin"
            int apiOffset = idMin - 1;
            // Le calcul est le même que précédemment, mais pour la clarté des choses on peut utiliser le même calcul sur une autre variable
            // La limite est le nombre de pokémons à retourner, donc le nombre d'id dans la plage renseignée en paramètres
            int apiLimit = idMax - idMin + 1;
            Retrofit retrofit = makePokeapiRetrofit();
            retrofit.create(PokeapiApiService.class).getPokemonList(apiLimit, apiOffset).enqueue(new Callback<PokeapiApi.ResponseList>() {
                @Override
                public void onResponse(Call<PokeapiApi.ResponseList> call, Response<PokeapiApi.ResponseList> response) {
                    if (response.isSuccessful()) {
                        // Indexation des pokémons dans le hashset
                        HashSet<Integer> pokemonSetInDatabase = new HashSet<>();
                        for (PokemonEntity pokemon : pokemonsInDatabase) {
                            pokemonSetInDatabase.add(pokemon.id);
                            pokemons.add(pokemon); // On ajoute les pokémons déjà existants en base
                        }
                        Integer pokemonId = apiOffset;
                        for (PokeapiApi.Response responseItem : response.body().results) {
                            pokemonId += 1;
                            Boolean pokemonExistsInDatabase = pokemonSetInDatabase.contains(pokemonId);
                            if (pokemonExistsInDatabase) {
                                // Ce pokémon a déjà été ajouté quelques lignes auparavant, on ne fait rien.
                            } else {
                                // Ce pokémon n'est pas présent en base. On l'instancie et l'ajoute à la base et aux résultats
                                PokemonEntity pokemon = makePokemon(pokemonId, responseItem.name);
                                dao.insert(pokemon);
                                pokemons.add(pokemon);
                            }
                        }
                        AppDatabase.getInstance(context).close();
                        // 3.1. La liste est complète, appeler le callback
                        callback.executer(pokemons);
                    } else {
                        Log.e(Constantes.TAG, String.format("Erreur lors de la récupération d'un pokémon (by id range). onResponse : %s.", response.errorBody()));
                    }
                }
                @Override
                public void onFailure(Call<PokeapiApi.ResponseList> call, Throwable t) {
                    Log.e(Constantes.TAG, String.format("Erreur lors de la récupération d'un pokémon (by id range). onFailure : %s.", t.getMessage()), t);
                }
            });
        }
        else {
            AppDatabase.getInstance(context).close();
            // 3.2. Appeler le callback avec les données de la base
            callback.executer(pokemonsInDatabase);
        }
    }

    public static void fetchPokemonsByNames(Context context, List<String> names, MonCallback.PassantEnParametreUneListeDePokemons callback) throws IndexOutOfBoundsException, NullPointerException {
        // 1. Récupération des pokémons dans la base de données
        PokemonDao dao = AppDatabase.getInstance(context).pokemonDao();
        List<PokemonEntity> pokemonsInDatabase = dao.getByNames(names);
        // 2. La liste retournée devrait en pratique avoir autant de pokémons que spécifiés avec la plage passée en paramètres
        // Sinon, cela signifie que la liste est incomplète et que l'on doit la mettre à jour grâce aux appels vers l'api
        int requiredPokemonCountInDatabase = names.size();
        if (pokemonsInDatabase.size() < requiredPokemonCountInDatabase) {
            // 2.1. Si ce n'est pas le cas, la liste est incomplète. On doit récupérer les pokémons manquants via l'api
            // puis les ajouter dans la base de données.
            List<PokemonEntity> pokemons = new ArrayList<>(); // On part d'une liste vide
            Retrofit retrofit = makePokeapiRetrofit();
            Integer index = 0;
            for (String name : names) {
                index += 1;
                final Boolean lastInList = (index == names.size());
                retrofit.create(PokeapiApiService.class).getPokemonDetailsFromName(name).enqueue(new Callback<PokeapiApi.Response>() {
                    @Override
                    public void onResponse(Call<PokeapiApi.Response> call, Response<PokeapiApi.Response> response) {
                        if (response.isSuccessful()) {
                            // Indexation des pokémons dans le hashset
                            HashSet<Integer> pokemonSetInDatabase = new HashSet<>();
                            for (PokemonEntity pokemon : pokemonsInDatabase) {
                                pokemonSetInDatabase.add(pokemon.id);
                                pokemons.add(pokemon); // On ajoute les pokémons déjà existants en base
                            }
                            PokeapiApi.Response responseItem = response.body();
                            if (responseItem.url != null) {
                                String[] urlParts = responseItem.url.split("/");
                                Integer pokemonId = Integer.parseInt(urlParts[urlParts.length - 2]);
                                Boolean pokemonExistsInDatabase = pokemonSetInDatabase.contains(pokemonId);
                                if (pokemonExistsInDatabase) {
                                    // Ce pokémon a déjà été ajouté quelques lignes auparavant, on ne fait rien.
                                } else {
                                    // Ce pokémon n'est pas présent en base. On l'instancie et l'ajoute à la base et aux résultats
                                    PokemonEntity pokemon = makePokemon(pokemonId, responseItem.name);
                                    dao.insert(pokemon);
                                    pokemons.add(pokemon);
                                }
                            }
                            AppDatabase.getInstance(context).close();
                            // 3.1. QUAND liste est complète, appeler le callback
                            if (lastInList) {
                                callback.executer(pokemons);
                            }
                        } else {
                            Log.e(Constantes.TAG, String.format("Erreur lors de la récupération d'un pokémon (by names). onResponse : %s.", response.errorBody()));
                        }
                    }
                    @Override
                    public void onFailure(Call<PokeapiApi.Response> call, Throwable t) {
                        Log.e(Constantes.TAG, String.format("Erreur lors de la récupération d'un pokémon (by names). onFailure : %s.", t.getMessage()), t);
                    }
                });
            }
        }
        else {
            AppDatabase.getInstance(context).close();
            // 3.2. Appeler le callback avec les données de la base
            callback.executer(pokemonsInDatabase);
        }
    }
}
