package fr.petitpre.martin.examenandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JoueursAdapter extends RecyclerView.Adapter<JoueursAdapter.ViewHolder> {

    private final ListeJoueurs mJoueurs;
    private final Context context;

    public JoueursAdapter(ListeJoueurs j,  Context context1){
        mJoueurs = j;
        this.context = context1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.activity_main, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Joueur j = mJoueurs.getItems().get(position);

        TextView nomJoueurView = holder.nomJoueurTextView;
        nomJoueurView.setText(j.getName());
        j.loadImageIntoImageView(this.context, holder.joueurImageView);
        holder.joueurImageView.setOnClickListener(view -> {
            Intent intent = new Intent(this.context, MainActivity.class);
            //intent.putExtra(Constantes.EXTRA_POKEMON_ID, pokemon.id);
            this.context.startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        return mJoueurs.getItems().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nomJoueurTextView;
        public ImageView joueurImageView;



        public ViewHolder(View itemView){
            super(itemView);

            this.joueurImageView = (ImageView) itemView.findViewById(R.id.joueurImageView);
            this.nomJoueurTextView = (TextView) itemView.findViewById(R.id.nomJoueur);

        }

    }
}
