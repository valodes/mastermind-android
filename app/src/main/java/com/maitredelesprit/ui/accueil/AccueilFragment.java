package com.maitredelesprit.ui.accueil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.maitredelesprit.R;
import com.maitredelesprit.databinding.FragmentAccueilBinding;

/**
 * Fragment de l'accueil de l'application
 *
 * @version 1.0
 * @author Valentin HUARD et Maud LEFORT
 */
public class AccueilFragment extends Fragment {

    private FragmentAccueilBinding binding;
    private EditText editTextPseudo;
    private Button boutonSeConnecter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccueilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.editTextPseudo = binding.editTextPseudo;
        this.boutonSeConnecter = binding.boutonSeConnecter;

        this.editTextPseudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //EMPTY
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                verifierLePseudo();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //EMPTY
            }
        });

        this.boutonSeConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String pseudo = editTextPseudo.getText().toString();
                String pseusoChange = pseudo.substring(0, 1).toUpperCase() + pseudo.substring(1).toLowerCase();
                bundle.putString("pseudo", pseusoChange);

                NavHostFragment.findNavController(AccueilFragment.this).navigate(R.id.nav_choix, bundle);
            }
        });
    }

    /**
     * Vérifie si le pseudo est écrit
     * S'il est écrit, le bouton de connexion est activé sinon il est désactivé
     *
     * @return void
     *
     * @version 1.0
     * @author Valentin HUARD et Maud LEFORT
     */
    private void verifierLePseudo() {
        String pseudo = editTextPseudo.getText().toString();
        if (pseudo.isEmpty()) {
            boutonSeConnecter.setEnabled(false);
        } else {
            boutonSeConnecter.setEnabled(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}