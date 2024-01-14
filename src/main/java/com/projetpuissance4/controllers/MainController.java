package com.projetpuissance4.controllers;

import com.projetpuissance4.models.*;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
        IAMinimax IAminimax = new IAMinimax();
        IARandom IAnv0 = new IARandom();
        IAExploration IA = new IAExploration();

        Random rand = new Random();
        int i = rand.nextInt();
        while((p.playerWin(1))[0] == 0 && (p.playerWin(2))[0] == 0 && p.checkDraw()==false )
        {
            int joueur;
            int column;
            if(i%2 == 0)
            {
                joueur = 2;
                column = IAnv0.Play();
                p.setMatValue(column,joueur);
                //System.out.println(p.toString());
            }
            else {
                joueur = 1;
                column = IA.play(p);
                p.setMatValue(column,1);
                //System.out.println(p.toString());
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
            System.out.println(p.toString());
            p.matrixZero();
        }
        System.out.println("Sur " + nombre + " parties, J1 gagne " + J1 + " fois, J2 gagne " + J2 + " fois, egalité " + egalite + " fois !");
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        /*MainController control = new MainController();
        P4 p = new P4();
        control.plusieursGrilles(50, p);*/
        /*TCPClientController tcp = new TCPClientController();
        tcp.setClient();*/

        TCPClientP4 tcpClientP4 = new TCPClientP4("127.0.0.3",8090);
        tcpClientP4.connectToServer();
        TimeUnit.SECONDS.sleep(3);
        tcpClientP4.startReceive();
    }


}