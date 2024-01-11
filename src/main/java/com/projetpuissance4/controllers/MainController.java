package com.projetpuissance4.controllers;

import com.projetpuissance4.models.IAExploration;
import com.projetpuissance4.models.IAminimax;
import com.projetpuissance4.models.IARandom;
import com.projetpuissance4.models.P4;

import java.util.Random;

public class MainController {
    private P4 p = new P4();

    public MainController(){
        /*System.out.println(p.toString());
        IAminimax IAminimax = new IAminimax();
        IAnv0 IAnv0 = new IAnv0();

        int i = 0;
        while((p.JoueurGagnant(1))[0] == 0 && (p.JoueurGagnant(2))[0] == 0 )
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
        if((p.JoueurGagnant(1))[0] == 1)
        {
            System.out.println("J1 gagant");
        } else if ((p.JoueurGagnant(2))[0] == 1) {
            System.out.println("J2 gagant");
        } else{
            System.out.println("aucun gagnant");
        }*/
    }

    public int Jouer(P4 p)
    {
        IAminimax IAminimax = new IAminimax();
        IARandom IAnv0 = new IARandom();
        IAExploration IA = new IAExploration();

        Random rand = new Random();
        int i = 1;
        while((p.playerWin(1))[0] == 0 && (p.playerWin(2))[0] == 0 && p.checkDraw()==false )
        {
            int joueur;
            int column;
            if(i%2 == 0)
            {
                joueur = 2;
                column = IA.play(p);
                p.setMatValue(column,2);
                System.out.println(p.toString());
            }
            else {
                joueur = 1;
                column = IAminimax.playV2(1,7,p);
                p.setMatValue(column,1);
                System.out.println(p.toString());
            }
            i++;
        }
        if((p.playerWin(1))[0] == 1)
        {
            return 1;
        } else if ((p.playerWin(2))[0] == 1) {
            return 2;
        } else{
            return 0;
        }
    }

    public void plusieursGrilles(int nombre, P4 p)
    {
        int number;
        int J1 = 0;
        int J2 = 0;
        int egalite = 0;

        for (int i=0; i<nombre; i++)
        {
            number = Jouer(p);
            if (number ==1 )
                J1++;
            else if (number == 2)
                J2++;
            else
                egalite++;

            p.matrixZero();
        }
        System.out.println("Sur " + nombre + " parties, J1 gagne " + J1 + " fois, J2 gagne " + J2 + " fois, egalité " + egalite + " fois !");
    }
    public static void main(String[] args)
    {
        MainController control = new MainController();
        P4 p = new P4();
        control.plusieursGrilles(1, p);
        /*TCPClientController tcp = new TCPClientController();
        tcp.setClient();*/
    }


}