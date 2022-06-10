package com.maitredelesprit.game.boules;

public class Boules {

    /**
     * Couleurs des boules
     */
    private Couleurs couleur;

    /**
     * Constructeur de la classe Boules qui initialise la couleur de la boule
     *
     * @param couleur Couleur de la boule (ROUGE, BLEU, VERT, JAUNE, ORANGE, VIOLET)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public Boules(Couleurs couleur) {
        this.couleur = couleur;
    }

    /**
     * Retourne la couleur de la boule (ROUGE, BLEU, VERT, JAUNE, ORANGE, VIOLET)
     *
     * @return Couleur de la boule
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public Couleurs getCouleur() {
        return couleur;
    }
}
