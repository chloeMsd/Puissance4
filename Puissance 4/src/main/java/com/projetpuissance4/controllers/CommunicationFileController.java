package com.projetpuissance4.controllers;

import java.io.*;

/**
 * Class to communicate with the application and the client
 */
public class CommunicationFileController {

    /**
     * To write the value on a file (filename)
     * @param value
     * @param fileName
     */
    public static void writeToFile(String value,String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * To read the content of the file (filename)
     * @param fileName
     * @return the content of the file
     */
    public static String readFromFile(String fileName) {
        StringBuilder content = new StringBuilder();
        while(!doesFileExist(fileName))
        {

        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }

    /**
     * to delete the file (filename)
     * @param fileName
     */
    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Fichier supprimé avec succès.");
            } else {
                System.err.println("Échec de la suppression du fichier.");
            }
        }
    }

    /**
     *
     * @param fileName
     * @return true if the file exists/ false if not
     */
    public static boolean doesFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

}
