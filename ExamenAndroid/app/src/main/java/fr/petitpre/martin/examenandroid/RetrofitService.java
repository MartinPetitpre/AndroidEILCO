
package fr.petitpre.martin.examenandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    public static final String ENDPOINT = "https://android.busin.fr";

    @GET("/api/lakers/players")
    Call<List<Joueur>> getListeJoueurs();

}