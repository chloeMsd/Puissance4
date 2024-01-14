import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        TCPServerFile serveur = new TCPServerFile();
        serveur.setServerIP("127.0.0.3");
        serveur.setPort(8090);
        Thread serveurThread = new Thread(serveur);
        serveurThread.start();
    }
}