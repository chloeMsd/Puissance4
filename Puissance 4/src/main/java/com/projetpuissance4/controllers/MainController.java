package com.projetpuissance4.controllers;

import com.projetpuissance4.models.*;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainController {
    private Grid p = new Grid();

    public MainController(){

    }

    public static int Jouer(Grid p, int dephtJ1, int dephtJ2)
    {
        IAMinimax IAminimax = new IAMinimax();
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
                column = IAminimax.playV2(2,dephtJ2,p);
                p.setMatValue(column,joueur);
                //System.out.println(p.toString());
            }
            else {
                joueur = 1;
                column = IAminimax.playV2(1,dephtJ1,p);
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

    public static void plusieursGrilles(int nombre, Grid p, int dephtJ1, int dephtJ2)
    {
        int number;
        int J1 = 0;
        int J2 = 0;
        int egalite = 0;

        for (int i=0; i<nombre; i++)
        {
            number = Jouer(p,dephtJ1,dephtJ2);
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

    public static String arrayToString(int[][] array) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                sb.append(array[i][j]);

                if (j < array[i].length - 1) {
                    sb.append(", "); // Add a comma and space if not the last element in the row
                }
            }

            if (i < array.length - 1) {
                sb.append("\n"); // Add a new line if not the last row
            }
        }

        return sb.toString();
    }

    /*public static void main(String[] args) throws IOException, InterruptedException {
        MainController control = new MainController();
        Grid p = new Grid();
        //plusieursGrilles(1,p,3,3);
        int[][] tab = new int[8][8];
        for(int i = 0; i<8;i++)
        {
            for(int j = 0; j<8;j++)
            {
                System.out.println(" RES POUR : J1 "+(i+1)+", J2 "+(j+1));
                tab[i][j] = Jouer(p,i+1,j+1);
                p.matrixZero();
            }
        }
        System.out.println(arrayToString(tab));



        //control.plusieursGrilles(1, p);

        /*TCPClientController tcp = new TCPClientController();
        tcp.setClient();*/

        /*TCPClientP4 tcpClientP4 = new TCPClientP4("127.0.0.3",8090);
        tcpClientP4.connectToServer();
        TimeUnit.SECONDS.sleep(3);
        tcpClientP4.startReceive();
      }*/



}