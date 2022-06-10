package com.maitredelesprit.ui.stats;

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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maitredelesprit.R;
import com.maitredelesprit.database.Stats;
import com.maitredelesprit.databinding.FragmentStatsPersoBinding;
import com.maitredelesprit.ui.jeu.StatsOuJouerFragment;

/**
 * Fragment des statistiques personnelles de l'utilisateur (nombre de parties gagnées, etc.)
 *
 * @version 1.0
 * @author Valentin HUARD et Maud LEFORT
 */
public class StatsPersoFragment extends Fragment {

    private FragmentStatsPersoBinding binding; //Binding de la vue du fragment (fragment_stats_perso.xml)
    private FirebaseDatabase db; //Base de données Firebase (FirebaseDatabase)

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatsPersoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FirebaseApp.initializeApp(getContext());

        super.onViewCreated(view, savedInstanceState);

        String pseudo = getArguments().getString("pseudo");
        binding.pseudo.setText(pseudo);

        db = FirebaseDatabase.getInstance();

        db.getReference("statistiques").orderByChild("ratio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.child("pseudo").getValue().equals(pseudo)) {
                        Stats stats = snapshot.getValue(Stats.class);
                        binding.ratio.setText(String.valueOf(stats.getRatio()));
                        binding.gagne.setText(String.valueOf(stats.getGagne()));
                        binding.perdu.setText(String.valueOf(stats.getPerdu()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Erreur lors de la récupération des statistiques", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}