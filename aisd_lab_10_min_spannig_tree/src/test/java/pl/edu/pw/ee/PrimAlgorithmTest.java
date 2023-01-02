package pl.edu.pw.ee;

import org.junit.Test;
import pl.edu.pw.ee.services.MinSpanningTree;

public class PrimAlgorithmTest {

    private MinSpanningTree prim = new PrimAlgorithm();
    
    @Test
    public void itShouldCorrectlyFindMST() {
        System.out.println(prim.findMST(null));
    }
    
}
