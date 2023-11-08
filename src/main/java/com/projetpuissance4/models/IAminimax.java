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
        int[] validPosition = getValidPosition(Grille);
        int eval;
        if (depth == 0 || Grille.isJoueurGagnantWithThisToken(player, position) == true)
        {
            return eval = evaluation(player, position, Grille);
        }

        if (maximizingPlayer)
        {
            int maxEval = -infinity;
            for (int i=0; i<7; i++)
            {
                if (validPosition[i] == 1)
                {
                    eval = minimax(player, i, depth - 1, false, Grille);
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
                    eval = minimax(player, i, depth - 1, true, Grille);
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
            if (Grille.checkGraviter(i) != 6)
                validPositions[i] = 1;
            else
                validPositions[i] = 0;
        }
        return validPositions;
    }

    public int evaluation(int player, int column, P4 Grille) {
        int eval = 0;

        eval = eval + evaluateLines(column, player, Grille);
        eval = eval + evaluateColumns(column, player, Grille);
        eval = eval + evaluateDiagonals(column, player, Grille);

        return eval;
    }

    public int evaluateLines(int column, int player, P4 Grille)
    {
        int eval =0;
        int consecutiveToken = 0;
        int emptyToken = 0;
        int line = Grille.checkGraviter(column);

        for (int i=-3; i<=3; i++)
        {
            int currentCol = column + i;
            if (currentCol< 7 && currentCol>=0)
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
        int line = Grille.checkGraviter(column);

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
        int line = Grille.checkGraviter(column);

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
            eval = evaluation(player, i, Grille);
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
