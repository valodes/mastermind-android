package com.maitredelesprit.database;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe permettant de stocker les statistiques du jeu.
 * Cette classe est utilisée pour stocker les statistiques du jeu dans la base de données Firebase.
 *
 * @version 1.0
 * @author Valentin HUARD et Maud LEFORT
 */
public class Stats {

    private String pseudo;
    private long perdu; // Nombre de parties perdues
    private long gagne; // Nombre de parties gagnées
    private long ratio; // Ratio de victoire

    /**
     * Constructeur par défaut de la classe.
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public Stats() {
    }

    /**
     * Constructeur de la classe Stats qui initialise les statistiques du jeu.
     *
     * @param pseudo Pseudo du joueur. (non-null)
     * @param perdu Nombre de parties perdues. (non-null)
     * @param gagne Nombre de parties gagnées. (non-null)
     * @param ratio Ratio de victoire. (non-null)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public Stats(String pseudo, long gagne, long perdu, long ratio) {
        this.pseudo = pseudo;
        this.perdu = perdu;
        this.gagne = gagne;
        this.ratio = ratio;
    }

    /**
     * Transforme les statistiques du jeu en Map.
     *
     * @return Map contenant les statistiques du jeu. (non-null)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pseudo", pseudo);
        result.put("perdu", perdu);
        result.put("gagne", gagne);
        result.put("ratio", ratio);

        return result;
    }

    /**
     * Retourne le pseudo du joueur.
     *
     * @return Pseudo du joueur. (non-null)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Retourne le nombre de parties perdues.
     *
     * @return Nombre de parties perdues. (non-null)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public long getPerdu() {
        return perdu;
    }

    /**
     * Retourne le nombre de parties gagnées.
     *
     * @return Nombre de parties gagnées. (non-null)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public long getGagne() {
        return gagne;
    }

    /**
     * Retourne le ratio de victoire.
     *
     * @return Ratio de victoire. (non-null)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public long getRatio() {
        return ratio;
    }
}
