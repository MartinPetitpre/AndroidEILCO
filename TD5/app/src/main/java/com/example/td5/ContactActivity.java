package com.example.td5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);



        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        contacts.add(new Contact("Antoine","Pierre","https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/69143430_2227297990709372_5993545583606890496_o.jpg?_nc_cat=108&_nc_sid=09cbfe&_nc_ohc=z8iEDkU0SMMAX8nBabO&_nc_ht=scontent-cdg2-1.xx&oh=31fb2cf75caa41ca124516366e45a9a6&oe=5FB4B6DF"));
        contacts.add(new Contact("Antoine","Paul","https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/69143430_2227297990709372_5993545583606890496_o.jpg?_nc_cat=108&_nc_sid=09cbfe&_nc_ohc=z8iEDkU0SMMAX8nBabO&_nc_ht=scontent-cdg2-1.xx&oh=31fb2cf75caa41ca124516366e45a9a6&oe=5FB4B6DF"));
        contacts.add(new Contact("Antoine","Jacques","https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/69143430_2227297990709372_5993545583606890496_o.jpg?_nc_cat=108&_nc_sid=09cbfe&_nc_ohc=z8iEDkU0SMMAX8nBabO&_nc_ht=scontent-cdg2-1.xx&oh=31fb2cf75caa41ca124516366e45a9a6&oe=5FB4B6DF"));
        contacts.add(new Contact("Antoine","Manga","https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/69143430_2227297990709372_5993545583606890496_o.jpg?_nc_cat=108&_nc_sid=09cbfe&_nc_ohc=z8iEDkU0SMMAX8nBabO&_nc_ht=scontent-cdg2-1.xx&oh=31fb2cf75caa41ca124516366e45a9a6&oe=5FB4B6DF"));
        contacts.add(new Contact("Antoine","Thomas","https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/69143430_2227297990709372_5993545583606890496_o.jpg?_nc_cat=108&_nc_sid=09cbfe&_nc_ohc=z8iEDkU0SMMAX8nBabO&_nc_ht=scontent-cdg2-1.xx&oh=31fb2cf75caa41ca124516366e45a9a6&oe=5FB4B6DF"));
        contacts.add(new Contact("Antoine","Junior","https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/69143430_2227297990709372_5993545583606890496_o.jpg?_nc_cat=108&_nc_sid=09cbfe&_nc_ohc=z8iEDkU0SMMAX8nBabO&_nc_ht=scontent-cdg2-1.xx&oh=31fb2cf75caa41ca124516366e45a9a6&oe=5FB4B6DF"));
        contacts.add(new Contact("Antoine","JuniorJunior","https://scontent-cdg2-1.xx.fbcdn.net/v/t1.0-9/69143430_2227297990709372_5993545583606890496_o.jpg?_nc_cat=108&_nc_sid=09cbfe&_nc_ohc=z8iEDkU0SMMAX8nBabO&_nc_ht=scontent-cdg2-1.xx&oh=31fb2cf75caa41ca124516366e45a9a6&oe=5FB4B6DF"));

        ContactAdapter adapter = new ContactAdapter(contacts, this);

        rvContacts.setAdapter(adapter);

        rvContacts.setLayoutManager(new LinearLayoutManager(this));





    }
}