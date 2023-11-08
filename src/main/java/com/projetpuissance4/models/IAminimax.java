package com.projetpuissance4.models;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class IAminimax {
    private int infinity = 9999;
    private static final int LIGNE = 6;
    private static final int COLONNE = 7;
    private int depth;
    private boolean maximizingPlayer; //true if maximizing the player else false if we need to minimizing the player
    public IAminimax()
    {

    }

    public int minimax(int player, int position, int depth, boolean maximizingPlayer, P4 Grille)
    {
        if(player == 1){
            player++;
        }
        else if (player == 2){
            player--;
        }
        P4 Grille2 = new P4(Grille.getMat());
        Grille2.setMatValeur(position,player);
        System.out.println("grille2 : " + Grille2.toString());
        int[] validPosition = getValidPosition(Grille2);
        int eval;

        if (depth == 0 || Grille2.JoueurGagnant(player)[0] == 1)
        {
            System.out.println("eval enfait : "+evaluation(player, position, Grille2,maximizingPlayer));
            return evaluation(player, position, Grille2,maximizingPlayer);
        }

        if (maximizingPlayer)
        {
            int maxEval = -infinity;
            for (int i=0; i<7; i++)
            {
                if (validPosition[i] == 1)
                {
                    eval = minimax(player, i, depth - 1, false, Grille2);
                    maxEval= max(maxEval, eval);
                }
            }
            return maxEval;
        }
        else
        {
            int minEval = infinity;
            for (int i=0; i<7; i++)
            {
                if (validPosition[i] == 1)
                {
                    eval = minimax(player, i, depth - 1, true, Grille2);
                    minEval= min(minEval, eval);
                }
            }
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

    public int evaluation(int player, int column, P4 Grille, boolean isMaximise) {
        /*int eval = 0;
        eval += evaluateLines(column, player, Grille);
        eval += evaluateColumns(column, player, Grille);
        eval += evaluateDiagonals(column, player, Grille);*/
        int eval = 0;
        int[] tab = {1,2,3,4,3,2,1};
        int player2;
        if(player == 1) {player2 = 2;}
        else{player2 =1;}
        if(isMaximise)
        {
            eval += tab[column];
        }
        else {
            eval -= tab[column];
        }
        if(Grille.JoueurGagnant(player)[0] == 1)
        {
            eval = 100 + tab[column];
        }
        else if (Grille.JoueurGagnant(player2)[0] == 1)
        {
            eval = -100 - tab[column];
        }

        return eval;
    }

    public int evaluateLines(int column, int player, P4 Grille)
    {
        int eval =0;
        int consecutiveToken = 0;
        int emptyToken = 0;
        int line = -1;
        if(Grille.checkGraviter(column) >= 0)
        {
            line = Grille.checkGraviter(column);
        }

        for (int i=-3; i<=3; i++)
        {
            int currentCol = column + i;
            if (currentCol< 7 && currentCol>=0 && line >= 0)
            {
                if (Grille.getPoint(line,currentCol) == player)
                {
                    consecutiveToken++;
                } else if(Grille.getPoint(line,currentCol) == 0){
                    emptyToken++;
                }
            }
        }

        if (consecutiveToken > 0)
        {
            eval = evaluateConsecutiveToken(consecutiveToken, emptyToken);
        }
        return eval;
    }

    public int evaluateColumns(int column, int player, P4 Grille)
    {
        int eval =0;
        int consecutiveToken = 0;
        int emptyToken = 0;
        int line = -1;
        if(Grille.checkGraviter(column) >= 0)
        {
            line = Grille.checkGraviter(column);
        }

        for (int i=-3; i<=3; i++)
        {
            int currentLine = line + i;
            if (currentLine< 6 && currentLine>=0)
            {
                if (Grille.getPoint(currentLine,column) == player)
                {
                    consecutiveToken++;
                } else if(Grille.getPoint(currentLine,column) == 0){
                    emptyToken++;
                }
            }
        }

        if (consecutiveToken > 0)
        {
            eval = evaluateConsecutiveToken(consecutiveToken, emptyToken);
        }
        return eval;
    }

    public int evaluateDiagonals(int column, int player, P4 Grille)
    {
        int eval =0;
        int consecutiveToken = 0;
        int emptyToken = 0;
        int line = -1;
        if(Grille.checkGraviter(column) >= 0)
        {
            line = Grille.checkGraviter(column);
        }

        for (int i=-3; i<=3; i++)
        {
            int currentLine = line + i;
            int currentColumn = column + 1;
            if (currentLine< 6 && currentLine>=0 && currentColumn < 7 && currentLine >= 0)
            {
                if (Grille.getPoint(currentLine,currentColumn) == player)
                {
                    consecutiveToken++;
                } else if(Grille.getPoint(currentLine,currentColumn) == 0){
                    emptyToken++;
                }
            }
        }

        if (consecutiveToken > 0)
        {
            eval = evaluateConsecutiveToken(consecutiveToken, emptyToken);
        }
        return eval;
    }
    public int evaluateConsecutiveToken(int consecutiveToken, int emptyToken)
    {
        int eval = 0;

        if (consecutiveToken == 3 && emptyToken == 1)
        {
            eval = eval + 1000;
        } else if (consecutiveToken == 2 && emptyToken == 2){
            eval = eval + 100;
        } else if (consecutiveToken == 1 && emptyToken == 3){
            eval = eval + 10;
        }
        return eval;
    }

    public int jouer(int player, P4 Grille)
    {
        int maxEval = -1;
        int eval = 0;
        int columnToPlay = 0;
        for (int i=0; i<7; i++)
        {
            eval = minimax(player,i,1,true,Grille);
            if (eval >= maxEval)
            {
                System.out.println("ouiii");
                maxEval = eval;
                columnToPlay = i;
            }
        }
        System.out.println("maxEvalaution = " + maxEval);
        System.out.println("columnToPlay = " + columnToPlay);
        if (Grille.getPoint(0, columnToPlay) == 0 && columnToPlay<=6 && columnToPlay>=0) {
            return columnToPlay;
        } else {
            return (columnToPlay -2);
        }
    }
}
