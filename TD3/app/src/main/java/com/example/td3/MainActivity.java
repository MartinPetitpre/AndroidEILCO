package com.example.td3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean isEmpty(EditText etText) { //verifie si la saisie est nulle
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    int rand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rollButton = (Button) findViewById(R.id.button);
        final TextView tView = (TextView) findViewById(R.id.textView);
        final TextView tView2 = (TextView) findViewById(R.id.textView2);

        final EditText s =(EditText)findViewById(R.id.saisie);


        final Random numRandom = new Random();
        final Random numRandom2 = new Random();


        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Toast toast = Toast.makeText(MainActivity.this, "Dé lancé!", Toast.LENGTH_SHORT);
                toast.show();*/

                int nbFace=6; //par defaut le nb de face est 6

                if (isEmpty(s)==false) //si la saisie nest pas nulle
                {
                    nbFace= Integer.parseInt(s.getText().toString());
                    if (nbFace <= 0)// si la saisie est inf à 0 alors nb face reprend la val 6
                    {
                        nbFace = 6;
                    }

                }


                int rand = 1+ numRandom.nextInt(nbFace);  //nb aleatoire compris entre 1 et nbface
                tView.setText(String.valueOf(rand));

                int rand2 = 1+ numRandom2.nextInt(nbFace);
                tView2.setText(String.valueOf(rand2));
            }
        });

    }
}