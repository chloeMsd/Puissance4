package com.projetpuissance4.views;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class PseudoView {
    private TextInputDialog dialog;

    public PseudoView() {
        dialog = null;
    }
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
