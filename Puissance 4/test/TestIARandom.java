import com.projetpuissance4.models.IARandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestIARandom {

    @Test
    public void testPlayColumn() {
        IARandom Ia = new IARandom();
        int counter = 0;
        for(int i = 0 ;i<100;i++)
        {
            if(Ia.Play() <=6 && Ia.Play() >= 0)
            {
                counter++;
            }
        }
        assertEquals(100,counter);
    }

}
