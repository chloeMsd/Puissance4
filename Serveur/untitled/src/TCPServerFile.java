import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

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

    private static int nbClient;

    private static List<Socket> listClient;

    private static boolean isTwoPlayersConnected;

    /**
     *  Instantiates a new TCPServerFile
     *
     * @param port the port
     * @param serverIP the server ip
     */
    public TCPServerFile(int port, String serverIP) {
        this.port = port;
        this.serverIP = serverIP;
        listClient = new ArrayList<>();
    }
    /**
     *  Instantiates a new TCPServerFile
     */
    public TCPServerFile() {
        port = 0;
        serverIP = null;
        listClient = new ArrayList<>();
    }
    /**
     *  Is server running ? Test if the server is running
     *
     * @return boolean true if the server is running
     */
    public boolean isServerRunning() {
        return isServerRunning;
    }
    /**
     *  Sets port
     *
     * @param port the port
     */
    public void setPort(int port) {
        this.port = port;
    }
    /**
     *  Sets server ip
     *
     * @param serverIP the server ip
     */
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    /**
     *  Run the server
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

                // We add the client and actualize data on server
                listClient.add(clientSocket);
                nbClient++;
                if(nbClient == 2)
                {
                    this.chooseFirstPlayer();
                }
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {
            // While the server is open -> read messages
            while (isServerRunning) {

                String read = reader.readLine();
                //System.err.println("running");

                if (read != null) {
                    System.out.println(read);
                    // Help the client to close the thread of reconnection
                    /*if (read.equals("STOP")) {

                        // We tell to the client that he needs to close
                        writer.println("STOP");

                        // We close the close and remove it from the list
                        clientSocket.close();
                        listClient.remove(clientSocket);

                        // We actualize data
                        nbClient--;
                        isTwoPlayersConnected = false;


                        // We notify the other client that the other had left the server
                        if (listClient != null) {

                            for (Socket cSocket : listClient) {

                                // We check if the socket is valid or not
                                if (cSocket != null && !cSocket.isClosed()) {

                                    // Send the message "Other Player Left" to the client
                                    PrintWriter writ = new PrintWriter(cSocket.getOutputStream(), true);
                                    writ.println("Other Player Left");
                                }
                            }
                        }

                        System.out.println("Client disconnected : " + clientSocket.getInetAddress());
                    }*/

                    // Reading a played shot
                    if (read.matches("\\d+")) { // We check if the message is an integer

                        System.out.println("read");
                        // We notify the other client that the other had played a turn
                        if (listClient != null) {

                            for (Socket cSocket : listClient) {

                                // We check if the socket is valid or not
                                if (cSocket != null && !cSocket.isClosed()) {
                                    // Send the message "Number Column" to the client
                                    PrintWriter writ = new PrintWriter(cSocket.getOutputStream(), true);
                                    writ.println("N"+read);
                                }
                            }
                        }
                    }
                } /*else {
                    // We close the close and remove it from the list
                    clientSocket.close();
                    listClient.remove(clientSocket);

                    // We actualize data
                    nbClient--;
                    isTwoPlayersConnected = false;


                    // We notify the other client that the other had left the server
                    if (listClient != null) {

                        for (Socket cSocket : listClient) {

                            // We check if the socket is valid or not
                            if (cSocket != null && !cSocket.isClosed()) {

                                // Send the message "Other Player Left" to the client
                                PrintWriter writ = new PrintWriter(cSocket.getOutputStream(), true);
                                writ.println("Other Player Left");
                            }
                        }
                    }
                }*/
            }

        } catch (IOException IOE) {
            System.err.println("Client Disconnected !");
        }
    }
    /**
     *  Start delay timer
     * @param clientSocket the client socket
     *
     * @param inputStream the input stream
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
     *  Reset timer
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
     *  Stop server
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

    public void sendStart() throws IOException {
        if(nbClient == 2)
        {
            for (Socket cSocket : listClient) {

                // We check if the socket is valid or not
                if (cSocket != null && !cSocket.isClosed()) {

                    // Send the message "Other Player Left" to the client
                    PrintWriter writ = new PrintWriter(cSocket.getOutputStream(), true);
                    writ.println("START");
                    System.out.println("message send");
                }
            }
        }
    }

    public void chooseFirstPlayer() throws IOException {
        // We choose randomly the player who will start the game
        Random random = new Random();
        int playerStartoing = random.nextInt(2);

        int boucle = 0;

        for (Socket clientSocket : listClient) {
            PrintWriter writer;
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // We check if the socket is valid or not
            if (clientSocket != null && !clientSocket.isClosed()) {
                if (boucle == playerStartoing) {

                    // Send the message "You Start" to the good client
                    writer.println("You Start");

                } else if (boucle != playerStartoing) {

                    // Send the message "You Start" to the good client
                    writer.println("Opponent Will Start");
                }
            }
            boucle++;
        }
    }
}