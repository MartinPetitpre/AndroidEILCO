package com.example.td4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.td4.R.id.details;
import static com.example.td4.R.id.logout;
import static com.example.td4.R.id.rick;


public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle(getLocalClassName());

        final TextView tView = (TextView) findViewById(R.id.tview);


        Button detailsButton = (Button) findViewById(details);
        Button logoutButton = (Button) findViewById(logout);
        Button rickButton = (Button) findViewById(rick);

        Intent intent4 = getIntent();
        String login="";
        if (intent4.hasExtra("login")) {
            login = intent4.getStringExtra("login");
        }

        tView.setText(login);


        final Intent intent = new Intent(this, DetailsActivity.class);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }

        });

        final Intent intent2 = new Intent(this, LoginActivity.class);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent2);
                finish();
            }

        });

        String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        final Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        rickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent3);
            }

        });

    }





    }





