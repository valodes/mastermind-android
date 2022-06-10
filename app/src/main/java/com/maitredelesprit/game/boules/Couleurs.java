package com.maitredelesprit.game.boules;


import com.maitredelesprit.R;


/**
 * Enumération des couleurs des boules du jeu.
 *
 * @version 1.0
 * @author Valentin HUARD et Maud LEFORT
 */
public enum Couleurs {

    ROUGE (R.color.rouge),
    BLEU (R.color.bleu),
    VERT (R.color.vert),
    MARRON (R.color.marron),
    BLEUCLAIR (R.color.bleuClair),
    VIOLET (R.color.violet);

    private int couleur;

    Couleurs(int couleur) {
        this.couleur = couleur;
    }

    public int getCouleur() {
        return couleur;
    }

    /**
     * Génère une couleur aléatoire parmi les couleurs disponibles.
     *
     * @return La couleur aléatoire.
     */
    public static Couleurs getRandomCouleur() {
        int random = (int) (Math.random() * Couleurs.values().length);
        return Couleurs.values()[random];
    }

    public static Couleurs getNextCouleur(Boules boule) {
        Couleurs valeur = null;

        if (boule == null || boule.getCouleur().ordinal() == values().length - 1) {
            valeur = values()[0];
        } else {
            valeur = values()[boule.getCouleur().ordinal() + 1];
        }

        return valeur;
    }
}
