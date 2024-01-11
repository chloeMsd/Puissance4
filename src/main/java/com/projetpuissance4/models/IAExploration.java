package com.projetpuissance4.models;

import java.util.ArrayList;
import java.util.HashMap;

public class IAExploration {
    private long nodeCount = 0;
    private int[] order = {3, 2, 4, 1, 5, 0, 6};
    private HashMap<Long, Integer> HM = new HashMap<Long, Integer>();

    private int alpha = 999999999;

    public IAExploration() {
        this.nodeCount = 0;
    }

    public void resetVar()
    {
        HM.clear();
        nodeCount = 0;
    }
    public int Exploration(P4 P, int depht) {
        int retMin = 50;
        nodeCount++;

        for (int i = 0; i < 7; i++) {
            if (!P.columnFull(i) && P.isPlayerWinWithThisToken(i,2)) {
                retMin = depht;
                if (alpha > retMin) {
                    alpha = retMin;
                }
                return retMin;
            }
        }

        depht++;
        if (HM.get(P.gridToLong(P.getMatrix())) != null) {
            Integer val = HM.get(P.gridToLong(P.getMatrix()));
            if (val != 0) {
                return depht;
            }
        }
        if (alpha < depht) {
            return retMin;
        }
        for (int j = 0; j < 7; j++) {
            if (!P.columnFull(order[j])) {
                P4 Grille2 = new P4(P.getMatrix());
                Grille2.setMatValue(order[j],2);
                int ret = Exploration(Grille2, depht);
                if(ret < retMin)
                {
                    retMin = ret;
                }
            }
        }
        if (HM.size() < 8388593)
            HM.put(P.gridToLong(P.getMatrix()), depht);
        return retMin;
    }

    public int play(P4 Grille){
        int[] score = new int[7];
        int min = 9999999;
        int itMin = -1;
        for (int j = 0; j < 7; j++)
        {
            score[j] = -1;
            P4 Grille2 = new P4(Grille.getMatrix());
            Grille2.setMatValue(j,2);
            P4 Grille3 = new P4(Grille.getMatrix());
            Grille3.setMatValue(j,1);
            if(Grille3.playerWin(2)[0] == 1)
            {
                return j;
            }
            else if (Grille2.playerWin(1)[0] == 1)
            {
                return j;
            }
            else{
                HM.clear();
                score[j] = Exploration(Grille2,0);

                System.out.println("score at " + j + " : " + score[j]);
                if(score[j]<min)
                {
                    itMin = j;
                    min = score[j];
                }
            }
        }
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
