package com.projetpuissance4.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.projetpuissance4.controllers.CommunicationFileController;
import javafx.application.Platform;

public class TCPClientP4 implements AutoCloseable {

    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;


    private boolean isConnectedToServer;
    private boolean is2ndClientConnected;
    private boolean isClientOpened;
    private boolean isReconnectedToServerInProgress;
    private boolean isPlayerStarts;

    private int port;
    private String IP;
    private int numClient;
    private P4 Grille = new P4();

    private int whoPlay = 0;

    private IAExploration IA = new IAExploration();
    /**
     * Default Constructor for a ClientTCP
     *
     * @param IP
     * @param port
     * @throws IOException
     */
    public TCPClientP4(String IP, int port) {
        this.isConnectedToServer = false;
        this.is2ndClientConnected = false;
        this.isClientOpened = true;
        this.isReconnectedToServerInProgress = false;
        this.isPlayerStarts = false;

        this.numClient = -1;

        this.port = port;
        this.IP = IP;
    }

    /**
     * Getter for the client's number
     *
     * @return
     */
    public int getNumClient() {
        return this.numClient;
    }

    /**
     * Getter for the client's IP
     *
     * @return
     */
    public String getIP() {
        return this.IP;
    }

    /**
     * Getter for the client's IP
     *
     * @return
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Getter for the writer
     *
     * @return
     */
    public PrintWriter getWriter() {
        return writer;
    }

    /**
     * Setter for the boolean isClientOpened
     *
     * @param isClientOpened
     */
    public void setClientOpened(boolean isClientOpened) {
        this.isClientOpened = isClientOpened;
    }

    /**
     * Method of launching the connection with the server
     *
     * @throws IOException
     */
    public void connectToServer() {
        try {
            // For close reconnect () when program is closing
            if (isClientOpened) {

                // Initialization of communication
                this.clientSocket = new Socket(IP, port);

                // Actualization of data
                isConnectedToServer = true;
                System.out.println("Connected to the server !");

                // Creation of writer and reader
                this.writer = new PrintWriter(this.clientSocket.getOutputStream(), true);
                this.reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));


            }
        } catch (IOException IOE) {
            // If the server is not open
            if (this.clientSocket == null) {
                if (isClientOpened) {
                    System.err.println("Server not open !");

                    // We  try a reconnection
                    reconnect();
                }
            } else {
                IOE.printStackTrace();
            }
        }
    }


    public void startReceive()
    {
        // Get Message of server
        if (isConnectedToServer && isClientOpened) {
            Thread clientThread = new Thread(() -> receiveMessage());
            clientThread.start();
        }
    }
    /**
     * Use to retry a connection with the server
     */
    private void reconnect() {
        // if the port is being modified
        if (isReconnectedToServerInProgress) {
            return;
        }

        // Start the Thread to take a break
        Thread waitThread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                    Platform.runLater(new Runnable() {
                        public void run() {
                            if (!isConnectedToServer || isClientOpened) {
                                // Retry the connection
                                connectToServer();
                            }
                        }
                    });
                } catch (InterruptedException IE) {
                    IE.printStackTrace();
                }
            }
        });

        // Starting the Thread
        waitThread.start();
    }

    /**
     * Method use to close socket, Reader and Writer
     */
    public void disconnect() {
        try {
            if (this.clientSocket != null) {
                // Reader
                if (this.reader != null) {
                    this.reader.close();
                }

                // Writer
                if (this.writer != null) {
                    this.writer.close();
                }

                System.out.println("Socket Closed");
                this.clientSocket.close();
            }

            // If the server is close before the Scoring Machine
            this.clientSocket = null;

            isConnectedToServer = false;

        } catch (IOException IOE) {
            System.err.println("Error disconnecting from the server: " + IOE.getMessage());
        }
    }

    /**
     * Method the receive Messages from the Server
     */
    public void receiveMessage() {
        String finalMessage = "";
        while (isConnectedToServer)
        {
            try {
                // Read Message
                finalMessage = this.reader.readLine();
                System.out.println("final message : " +finalMessage);

                // If the connection have been stopped
                if (finalMessage.equals("STOP")) {
                    System.out.println("Connection to the server lost !");

                    // Closure of socket / in / out
                    disconnect();

                    // If the program are stopping -> don't retry the connection
                    if (isClientOpened) {
                        reconnect();
                    }

                }

                // We are looking if the message is type of "N + integer"
                Pattern pattern = Pattern.compile("N(\\d+)");
                Matcher matcher = pattern.matcher(finalMessage);


                // Message for client's number assignment
                if (matcher.find()) {
                    String integerPart = matcher.group(1);
                    numClient = Integer.parseInt(integerPart);
                    if(Grille.playerWin(1)[0] == 1)
                    {
                        System.out.println("opponent win");
                    }
                    else if(Grille.playerWin(2)[0] == 1)
                    {
                        System.out.println("you win");
                    }
                    else {
                        if(whoPlay%2 == 1)
                        {
                            System.out.println("You have play : " + numClient);
                            whoPlay++;
                        }
                        else {
                            System.out.println("The opponent have play : " + numClient);
                            CommunicationFileController.writeToFile(String.valueOf(numClient),"comToProcess.txt");
                            Grille.setMatValue(numClient,1);
                            whoPlay++;
                            //System.out.println(Grille.toString());
                            while(!CommunicationFileController.doesFileExist("comToClient.txt"))
                            {
                            }
                            int colmun = Integer.parseInt(CommunicationFileController.readFromFile("comToClient.txt"));
                            CommunicationFileController.deleteFile("comToClient.txt");
                            Grille.setMatValue(colmun,2);
                            this.sendFile(String.valueOf(colmun));
                        }
                    }

                }

                // Message that says two players are connected to the server
                else if (finalMessage.equals("2 Players Connected")) {
                    is2ndClientConnected = true;
                }

                // Message that says two players are connected to the server
                else if (finalMessage.equals("Other Player Left")) {
                    is2ndClientConnected = false;
                }

                // Message that says the player will start the game
                else if (finalMessage.equals("You Start")) {
                    isPlayerStarts = true;
                    whoPlay = 0;
                    CommunicationFileController.writeToFile("1","whoPlay.txt");
                    while(!CommunicationFileController.doesFileExist("comToClient.txt"))
                    {
                    }
                    System.out.println("read com : " + CommunicationFileController.readFromFile("comToClient.txt"));
                    int colmun = Integer.parseInt(CommunicationFileController.readFromFile("comToClient.txt"));
                    CommunicationFileController.deleteFile("comToClient.txt");
                    Grille.setMatValue(colmun,2);
                    System.out.println(Grille.toString());
                    this.sendFile(String.valueOf(colmun));
                    whoPlay++;
                    CommunicationFileController.deleteFile("whoPlay.txt");
                }

                // Message that says the player will not start the game
                else if (finalMessage.equals("Opponent Will Start")) {
                    isPlayerStarts = false;
                    whoPlay = 0;
                }

                // Message that says the other player had play
                else if (finalMessage.matches("\\d+")) {
                }


            } catch (IOException IOE) {
                IOE.printStackTrace();
                disconnect();
            }
        }
    }
    /**
     * Using to change Port / IP of the connection to Server
     *
     * @param IP   : IP for connect to the server
     */
    public void changeIP_Port(String IP, String Port) {

        System.out.println("Connection to : " + IP + " - " + Port);

        // Actualization of attributes
        this.IP = IP;
        this.port = Integer.parseInt(Port);

        isReconnectedToServerInProgress = true;

        // Stop A potential Thread
        if (isConnectedToServer) {
            this.writer.println("STOP");
        }

        // Reconnect with new informations
        reconnect();

        isReconnectedToServerInProgress = false;
    }

    /**
     * Method that allows to close the client
     */
    @Override
    public void close() throws Exception {
        isClientOpened = false;

        if (writer != null) {
            writer.println("STOP");
        }

        System.out.println("Client closed");
    }

    public void sendFile(String message) throws IOException {
        if (!clientSocket.isConnected()) {
            System.out.println("La connexion au serveur est fermée. Impossible d'envoyer le fichier.");
            return;
        }
        this.writer.println(message);
        System.out.println("message send");

    }
}
