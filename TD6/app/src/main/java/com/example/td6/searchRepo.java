package com.example.td6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.td6.R.id.saisie;
import static com.example.td6.R.id.rechercher;

public class searchRepo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_repo);

        Button valider = (Button) findViewById(rechercher);
        final EditText edtext = (EditText) findViewById(saisie);

        Intent intent = getIntent();
        String search="toto";
        if (intent.hasExtra("recherche")) {
            search = intent.getStringExtra("recherche");
        }
        Toast.makeText(searchRepo.this,"Résultats de recherche pour :" + search, Toast.LENGTH_SHORT).show();

        GithubService githubService = new Retrofit.Builder()
                .baseUrl(GithubService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService.class);

        githubService.searchRepos(search).enqueue(new Callback<Repos>() {
            @Override
            public void onResponse(Call<Repos> call, Response<Repos> response) {
                afficherRepos(response.body());
            }

            @Override
            public void onFailure(Call<Repos> call, Throwable t) {
                Log.e("erreur search", t.getMessage());
                Toast.makeText(searchRepo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView rvRepos = (RecyclerView) findViewById(R.id.rvRepos);

    }
    public void afficherRepos(Repos repos) {
        //Toast.makeText(this,"nombre de dépots : "+repos.size(), Toast.LENGTH_SHORT).show();
        RecyclerView rvRepos = (RecyclerView) findViewById(R.id.rvRepos);
        SearchRepoAdapter adapter = new SearchRepoAdapter(repos,this);
        rvRepos.setAdapter(adapter);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
    }
}