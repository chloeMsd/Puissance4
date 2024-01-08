import org.junit.Test;
import com.projetpuissance4.models.*;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TestGrille {
    @Test
    public void testJ1GagnantLigne() {
        P4 Grille = new P4();
        Grille.setMatValue(3,1);
        Grille.setMatValue(2,1);
        Grille.setMatValue(1,1);
        Grille.setMatValue(4,1);
        assertEquals(true, Grille.playerWin(1));
    }

    @Test
    public void testJ1GagnantColonne() {
        P4 Grille = new P4();
        Grille.setMatValue(3,1);
        Grille.setMatValue(3,1);
        Grille.setMatValue(3,1);
        Grille.setMatValue(3,1);
        assertEquals(true, Grille.playerWin(1));
    }

    @Test
    public void testJ1GagnantDiagDroite() {
        P4 Grille = new P4();
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
        assertEquals(true, Grille.playerWin(1));
    }
    @Test
    public void testJ1GagnantDiagGauche() {
        P4 Grille = new P4();
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
        System.out.println(Grille.toString());
        assertEquals(true, Grille.playerWin(1));
    }
}
