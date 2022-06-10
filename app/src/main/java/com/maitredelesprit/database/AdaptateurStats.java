package com.maitredelesprit.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maitredelesprit.R;

import java.util.ArrayList;

/**
 * RecyclerView pour l'affichage des statistiques du jeu
 *
 * @link https://developer.android.com/guide/topics/ui/layout/recyclerview
 *
 * @version 1.0
 * @author Valentin HUARD et Maud LEFORT
 */
public class AdaptateurStats extends RecyclerView.Adapter<AdaptateurStats.ViewHolder> {

    private ArrayList<Stats> listeStats; // Liste des stats à afficher dans la liste
    private Context context; // Contexte de l'application

    /**
     * Constructeur de l'adaptateur
     *
     * @param listeStats Liste des stats à afficher dans la liste
     * @param context Contexte de l'application
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public AdaptateurStats(ArrayList<Stats> listeStats, Context context) {
        this.listeStats = listeStats;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptateurStats.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdaptateurStats.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_stats, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptateurStats.ViewHolder holder, int position) {
        Stats stats = listeStats.get(position);
        holder.pseudo.setText(stats.getPseudo());
        holder.gagne.setText(String.valueOf(stats.getGagne()));
        holder.perdu.setText(String.valueOf(stats.getPerdu()));
        holder.classement.setText(String.valueOf(position+1)); // On commence à 1
        holder.ratio.setText(String.valueOf(stats.getRatio()));
    }

    /**
     * Retourne le nombre d'éléments dans la liste des stats
     *
     * @return int Nombre d'éléments dans la liste des stats
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    @Override
    public int getItemCount() {
        return listeStats.size();
    }

    /**
     * Classe pour l'affichage des éléments dans la liste des stats (ViewHolder)
     *
     * @see ViewHolder
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView classement, pseudo, gagne, perdu, ratio;

        /**
         * Constructeur de la classe ViewHolder pour l'affichage des éléments dans la liste des stats
         *
         * @param itemView View de l'élément à afficher dans la liste des stats
         *
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            classement = itemView.findViewById(R.id.classement);
            pseudo = itemView.findViewById(R.id.pseudo);
            gagne = itemView.findViewById(R.id.gagne);
            perdu = itemView.findViewById(R.id.perdu);
            ratio = itemView.findViewById(R.id.ratio);
        }
    }

}
