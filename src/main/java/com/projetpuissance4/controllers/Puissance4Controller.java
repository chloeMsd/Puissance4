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

import java.lang.reflect.GenericArrayType;


public class Puissance4Controller {
    @FXML
    private AnchorPane myAnchorPane;
    @FXML
    private ImageView board;
    @FXML
    private ImageView Halo;
    private P4 Grille = new P4();
    private boolean Play = true;
    private boolean isRunning = true;
    private Button invisibleButtonColumn1;
    private Button invisibleButtonColumn2;
    private Button invisibleButtonColumn3;
    private Button invisibleButtonColumn4;
    private Button invisibleButtonColumn5;
    private Button invisibleButtonColumn6;
    private Button invisibleButtonColumn7;

    private Polygon triangle1;
    private Polygon triangle2;
    private Polygon triangle3;
    private Polygon triangle4;
    private Polygon triangle5;
    private Polygon triangle6;
    private Polygon triangle7 ;

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

        triangle1 = CreateTriangle(1);
        triangle2 = CreateTriangle(2);
        triangle3 = CreateTriangle(3);
        triangle4 = CreateTriangle(4);
        triangle5 = CreateTriangle(5);
        triangle6 = CreateTriangle(6);
        triangle7 = CreateTriangle(7);

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

        String gameOption = PrintGameOption();
        if (gameOption.equals("Jeu contre un autre joueur")) {
            AfficherPseudoJoueur1(SaisirPseudo(1));
            AfficherPseudoJoueur2(SaisirPseudo(2));
        } else if (gameOption.equals("Jeu contre une IA débutante")) {
            AfficherPseudoJoueur1(SaisirPseudo(1));
            AfficherPseudoJoueur2("IA débutante");
        }else if (gameOption.equals("Jeu contre une IA intermédiaire")) {
            AfficherPseudoJoueur1(SaisirPseudo(1));
            AfficherPseudoJoueur2("IA intermédiaire");
        }else if (gameOption.equals("Jeu contre une IA experte")) {
            AfficherPseudoJoueur1(SaisirPseudo(1));
            AfficherPseudoJoueur2("IA experte");
        }

    }
    public void ButtonPlay(int iButton)
    {
        if(Play)
        {
            if(whoPlay % 2 == 0){
                AddRedToken(CreationRedToken(100,100),iButton,6-Grille.checkGraviter(iButton-1));
                int ligne = 6 - Grille.checkGraviter(iButton-1);
                Halo.setX(152 + (iButton - 1)*100);
                Halo.setY(594 - (ligne - 1)*100);
                Halo.setVisible(true);
                Grille.setMatValeur(iButton-1,1);
            }
            else {
                AddYellowToken(CreationYellowToken(100,100),iButton,6-Grille.checkGraviter(iButton-1));
                int ligne = 6 - Grille.checkGraviter(iButton-1);
                Halo.setX(152 + (iButton - 1)*100);
                Halo.setY(594 - (ligne - 1)*100);
                Halo.setVisible(true);
                Grille.setMatValeur(iButton-1,2);
            }
            whoPlay++;
            System.out.println(Grille.toString());
            int[] Joueur1 = Grille.JoueurGagnant(1);
            int[] Joueur2 = Grille.JoueurGagnant(2);
            if (Joueur2[0] == 1)
            {
                PrintWonTokens(Joueur2);
                PrintWon(2);
                Play = false;
                setOpacityTriangle(0);
            }
            else if (Joueur1[0] == 1)
            {
                PrintWonTokens(Joueur1);
                PrintWon(1);
                Play = false;
                setOpacityTriangle(0);
            }
            else if (Grille.TestEgalite())
            {
                PrintEqual();
                Play = false;
            }
        }
    }

    public void setOpacityTriangle(double opacity)
    {
        triangle1.setOpacity(opacity);
        triangle2.setOpacity(opacity);
        triangle3.setOpacity(opacity);
        triangle4.setOpacity(opacity);
        triangle5.setOpacity(opacity);
        triangle6.setOpacity(opacity);
        triangle7.setOpacity(opacity);
    }
    public Button CreationInvisibleButton(int column)
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

    public ImageView CreationRedToken(double width, double height)
    {
        Image image = new Image(Puissance4.class.getResourceAsStream("RedToken.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    public ImageView CreationYellowToken(double width, double height)
    {
        Image image = new Image(Puissance4.class.getResourceAsStream("YellowToken.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    public void AddRedToken(ImageView jetonRouge, int column, int line)
    {
        AnchorPane.setLeftAnchor(jetonRouge, 150 + (column-1) * jetonRouge.getFitHeight());
        AnchorPane.setTopAnchor(jetonRouge, 593 - (line-1) * jetonRouge.getFitWidth());
        myAnchorPane.getChildren().add(jetonRouge);
    }

    public void AddYellowToken(ImageView jetonJaune, int column, int line)
    {
        AnchorPane.setLeftAnchor(jetonJaune,  150 + (column-1) * jetonJaune.getFitHeight());
        AnchorPane.setTopAnchor(jetonJaune,  593 - (line-1) * jetonJaune.getFitWidth());
        myAnchorPane.getChildren().add(jetonJaune);
    }
    public Polygon CreateTriangle(int column)
    {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(185.0 + (column - 1) * 100,80.0,215.0 + (column - 1) * 100,80.0,200.0 + (column - 1) * 100,100.0);
        triangle.setFill(Color.RED);
        triangle.setVisible(false);
        myAnchorPane.getChildren().add(triangle);
        return triangle;
    }

    public void CursorAppear(Button button, Polygon triangle)
    {
        button.setOnMouseEntered(event -> triangle.setVisible(true));
        button.setOnMouseExited(event -> triangle.setVisible(false));
    }

    public void PrintWon(int numero)
    {
        board.setOpacity(0.5);
        Text message = new Text();
        message.setText("Joueur " + numero + "\nGagnant");
        message.setLayoutX(100);
        message.setLayoutY(275);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 200));
        myAnchorPane.getChildren().add(message);
    }

    public void PrintEqual()
    {
        board.setOpacity(0.5);
        Text message = new Text();
        message.setText("Egalité");
        message.setLayoutX(195);
        message.setLayoutY(400);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 200));
        myAnchorPane.getChildren().add(message);
    }

    public String SaisirPseudo(int numeroJoueur)
    {
        PseudoView P = new PseudoView();
        return P.Pseudo(numeroJoueur);
    }

    public void AfficherPseudoJoueur1(String name)
    {
        Text message = new Text();
        message.setText(name);
        message.setLayoutX(120);
        message.setLayoutY(34);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 20));
        myAnchorPane.getChildren().add(message);
    }
    public void AfficherPseudoJoueur2(String name)
    {
        Text message = new Text();
        message.setText(name);
        message.setLayoutX(120);
        message.setLayoutY(59);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 20));
        myAnchorPane.getChildren().add(message);
    }

    public String PrintGameOption()
    {
        OptionView option = new OptionView();
        Text message = new Text();
        message.setText(option.GameOptions());
        message.setLayoutX(700);
        message.setLayoutY(50);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 20));
        myAnchorPane.getChildren().add(message);
        String texte = message.getText();
        return texte;
    }

    public void PrintWonTokens(int[] token)
    {
        Image halo1 = new Image(Puissance4.class.getResourceAsStream("halo.png"));
        ImageView im1 = new ImageView(halo1);
        im1.setFitWidth(96.0);
        im1.setFitHeight(96.0);
        im1.setX(152  + (token[2])*100);
        im1.setY(594 - (5 - token[1])*100);
        myAnchorPane.getChildren().add(im1);

        Image halo2 = new Image(Puissance4.class.getResourceAsStream("halo.png"));
        ImageView im2 = new ImageView(halo1);
        im2.setFitWidth(96.0);
        im2.setFitHeight(96.0);
        im2.setX(152  + (token[4])*100);
        im2.setY(594 - (5 - token[3])*100);
        myAnchorPane.getChildren().add(im2);

        Image halo3 = new Image(Puissance4.class.getResourceAsStream("halo.png"));
        ImageView im3 = new ImageView(halo1);
        im3.setFitWidth(96.0);
        im3.setFitHeight(96.0);
        im3.setX(152  + (token[6])*100);
        im3.setY(594 - (5 - token[5])*100);
        myAnchorPane.getChildren().add(im3);

        Image halo4 = new Image(Puissance4.class.getResourceAsStream("halo.png"));
        ImageView im4 = new ImageView(halo1);
        im4.setFitWidth(96.0);
        im4.setFitHeight(96.0);
        im4.setX(152  + (token[8])*100);
        im4.setY(594 - (5 - token[7])*100);
        myAnchorPane.getChildren().add(im4);
    }
}