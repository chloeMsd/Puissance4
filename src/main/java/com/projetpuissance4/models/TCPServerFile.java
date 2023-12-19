package com.projetpuissance4.models;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The type TCPServerFile
 */
public class TCPServerFile implements Runnable {

    /**
     * the port
     */
    private int port;
    /**
     * the server ip
     */
    private String serverIP;
    /**
     * the timer
     */
    private Timer timer = new Timer();
    /**
     * the boolean isServerRunning
     *
     * true if the server is running
     */
    private boolean isServerRunning = true;

    /**
     * The delay of the timer
     */
    private long delay = 86000000;
    /**
     *   Is server running ? Test if the server is running
     *
     * @return boolean true if the server is running
     */
    public boolean isServerRunning() {
        return isServerRunning;
    }

    /**
     *   Instantiates a new TCPServerFile
     *
     * @param port the port
     * @param serverIP the server ip
     */
    public TCPServerFile(int port, String serverIP) {
        this.port = port;
        this.serverIP = serverIP;
    }

    /**
     *   Sets port
     *
     * @param port the port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     *   Sets server ip
     *
     * @param serverIP the server ip
     */
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    /**
     *   Instantiates a new TCPServerFile
     */
    public TCPServerFile() {
        port = 0;
        serverIP = null;
    }

    /**
     *   Run the server
     */
    @Override
    public void run() {
        int port = this.port; // Port d'écoute du serveur
        String serverIP = this.serverIP; // Adresse IP du serveur

        try {
            ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(serverIP));
            System.out.println("Serveur en attente de connexion sur " + serverIP + ":" + port);
            while(isServerRunning && !serverSocket.isClosed())
            {
                // Attente d'une connexion cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté : " + clientSocket.getInetAddress().getHostAddress());

                // Flux d'entrée pour recevoir les données
                InputStream inputStream = clientSocket.getInputStream();

                DataInputStream dataInputStream = new DataInputStream(inputStream);
                int receivedValue = dataInputStream.readInt();

                System.out.println("Valeur reçu et enregistré : " + receivedValue);
                resetTimer( inputStream, clientSocket, serverSocket);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    /**
     *   Start timer
     *
     * @param inputStream the input stream
     * @param clientSocket the client socket
     * @param serverSocket the server socket
     */
    private void startTimer(InputStream inputStream,Socket clientSocket,ServerSocket serverSocket) {
        long delay = this.delay;

        // Exécuter l'action lorsque le délai est écoulé
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Le délai est écoulé. Arrêt du serveur.");
                isServerRunning = false;
                stopServer(inputStream,clientSocket,serverSocket);
            }
        };

        // Démarrer le timer avec le délai spécifié
        timer.schedule(task, delay);
    }

    /**
     *   Reset timer
     *
     * @param inputStream the input stream
     * @param clientSocket the client socket
     * @param serverSocket the server socket
     */
    private void resetTimer(InputStream inputStream,Socket clientSocket,ServerSocket serverSocket) {
        // Annuler le timer actuel
        timer.cancel();

        // Créer un nouveau timer pour redémarrer le délai
        timer = new Timer();

        // Démarrer le nouveau timer
        startTimer(inputStream, clientSocket, serverSocket);
    }

    /**
     *   Stop server
     *
     * @param inputStream the input stream
     * @param clientSocket the client socket
     * @param serverSocket the server socket
     */
    public void stopServer(InputStream inputStream,Socket clientSocket,ServerSocket serverSocket) {
        isServerRunning = false;

        try {

            inputStream.close();
            if(clientSocket.isConnected())
            {
                clientSocket.close();
            }
            if(!serverSocket.isClosed())
            {
                serverSocket.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}