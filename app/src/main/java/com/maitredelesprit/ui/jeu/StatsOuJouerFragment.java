package com.maitredelesprit.ui.jeu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maitredelesprit.R;
import com.maitredelesprit.database.Stats;
import com.maitredelesprit.databinding.FragmentStatsOuJouerBinding;

/**
 * Fragment pour le choix entre stats ou jouer
 *
 * @version 1.0
 * @author Valentin HUARD et Maud LEFORT
 */
public class StatsOuJouerFragment extends Fragment {

    private FragmentStatsOuJouerBinding binding; // Binding de la vue du fragment (fragment_stats_ou_jouer.xml)

    private FirebaseDatabase db; // Déclaration de la base de données Firebase (FirebaseDatabase)

    private static String key = null, pseudo = null; // Pour ajouter un nouveau joueur à la base de données Firebase

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatsOuJouerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FirebaseApp.initializeApp(getContext());

        super.onViewCreated(view, savedInstanceState);

        db = FirebaseDatabase.getInstance(); // Initialisation de la base de données Firebase
        pseudo = getArguments().getString("pseudo");
        binding.pseudo.setText(pseudo);
        ajouterUnJoueur(); // Ajout d'un nouveau joueur à la base de données

        binding.boutonJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changerFragment(R.id.nav_jeu);
            }
        });

        binding.boutonStatistiques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changerFragment(R.id.nav_stats_perso);
            }
        });
    }

    /**
     * Méthode pour passer d'un fragment à un autre en utilisant le NavHostFragment
     *
     * @param idNav Identifiant du fragment à ouvrir
     *
     * @see NavHostFragment
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public void changerFragment(int idNav) {
        Bundle bundle = new Bundle();
        bundle.putString("pseudo", pseudo);

        NavHostFragment.findNavController(StatsOuJouerFragment.this).navigate(idNav, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Ajout d'un nouveau joueur à la base de données Firebase
     *
     * @return void
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    private void ajouterUnJoueur() {
        db.getReference().child("statistiques").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int noInFireBase = 0;

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    if (singleSnapshot.child("pseudo").getValue().toString().equals(pseudo)) {
                        noInFireBase = 1;
                    }
                }

                if (noInFireBase == 0) {
                    long childrenCount = dataSnapshot.getChildrenCount();
                    childrenCount += 1;
                    DatabaseReference mRef = db.getReference().child("statistiques").child(""+childrenCount);
                    mRef.setValue(new Stats(pseudo, 0, 0, 0));

                    key = db.getReference().child("statistiques").push().getKey();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Pas de donnée trouvée dans la base de donnée", Toast.LENGTH_SHORT).show();
            }
        });
    }

}