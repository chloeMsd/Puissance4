package com.projetpuissance4.controllers;


import com.projetpuissance4.Puissance4;
import com.projetpuissance4.models.Settings;
import com.projetpuissance4.models.TCPFileSender;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * The type TCP client controller
 */
public class TCPClientController {
    /**
     * the client TCP file sender
     */
    static TCPFileSender client = new TCPFileSender();

    /**
     *   Sets client
     *
     * @param stage the stage
     */
    public void setClient(Stage stage)
    {
        stage.getScene().getStylesheets().add(Puissance4.class.getResource("style.css").toExternalForm());
        Settings settings = new Settings();
        settings.loadSettings("SettingsClient.ser");
        if(settings.getPort() == null || settings.getIp() == null || Objects.equals(settings.getPort(), "") || Objects.equals(settings.getIp(), ""))
        {
            System.out.println("Connexion au server impossible.");
        }
        else {

            client.setServerAddress(settings.getIp());
            client.setServerPort( Integer.parseInt(settings.getPort()));
            client.run();

            if(client.isCo())
            {
                System.out.println("Envoi des données effectuées.");
            }
            else {
                System.out.println("Enregistrement des données effectuées.");
            }
        }
    }
    public void setClientDot(Stage stage, Circle dotStatut)
    {
        stage.getScene().getStylesheets().add(Puissance4.class.getResource("style.css").toExternalForm());
        Settings settings = new Settings();
        settings.loadSettings("SettingsClient.ser");
        if(settings.getPort() == null || settings.getIp() == null || Objects.equals(settings.getPort(), "") || Objects.equals(settings.getIp(), ""))
        {
            System.out.println("Connexion au server impossible.");
        }
        else {

            client.setServerAddress(settings.getIp());
            client.setServerPort( Integer.parseInt(settings.getPort()));
            client.run();

            if(client.isCo())
            {
                dotStatut.setFill(Color.GREEN);
            }
            else {
                dotStatut.setFill(Color.RED);
            }
        }
    }
}
