package com.maitredelesprit.game;

import com.maitredelesprit.game.aides.Aides;
import com.maitredelesprit.game.boules.Boules;

import java.util.HashMap;

/**
 * Classe de gestion de la partie.
 *
 * @extends com.maitredelesprit.game.Grille
 *
 * @author Valentin HUARD et Maud LEFORT
 * @version 1.0
 */
public class Partie extends Grille {

    private Etats etat = Etats.EN_COURS;

    /**
     * Constructeur de la classe Partie.
     *
     * @param nbLignes Le nombre de lignes de la grille. (non-null)
     * @param nbColonnes Le nombre de colonnes de la grille. (non-null)
     */
    public Partie(int nbLignes, int nbColonnes) {
        super(nbLignes, nbColonnes);
    }

    /**
     * Génère les aides de la partie
     *
     * @return void
     */
    public void genererAides() {
        int positionAide = 0;
        for (int secretCol = 0; secretCol < nbColonnes; secretCol++) {
            for (int col = 0; col < nbColonnes; col++) {
                if (boules[0][secretCol].getCouleur() == boules[ligneCourante][col].getCouleur()) { // si la couleur de la boule de la ligne unique est la même que la boule de la ligne courante
                    aides[ligneCourante][positionAide++] = Aides.BONNE_COULEUR; // on crée une aide de type bonne couleur
                    break;
                }
            }
        }

        positionAide = 0;

        for (int col = 0; col < nbColonnes; col++) {
            if (boules[0][col].getCouleur() == boules[ligneCourante][col].getCouleur()) { // si la couleur de la boule de la ligne unique est la même que la boule de la ligne courante
                aides[ligneCourante][positionAide++] = Aides.BONNE_PLACE; // on crée une aide de type bonne place
            }
        }
    }

    /**
     * Méthode permettant de connaître les aides, et les mettres dans un HashMap
     *
     * @param aide Le tableau d'aides à mettre dans le HashMap
     * @param ligne La ligne à laquelle on ajoute les aides
     *
     * @see com.maitredelesprit.game.aides.Aides
     * @see java.util.HashMap
     *
     * @return Le HashMap contenant les aides
     */
    public HashMap getAidesHashMap(Aides[][] aide, int ligne) {
        int couleurs = 0; // nombre de couleurs différentes dans la ligne
        int places = 0; // nombre de places différentes dans la ligne

        for (int col = 0; col < nbColonnes; col++) { // on parcourt les colonnes de la ligne
            couleurs = aide[ligne][col] == Aides.BONNE_COULEUR ? couleurs + 1 : couleurs; // on compte le nombre de couleurs différentes
            places = aide[ligne][col] == Aides.BONNE_PLACE ? places + 1 : places; // on compte le nombre de places différentes
        }

        HashMap<String, Integer> aidesHashMap = new HashMap<String, Integer>(); // on crée un HashMap pour stocker les aides de la ligne courante

        aidesHashMap.put("BONNE_COULEUR", couleurs); // on ajoute le nombre de couleurs différentes à la HashMap
        aidesHashMap.put("BONNE_PLACE", places); // on ajoute le nombre de places différentes à la HashMap

        return aidesHashMap;
    }

    /**
     * Méthode pour changer l'état de la partie en fonction de la ligne courante
     *
     * @return void
     */
    public void changerEtatJeu() {
        etat = Etats.GAGNE;

        for(int col = 0; col < nbColonnes; col++) {
            if(boules[0][col].getCouleur() != boules[ligneCourante][col].getCouleur()) {
                /* si la ligne courante est la première, on change l'état du jeu à perdu sinon
                 on change l'état du jeu à en cours (pour laisser le joueur jouer) et on décrémente
                  la ligne courante pour laisser le joueur jouer une autre ligne
                 */
                etat = ligneCourante -1 > 0 ? Etats.EN_COURS : Etats.PERDU;
                break;
            }
        }
    }

    /**
     * Méthode pour savoir si la ligne courante est remplie
     *
     * @return boolean True si la ligne courante est remplie, false sinon
     */
    public boolean ligneEstRemplie() {
        for(int col = 0; col < nbColonnes; col++) {
            if(boules[ligneCourante][col] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode pour savoir si la ligne est valide (si aucune boule n'est en double)
     *
     * @return boolean True si la ligne est valide, false sinon
     */
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

    /**
     * Méthode pour rejouer une partie
     *
     * @return void
     */
    public void rejouer() {
        etat = Etats.EN_COURS;
        ligneCourante = nbLignes - 1;
        boules = new Boules[nbLignes][nbColonnes];
        aides = new Aides[nbLignes][nbColonnes];

        genererLigneUnique();
    }

    /**
     * Getter pour l'état de la partie
     *
     * @return Etats L'état de la partie
     */
    public Etats getEtat() {
        return etat;
    }
}
