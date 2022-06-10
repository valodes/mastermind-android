package com.maitredelesprit.game;

import android.util.Log;

import com.maitredelesprit.game.aides.Aides;
import com.maitredelesprit.game.boules.Boules;
import com.maitredelesprit.game.boules.Couleurs;

public class Grille
{
    protected final int nbLignes;
    protected final int nbColonnes;
    protected Boules[][] boules;
    protected Aides[][] aides;
    protected int ligneCourante;

    public Grille(int nbLignes, int nbColonnes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.ligneCourante = nbLignes-1;

        boules = new Boules[nbLignes][nbColonnes];
        aides = new Aides[nbLignes][nbColonnes];

        genererLigneUnique(); // la ligne de départ est générée aléatoirement
    }

    /**
     * Génère une ligne de boules aléatoirement
     *
     * @return void
     */
    protected void genererLigneUnique() {
        for (int col = 0; col < nbColonnes; col++) {
            do {
                boules[0][col] = new Boules(Couleurs.getRandomCouleur());
            } while (!couleurUnique(col));

            Log.d(" -------------------------- ", "Col " + col + " : " + boules[0][col].getCouleur());
        }
    }

    public boolean couleurUnique(int col) {
        boolean unique = true;

        for (int i = 0; i < col; i++) {
            if (boules[0][i].getCouleur() == boules[0][col].getCouleur()) {
                unique = false;
            }
        }
        return unique;
    }

    public boolean ligneEstRemplie() {
        for(int col = 0; col < nbColonnes; col++) {
            if(boules[ligneCourante][col] == null) {
                return false;
            }
        }
        return true;
    }

    public boolean ligneEstValide() {
        for(int col = 0; col < nbColonnes; col++) {
            for(int col1 = 0; col1 < nbColonnes; col1++) {
                if (boules[ligneCourante][col].getCouleur() == boules[ligneCourante][col1].getCouleur() && col != col1) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getLigneCourante() {
        return ligneCourante;
    }

    public void ligneSuivante() {
        ligneCourante--;
    }

    public void setBoules(int nbColonnes, Couleurs couleur) {
        boules[ligneCourante][nbColonnes - 1] = new Boules(couleur);
    }

    public Boules getBoule(int ligne, int colonne) {
        return boules[ligne][colonne];
    }

    public Aides[][] getAide() {
        return aides;
    }
}
