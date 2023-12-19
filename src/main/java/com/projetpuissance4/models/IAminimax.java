package com.projetpuissance4.models;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.HashMap;
public class IAminimax {
    private int infinity = 9999;
    private static final int LIGNE = 6;
    private static final int COLONNE = 7;
    private int depth;
    private HashMap<Long,Integer> HM = new HashMap<Long,Integer>();
    private boolean maximizingPlayer; //true if maximizing the player else false if we need to minimizing the player
    public IAminimax()
    {

    }

    public int minimaxAlphaBeta(int player, int position, double alpha, double beta, int depth, boolean maximizingPlayer, P4 Grille)
    {
        P4 Grille2 = new P4(Grille.getMat());
        Grille2.setMatValeur(position, player);
        //System.out.println("grille2 : " + Grille2.toString());

        int[] eval = new int[7];

        if (depth == 0 || Grille2.JoueurGagnant(player)[0] == 1) {
            /*if (HM.size() < 8388593)
                HM.put(Grille2.gridToLong(Grille2.getMat()), evaluation(2, position, Grille2));*/
            return evaluation(player, position, Grille2);
        }

        /*if(HM.get(Grille2.gridToLong(Grille2.getMat())) != null)
        {
            System.out.println("trouver");
            Integer val = HM.get(Grille2.gridToLong(Grille2.getMat()));
            if(val != 0)
            {
                return evaluation(2, position,Grille2);
            }
        }*/

        if (maximizingPlayer) {
            int maxEval = -infinity;
            if (player == 1) {
                player++;
            } else if (player == 2) {
                player--;
            }
            if (maximizingPlayer) {
                maximizingPlayer = false;
            } else {
                maximizingPlayer = true;
            }
            for (int i = 0; i < 7; i++) {
                //System.out.println("bonjour : maximizing player " + player);
                eval[i] = minimaxAlphaBeta(player, i, alpha, beta, depth - 1,  false, Grille2);
                //System.out.println("maximizing player " + player + " score eval on column " + i + " = " + eval[i] + " on depth = " + depth);
                maxEval = max(maxEval, eval[i]);
                alpha = max(alpha, eval[i]);
                //System.out.println("    ALPHA  =  " + alpha);
                if (beta <= alpha)
                {
                    break;
                }
            }
            //System.out.println("MAX EVAL = " + maxEval);

            /*if (HM.size() < 8388593)
                HM.put(Grille2.gridToLong(Grille2.getMat()), evaluation(2, position, Grille2));*/

            return maxEval;
        } else {
            int minEval = infinity;
            if (player == 1) {
                player++;
            } else if (player == 2) {
                player--;
            }
            if (maximizingPlayer) {
                maximizingPlayer = false;
            } else {
                maximizingPlayer = true;
            }
            for (int i = 0; i < 7; i++) {
                eval[i] = minimaxAlphaBeta(player, i, alpha, beta, depth - 1, true, Grille2);
                //System.out.println("minimizing player " + player + " score eval on column " + i + " = " + eval[i] + " on depth = " + depth);
                minEval = min(minEval, eval[i]);
                beta = min(beta, eval[i]);
                //System.out.println("    BETA  =  " + beta);
                if (beta <= alpha)
                {
                    break;
                }
            }
            //System.out.println("MIN EVAL = " + minEval);

            /*if (HM.size() < 8388593)
                HM.put(Grille2.gridToLong(Grille2.getMat()), evaluation(2, position, Grille2));*/

            return minEval;
        }


    }


    public int minimax(int player, int position, int depth, boolean maximizingPlayer, P4 Grille) {
        P4 Grille2 = new P4(Grille.getMat());
        Grille2.setMatValeur(position, player);
        //System.out.println("grille2 : " + Grille2.toString());

        int[] eval = new int[7];

        if (depth == 0 || Grille2.JoueurGagnant(player)[0] == 1) {
            //System.out.println("eval enfait : "+evaluation(player, position, Grille2,maximizingPlayer));
            return evaluation(player, position, Grille2);
        }
        if (maximizingPlayer) {
            int maxEval = -infinity;
            if (player == 1) {
                player++;
            } else if (player == 2) {
                player--;
            }
            if (maximizingPlayer) {
                maximizingPlayer = false;
            } else {
                maximizingPlayer = true;
            }
            for (int i = 0; i < 7; i++) {
                //System.out.println("bonjour : maximizing player " + player);
                eval[i] = minimax(player, i, depth - 1, false, Grille2);
                //System.out.println("maximizing player " + player + " score eval on column " + i + " = " + eval[i] + " on depth = " + depth);
                maxEval = max(maxEval, eval[i]);
            }
            //System.out.println("MAX EVAL = " + maxEval);
            return maxEval;
        } else {
            int minEval = infinity;
            if (player == 1) {
                player++;
            } else if (player == 2) {
                player--;
            }
            if (maximizingPlayer) {
                maximizingPlayer = false;
            } else {
                maximizingPlayer = true;
            }
            for (int i = 0; i < 7; i++) {
                eval[i] = minimax(player, i, depth - 1, true, Grille2);
                //System.out.println("minimizing player " + player + " score eval on column " + i + " = " + eval[i] + " on depth = " + depth);
                minEval = min(minEval, eval[i]);
            }
            //System.out.println("MIN EVAL = " + minEval);
            return minEval;
        }

    }
    public int[] getValidPosition(P4 Grille)
    {
        int[] validPositions = new int[7];
        for (int i=0; i<7; i++)
        {
            if (Grille.checkGraviter(i) != 6 && Grille.checkGraviter(i) >= 0)
                validPositions[i] = 1;
            else
                validPositions[i] = 0;
        }
        return validPositions;
    }

    public int minimaxIT1(int player, P4 Grille)
    {
        int[] score = new int[7];
        int col = -1;
        int min = 0;
        for(int i = 0;i<7;i++)
        {
            P4 Grille2 = new P4(Grille.getMat());
            if(!Grille2.colonneFull(i))
            {
                Grille2.setMatValeur(i,player);
                //System.out.println(Grille2.toString());
                score[i] = -evaluation(player,i,Grille2);
                System.out.println("score " + score[i]);
                if(score[i] < min) {
                    min = score[i];
                    col = i;
                }
            }
        }
        return col;
    }

    public int minimaxIT2(int player, P4 Grille)
    {
        int player2 = 0;
        if(player == 2)
        {
            player2 = 1;
        }
        else if(player == 1)
        {
            player2 = 2;
        }
        int col = -1;
        int[] score = new int[7];
        int max = -999999;
        for(int i = 0;i<7;i++)
        {
            P4 Grille2 = new P4(Grille.getMat());
            if(!Grille2.colonneFull(i))
            {
                Grille2.setMatValeur(i,player);
                int min = 999999;
                for(int j = 0;j<7;j++) {
                    int[] score2 = new int[7];
                    P4 Grille3 = new P4(Grille2.getMat());
                    if (!Grille3.colonneFull(j)) {
                        Grille3.setMatValeur(j, player2);

                        score2[j] = evaluation(player, j, Grille3);
                        System.out.println("score2 " + j + " " + score2[j]);
                        if (score2[j] < min) {
                            min = score2[j];
                        }
                    }
                }
                score[i] = min;
                System.out.println("score " + score[i]);
            }
            if(score[i] > max) {
                max = score[i];
                col = i;
            }
        }
        System.out.println("score final : " + max);
        System.out.println("col : " + col);
        return col;
    }

    public int evaluation(int player, int column, P4 Grille) {
        int eval = 0;
        int line = 0;
        if (Grille.checkGraviter(column) >= 0) {
            line = Grille.checkGraviter(column)+1;
        }

        int[] tab = {1,2,3,4,3,2,1};

        int player2;
        if (player == 1) {
            player2 = 2;
        } else{
            player2 =1;
        }

        eval += tab[column];

        if (Grille.JoueurGagnant(player)[0] == 1) {
            eval = 1051;
        }
        if (Grille.JoueurGagnant(player2)[0] == 1) {
            eval = -1000;
        }
        //System.out.println("colonne eval = " + column);
        //System.out.println("ligne eval = " + line);

        for (int row = 0; row < LIGNE; row++) {
            for (int col = 0; col < COLONNE; col++) {
                if (col >=0 && row >=0 && col < COLONNE && row < LIGNE){
                    // Vérifiez l'horizontale (gauche à droite)
                    if (col + 2 < COLONNE && Grille.getPoint(row,col) == player && Grille.getPoint(row,col + 1) == player && Grille.getPoint(row,col + 2) == player) {
                        eval = eval + 50;
                    }
                    if (col + 2 < COLONNE && Grille.getPoint(row,col) == player2 && Grille.getPoint(row,col + 1) == player2 && Grille.getPoint(row,col + 2) == player2) {
                        eval = eval - 50;
                    }
                    // Vérifiez la verticale (bas vers le haut)
                    if (row + 2 < LIGNE && Grille.getPoint(row,col) == player && Grille.getPoint(row + 1,col) == player && Grille.getPoint(row + 2,col) == player) {
                        eval = eval + 50;
                    }
                    if (row + 2 < LIGNE && Grille.getPoint(row,col) == player2 && Grille.getPoint(row + 1,col) == player2 && Grille.getPoint(row + 2,col) == player2) {
                        eval = eval - 50;
                    }
                    // Vérifiez la diagonale ascendante (bas gauche vers haut droite)
                    if (row + 2 < LIGNE && col + 2 < COLONNE && Grille.getPoint(row,col) == player && Grille.getPoint(row + 1,col + 1) == player && Grille.getPoint(row + 2,col + 2) == player) {
                        eval = eval + 50;
                    }
                    if (row + 2 < LIGNE && col + 2 < COLONNE && Grille.getPoint(row,col) == player2 && Grille.getPoint(row + 1,col + 1) == player2 && Grille.getPoint(row + 2,col + 2) == player2) {
                        eval = eval - 50;
                    }
                    // Vérifiez la diagonale descendante (haut gauche vers bas droite)
                    if (row - 2 >= 0 && col + 2 < COLONNE && Grille.getPoint(row,col) == player && Grille.getPoint(row - 1,col + 1) == player && Grille.getPoint(row - 2,col + 2) == player) {
                        eval = eval + 50;
                    }
                    if (row - 2 >= 0 && col + 2 < COLONNE && Grille.getPoint(row,col) == player2 && Grille.getPoint(row - 1,col + 1) == player2 && Grille.getPoint(row - 2,col + 2) == player2) {
                        eval = eval - 50;
                    }
                }
            }
        }

        for (int row = 0; row < LIGNE; row++) {
            for (int col = 0; col < COLONNE; col++) {
                //System.out.println("row = " + row + "   col = " + col);
                if (col >=0 && row >=0){
                    // Vérifiez l'horizontale (gauche à droite)
                    if (col + 1 < COLONNE && Grille.getPoint(row,col) == player && Grille.getPoint(row,col + 1) == player) {
                        eval = eval + 30;
                    }
                    if (col + 1 < COLONNE && Grille.getPoint(row,col) == player2 && Grille.getPoint(row,col + 1) == player2) {
                        eval = eval - 30;
                    }
                    // Vérifiez la verticale (bas vers le haut)
                    if (row + 1 < LIGNE && Grille.getPoint(row,col) == player && Grille.getPoint(row + 1,col) == player) {
                        eval = eval + 30;
                    }
                    if (row + 1 < LIGNE && Grille.getPoint(row,col) == player2 && Grille.getPoint(row + 1,col) == player2) {
                        eval = eval - 30;
                    }
                    // Vérifiez la diagonale ascendante (bas gauche vers haut droite)
                    if (row + 1 < LIGNE && col + 1 < COLONNE && Grille.getPoint(row,col) == player && Grille.getPoint(row + 1,col + 1) == player) {
                        eval = eval + 30;
                    }
                    if (row + 1 < LIGNE && col + 1 < COLONNE && Grille.getPoint(row,col) == player2 && Grille.getPoint(row + 1,col + 1) == player2) {
                        eval = eval - 30;
                    }
                    // Vérifiez la diagonale descendante (haut gauche vers bas droite)
                    if (row - 1 >= 0 && col + 1 < COLONNE &&  Grille.getPoint(row,col) == player && Grille.getPoint(row - 1,col + 1) == player) {
                        eval = eval + 30;
                    }
                    if (row - 1 >= 0 && col + 1 < COLONNE &&  Grille.getPoint(row,col) == player2 && Grille.getPoint(row - 1,col + 1) == player2) {
                        eval = eval - 30;
                    }
                }
            }
        }
        return eval;
    }

    public int jouer(int player, int depth, P4 Grille)
    {
        long startTime = System.currentTimeMillis();

        int mineval = infinity;
        int[] eval = new int[7];
        int columnToPlay = 0;
        for (int i=0; i<7; i++) {
            if (!Grille.colonneFull(i)){

                eval[i] = minimaxAlphaBeta(player,i,-infinity, infinity,depth, true,Grille);
                //eval[i] = minimax(player,i,6, true,Grille);

                if(Grille.isJoueurGagnantWithThisToken(i,player))
                {
                    eval[i] = -1000000;
                }
                System.out.println("colonne " + i + ", eval = " + eval[i]);
                if (eval[i] < mineval) {
                    mineval = eval[i];
                    columnToPlay = i;
                }
            }
        }
        for (int i=0; i<7; i++) {
            System.out.println("eval " + i +" : " + eval[i]);
        }


        ArrayList<Integer> indicesOccurrences = new ArrayList<>();

        for (int i = 0; i < eval.length; i++) {
            if (eval[i] == mineval) {
                indicesOccurrences.add(i);
            }
        }
        int distanceMin = 99999999;

        for (int indice : indicesOccurrences) {
            int distance = Math.abs(indice - 3);
            if (distance < distanceMin) {
                distanceMin = distance;
                columnToPlay = indice;
            }
        }
        System.out.println(indicesOccurrences.toString());
        System.out.println("minEvalaution = " + mineval);
        System.out.println("columnToPlay = " + columnToPlay);
        long endTime = System.currentTimeMillis();

        // Calculez la différence
        long executionTime = endTime - startTime;
        System.out.println("                    TEMPS D'EXECUTION DE L'ALGORITHME : " + executionTime + " millisecondes");

        return columnToPlay;
    }
}
