package com.projetpuissance4.models;

import java.io.IOException;
import java.io.*;

/**
 * The type settings
 */
public class Settings {

    /**
     * the ip
     */
    private String ip;
    /**
     * the port
     */
    private String port;

    /**
     *   Settings
     */
    public Settings(){}

    /**
     *   Settings
     *
     * @param IP the ip
     * @param port the port
     */
    public Settings(String IP, String port) {
        this.ip = IP;
        this.port = port;
    }

    /**
     *   Gets ip
     *
     * @return String the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     *   Sets ip
     *
     * @param ip the ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     *   Gets port
     *
     * @return String the port
     */
    public String getPort() {
        return port;
    }

    /**
     *   Sets port
     *
     * @param port the port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     *   Save settings
     *
     * @param IP the ip
     * @param Port the port
     * @throws IOException
     */
    public void saveSettings(String IP, String Port, String Filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(Filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
            oos.writeObject(IP);
            oos.writeObject(Port);
            // Mise à jour de la TableView
            setPort(Port);
            setIp(IP);
            oos.close();
            fos.close();
        } catch (NotSerializableException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     *   Load list employees
     *
     * @param Filename the filename
     */
    public void loadSettings(String Filename) {
        try {
            FileInputStream fileIn = new FileInputStream(Filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            setIp((String) in.readObject());
            setPort((String) in.readObject());
            in.close();
            fileIn.close();
        } catch (IOException i) {
            try {
                FileOutputStream fileOut = new FileOutputStream(Filename);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject("");
                out.writeObject("");
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }

    }
}

