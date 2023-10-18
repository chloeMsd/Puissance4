package com.projetpuissance4;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Optional;

public class HelloController {
    @FXML
    private AnchorPane myAnchorPane;

    public void initialize() {
        double initialX = 150;
        double initialY = 93.0;
        double imageWidth = 100.0;
        double imageHeight = 100.0;

        Button invisibleButtonColumn1 = CreationInvisibleButton(1);
        Button invisibleButtonColumn2 = CreationInvisibleButton(2);
        Button invisibleButtonColumn3 = CreationInvisibleButton(3);
        Button invisibleButtonColumn4 = CreationInvisibleButton(4);
        Button invisibleButtonColumn5 = CreationInvisibleButton(5);
        Button invisibleButtonColumn6 = CreationInvisibleButton(6);
        Button invisibleButtonColumn7 = CreationInvisibleButton(7);

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


        invisibleButtonColumn1.setOnAction(event -> AddRedToken(CreationRedToken(100,100),1,1));
        invisibleButtonColumn2.setOnAction(event -> AddRedToken(CreationYellowToken(100,100),2,4));

        //invisibleButtonColumn1.setOnAction(event -> PrintWon());
        AfficherPseudo(SaisirPseudo());
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
        Image image = new Image(HelloApplication.class.getResourceAsStream("RedToken.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    private ImageView CreationYellowToken(double width, double height)
    {
        Image image = new Image(HelloApplication.class.getResourceAsStream("YellowToken.png"));
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

    private String SaisirPseudo()
    {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Pseudo");
        dialog.setHeaderText("Veuillez saisir votre pseudo :");
        dialog.setContentText("Pseudo :");
        Optional<String> result = dialog.showAndWait();
        String name = result.get();
        return name;
    }

    private void AfficherPseudo(String name)
    {
        Text message = new Text();
        message.setText(name);
        message.setLayoutX(120);
        message.setLayoutY(34);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 20));
        myAnchorPane.getChildren().add(message);
    }

    
}