package com.projetpuissance4.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class for the ia of exploration
 */
public class IAExploration {
    private long nodeCount = 0;
    private int[] order = {3, 2, 4, 1, 5, 0, 6};
    private HashMap<Long, Integer> HM = new HashMap<Long, Integer>();

    private int alpha = 999999999;


    public IAExploration() {
        this.nodeCount = 0;
    }

    /**
     * The algorithm of exploration
     * @param P
     * @param depht
     * @return
     */
    public int Exploration(Grid P, int depht) {
        int retMin = 50;
        nodeCount++;

        //check if there is winning move
        for (int i = 0; i < 7; i++) {
            if (!P.columnFull(i) && P.isPlayerWinWithThisToken(i,2)) {
                retMin = depht;
                if (alpha > retMin) {
                    alpha = retMin;
                }
                return retMin;
            }
        }

        //check if the grid is in the hash map
        //if it's true return
        //if not continue
        depht++;
        if (HM.get(P.gridToLong(P.getMatrix())) != null) {
            Integer val = HM.get(P.gridToLong(P.getMatrix()));
            if (val != 0) {
                return depht;
            }
        }
        //Check if we are deeper than the best winning move
        if (alpha < depht) {
            return retMin;
        }
        //recursive call on each playable column
        for (int j = 0; j < 7; j++) {
            if (!P.columnFull(order[j])) {
                Grid Grille2 = new Grid(P.getMatrix());
                Grille2.setMatValue(order[j],2);
                int ret = Exploration(Grille2, depht);
                if(ret < retMin)
                {
                    retMin = ret;
                }
            }
        }
        //if the hashmap is not full we save the grid
        if (HM.size() < 8388593)
            HM.put(P.gridToLong(P.getMatrix()), depht);
        return retMin;
    }

    /**
     * To play
     * @param grid
     * @return
     */
    public int play(Grid grid){
        int[] score = new int[7];
        int min = 9999999;
        int itMin = -1;
        //check if there is no winning move for the ia or the opponent, if not we start the exploration
        for (int j = 0; j < 7; j++)
        {
            score[j] = -1;
            Grid grid2 = new Grid(grid.getMatrix());
            grid2.setMatValue(j,2);
            Grid grid3 = new Grid(grid.getMatrix());
            grid3.setMatValue(j,1);
            if(grid3.playerWin(1)[0] == 1)
            {
                return j;
            }
            else if (grid2.playerWin(2)[0] == 1)
            {
                return j;
            }
            else{
                HM.clear();
                score[j] = Exploration(grid2,0) + 1;
                if(score[j]<min)
                {
                    itMin = j;
                    min = score[j];
                }
            }
        }

        //this part is use if there is some same best score to choose the closest of the center
        ArrayList<Integer> occurenceIndex = new ArrayList<>();

        for (int i = 0; i < score.length; i++) {
            if (score[i] == min) {
                occurenceIndex.add(i);
            }
        }
        int distanceMin = 99999999;

        for (int index : occurenceIndex) {
            int distance = Math.abs(index - 3);
            if (distance < distanceMin) {
                distanceMin = distance;
                itMin = index;
            }
        }
        alpha = 9999999;
        return itMin;
    }


}
