package com.projetpuissance4.views;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Class for the pseudo window
 */
public class PseudoView {
    private TextInputDialog dialog;

    public PseudoView() {
        dialog = null;
    }

    /**
     * Pseudo
     * @brief Choose the pseudo for the two players
     * @param numerojoueur
     * @return
     */
    public String Pseudo(int numerojoueur)
    {
        dialog = new TextInputDialog();
        dialog.setTitle("Pseudo");
        dialog.setHeaderText("Pseudo Joueur " + numerojoueur + " :");
        dialog.setContentText("Pseudo :");
        Optional<String> result = dialog.showAndWait();
        String name = result.get();
        return name;
    }
}
