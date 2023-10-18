package com.projetpuissance4.views;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class PseudoView {
    private TextInputDialog dialog = new TextInputDialog();

    public PseudoView() {
        dialog = null;
    }
    public String Pseudo()
    {
        dialog = new TextInputDialog();
        dialog.setTitle("Pseudo");
        dialog.setHeaderText("Veuillez saisir votre pseudo :");
        dialog.setContentText("Pseudo :");
        Optional<String> result = dialog.showAndWait();
        String name = result.get();
        return name;
    }
}
