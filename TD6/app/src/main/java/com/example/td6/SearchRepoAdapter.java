package com.example.td6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class SearchRepoAdapter extends RecyclerView.Adapter<SearchRepoAdapter.ViewHolder> {

    private final Repos mRepos;
    private final Context context;

    public SearchRepoAdapter(Repos repos,  Context context1){
        mRepos = repos;
        this.context = context1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.activity_search_repo, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Repo repo = mRepos.getItems().get(position);

        TextView nomRepoView = holder.nomRepoTextView;
        nomRepoView.setText(repo.getFull_name());



    }

    @Override
    public int getItemCount() {
        return mRepos.getItems().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nomRepoTextView;


        public ViewHolder(View itemView){
            super(itemView);

            nomRepoTextView = (TextView) itemView.findViewById(R.id.nomRepo);

        }

    }


}
