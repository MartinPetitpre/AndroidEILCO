package com.example.td6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import static com.example.td6.R.id.saisie;

public class listeRepo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_repo);

        final EditText edtext = (EditText) findViewById(saisie);
    }
}