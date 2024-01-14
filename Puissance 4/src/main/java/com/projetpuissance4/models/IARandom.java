package com.projetpuissance4.models;

/**
 * IARandom
 */
public class IARandom {
    public IARandom() {
    }

    /**
     * Play
     * @brief Random column to play
     * @return column to play
     */
    public int Play()
    {
        int columnToPlay = (int) (0 + (Math.random() * (6)));
        return columnToPlay;
    }
}