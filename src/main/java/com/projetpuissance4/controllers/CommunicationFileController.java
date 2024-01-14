package com.projetpuissance4.controllers;

import java.io.*;

public class CommunicationFileController {

    public static void writeToFile(String value,String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour lire depuis un fichier
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

    // Méthode pour traiter la sortie
    public static void processOutput(String output) {
        try {
            // Convertir la valeur lue en entier
            int entierSaisi = Integer.parseInt(output);
            System.out.println("Entier récupéré : " + entierSaisi);
        } catch (NumberFormatException e) {
            System.err.println("Erreur de conversion en entier : " + e.getMessage());
        }
    }

    // Méthode pour supprimer le fichier
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

    public static boolean doesFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

}
