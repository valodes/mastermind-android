package com.maitredelesprit.ui.jeu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maitredelesprit.R;
import com.maitredelesprit.database.Stats;
import com.maitredelesprit.databinding.FragmentJeuBinding;
import com.maitredelesprit.game.Etats;
import com.maitredelesprit.game.Partie;
import com.maitredelesprit.game.aides.Aides;
import com.maitredelesprit.game.aides.TexteAide;
import com.maitredelesprit.game.boules.Boules;
import com.maitredelesprit.game.boules.BoutonBoules;
import com.maitredelesprit.game.boules.Couleurs;

import java.util.HashMap;
import java.util.Map;

/**
 * Fragment qui affiche le jeu et gère les interactions avec l'utilisateur
 *
 * @version 1.0
 * @author Valentin HUARD et Maud LEFORT
 */
public class JeuFragment extends Fragment {

    private FragmentJeuBinding binding; //Binding de la vue du fragment Jeu

    private Partie partie; //Grille de jeu (tableau de tableaux de cases)
    private FirebaseDatabase db; // Déclaration de la base de données Firebase (pour les stats)

    private int toutesLesLignes, toutesLesColonnes; // nombre de lignes et de colonnes de la grille de jeu

    private static String key = null, pseudo = null; //Pour la sauvegarde de la partie en cours (si on veut la reprendre)
    private static long perdu = 0, gagne = 0, ratio = 0; //Pour la sauvegarde de la partie (pour le score)

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJeuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FirebaseApp.initializeApp(getContext()); // Initialise Firebase App (if not already done)

        super.onViewCreated(view, savedInstanceState);

        db = FirebaseDatabase.getInstance(); // Initialise Firebase Database

        pseudo = getArguments().getString("pseudo");
        binding.pseudo.setText(pseudo);
        ajouterDonnees(); //Ajoute les données du joeur à Firebase

        this.toutesLesLignes = 8; // Nombre de lignes de la grille
        this.toutesLesColonnes = 4; // Nombre de colonnes de la grille

        this.toutesLesLignes += 1; // Ajoute une ligne pour la ligne finale

        this.partie = new Partie(this.toutesLesLignes, this.toutesLesColonnes); // Initialise la grille

        grille();

        binding.boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerLigne();
            }
        });

        binding.boutonRejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejouer();
            }
        });

    }

    /**
     * Méthode qui permet de valider une ligne
     *
     * @see Partie
     * @see #finirJeu()
     *
     * @return void
     */
    public void validerLigne() {
        switch (partie.getEtat()) {
            case EN_COURS:
                if(partie.ligneEstRemplie() && partie.ligneEstValide()) {
                    partie.changerEtatJeu();
                    partie.genererAides();
                    partie.ligneSuivante();

                    if(partie.getEtat() != Etats.EN_COURS) {
                        validerLigne();
                    }
                } else if (!partie.ligneEstRemplie()){
                    Toast.makeText(getContext(), R.string.ligne_incomplete_msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.ligne_invalide_msg, Toast.LENGTH_SHORT).show();
                }

                break;
            case GAGNE:
                Toast.makeText(getContext(), R.string.gagne_msg, Toast.LENGTH_SHORT).show();
                gagne++;
                finirJeu();
                break;
            case PERDU:
                Toast.makeText(getContext(), R.string.perdu_msg, Toast.LENGTH_SHORT).show();
                perdu++;
                finirJeu();
                break;
            default:
                break;
        }

        grille();
    }

    /**
     * Méthode qui permet de finir le jeu (sauvegarder les stats)
     *
     * @see #donneeVersFirebase()
     *
     * @return void
     */
    public void finirJeu() {
        ratio = gagne / (gagne + perdu);
        donneeVersFirebase();
        binding.boutonValider.setEnabled(false);
        binding.boutonRejouer.setEnabled(true);
    }

    /**
     * Méthode qui permet de rejouer
     *
     * @return void
     */
    private void rejouer() {
        binding.boutonValider.setEnabled(true);
        binding.boutonRejouer.setEnabled(false);
        partie.rejouer();
        grille();
    }

    /**
     * Affiche la grille de jeu dans la vue du fragment Jeu
     *
     * @version 1.0
     *
     * @see Partie
     * @see TableLayout
     * @see TableRow
     * @see TextView
     *
     * @return void
     */
    public void grille() {
        TableLayout table = binding.tableauJeu;
        table.removeAllViews();

        for(int ligne = 0; ligne < toutesLesLignes; ligne++) {
            TableRow ligneTable = new TableRow(getContext());
            table.addView(ligneTable);

            HashMap aides = partie.getAidesHashMap(partie.getAide(), ligne); // Récupère les aides de la ligne en cours de traitement

            TexteAide texteAidePlace = new TexteAide(getContext(), aides.get("BONNE_PLACE"), Aides.BONNE_PLACE); // Bouton Aide Bonne Place (ligne)
            ligneTable.addView(texteAidePlace); // Ajoute le bouton d'aide à la ligne en cours de traitement

            for (int colonne = 0; colonne < toutesLesColonnes; colonne++) {
                Boules boule = partie.getBoule(ligne, colonne); // Récupère la boule de la ligne et de la colonne en cours de traitement
                int couleur = ContextCompat.getColor(getContext(), R.color.md_theme_secondary); // Récupère la couleur de la boule en cours de traitement

                if(ligne == 0) { // Si c'est la première ligne
                    if(partie.getEtat() == Etats.EN_COURS) { // Si le jeu n'est pas terminé
                        couleur = ContextCompat.getColor(getContext(), R.color.md_theme_secondary); // Couleur de la boule en cours de traitement (grisée)
                    } else { // Si le jeu est terminé
                        couleur = ContextCompat.getColor(getContext(), boule.getCouleur().getCouleur()); // Couleur de la boule en cours de traitement (couleur de la boule)
                    }
                } else if (boule != null) { // Si la boule n'est pas null (si la ligne n'est pas la première)
                    couleur = ContextCompat.getColor(getContext(), boule.getCouleur().getCouleur()); // Couleur de la boule en cours de traitement (couleur de la boule)
                }

                BoutonBoules boutonBoule = new BoutonBoules(getContext(), couleur); // Bouton Boule (ligne, colonne) (couleur)
                ligneTable.addView(boutonBoule); // Ajoute le bouton à la ligne en cours de traitement

                if(ligne == 0) {
                    boutonBoule.setTextColor(ContextCompat.getColor(getContext(), R.color.md_theme_onSecondary)); // Couleur du texte de la boule en cours de traitement (grisée)
                    boutonBoule.setText("?"); // Texte de la boule en cours de traitement (?)
                }

                final int LIGNE_POSITION = ligne, COLONNE_POSITION = colonne; // Position de la ligne et de la colonne en cours de traitement
                if (partie.getLigneCourante() == ligne) { // Si la ligne en cours de traitement est la ligne en cours de traitement
                    boutonBoule.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(partie.getEtat() == Etats.EN_COURS) { // Si le jeu n'est pas terminé
                                Boules boules = partie.getBoule(LIGNE_POSITION, COLONNE_POSITION); // Récupère la boule de la ligne et de la colonne en cours de traitement
                                Couleurs nouvelleCouleur = Couleurs.getNextCouleur(boules); // Récupère la couleur suivante de la boule en cours de traitement
                                partie.setBoules(COLONNE_POSITION + 1, nouvelleCouleur); // Change la couleur de la boule en cours de traitement (colonne + 1)
                            }
                            grille(); // Liste la grille
                        }
                    });
                }
            }

            TexteAide texteAideCouleur = new TexteAide(getContext(), aides.get("BONNE_COULEUR"), Aides.BONNE_COULEUR); // Bouton Aide Bonne Couleur (ligne)
            ligneTable.addView(texteAideCouleur); // Ajoute le bouton d'aide à la ligne en cours de traitement

            if(ligne <= partie.getLigneCourante()) { // Si la ligne en cours de traitement est la ligne en cours de traitement
                texteAidePlace.setVisibility(View.INVISIBLE); // Cache le bouton d'aide de la ligne en cours de traitement
                texteAideCouleur.setVisibility(View.INVISIBLE); // Cache le bouton d'aide de la ligne en cours de traitement
            }
        }
    }

    /**
     * Méthode qui permet de donner les données à Firebase pour enregistrer la partie
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    private void ajouterDonnees() {
        db.getReference().child("statistiques").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    if (singleSnapshot.child("pseudo").getValue().toString().equals(pseudo)) {
                        gagne = (long) singleSnapshot.child("gagne").getValue();
                        perdu = (long) singleSnapshot.child("perdu").getValue();
                        ratio = (long) singleSnapshot.child("ratio").getValue();
                        key = singleSnapshot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Pas de donnée trouvée dans la base de donnée", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Méthode qui permet de mettre à jour les stats dans la base de données Firebase
     *
     * @link https://firebase.google.com/docs/database/android/read-and-write
     *
     * @return void
     */
    private void donneeVersFirebase() {
        Stats stats = new Stats(pseudo, gagne, perdu, ratio);
        Map<String, Object> data = stats.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/statistiques/" + key, data);

        db.getReference().updateChildren(childUpdates);
    }

    /**
     * Méthode pour détruire le fragment
     *
     * @return void
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}