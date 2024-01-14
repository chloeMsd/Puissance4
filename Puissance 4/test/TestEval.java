import com.projetpuissance4.models.Grid;
import com.projetpuissance4.models.IAMinimax;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestEval {

    @Test
    public void testEval() {
        Grid Grille = new Grid();
        IAMinimax IA = new IAMinimax();
        Grille.setMatValue(3,1);
        Grille.setMatValue(2,1);
        Grille.setMatValue(1,1);
        Grille.setMatValue(4,2);

    }

}
