package com.maitredelesprit.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maitredelesprit.database.AdaptateurStats;
import com.maitredelesprit.database.Stats;
import com.maitredelesprit.databinding.FragmentStatsBinding;

import java.util.ArrayList;
import java.util.Collections;

public class StatsFragment extends Fragment {

    private FragmentStatsBinding binding;

    private AdaptateurStats adapter;
    private ArrayList<Stats> statsArrayList;
    private FirebaseDatabase db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FirebaseApp.initializeApp(getContext());
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseDatabase.getInstance();
        statsArrayList = new ArrayList<>();
        binding.listeStats.setHasFixedSize(true);
        binding.listeStats.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AdaptateurStats(statsArrayList, getContext());
        binding.listeStats.setAdapter(adapter);

        // Rechercher toutes les statistiques dans la base de données, ordonnées par ratio
        db.getReference("statistiques").orderByChild("ratio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binding.idProgressBar.setVisibility(View.GONE);

                statsArrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Stats stats = snapshot.getValue(Stats.class);
                    statsArrayList.add(stats);
                    //Inversion de la liste pour avoir les meilleurs ratio en premier
                    Collections.reverse(statsArrayList);
                }

                adapter.notifyDataSetChanged();
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