package com.projetpuissance4.views;

import com.projetpuissance4.models.Options;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.projetpuissance4.models.Options.*;

public class OptionView {
    private ChoiceDialog<String> dialog;
    private List<String> options;
    public OptionView() {
        dialog = null;
    }
    public String GameOptions()
    {
        options = new ArrayList<>();

        for (Options option : Options.values()) {
            options.add(option.getOption());
        }

        dialog = new ChoiceDialog<String>(options.get(0),options);
        dialog.setTitle("Sélectionnez une option de jeu");
        dialog.setHeaderText("Choisissez une option :");
        dialog.setContentText("Option :");

        Optional<String> result = dialog.showAndWait();
        String name = result.get();
        return name;
    }
}
