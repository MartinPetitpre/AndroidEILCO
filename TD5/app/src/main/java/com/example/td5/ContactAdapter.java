package com.example.td5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final List<Contact> mContacts;
    private final Context context;

    public ContactAdapter(List<Contact> contacts,  Context context1){
        mContacts = contacts;
        this.context = context1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);

        TextView firstNameView = holder.firstNameTextView;
        firstNameView.setText(contact.getPrenom());

        TextView lastNameView = holder.lastNameTextView;
        lastNameView.setText(contact.getNom());

        ImageView imageUrl = holder.imageUrl;
        Glide.with(this.context).load(contact.getImageUrl()).into(imageUrl);


    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView firstNameTextView;
        public TextView lastNameTextView;
        public ImageView imageUrl;

        public ViewHolder(View itemView){
            super(itemView);

            firstNameTextView = (TextView) itemView.findViewById(R.id.viewPrenom);
            lastNameTextView = (TextView) itemView.findViewById(R.id.viewNom);
            imageUrl = (ImageView) itemView.findViewById(R.id.imageView2);
        }

    }


}
