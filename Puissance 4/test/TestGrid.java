import org.junit.Test;
import com.projetpuissance4.models.*;


import static org.junit.Assert.assertEquals;

public class TestGrid {
    @Test
    public void testJ1WinLine() {
        Grid Grille = new Grid();
        Grille.setMatValue(3,1);
        Grille.setMatValue(2,1);
        Grille.setMatValue(1,1);
        Grille.setMatValue(4,1);
        int[] tabResult = Grille.playerWin(1);
        assertEquals(1,tabResult[0]);
    }

    @Test
    public void testJ1NoWinLine() {
        Grid Grille = new Grid();
        Grille.setMatValue(3,1);
        Grille.setMatValue(2,1);
        Grille.setMatValue(1,1);
        int[] tabResult = Grille.playerWin(1);
        assertEquals(0,tabResult[0]);
    }

    @Test
    public void testJ1WinColumn() {
        Grid Grille = new Grid();
        Grille.setMatValue(3,1);
        Grille.setMatValue(3,1);
        Grille.setMatValue(3,1);
        Grille.setMatValue(3,1);
        int[] tabResult = Grille.playerWin(1);
        assertEquals(1,tabResult[0]);
    }

    @Test
    public void testJ1NoWinColumn() {
        Grid Grille = new Grid();
        Grille.setMatValue(3,1);
        Grille.setMatValue(3,1);
        Grille.setMatValue(3,1);
        int[] tabResult = Grille.playerWin(1);
        assertEquals(0,tabResult[0]);
    }

    @Test
    public void testJ1WinDiagWrite() {
        Grid Grille = new Grid();
        Grille.setMatValue(0,1);
        Grille.setMatValue(1,2);
        Grille.setMatValue(2,2);
        Grille.setMatValue(3,2);
        Grille.setMatValue(1,1);
        Grille.setMatValue(2,2);
        Grille.setMatValue(3,2);
        Grille.setMatValue(2,1);
        Grille.setMatValue(3,2);
        Grille.setMatValue(3,1);
        int[] tabResult = Grille.playerWin(1);
        assertEquals(1,tabResult[0]);
    }

    @Test
    public void testJ1NoWinDiagWrite() {
        Grid Grille = new Grid();
        Grille.setMatValue(0,1);
        Grille.setMatValue(1,2);
        Grille.setMatValue(2,2);
        Grille.setMatValue(3,2);
        Grille.setMatValue(1,1);
        Grille.setMatValue(2,2);
        Grille.setMatValue(3,2);
        Grille.setMatValue(2,1);
        Grille.setMatValue(3,2);
        int[] tabResult = Grille.playerWin(1);
        assertEquals(0,tabResult[0]);
    }
    @Test
    public void testJ1NOWinDiagLeft() {
        Grid Grille = new Grid();
        Grille.setMatValue(3,1);
        Grille.setMatValue(2,2);
        Grille.setMatValue(1,2);
        Grille.setMatValue(0,2);
        Grille.setMatValue(2,1);
        Grille.setMatValue(1,2);
        Grille.setMatValue(0,2);
        Grille.setMatValue(1,1);
        Grille.setMatValue(0,2);
        Grille.setMatValue(0,1);
        int[] tabResult = Grille.playerWin(1);
        assertEquals(1,tabResult[0]);
    }

    @Test
    public void testColumnFull() {
        Grid Grille = new Grid();
        Grille.setMatValue(0,1);
        Grille.setMatValue(0,2);
        Grille.setMatValue(0,1);
        Grille.setMatValue(0,2);
        Grille.setMatValue(0,1);
        Grille.setMatValue(0,2);
        assertEquals(true, Grille.columnFull(0));
    }

    @Test
    public void testNoColumnFull() {
        Grid Grille = new Grid();
        Grille.setMatValue(0,1);
        Grille.setMatValue(0,2);
        Grille.setMatValue(0,1);
        Grille.setMatValue(0,2);
        Grille.setMatValue(0,1);
        assertEquals(false, Grille.columnFull(0));
    }

    @Test
    public void testGravityCheck() {
        Grid Grille = new Grid();
        Grille.setMatValue(0,1);
        assertEquals(4, Grille.gravityCheck(0));
    }

    @Test
    public void testIsPlayerWinWithThisToken() {
        Grid Grille = new Grid();
        Grille.setMatValue(3,1);
        Grille.setMatValue(2,1);
        Grille.setMatValue(1,1);
        assertEquals(true, Grille.isPlayerWinWithThisToken(4,1));
    }


    @Test
    public void testIsNoPlayerWinWithThisToken() {
        Grid Grille = new Grid();
        Grille.setMatValue(3,1);
        Grille.setMatValue(2,1);
        assertEquals(false, Grille.isPlayerWinWithThisToken(4,1));
    }
}
