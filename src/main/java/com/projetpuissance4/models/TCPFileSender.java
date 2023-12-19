package com.projetpuissance4.models;

import java.io.IOException;
import java.io.*;
import java.net.Socket;

/**
 * The type TCPFileSender
 */
public class TCPFileSender implements Runnable {

    /**
     * the server address ip
     */
    private String serverAddress;
    /**
     * the server port
     */
    private int serverPort;
    /**
     * boolean isCo
     *
     * True if the server is connecting
     */
    private boolean isCo = false;

    /**
     *   TCP file sender
     *
     * @param serverAddress the server address
     * @param serverPort the server port
     */
    public TCPFileSender(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     *   Sets server address
     *
     * @param serverAddress the server address
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    /**
     *   Sets server port
     *
     * @param serverPort the server port
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     *   Is co ? Test if the server is connecting
     *
     * @return boolean true if it is connecting
     */
    public boolean isCo() {
        return isCo;
    }

    /**
     *   Instantiates a new TCPFileSender
     */
    public TCPFileSender() {
        serverAddress = null;
        serverPort = 0;
    }

    /**
     *   Run
     */
    @Override
    public void run() {
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            if(socket.isConnected())
            {
                isCo = true;
                System.out.println("Connecté au serveur " + serverAddress + ":" + serverPort);
                int value = 42;
                sendValue(socket, value);
                socket.close();

                System.out.println("L'entier a été envoyé avec succès.");
            }
            else {
                isCo = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            isCo = false;
        }
    }

    /**
     *   Send File
     *
     * @param socket the socket
     * @param value the value to send
     * @throws IOException
     */
    private void sendValue(Socket socket, int value) throws IOException {
        if (!socket.isConnected()) {
            System.out.println("La connexion au serveur est fermée. Impossible d'envoyer la donnée.");
            return;
        }
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(value);
        outputStream.flush();

    }
}