package com.maitredelesprit.game;

import com.maitredelesprit.game.aides.Aides;
import com.maitredelesprit.game.boules.Boules;

import java.util.HashMap;

/**
 *
 */
public class Partie extends Grille {

    private Etats etat = Etats.EN_COURS;

    public Partie(int nbLignes, int nbColonnes) {
        super(nbLignes, nbColonnes);
    }

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

    public void rejouer() {
        etat = Etats.EN_COURS;
        ligneCourante = nbLignes - 1;
        boules = new Boules[nbLignes][nbColonnes];
        aides = new Aides[nbLignes][nbColonnes];

        genererLigneUnique();
    }

    public Etats getEtat() {
        return etat;
    }
}
