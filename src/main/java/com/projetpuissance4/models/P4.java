package com.projetpuissance4.models;

import java.util.Arrays;

public class P4 {

    private static final int LIGNE = 6;
    private static final int COLONNE = 7;
    private int [][] mat = new int[LIGNE][COLONNE];
    public P4() {
        int [][] mat = new int[LIGNE][COLONNE];
        for(int i = 0; i<LIGNE;i++)
        {
            for(int j = 0; j<COLONNE;j++)
            {
                mat[i][j] = 0;
            }
        }
        this.mat = mat;
    }
    public P4(int[][] mat) {
        this.mat = mat;
    }

    public void setMat(int[][] mat) {
        this.mat = mat;
    }

    public void setMatValeur(int Col, int Val) {
        int nvLig = checkGraviter(Col);
        this.mat[nvLig][Col] = Val;
    }

    public int[][] getMat() {
        return mat;
    }


    public int getPoint(int line, int column)
    {
        return mat[line][column];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                builder.append(mat[i][j]);
                if (j < mat[i].length - 1) {
                    builder.append("\t"); // Ajoutez une tabulation entre les éléments de la même ligne
                }
            }
            if (i < mat.length - 1) {
                builder.append("\n"); // Ajoutez un saut de ligne entre les lignes de la matrice
            }
        }
        return builder.toString();
    }

    public int checkGraviter(int colonne)
    {
        int ligne = LIGNE - 1;
        while(mat[ligne][colonne] != 0)
        {
            ligne--;
        }
        return ligne;
    }

    public boolean JoueurGagnant(int player) {
        // Vérifiez les directions horizontales, verticales et diagonales
        for (int row = 0; row < LIGNE; row++) {
            for (int col = 0; col < COLONNE; col++) {
                // Vérifiez l'horizontale (gauche à droite)
                if (col + 3 < COLONNE &&
                        this.mat[row][col] == player &&
                        this.mat[row][col + 1] == player &&
                        this.mat[row][col + 2] == player &&
                        this.mat[row][col + 3] == player) {
                    return true;
                }
                // Vérifiez la verticale (bas vers le haut)
                if (row + 3 < LIGNE &&
                        this.mat[row][col] == player &&
                        this.mat[row + 1][col] == player &&
                        this.mat[row + 2][col] == player &&
                        this.mat[row + 3][col] == player) {
                    return true;
                }
                // Vérifiez la diagonale ascendante (bas gauche vers haut droite)
                if (row + 3 < LIGNE && col + 3 < COLONNE &&
                        this.mat[row][col] == player &&
                        this.mat[row + 1][col + 1] == player &&
                        this.mat[row + 2][col + 2] == player &&
                        this.mat[row + 3][col + 3] == player) {
                    return true;
                }
                // Vérifiez la diagonale descendante (haut gauche vers bas droite)
                if (row - 3 >= 0 && col + 3 < COLONNE &&
                        this.mat[row][col] == player &&
                        this.mat[row - 1][col + 1] == player &&
                        this.mat[row - 2][col + 2] == player &&
                        this.mat[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean TestEgalite()
    {
        for (int i=0; i<7 ;i++)
        {
            if (getPoint(0,i) == 0)
            {
                return false;
            }
        }
        return true;
    }
}
