package com.example.td4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.td4.R.id.news;
import static com.example.td4.R.id.saisie;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //toujours ces deux lignes en premiers dans le onCreate

        Button newsButton = (Button) findViewById(news);
        final EditText edtext = (EditText) findViewById(saisie);

        setTitle(getLocalClassName());
        final Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("login", edtext.getText().toString());



        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
                finish();

            }

        });

    }


}