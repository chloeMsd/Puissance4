import com.projetpuissance4.models.P4;
import com.projetpuissance4.models.IAminimax;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestEval {

    @Test
    public void testEval() {
        P4 Grille = new P4();
        IAminimax IA = new IAminimax();
        Grille.setMatValeur(3,1);
        Grille.setMatValeur(2,1);
        Grille.setMatValeur(1,1);
        Grille.setMatValeur(4,2);

    }

}
