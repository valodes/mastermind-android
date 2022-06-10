package com.maitredelesprit.game.boules;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;

import com.maitredelesprit.R;

/**
 * Classe permettant de créer un bouton de type couleur.
 *
 * @author Valentin HUARD et Maud LEFORT
 */
public class BoutonBoules extends AppCompatButton {

    /**
     * Constructeur de la classe.
     *
     * @param context Le contexte de l'application. (non-null)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public BoutonBoules(Context context) {
        super(context);
    }

    /**
     * Constructeur de la classe. Initialise le bouton avec la couleur donnée en paramètre.
     *
     * @param context Le contexte de l'application. (non-null)
     * @param color La couleur du bouton. (non-null)
     *
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public BoutonBoules(Context context, int color) {
        super(context);
        setGradientColor(color);
    }

    public void setGradientColor(int color) {
        GradientDrawable gradient = new GradientDrawable();
        gradient.setCornerRadius(999f);
        gradient.setColor(color);
        setBackground(gradient);
    }

    /**
     * Attache le bouton à la vue parente. Ajoute un élément de marge autour du bouton.
     *
     * @see #onAttachedToWindow()
     * @see #setElevation(float)
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        setElevation(3f);

        final int BOULE_RADIUS = getResources().getInteger(R.integer.rayon_boule);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = BOULE_RADIUS;
        params.height = BOULE_RADIUS;
        setLayoutParams(params);

        final int BOULE_MARGE = getResources().getInteger(R.integer.marge_boule);

        if(this.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) this.getLayoutParams();
            marginParams.setMargins(BOULE_MARGE, BOULE_MARGE, BOULE_MARGE, BOULE_MARGE);
            this.requestLayout(); // Appel de la méthode requestLayout() pour forcer le redessin du bouton.
        }
    }

}
