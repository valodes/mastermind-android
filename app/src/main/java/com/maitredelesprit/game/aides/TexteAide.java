package com.maitredelesprit.game.aides;

import android.content.Context;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;

import com.maitredelesprit.R;

/**
 * Classe permettant de créer un texte d'aide pour le jeu.
 *
 * @version 1.0
 * @author Valentin HUARD et Maud LEFORT
 */
public class TexteAide extends AppCompatTextView {

    /**
     * Constructeur de la classe BoutonAide.
     *
     * @param context Le contexte de l'application. (non-null)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public TexteAide(Context context) {
        super(context);
    }

    /**
     * Constructeur de la classe BoutonAide.
     *
     * @param context Le contexte de l'application. (non-null)
     * @param devinee L'objet devinee. (non-null)
     * @param aide L'aide à afficher. (non-null)
     *
     * @see #setText(int)
     * @see #setBackgroundResource(int)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public TexteAide(Context context, Object devinee, Aides aide) {
        super(context);

        int stringId = 0;

        switch (aide) {
            case BONNE_COULEUR:
                stringId = R.string.bonne_couleur;
                break;
            case BONNE_PLACE:
                stringId = R.string.bonne_place;
                break;
        }

        setText(getResources().getString(stringId, devinee));
    }

    /**
     * Attache le bouton à la vue parente.
     * Ajoute un élément de marge autour du bouton.
     *
     * @see #onAttachedToWindow()
     * @see #setElevation(float)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = getResources().getInteger(R.integer.largeur_aide);
        params.height = getResources().getInteger(R.integer.hauteur_aide);
        setLayoutParams(params);

        setTextAlignment(TEXT_ALIGNMENT_CENTER);

        final int AIDE_MARGE = getResources().getInteger(R.integer.marge_aide);

        if(this.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) this.getLayoutParams();
            marginParams.setMargins(AIDE_MARGE, AIDE_MARGE, AIDE_MARGE, AIDE_MARGE);
        }
    }
}
