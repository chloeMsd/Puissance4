package com.projetpuissance4.models;

/**
 * class that is the board game
 */
public class Grid {

    private static final int LINE = 6;
    private static final int COLUMN = 7;
    private int [][] matrix = new int[LINE][COLUMN];

    /**
     * Constructor
     */
    public Grid() {
        int [][] mat = new int[LINE][COLUMN];
        for(int i = 0; i< LINE; i++)
        {
            for(int j = 0; j< COLUMN; j++)
            {
                mat[i][j] = 0;
            }
        }
        this.matrix = mat;
    }

    /**
     * constructor
     * @param mat
     */
    public Grid(int[][] mat) {

        for(int i = 0; i< LINE; i++)
        {
            for(int j = 0; j< COLUMN; j++)
            {
                this.matrix[i][j] = mat[i][j];
            }
        }
    }

    /**
     * SetMat
     * Setter Mat
     * @param matrix
     */
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * SetMatValeur
     * Setter Mat Value
     * @param Col
     * @param Val
     */
    public void setMatValue(int Col, int Val) {
        int newLine = gravityCheck(Col);
        if(newLine < LINE && newLine >= 0)
        {
            this.matrix[newLine][Col] = Val;
        }
    }

    /**
     * Getter Matrix
     * @return the Matrix
     */
    public int[][] getMatrix() {
        return matrix;
    }


    /**
     * Get Point
     * Get the point at line/column
     * @param line
     * @param column
     * @return
     */
    public int getPoint(int line, int column)
    {
        return matrix[line][column];
    }

    /**
     * Grid To String
     * To String
     * @return
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                builder.append(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    builder.append("\t");
                }
            }
            if (i < matrix.length - 1) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    /**
     * Column Full
     * check if column full
     * @param column
     * @return
     */
    public boolean columnFull(int column)
    {
        for(int i = 0; i< LINE; i++)
        {
            if(matrix[i][column] == 0)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Gravity Check
     * Check the gravity
     * @param column
     * @return the line
     */
    public int gravityCheck(int column)
    {
        if(column >= 0 && !columnFull(column))
        {
            int line = LINE - 1;

            while(matrix[line][column] != 0)
            {
                line--;
            }
            return line;
        }
        return -10;
    }

    /**
     * Is player win with this token
     * Check if a player win with this token
     * @param column
     * @param player
     * @return
     */
    public boolean isPlayerWinWithThisToken(int column,int player) {
        //duplicate the grid
        Grid tmpGrid = new Grid(this.matrix);
        tmpGrid.setMatValue(column, player);
        //check for every token
        for (int row = 0; row < LINE; row++) {
            for (int col = 0; col < COLUMN; col++) {
                //check for column
                if (col + 3 < COLUMN &&
                        tmpGrid.getMatrix()[row][col] == player &&
                        tmpGrid.getMatrix()[row][col + 1] == player &&
                        tmpGrid.getMatrix()[row][col + 2] == player &&
                        tmpGrid.getMatrix()[row][col + 3] == player) {
                    return true;
                }
                //check for line
                if (row + 3 < LINE &&
                        tmpGrid.getMatrix()[row][col] == player &&
                        tmpGrid.getMatrix()[row + 1][col] == player &&
                        tmpGrid.getMatrix()[row + 2][col] == player &&
                        tmpGrid.getMatrix()[row + 3][col] == player) {
                    return true;
                }
                //check for diagonal
                if (row + 3 < LINE && col + 3 < COLUMN &&
                        tmpGrid.getMatrix()[row][col] == player &&
                        tmpGrid.getMatrix()[row + 1][col + 1] == player &&
                        tmpGrid.getMatrix()[row + 2][col + 2] == player &&
                        tmpGrid.getMatrix()[row + 3][col + 3] == player) {
                    return true;
                }
                //check for diagonal
                if (row - 3 >= 0 && col + 3 < COLUMN &&
                        tmpGrid.getMatrix()[row][col] == player &&
                        tmpGrid.getMatrix()[row - 1][col + 1] == player &&
                        tmpGrid.getMatrix()[row - 2][col + 2] == player &&
                        tmpGrid.getMatrix()[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Player Win
     * Check if player is winning
     * @param player
     * @return
     */
    public int[] playerWin(int player) {
        int[] back = new int[9];
        back[0] = 0;
        //check for each token
        for (int row = 0; row < LINE; row++) {
            for (int col = 0; col < COLUMN; col++) {
                // Check horizontal (left to right)
                if (col + 3 < COLUMN &&
                        this.matrix[row][col] == player &&
                        this.matrix[row][col + 1] == player &&
                        this.matrix[row][col + 2] == player &&
                        this.matrix[row][col + 3] == player) {
                    back[0] = 1;
                    for (int i=1; i<5; i++){
                        back[2*i -1] = row;
                        back[2*i] = col + i - 1;
                    }
                }
                // Check vertical (bottom up)
                if (row + 3 < LINE &&
                        this.matrix[row][col] == player &&
                        this.matrix[row + 1][col] == player &&
                        this.matrix[row + 2][col] == player &&
                        this.matrix[row + 3][col] == player) {
                    back[0] = 1;
                    for (int i=1; i<5; i++){
                        back[2*i -1] = row + i - 1;
                        back[2*i] = col;
                    }
                }
                // Check the ascending diagonal (bottom left to top right)
                if (row + 3 < LINE && col + 3 < COLUMN &&
                        this.matrix[row][col] == player &&
                        this.matrix[row + 1][col + 1] == player &&
                        this.matrix[row + 2][col + 2] == player &&
                        this.matrix[row + 3][col + 3] == player) {
                    back[0] = 1;
                    for (int i=1; i<5; i++){
                        back[2*i -1] = row + i - 1;
                        back[2*i] = col + i - 1;
                    }
                }
                // Check the downward diagonal (top left to bottom right)
                if (row - 3 >= 0 && col + 3 < COLUMN &&
                        this.matrix[row][col] == player &&
                        this.matrix[row - 1][col + 1] == player &&
                        this.matrix[row - 2][col + 2] == player &&
                        this.matrix[row - 3][col + 3] == player) {
                    back[0] = 1;
                    for (int i=1; i<5; i++){
                        back[2*i -1] = row - i + 1;
                        back[2*i] = col + i - 1;
                    }
                }
            }
        }
        return back;
    }

    /**
     * Check Draw
     * Check if there is a draw
     * @return
     */
    public boolean checkDraw()
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

    /**
     * Matrix Zero
     * replace matrix by 0
     */
    public void matrixZero()
    {
        for(int i = 0; i< LINE; i++)
        {
            for(int j = 0; j< COLUMN; j++)
            {
                matrix[i][j] = 0;
            }
        }
    }

    /**
     * Grid to long
     * create a long by the grid
     * @param grid
     * @return
     */
    public static long gridToLong(int[][] grid) {
        long result = 0L;

        for (int row = 0; row < LINE; row++) {
            for (int col = 0; col < COLUMN; col++) {
                result <<= 2;
                result |= grid[row][col];
            }
        }
        return result;
    }
}
