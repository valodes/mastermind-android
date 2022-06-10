package com.maitredelesprit.game;

import android.util.Log;

import com.maitredelesprit.game.aides.Aides;
import com.maitredelesprit.game.boules.Boules;
import com.maitredelesprit.game.boules.Couleurs;

/**
 * Classe permettant de faire la gestion de la grille de jeu.
 *
 * @author Maud LEFORT
 * @version 1.0
 */
public class Grille
{
    protected final int nbLignes;
    protected final int nbColonnes;
    protected Boules[][] boules;
    protected Aides[][] aides;
    protected int ligneCourante;

    /**
     * Constructeur de la classe Grille.
     *
     * @param nbLignes Le nombre de lignes de la grille. (non-null)
     * @param nbColonnes Le nombre de colonnes de la grille. (non-null)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public Grille(int nbLignes, int nbColonnes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.ligneCourante = nbLignes-1;

        boules = new Boules[nbLignes][nbColonnes];
        aides = new Aides[nbLignes][nbColonnes];

        genererLigneUnique(); // la ligne de départ est générée aléatoirement
    }

    /**
     * Génère une ligne de boules uniques aléatoirement
     *
     * @return void
     */
    protected void genererLigneUnique() {
        for (int col = 0; col < nbColonnes; col++) {
            do {
                boules[0][col] = new Boules(Couleurs.getRandomCouleur());
            } while (!couleurUnique(col));

            Log.d(" -------------------------- ", "Col " + col + " : " + boules[0][col].getCouleur()); // DEBUG : affiche les couleurs gagnantes
        }
    }

    /**
     * Vérifie si la colonne donnée en paramètre contient une couleur unique ou non.
     *
     * @param col La colonne à vérifier. (non-null)
     *
     * @return true si la couleur est unique, false sinon.
     */
    public boolean couleurUnique(int col) {
        boolean unique = true;

        for (int i = 0; i < col; i++) {
            if (boules[0][i].getCouleur() == boules[0][col].getCouleur()) {
                unique = false;
            }
        }
        return unique;
    }

    /**
     * Retourne la ligne courante.
     *
     * @return Un entier représentant la ligne courante.
     */
    public int getLigneCourante() {
        return ligneCourante;
    }

    /**
     * Permet de passer à la ligne suivante.
     *
     * @return void
     */
    public void ligneSuivante() {
        ligneCourante--;
    }

    /**
     * Permet de créer une ligne de boules à partir de la ligne courante.
     *
     * @param nbColonnes Le nombre de colonnes à créer. (non-null)
     * @param couleur La couleur de la boule. (non-null)
     *
     * @return void
     */
    public void setBoules(int nbColonnes, Couleurs couleur) {
        boules[ligneCourante][nbColonnes - 1] = new Boules(couleur);
    }

    /**
     * Getter de la grille de boules.
     *
     * @param ligne La ligne ou on veut récupérer la boule. (non-null)
     * @param colonne La colonne ou on veut récupérer la boule. (non-null)
     *
     * @return La boule retournée.
     */
    public Boules getBoule(int ligne, int colonne) {
        return boules[ligne][colonne];
    }

    /**
     * Getter de la grille d'aides.
     *
     * @return La grille d'aides.
     */
    public Aides[][] getAide() {
        return aides;
    }
}
