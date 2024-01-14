package com.projetpuissance4.controllers;

import com.projetpuissance4.Puissance4;
import com.projetpuissance4.models.IAExploration;
import com.projetpuissance4.models.IAMinimax;
import com.projetpuissance4.models.IARandom;
import com.projetpuissance4.models.Grid;
import com.projetpuissance4.views.OptionView;
import com.projetpuissance4.views.PseudoView;
import com.projetpuissance4.models.TCPClientP4;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


public class Puissance4Controller {
    @FXML
    private AnchorPane myAnchorPane;
    @FXML
    private ImageView board;
    @FXML
    private ImageView Halo;
    @FXML
    private Button replay;
    private Grid grid = new Grid();
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
    private IARandom IArandom = new IARandom();
    private IAMinimax IAminimax = new IAMinimax();
    private IAExploration IAExploration = new IAExploration();

    private static Random rand = new Random();
    private static int whoPlay = rand.nextInt();

    public void initialize() throws InterruptedException {
        CommunicationFileController.deleteFile("comToProcess.txt");
        CommunicationFileController.deleteFile("comToClient.txt");

        // Creation of invisible button that represent column
        invisibleButtonColumn1 = CreationInvisibleButton(1);
        invisibleButtonColumn2 = CreationInvisibleButton(2);
        invisibleButtonColumn3 = CreationInvisibleButton(3);
        invisibleButtonColumn4 = CreationInvisibleButton(4);
        invisibleButtonColumn5 = CreationInvisibleButton(5);
        invisibleButtonColumn6 = CreationInvisibleButton(6);
        invisibleButtonColumn7 = CreationInvisibleButton(7);

        //Create triangle to put on the top of each column
        triangle1 = CreateTriangle(1);
        triangle2 = CreateTriangle(2);
        triangle3 = CreateTriangle(3);
        triangle4 = CreateTriangle(4);
        triangle5 = CreateTriangle(5);
        triangle6 = CreateTriangle(6);
        triangle7 = CreateTriangle(7);

        //Triangle appear when cursor is on a column
        CursorAppear(invisibleButtonColumn1, triangle1);
        CursorAppear(invisibleButtonColumn2, triangle2);
        CursorAppear(invisibleButtonColumn3, triangle3);
        CursorAppear(invisibleButtonColumn4, triangle4);
        CursorAppear(invisibleButtonColumn5, triangle5);
        CursorAppear(invisibleButtonColumn6, triangle6);
        CursorAppear(invisibleButtonColumn7, triangle7);


        String gameOption = PrintGameOption();
        if (gameOption.equals("Jeu contre un autre joueur")) {
            //Write who start
            if(whoPlay % 2 == 0)
            {
                //Display pseudo of each player (real player can choose those)
                displayNamePlayer1(enterPseudo(1) + " commence");
                displayNamePlayer2(enterPseudo(2));
            }
            else {
                displayNamePlayer1(enterPseudo(1));
                displayNamePlayer2(enterPseudo(2) + " commence");
            }

            //Define what mathod to call when button are pressed
            invisibleButtonColumn1.setOnAction(event -> ButtonPlay(1));
            invisibleButtonColumn2.setOnAction(event -> ButtonPlay(2));
            invisibleButtonColumn3.setOnAction(event -> ButtonPlay(3));
            invisibleButtonColumn4.setOnAction(event -> ButtonPlay(4));
            invisibleButtonColumn5.setOnAction(event -> ButtonPlay(5));
            invisibleButtonColumn6.setOnAction(event -> ButtonPlay(6));
            invisibleButtonColumn7.setOnAction(event -> ButtonPlay(7));
        }
        else if (gameOption.equals("Jeu contre une IA débutante")) {
            displayNamePlayer1(enterPseudo(1));
            displayNamePlayer2("IA débutante");
            invisibleButtonColumn1.setOnAction(event -> ButtonPlayIAnv0(1));
            invisibleButtonColumn2.setOnAction(event -> ButtonPlayIAnv0(2));
            invisibleButtonColumn3.setOnAction(event -> ButtonPlayIAnv0(3));
            invisibleButtonColumn4.setOnAction(event -> ButtonPlayIAnv0(4));
            invisibleButtonColumn5.setOnAction(event -> ButtonPlayIAnv0(5));
            invisibleButtonColumn6.setOnAction(event -> ButtonPlayIAnv0(6));
            invisibleButtonColumn7.setOnAction(event -> ButtonPlayIAnv0(7));
        }
        else if (gameOption.equals("Jeu contre une IA intermédiaire")) {
            if(whoPlay % 2 == 0)
            {
                displayNamePlayer1(enterPseudo(1) + " commence");
                displayNamePlayer2("IA intermédiaire");
            }
            else {
                displayNamePlayer1(enterPseudo(1));
                displayNamePlayer2("IA intermédiaire : commence");
                IAFirstExploration();
            }

            invisibleButtonColumn1.setOnAction(event -> ButtonPlayIAexploration(1));
            invisibleButtonColumn2.setOnAction(event -> ButtonPlayIAexploration(2));
            invisibleButtonColumn3.setOnAction(event -> ButtonPlayIAexploration(3));
            invisibleButtonColumn4.setOnAction(event -> ButtonPlayIAexploration(4));
            invisibleButtonColumn5.setOnAction(event -> ButtonPlayIAexploration(5));
            invisibleButtonColumn6.setOnAction(event -> ButtonPlayIAexploration(6));
            invisibleButtonColumn7.setOnAction(event -> ButtonPlayIAexploration(7));

        }
        else if (gameOption.equals("Jeu contre une IA experte")) {

            if(whoPlay % 2 == 0)
            {
                displayNamePlayer1(enterPseudo(1) + " commence");
                displayNamePlayer2("IA experte");
            }
            else {
                displayNamePlayer1(enterPseudo(1));
                displayNamePlayer2("IA experte : commence");
                IAFirstMinimax();
            }

            invisibleButtonColumn1.setOnAction(event -> ButtonPlayIAminimax(1));
            invisibleButtonColumn2.setOnAction(event -> ButtonPlayIAminimax(2));
            invisibleButtonColumn3.setOnAction(event -> ButtonPlayIAminimax(3));
            invisibleButtonColumn4.setOnAction(event -> ButtonPlayIAminimax(4));
            invisibleButtonColumn5.setOnAction(event -> ButtonPlayIAminimax(5));
            invisibleButtonColumn6.setOnAction(event -> ButtonPlayIAminimax(6));
            invisibleButtonColumn7.setOnAction(event -> ButtonPlayIAminimax(7));

        }
        else if (gameOption.equals("Jeu en réseau")) {

            String player = enterPseudo(1);

            //For the online option we lauch the client
            TCPClientP4 tcpClientP4 = new TCPClientP4("127.0.0.3",8090);
            tcpClientP4.connectToServer();
            TimeUnit.SECONDS.sleep(3);
            tcpClientP4.startReceive();
            TimeUnit.SECONDS.sleep(3);

            //Lauch the thread that catch message from the server
            Thread netWork = new Thread(this::handleThread);
            netWork.start();

            if(CommunicationFileController.doesFileExist("whoPlay.txt"))
            {
                whoPlay = 0;
                displayNamePlayer1(player+ " : commence");
            }
            else {
                whoPlay = 1;
                displayNamePlayer2("Réseau : Commence");
            }
            invisibleButtonColumn1.setOnAction(event -> ButtonPlayNetWork(1));
            invisibleButtonColumn2.setOnAction(event -> ButtonPlayNetWork(2));
            invisibleButtonColumn3.setOnAction(event -> ButtonPlayNetWork(3));
            invisibleButtonColumn4.setOnAction(event -> ButtonPlayNetWork(4));
            invisibleButtonColumn5.setOnAction(event -> ButtonPlayNetWork(5));
            invisibleButtonColumn6.setOnAction(event -> ButtonPlayNetWork(6));
            invisibleButtonColumn7.setOnAction(event -> ButtonPlayNetWork(7));

        }
    }

    /**
     * The thread of the online game
     */
    private void handleThread() {
        while(isRunning)
        {
            Platform.runLater(()->
            {
                NetWork();//Catch server message
            });
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }

    /**
     * Method to play with two real person (one play and then the other untile the game stop)
     * @param iButton
     */
    public void ButtonPlay(int iButton)
    {
        if(Play)
        {
            //if the column is not full
            if(grid.gravityCheck(iButton-1) >= 0)
            {
                if(whoPlay % 2 == 0){
                    //Create red token for player 1
                    AddRedToken(CreationRedToken(100,100),iButton,6- grid.gravityCheck(iButton-1));
                    int ligne = 6 - grid.gravityCheck(iButton-1);
                    Halo.setX(152 + (iButton - 1)*100);
                    Halo.setY(594 - (ligne - 1)*100);
                    Halo.setVisible(true);
                    grid.setMatValue(iButton-1,1);
                }
                else {
                    //Create red token for player 2
                    AddYellowToken(CreationYellowToken(100,100),iButton,6- grid.gravityCheck(iButton-1));
                    int ligne = 6 - grid.gravityCheck(iButton-1);
                    Halo.setX(152 + (iButton - 1)*100);
                    Halo.setY(594 - (ligne - 1)*100);
                    Halo.setVisible(true);
                    grid.setMatValue(iButton-1,2);
                }
                whoPlay++;
                //check if there is a winner
                //if it's the case we glow the four winner token
                int[] Joueur1 = grid.playerWin(1);
                int[] Joueur2 = grid.playerWin(2);
                if (Joueur2[0] == 1)
                {
                    PrintWonTokens(Joueur2);
                    PrintWon(2);
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());

                }
                else if (Joueur1[0] == 1)
                {
                    PrintWonTokens(Joueur1);
                    PrintWon(1);
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
                else if (grid.checkDraw())
                {
                    PrintEqual();
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
            }
        }
    }

    /**
     * is use when we play against the IA random
     * @param iButton
     */
    public void ButtonPlayIAnv0(int iButton)
    {
        if(Play)
        {
            if(grid.gravityCheck(iButton -1) >= 0)
            {
                //Red token for real player
                AddRedToken(CreationRedToken(100,100),iButton,6- grid.gravityCheck(iButton-1));
                int ligne = 6 - grid.gravityCheck(iButton-1);
                Halo.setX(152 + (iButton - 1)*100);
                Halo.setY(594 - (ligne - 1)*100);
                Halo.setVisible(true);
                grid.setMatValue(iButton-1,1);

                //then the ia play (Yellow token)
                int column = IArandom.Play();
                AddYellowToken(CreationYellowToken(100,100),column+1,6- grid.gravityCheck(column));
                ligne = 6 - grid.gravityCheck(column);
                Halo.setX(152 + (column)*100);
                Halo.setY(594 - (ligne - 1)*100);
                Halo.setVisible(true);
                grid.setMatValue(column,2);

                whoPlay++;
                //check for winner
                int[] Joueur1 = grid.playerWin(1);
                int[] Joueur2 = grid.playerWin(2);
                if (Joueur2[0] == 1)
                {
                    PrintWonTokens(Joueur2);
                    PrintWon(2);
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
                else if (Joueur1[0] == 1)
                {
                    PrintWonTokens(Joueur1);
                    PrintWon(1);
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
                else if (grid.checkDraw())
                {
                    PrintEqual();
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
            }
        }
    }

    /**
     * is use when we play against the IA minimax
     * @param iButton
     */
    public void ButtonPlayIAminimax(int iButton)
    {
        if(Play)
        {
            if(grid.gravityCheck(iButton -1) >= 0 )
            {
                //Red token for real player
                AddRedToken(CreationRedToken(100,100),iButton,6- grid.gravityCheck(iButton-1));
                int ligne = 6 - grid.gravityCheck(iButton-1);
                Halo.setX(152 + (iButton - 1)*100);
                Halo.setY(594 - (ligne - 1)*100);
                Halo.setVisible(true);
                grid.setMatValue(iButton-1,1);

                //method to play with the IA Minimax
                IAFirstMinimax();
                //then the ia play (Yellow token)
                whoPlay++;
                //check for winner
                int[] Joueur1 = grid.playerWin(1);
                int[] Joueur2 = grid.playerWin(2);
                if (Joueur2[0] == 1)
                {
                    PrintWonTokens(Joueur2);
                    PrintWon(2);
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
                else if (Joueur1[0] == 1)
                {
                    PrintWonTokens(Joueur1);
                    PrintWon(1);
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
                else if (grid.checkDraw())
                {
                    PrintEqual();
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
            }
        }
    }


    /**
     * is use when we play against the IA Exploration
     * @param iButton
     */
    public void ButtonPlayIAexploration(int iButton)
    {
        if(Play)
        {
            if(grid.gravityCheck(iButton -1) >= 0 )
            {
                AddRedToken(CreationRedToken(100,100),iButton,6- grid.gravityCheck(iButton-1));
                int ligne = 6 - grid.gravityCheck(iButton-1);
                Halo.setX(152 + (iButton - 1)*100);
                Halo.setY(594 - (ligne - 1)*100);
                Halo.setVisible(true);
                grid.setMatValue(iButton-1,1);

                IAFirstExploration();

                whoPlay++;
                System.out.println(grid.toString());
                int[] Joueur1 = grid.playerWin(1);
                int[] Joueur2 = grid.playerWin(2);
                if (Joueur2[0] == 1)
                {
                    PrintWonTokens(Joueur2);
                    PrintWon(2);
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
                else if (Joueur1[0] == 1)
                {
                    PrintWonTokens(Joueur1);
                    PrintWon(1);
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
                else if (grid.checkDraw())
                {
                    PrintEqual();
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
            }
        }
    }

    /**
     * is use when we play against an other player/IA but online
     * @param iButton
     */
    public void ButtonPlayNetWork(int iButton) {
        if(Play)
        {
            if(grid.gravityCheck(iButton -1) >= 0 )
            {
                CommunicationFileController.writeToFile(String.valueOf(iButton-1),"comToClient.txt");
                AddRedToken(CreationRedToken(100,100),iButton,6- grid.gravityCheck(iButton-1));
                int ligne = 6 - grid.gravityCheck(iButton-1);
                Halo.setX(152 + (iButton - 1)*100);
                Halo.setY(594 - (ligne - 1)*100);
                Halo.setVisible(true);
                grid.setMatValue(iButton-1,1);

                //NetWork();

                whoPlay++;
                System.out.println(grid.toString());
                int[] Joueur1 = grid.playerWin(1);
                int[] Joueur2 = grid.playerWin(2);
                if (Joueur2[0] == 1)
                {
                    PrintWonTokens(Joueur2);
                    PrintWon(2);
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
                else if (Joueur1[0] == 1)
                {
                    PrintWonTokens(Joueur1);
                    PrintWon(1);
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
                else if (grid.checkDraw())
                {
                    PrintEqual();
                    Play = false;
                    setOpacityTriangle(0);
                    replay.setVisible(true);
                    replay.setOnAction(event -> replay());
                }
            }
        }
    }

    /**
     * Catch information of the other player online to put on the grid
     */
    public void NetWork() {
        if(CommunicationFileController.doesFileExist("comToProcess.txt"))
        {
            if (((grid.playerWin(1))[0])==0)
            {
                //we catch the column where the other player play
                int column = Integer.parseInt(CommunicationFileController.readFromFile("comToProcess.txt"));
                CommunicationFileController.deleteFile("comToProcess.txt");
                //then we add a yellow token
                AddYellowToken(CreationYellowToken(100,100),column+1,6- grid.gravityCheck(column));
                int ligne = 6 - grid.gravityCheck(column);
                Halo.setX(152 + (column)*100);
                Halo.setY(594 - (ligne - 1)*100);
                Halo.setVisible(true);
                grid.setMatValue(column,2);
            }
        }
    }
    public void IAFirstMinimax()
    {
        if (((grid.playerWin(1))[0])==0)
        {
            //We call the IA Minimax to choose the column to play
            int column = IAminimax.playV2(2,7, grid);
            //then we add the yellow token
            AddYellowToken(CreationYellowToken(100,100),column+1,6- grid.gravityCheck(column));
            int ligne = 6 - grid.gravityCheck(column);
            Halo.setX(152 + (column)*100);
            Halo.setY(594 - (ligne - 1)*100);
            Halo.setVisible(true);
            grid.setMatValue(column,2);
        }
    }


    public void IAFirstExploration()
    {
        if (((grid.playerWin(1))[0])==0)
        {
            //We call the IA Exploration to choose the column to play
            int column = IAExploration.play(grid);
            //then we add the yellow token
            AddYellowToken(CreationYellowToken(100,100),column+1,6- grid.gravityCheck(column));
            int ligne = 6 - grid.gravityCheck(column);
            Halo.setX(152 + (column)*100);
            Halo.setY(594 - (ligne - 1)*100);
            Halo.setVisible(true);
            grid.setMatValue(column,2);
        }
    }

    /**
     * To set the opacity of triangles
     * @param opacity
     */
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

    /**
     * To create all invisible button
     * @param column
     * @return
     */
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

    /**
     * to create red token
     * @param width
     * @param height
     * @return
     */
    public ImageView CreationRedToken(double width, double height)
    {
        Image image = new Image(Puissance4.class.getResourceAsStream("RedToken.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    /**
     * to create yellow token
     * @param width
     * @param height
     * @return
     */
    public ImageView CreationYellowToken(double width, double height)
    {
        Image image = new Image(Puissance4.class.getResourceAsStream("YellowToken.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    /**
     * To add red token
     * @param redToken
     * @param column
     * @param line
     */
    public void AddRedToken(ImageView redToken, int column, int line)
    {
        AnchorPane.setLeftAnchor(redToken, 150 + (column-1) * redToken.getFitHeight());
        AnchorPane.setTopAnchor(redToken, 593 - (line-1) * redToken.getFitWidth());
        myAnchorPane.getChildren().add(redToken);
    }

    /**
     * To add yellow token
     * @param yellowToken
     * @param column
     * @param line
     */
    public void AddYellowToken(ImageView yellowToken, int column, int line)
    {
        AnchorPane.setLeftAnchor(yellowToken,  150 + (column-1) * yellowToken.getFitHeight());
        AnchorPane.setTopAnchor(yellowToken,  593 - (line-1) * yellowToken.getFitWidth());
        myAnchorPane.getChildren().add(yellowToken);
    }

    /**
     * to Create triangle
     * @param column
     * @return
     */
    public Polygon CreateTriangle(int column)
    {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(185.0 + (column - 1) * 100,80.0,215.0 + (column - 1) * 100,80.0,200.0 + (column - 1) * 100,100.0);
        triangle.setFill(Color.RED);
        triangle.setVisible(false);
        myAnchorPane.getChildren().add(triangle);
        return triangle;
    }

    /**
     * Set visible triangle when the mouse is on and invisible when is not
     * @param button
     * @param triangle
     */
    public void CursorAppear(Button button, Polygon triangle)
    {
        button.setOnMouseEntered(event -> triangle.setVisible(true));
        button.setOnMouseExited(event -> triangle.setVisible(false));
    }

    /**
     * Display the winner player
     * @param number
     */
    public void PrintWon(int number)
    {
        board.setOpacity(0.5);
        Text message = new Text();
        message.setText("Joueur " + number + "\nGagnant");
        message.setLayoutX(100);
        message.setLayoutY(275);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 200));
        myAnchorPane.getChildren().add(message);
    }

    /**
     * Display if there is a draw
     */
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

    /**
     * To enter pseudo of real player
     * @param playerNumber
     * @return
     */
    public String enterPseudo(int playerNumber)
    {
        PseudoView P = new PseudoView();
        return P.Pseudo(playerNumber);
    }

    /**
     * to display the name of the player 1
     * @param name
     */
    public void displayNamePlayer1(String name)
    {
        Text message = new Text();
        message.setText(name);
        message.setLayoutX(120);
        message.setLayoutY(34);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 20));
        myAnchorPane.getChildren().add(message);
    }
    /**
     * to display the name of the player 2
     * @param name
     */
    public void displayNamePlayer2(String name)
    {
        Text message = new Text();
        message.setText(name);
        message.setLayoutX(120);
        message.setLayoutY(59);
        message.setFill(Color.BLACK);
        message.setFont(Font.font("Arial", 20));
        myAnchorPane.getChildren().add(message);
    }

    /**
     * To display and enter game option
     * @return
     */
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

    /**
     * To glow Token that won
     * @param token
     */
    public void PrintWonTokens(int[] token)
    {
        Image halo1 = new Image(Puissance4.class.getResourceAsStream("halo.png"));
        ImageView im1 = new ImageView(halo1);
        im1.setFitWidth(96.0);
        im1.setFitHeight(96.0);
        im1.setX(152  + (token[2])*100);
        im1.setY(594 - (5 - token[1])*100);
        myAnchorPane.getChildren().add(im1);

        ImageView im2 = new ImageView(halo1);
        im2.setFitWidth(96.0);
        im2.setFitHeight(96.0);
        im2.setX(152  + (token[4])*100);
        im2.setY(594 - (5 - token[3])*100);
        myAnchorPane.getChildren().add(im2);

        ImageView im3 = new ImageView(halo1);
        im3.setFitWidth(96.0);
        im3.setFitHeight(96.0);
        im3.setX(152  + (token[6])*100);
        im3.setY(594 - (5 - token[5])*100);
        myAnchorPane.getChildren().add(im3);

        ImageView im4 = new ImageView(halo1);
        im4.setFitWidth(96.0);
        im4.setFitHeight(96.0);
        im4.setX(152  + (token[8])*100);
        im4.setY(594 - (5 - token[7])*100);
        myAnchorPane.getChildren().add(im4);
    }

    /**
     * To clear the board when we restart the game
     */
    private void clearBoard()
    {
        ObservableList<Node> children = myAnchorPane.getChildren();
        ObservableList<Node> nodesToKeep = FXCollections.observableArrayList();

        for (Node child : children) {
            if ("board".equals(child.getId()) || "minRedToken".equals(child.getId()) || "minYellowToken".equals(child.getId()) || "Halo".equals(child.getId()) || child instanceof Label || child instanceof Button) {
                nodesToKeep.add(child);
            }
        }
        board.setOpacity(1);
        myAnchorPane.getChildren().clear();
        myAnchorPane.getChildren().setAll(nodesToKeep);
    }

    /**
     * Display the replay button
     */
    private void replay()
    {
        clearBoard();
        try {
            initialize();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        grid.matrixZero();
        Halo.setVisible(false);
        Play = true;
        replay.setVisible(false);
    }
}