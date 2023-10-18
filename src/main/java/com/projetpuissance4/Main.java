package com.projetpuissance4;

import com.projetpuissance4.models.P4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        P4 p = new P4();
        System.out.println(p.toString());
        int i = 0;
        while(!p.JoueurGagnant(1) && !p.JoueurGagnant(2) )
        {
            Scanner scanner2 = new Scanner(System.in);
            System.out.print("Veuillez entrer la colonne : ");
            int Colonne = scanner2.nextInt();
            if(i%2 == 0)
            {
                p.setMatValeur(Colonne,2);
            }
            else {
                p.setMatValeur(Colonne,1);
            }
            System.out.println(p.toString());
            i++;
        }
        if(p.JoueurGagnant(1))
        {
            System.out.println("J1 gagant");
        } else if (p.JoueurGagnant(2)) {
            System.out.println("J2 gagant");
        } else{
            System.out.println("aucun gagnant");
        }
    }
}