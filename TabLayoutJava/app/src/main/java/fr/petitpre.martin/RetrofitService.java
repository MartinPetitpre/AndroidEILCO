
package fr.petitpre.martin

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    public static final String ENDPOINT = "https://android.busin.fr/api/lakers";

    @GET("/")
    Call<List<Joueurs>> getListeJoueurs(@Path("user") String user);

}