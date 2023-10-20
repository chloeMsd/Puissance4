package com.projetpuissance4.controllers;

import com.projetpuissance4.Puissance4;
import com.projetpuissance4.models.Options;
import com.projetpuissance4.models.P4;
import com.projetpuissance4.views.OptionView;
import com.projetpuissance4.views.PseudoView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Puissance4Controller {
    @FXML
    private AnchorPane myAnchorPane;
    private P4 Grille = new P4();
    private boolean isRunning = true;
    private Button invisibleButtonColumn1;
    private Button invisibleButtonColumn2;
    private Button invisibleButtonColumn3;
    private Button invisibleButtonColumn4;
    private Button invisibleButtonColumn5;
    private Button invisibleButtonColumn6;
    private Button invisibleButtonColumn7;
    private static int whoPlay = 0;

    public void initialize() {
        double initialX = 150;
        double initialY = 93.0;
        double imageWidth = 100.0;
        double imageHeight = 100.0;

        invisibleButtonColumn1 = CreationInvisibleButton(1);
        invisibleButtonColumn2 = CreationInvisibleButton(2);
        invisibleButtonColumn3 = CreationInvisibleButton(3);
        invisibleButtonColumn4 = CreationInvisibleButton(4);
        invisibleButtonColumn5 = CreationInvisibleButton(5);
        invisibleButtonColumn6 = CreationInvisibleButton(6);
        invisibleButtonColumn7 = CreationInvisibleButton(7);


        Polygon triangle1 = CreateTriangle(1);
        Polygon triangle2 = CreateTriangle(2);
        Polygon triangle3 = CreateTriangle(3);
        Polygon triangle4 = CreateTriangle(4);
        Polygon triangle5 = CreateTriangle(5);
        Polygon triangle6 = CreateTriangle(6);
        Polygon triangle7 = CreateTriangle(7);


        CursorAppear(invisibleButtonColumn1, triangle1);
        CursorAppear(invisibleButtonColumn2, triangle2);
        CursorAppear(invisibleButtonColumn3, triangle3);
        CursorAppear(invisibleButtonColumn4, triangle4);
        CursorAppear(invisibleButtonColumn5, triangle5);
        CursorAppear(invisibleButtonColumn6, triangle6);
        CursorAppear(invisibleButtonColumn7, triangle7);


        invisibleButtonColumn1.setOnAction(event -> ButtonPlay(1));
        invisibleButtonColumn2.setOnAction(event -> ButtonPlay(2));
        invisibleButtonColumn3.setOnAction(event -> ButtonPlay(3));
        invisibleButtonColumn4.setOnAction(event -> ButtonPlay(4));
        invisibleButtonColumn5.setOnAction(event -> ButtonPlay(5));
        invisibleButtonColumn6.setOnAction(event -> ButtonPlay(6));
        invisibleButtonColumn7.setOnAction(event -> ButtonPlay(7));

        PrintGameOption();
        //invisibleButtonColumn1.setOnAction(event -> PrintWon());
        AfficherPseudoJoueur1(SaisirPseudo(1));
        AfficherPseudoJoueur2(SaisirPseudo(2));

    }
    private void ButtonPlay(int iButton)
    {
        if(whoPlay % 2 == 0){
            AddRedToken(CreationRedToken(100,100),iButton,6-Grille.checkGraviter(iButton-1));
            Grille.setMatValeur(iButton-1,1);
        }
        else {
            AddYellowToken(CreationYellowToken(100,100),iButton,6-Grille.checkGraviter(iButton-1));
            Grille.setMatValeur(iButton-1,2);
        }
        whoPlay++;
        System.out.println(Grille.toString());
    }
    private Button CreationInvisibleButton(int column)
    {
        Button invisibleButton = new Button();
        invisibleButton.setOpacity(0);
        invisibleButton.setPrefWidth(100);
        invisibleButton.setPrefHeight(628);
        invisibleButton.setLayoutX(150 + (column-1) * 100);
        invisibleButton.setLayoutY(75);
        myAnchorPane.getChildren().add(invisibleButton);
        return invisibleButton;
    }

    private ImageView CreationRedToken(double width, double height)
    {
        Image image = new Image(Puissance4.class.getResourceAsStream("RedToken.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    private void playergame()
    {
        System.out.println("oipzdnziond");
    }
    private ImageView CreationYellowToken(double width, double height)
    {
        Image image = new Image(Puissance4.class.getResourceAsStream("YellowToken.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    private void AddRedToken(ImageView jetonRouge, int column, int line)
    {
        AnchorPane.setLeftAnchor(jetonRouge, 150 + (column-1) * jetonRouge.getFitHeight());
        AnchorPane.setTopAnchor(jetonRouge, 593 - (line-1) * jetonRouge.getFitWidth());
        myAnchorPane.getChildren().add(jetonRouge);
    }

    private void AddYellowToken(ImageView jetonJaune, int column, int line)
    {
        AnchorPane.setLeftAnchor(jetonJaune,  150 + (column-1) * jetonJaune.getFitHeight());
        AnchorPane.setTopAnchor(jetonJaune,  593 - (line-1) * jetonJaune.getFitWidth());
        myAnchorPane.getChildren().add(jetonJaune);
    }
    private Polygon CreateTriangle(int column)
    {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(185.0 + (column - 1) * 100,80.0,215.0 + (column - 1) * 100,80.0,200.0 + (column - 1) * 100,100.0);
        triangle.setFill(Color.RED);
        triangle.setVisible(false);
        myAnchorPane.getChildren().add(triangle);
        return triangle;
    }

    private void CursorAppear(Button button, Polygon triangle)
    {
        button.setOnMouseEntered(event -> triangle.setVisible(true));
        button.setOnMouseExited(event -> triangle.setVisible(false));
    }

    private void PrintWon()
    {
        Text message = new Text();
        message.setText("Gagné");
        message.setLayoutX(200);
        message.setLayoutY(425);
        message.setFill(Color.RED);
        message.setFont(Font.font("Arial", 200));
        myAnchorPane.getChildren().add(message);
    }

    private void PrintLost()
    {
        Text message = new Text();
        message.setText("Perdu");
        message.setLayoutX(235);
        message.setLayoutY(425);
        message.setFill(Color.RED);
        message.setFont(Font.font("Arial", 200));
        myAnchorPane.getChildren().add(message);
    }

    private String SaisirPseudo(int numeroJoueur)
    {
        PseudoView P = new PseudoView();
        return P.Pseudo(numeroJoueur);
    }

    private void AfficherPseudoJoueur1(String name)
    {
        Text message = new Text();
        message.setText(name);
        message.setLayoutX(120);
        message.setLayoutY(34);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 20));
        myAnchorPane.getChildren().add(message);
    }
    private void AfficherPseudoJoueur2(String name)
    {
        Text message = new Text();
        message.setText(name);
        message.setLayoutX(120);
        message.setLayoutY(59);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 20));
        myAnchorPane.getChildren().add(message);
    }

    private void PrintGameOption()
    {
        OptionView option = new OptionView();
        Text message = new Text();
        message.setText(option.GameOptions());
        message.setLayoutX(700);
        message.setLayoutY(50);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 20));
        myAnchorPane.getChildren().add(message);
    }
}