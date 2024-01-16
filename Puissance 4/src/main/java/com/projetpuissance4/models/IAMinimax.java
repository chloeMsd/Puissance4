package com.projetpuissance4.models;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * IAminimax
 */
public class IAMinimax {
    private static final int INFINITY = 9999;
    private static final int LINE = 6;
    private static final int COLUMN = 7;
    public IAMinimax() {

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
     * @param Grid
     * @return
     */
    public int minimaxAlphaBetaV2(int player, int position, double alpha, double beta, int depth, boolean maximizingPlayer, Grid Grid)
    {
        com.projetpuissance4.models.Grid Grid2 = new Grid(Grid.getMatrix());
        Grid2.setMatValue(position, player);

        int[] eval = new int[7];

        if (depth == 0 || Grid2.playerWin(player)[0] == 1) {
            return evaluation(player, position, Grid2);
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
                eval[i] = minimaxAlphaBetaV2(player, i, alpha, beta, depth - 1,  false, Grid2);
                maxEval = max(maxEval, eval[i]);
                alpha = max(alpha, eval[i]);
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
                eval[i] = minimaxAlphaBetaV2(player, i, alpha, beta, depth - 1, true, Grid2);
                minEval = min(minEval, eval[i]);
                beta = min(beta, eval[i]);
                if (beta <= alpha)
                {
                    break;
                }
            }
            return minEval;
        }
    }


    /**
     * Minimax V1
     * @brief Algorithm Minimax V1
     * @param player
     * @param position
     * @param depth
     * @param maximizingPlayer
     * @param Grid
     * @return
     */
    public int minimaxV1(int player, int position, int depth, boolean maximizingPlayer, Grid Grid)
    {
        com.projetpuissance4.models.Grid Grid2 = new Grid(Grid.getMatrix());
        Grid2.setMatValue(position, player);

        int[] eval = new int[7];

        if (depth == 0 || Grid2.playerWin(player)[0] == 1) {
            return evaluation(player, position, Grid2);
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
                eval[i] = minimaxV1(player, i, depth - 1,  false, Grid2);
                maxEval = max(maxEval, eval[i]);
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
                eval[i] = minimaxV1(player, i,depth - 1, true, Grid2);
                minEval = min(minEval, eval[i]);
            }
            return minEval;
        }


    }

    /**
     * Evaluation
     * @brief Evaluate a grid
     * @param player
     * @param column
     * @param Grid
     * @return
     */
    public int evaluation(int player, int column, Grid Grid) {
        int eval = 0;

        int player2 = (player == 1) ? 2 : 1;

        if (Grid.playerWin(player)[0] == 1) {
            eval = 1000;
        }

        if (Grid.playerWin(player2)[0] == 1) {
            eval = -1000;
        }

        for (int row = 0; row < LINE; row++) {
            for (int col = 0; col < COLUMN; col++) {
                if (col >= 0 && row >= 0 && col < COLUMN && row < LINE) {
                    // Check horizontal (left to right)
                    if (col + 3 < COLUMN) {
                        int countPlayer = 0;
                        int countPlayer2 = 0;
                        int empty = 0;

                        for (int i = 0; i < 4; i++) {
                            int token = Grid.getPoint(row, col + i);

                            if (token == player) {
                                countPlayer++;
                            } else if (token == player2) {
                                countPlayer2++;
                            } else if (token == 0) {
                                empty++;
                            }
                        }

                        if (countPlayer == 3 && empty == 1) {
                            eval += 100;
                        }
                        else if (countPlayer == 3 && empty == 0) {
                            eval -= 50;
                        }
                        else if (countPlayer == 2 && empty == 2) {
                            eval += 20;
                        }
                        else if (countPlayer == 2 && countPlayer2 != 0) {
                            eval -= 20;
                        }
                        else if (countPlayer2 == 3 && empty == 1) {
                            eval -= 100;
                        }
                        else if (countPlayer2 == 3 && empty == 0) {
                            eval += 50;
                        }
                        else if (countPlayer2 == 2 && empty == 2) {
                            eval -= 20;
                        }
                        else if (countPlayer2 == 2 && countPlayer != 0) {
                            eval += 20;
                        }
                    }

                    // Check vertical (bottom up)
                    if (row + 3 < LINE) {
                        int countPlayer = 0;
                        int countPlayer2 = 0;
                        int empty = 0;

                        for (int i = 0; i < 4; i++) {
                            int token = Grid.getPoint(row + i, col);

                            if (token == player) {
                                countPlayer++;
                            } else if (token == player2) {
                                countPlayer2++;
                            } else if (token == 0) {
                                empty++;
                            }
                        }

                        if (countPlayer == 3 && empty == 1) {
                            eval += 100;
                        }
                        else if (countPlayer == 3 && empty == 0) {
                            eval -= 50;
                        }
                        else if (countPlayer == 2 && empty == 2) {
                            eval += 20;
                        }
                        else if (countPlayer == 2 && countPlayer2 != 0) {
                            eval -= 20;
                        }
                        else if (countPlayer2 == 3 && empty == 1) {
                            eval -= 100;
                        }
                        else if (countPlayer2 == 3 && empty == 0) {
                            eval += 50;
                        }
                        else if (countPlayer2 == 2 && empty == 2) {
                            eval -= 20;
                        }
                        else if (countPlayer2 == 2 && countPlayer != 0) {
                            eval += 20;
                        }
                    }

                    // Check the ascending diagonal (bottom left to top right)
                    if (row + 3 < LINE && col + 3 < COLUMN) {
                        int countPlayer = 0;
                        int countPlayer2 = 0;
                        int empty = 0;

                        for (int i = 0; i < 4; i++) {
                            int token = Grid.getPoint(row + i, col + i);

                            if (token == player) {
                                countPlayer++;
                            } else if (token == player2) {
                                countPlayer2++;
                            } else if (token == 0) {
                                empty++;
                            }
                        }

                        if (countPlayer == 3 && empty == 1) {
                            eval += 100;
                        }
                        else if (countPlayer == 3 && empty == 0) {
                            eval -= 50;
                        }
                        else if (countPlayer == 2 && empty == 2) {
                            eval += 20;
                        }
                        else if (countPlayer == 2 && countPlayer2 != 0) {
                            eval -= 20;
                        }
                        else if (countPlayer2 == 3 && empty == 1) {
                            eval -= 100;
                        }
                        else if (countPlayer2 == 3 && empty == 0) {
                            eval += 50;
                        }
                        else if (countPlayer2 == 2 && empty == 2) {
                            eval -= 20;
                        }
                        else if (countPlayer2 == 2 && countPlayer != 0) {
                            eval += 20;
                        }
                    }

                    // Check the downward diagonal (top left to bottom right)
                    if (row - 3 >= 0 && col + 3 < COLUMN) {
                        int countPlayer = 0;
                        int countPlayer2 = 0;
                        int empty = 0;

                        for (int i = 0; i < 4; i++) {
                            int token = Grid.getPoint(row - i, col + i);

                            if (token == player) {
                                countPlayer++;
                            } else if (token == player2) {
                                countPlayer2++;
                            } else if (token == 0) {
                                empty++;
                            }
                        }

                        if (countPlayer == 3 && empty == 1) {
                            eval += 100;
                        }
                        else if (countPlayer == 3 && empty == 0) {
                            eval -= 50;
                        }
                        else if (countPlayer == 2 && empty == 2) {
                            eval += 20;
                        }
                        else if (countPlayer == 2 && countPlayer2 != 0) {
                            eval -= 20;
                        }
                        else if (countPlayer2 == 3 && empty == 1) {
                            eval -= 100;
                        }
                        else if (countPlayer2 == 3 && empty == 0) {
                            eval += 50;
                        }
                        else if (countPlayer2 == 2 && empty == 2) {
                            eval -= 20;
                        }
                        else if (countPlayer2 == 2 && countPlayer != 0) {
                            eval += 20;
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
     * @param Grid
     * @return
     */

    public int playV2(int player, int depth, Grid Grid)
    {
        long startTime = System.currentTimeMillis();

        int mineval = INFINITY;
        int[] eval = new int[7];
        int columnToPlay = 0;
        //we start algorithm of each column that is not full
        for (int i=0; i<7; i++) {
            if (!Grid.columnFull(i)){
                eval[i] = minimaxAlphaBetaV2(player,i,-INFINITY, INFINITY,depth, true,Grid);
                //if the IA win at this column we put a better score
                if(Grid.isPlayerWinWithThisToken(i,player))
                {
                    eval[i] = -1000000;
                }
                //we keep the best move
                if (eval[i] < mineval) {
                    mineval = eval[i];
                    columnToPlay = i;
                }
            }
            else {
                eval[i] = INFINITY;
            }
        }
        //this part is use if there is some same best score to choose the closest of the center

        ArrayList<Integer> occurenceIndex = new ArrayList<>();

        for (int i = 0; i < eval.length; i++) {
            if (eval[i] == mineval) {
                occurenceIndex.add(i);
            }
        }
        int distanceMin = 99999999;

        for (int index : occurenceIndex) {
            int distance = Math.abs(index - 3);
            if (distance < distanceMin) {
                distanceMin = distance;
                columnToPlay = index;
            }
        }
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        //System.out.println("                    TEMPS D'EXECUTION DE L'ALGORITHME : " + executionTime + " millisecondes");

        return columnToPlay;
    }

    /**
     *
     * @param player
     * @param depth
     * @param grid
     * @return
     */
    public int playV1(int player, int depth, Grid grid)
    {
        //same process as the play V2
        long startTime = System.currentTimeMillis();

        int mineval = INFINITY;
        int[] eval = new int[7];
        int columnToPlay = 0;
        for (int i=0; i<7; i++) {
            if (!grid.columnFull(i)){

                eval[i] = minimaxV1(player,i,depth, true,grid);

                if(grid.isPlayerWinWithThisToken(i,player))
                {
                    eval[i] = -1000000;
                }
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
            //System.out.println("eval " + i +" : " + eval[i]);
        }


        ArrayList<Integer> occurenceIndex = new ArrayList<>();

        for (int i = 0; i < eval.length; i++) {
            if (eval[i] == mineval) {
                occurenceIndex.add(i);
            }
        }
        int distanceMin = 99999999;

        for (int index : occurenceIndex) {
            int distance = Math.abs(index - 3);
            if (distance < distanceMin) {
                distanceMin = distance;
                columnToPlay = index;
            }
        }
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        //System.out.println("                    TEMPS D'EXECUTION DE L'ALGORITHME : " + executionTime + " millisecondes");

        return columnToPlay;
    }
}
