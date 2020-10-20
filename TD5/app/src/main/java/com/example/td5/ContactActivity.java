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
        contacts.add(new Contact("Antoine","Paul","https://www.google.com/url?sa=i&url=https%3A%2F%2Ffr.wikipedia.org%2Fwiki%2FMaurice_Landrieu&psig=AOvVaw0crhvcIHy0p0aT7za1yWk0&ust=1603284357164000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJDKye2Zw-wCFQAAAAAdAAAAABAD"));
        contacts.add(new Contact("Antoine","Jacques","https://www.google.com/url?sa=i&url=https%3A%2F%2Ffr.wikipedia.org%2Fwiki%2FMaurice_Landrieu&psig=AOvVaw0crhvcIHy0p0aT7za1yWk0&ust=1603284357164000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJDKye2Zw-wCFQAAAAAdAAAAABAD"));
        contacts.add(new Contact("Antoine","Manga","https://www.google.com/url?sa=i&url=https%3A%2F%2Ffr.wikipedia.org%2Fwiki%2FMaurice_Landrieu&psig=AOvVaw0crhvcIHy0p0aT7za1yWk0&ust=1603284357164000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJDKye2Zw-wCFQAAAAAdAAAAABAD"));
        contacts.add(new Contact("Antoine","Thomas","https://www.google.com/url?sa=i&url=https%3A%2F%2Ffr.wikipedia.org%2Fwiki%2FMaurice_Landrieu&psig=AOvVaw0crhvcIHy0p0aT7za1yWk0&ust=1603284357164000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJDKye2Zw-wCFQAAAAAdAAAAABAD"));
        contacts.add(new Contact("Antoine","Junior","https://www.google.com/url?sa=i&url=https%3A%2F%2Ffr.wikipedia.org%2Fwiki%2FMaurice_Landrieu&psig=AOvVaw0crhvcIHy0p0aT7za1yWk0&ust=1603284357164000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJDKye2Zw-wCFQAAAAAdAAAAABAD"));
        contacts.add(new Contact("Antoine","JuniorJunior","https://www.google.com/url?sa=i&url=https%3A%2F%2Ffr.wikipedia.org%2Fwiki%2FMaurice_Landrieu&psig=AOvVaw0crhvcIHy0p0aT7za1yWk0&ust=1603284357164000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJDKye2Zw-wCFQAAAAAdAAAAABAD"));

        ContactAdapter adapter = new ContactAdapter(contacts, this);

        rvContacts.setAdapter(adapter);

        rvContacts.setLayoutManager(new LinearLayoutManager(this));





    }
}