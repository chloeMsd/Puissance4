package com.projetpuissance4.models;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * IAminimax
 */
public class IAminimax {
    private static final int INFINITY = 9999;
    private static final int LIGNE = 6;
    private static final int COLONNE = 7;
    public IAminimax() {

    }

    /**
     * Minimax Alpha Beta V2
     * @brief Algorithm Minimax V2 with Alpha-Beta pruning
     * @param player
     * @param position
     * @param alpha
     * @param beta
     * @param depth
     * @param maximizingPlayer
     * @param Grille
     * @return
     */
    public int minimaxAlphaBetaV2(int player, int position, double alpha, double beta, int depth, boolean maximizingPlayer, P4 Grille)
    {
        P4 Grille2 = new P4(Grille.getMat());
        Grille2.setMatValue(position, player);
        //System.out.println("grille2 : " + Grille2.toString());

        int[] eval = new int[7];

        if (depth == 0 || Grille2.playerWin(player)[0] == 1) {
            return evaluationV2(player, position, Grille2);
        }

        if (maximizingPlayer) {
            int maxEval = -INFINITY;
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
                eval[i] = minimaxAlphaBetaV2(player, i, alpha, beta, depth - 1,  false, Grille2);
                //System.out.println("maximizing player " + player + " score eval on column " + i + " = " + eval[i] + " on depth = " + depth);
                maxEval = max(maxEval, eval[i]);
                alpha = max(alpha, eval[i]);
                //System.out.println("    ALPHA  =  " + alpha);
                if (beta <= alpha)
                {
                    break;
                }
            }

            return maxEval;
        } else {
            int minEval = INFINITY;
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
                eval[i] = minimaxAlphaBetaV2(player, i, alpha, beta, depth - 1, true, Grille2);
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

    /**
     * Minimax Alpha Beta V1
     * @brief Algorithm Minimax V1 with Alpha-Beta pruning
     * @param player
     * @param position
     * @param alpha
     * @param beta
     * @param depth
     * @param maximizingPlayer
     * @param Grille
     * @return
     */
    public int minimaxAlphaBetaV1(int player, int position, double alpha, double beta, int depth, boolean maximizingPlayer, P4 Grille)
    {
        P4 Grille2 = new P4(Grille.getMat());
        Grille2.setMatValue(position, player);
        //System.out.println("grille2 : " + Grille2.toString());

        int[] eval = new int[7];

        if (depth == 0 || Grille2.playerWin(player)[0] == 1) {
            return evaluation(player, position, Grille2);
        }

        if (maximizingPlayer) {
            int maxEval = -INFINITY;
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
                eval[i] = minimaxAlphaBetaV1(player, i, alpha, beta, depth - 1,  false, Grille2);
                //System.out.println("maximizing player " + player + " score eval on column " + i + " = " + eval[i] + " on depth = " + depth);
                maxEval = max(maxEval, eval[i]);
                alpha = max(alpha, eval[i]);
                //System.out.println("    ALPHA  =  " + alpha);
                if (beta <= alpha)
                {
                    break;
                }
            }

            return maxEval;
        } else {
            int minEval = INFINITY;
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
                eval[i] = minimaxAlphaBetaV1(player, i, alpha, beta, depth - 1, true, Grille2);
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

    /**
     * Evaluation V1
     * @brief Evaluate the grid
     * @param player
     * @param column
     * @param Grille
     * @return
     */
    public int evaluation(int player, int column, P4 Grille) {
        int eval = 0;
        int line = 0;
        if (Grille.gravityCheck(column) >= 0) {
            line = Grille.gravityCheck(column)+1;
        }

        int[] tab = {1,2,3,4,3,2,1};

        int player2;
        if (player == 1) {
            player2 = 2;
        } else{
            player2 =1;
        }

        if (Grille.playerWin(player)[0] == 1) {
            eval = 1000;
        }
        if (Grille.playerWin(player2)[0] == 1) {
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

    /**
     * Evaluation V2
     * @brief Evaluate a grid
     * @param player
     * @param column
     * @param Grille
     * @return
     */
    public int evaluationV2(int player, int column, P4 Grille) {
        int eval = 0;
        int line = 0;

        if (Grille.gravityCheck(column) >= 0) {
            line = Grille.gravityCheck(column) + 1;
        }

        int[] tab = {1, 2, 3, 4, 3, 2, 1};

        int player2 = (player == 1) ? 2 : 1;

        if (Grille.playerWin(player)[0] == 1) {
            eval = 1000;
        }

        if (Grille.playerWin(player2)[0] == 1) {
            eval = -1000;
        }

        for (int row = 0; row < LIGNE; row++) {
            for (int col = 0; col < COLONNE; col++) {
                if (col >= 0 && row >= 0 && col < COLONNE && row < LIGNE) {
                    // Vérifiez l'horizontale (gauche à droite)
                    if (col + 3 < COLONNE) {
                        int countPlayer = 0;
                        int countPlayer2 = 0;

                        for (int i = 0; i < 4; i++) {
                            int token = Grille.getPoint(row, col + i);

                            if (token == player) {
                                countPlayer++;
                            } else if (token == player2) {
                                countPlayer2++;
                            }
                        }

                        if (countPlayer == 3 && countPlayer2 == 0) {
                            eval += 50;
                        } else if (countPlayer2 == 3 && countPlayer == 0) {
                            eval -= 50;
                        }
                    }

                    // Vérifiez la verticale (bas vers le haut)
                    if (row + 3 < LIGNE) {
                        int countPlayer = 0;
                        int countPlayer2 = 0;

                        for (int i = 0; i < 4; i++) {
                            int token = Grille.getPoint(row + i, col);

                            if (token == player) {
                                countPlayer++;
                            } else if (token == player2) {
                                countPlayer2++;
                            }
                        }

                        if (countPlayer == 3 && countPlayer2 == 0) {
                            eval += 50;
                        } else if (countPlayer2 == 3 && countPlayer == 0) {
                            eval -= 50;
                        }
                    }

                    // Vérifiez la diagonale ascendante (bas gauche vers haut droite)
                    if (row + 3 < LIGNE && col + 3 < COLONNE) {
                        int countPlayer = 0;
                        int countPlayer2 = 0;

                        for (int i = 0; i < 4; i++) {
                            int token = Grille.getPoint(row + i, col + i);

                            if (token == player) {
                                countPlayer++;
                            } else if (token == player2) {
                                countPlayer2++;
                            }
                        }

                        if (countPlayer == 3 && countPlayer2 == 0) {
                            eval += 50;
                        } else if (countPlayer2 == 3 && countPlayer == 0) {
                            eval -= 50;
                        }
                    }

                    // Vérifiez la diagonale descendante (haut gauche vers bas droite)
                    if (row - 3 >= 0 && col + 3 < COLONNE) {
                        int countPlayer = 0;
                        int countPlayer2 = 0;

                        for (int i = 0; i < 4; i++) {
                            int token = Grille.getPoint(row - i, col + i);

                            if (token == player) {
                                countPlayer++;
                            } else if (token == player2) {
                                countPlayer2++;
                            }
                        }

                        if (countPlayer == 3 && countPlayer2 == 0) {
                            eval += 50;
                        } else if (countPlayer2 == 3 && countPlayer == 0) {
                            eval -= 50;
                        }
                    }
                }
            }
        }

        return eval;
    }


    /**
     * Play V2
     * @brief Play with V2 Evaluation
     * @param player
     * @param depth
     * @param Grille
     * @return
     */

    public int playV2(int player, int depth, P4 Grille)
    {
        long startTime = System.currentTimeMillis();

        int mineval = INFINITY;
        int[] eval = new int[7];
        int columnToPlay = 0;
        for (int i=0; i<7; i++) {
            if (!Grille.columnFull(i)){

                eval[i] = minimaxAlphaBetaV2(player,i,-INFINITY, INFINITY,depth, true,Grille);
                //eval[i] = minimax(player,i,6, true,Grille);

                if(Grille.isPlayerWinWithThisToken(i,player))
                {
                    eval[i] = -1000000;
                }
                System.out.println("colonne " + i + ", eval = " + eval[i]);
                if (eval[i] < mineval) {
                    mineval = eval[i];
                    columnToPlay = i;
                }
            }
            else {
                eval[i] = INFINITY;
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

    /**
     *
     * @param player
     * @param depth
     * @param Grille
     * @return
     */
    public int playV1(int player, int depth, P4 Grille)
    {
        long startTime = System.currentTimeMillis();

        int mineval = INFINITY;
        int[] eval = new int[7];
        int columnToPlay = 0;
        for (int i=0; i<7; i++) {
            if (!Grille.columnFull(i)){

                eval[i] = minimaxAlphaBetaV1(player,i,-INFINITY, INFINITY,depth, true,Grille);
                //eval[i] = minimax(player,i,6, true,Grille);

                if(Grille.isPlayerWinWithThisToken(i,player))
                {
                    eval[i] = -1000000;
                }
                System.out.println("colonne " + i + ", eval = " + eval[i]);
                if (eval[i] < mineval) {
                    mineval = eval[i];
                    columnToPlay = i;
                }
            }
            else {
                eval[i] = INFINITY;
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
