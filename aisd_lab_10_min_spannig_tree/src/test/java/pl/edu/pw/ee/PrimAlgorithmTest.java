package pl.edu.pw.ee;

import org.junit.Test;
import pl.edu.pw.ee.services.MinSpanningTree;

import static org.junit.Assert.assertEquals;

public class PrimAlgorithmTest {

    private static final String TEST_FILES_PATH = "./test_files/";

    private final MinSpanningTree prim = new PrimAlgorithm();
    
    @Test
    public void itShouldCorrectlyFindMST() {
        // given
        String pathToFile = TEST_FILES_PATH + "correct_small_data.txt";

        // when
        String result = prim.findMST(pathToFile);

        // then
        String expected = "A_3_B|B_1_C|C_1_D|D_7_E";
        assertEquals(expected, result);
    }

    @Test
    public void itShouldCorrectlyFindMSTWhenOneEdge() {
        // given
        String pathToFile = TEST_FILES_PATH + "one_edge.txt";

        // when
        String result = prim.findMST(pathToFile);

        // then
        String expected = "A_3_B";
        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenNullPath() {
        // given
        String pathToFile = null;

        // when
        String result = prim.findMST(pathToFile);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenBlankPath() {
        // given
        String pathToFile = "   ";

        // when
        String result = prim.findMST(pathToFile);

        // then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionWhenUnconnectedGraph() {
        // given
        String pathToFile = "unconnected.txt";

        // when
        String result = prim.findMST(pathToFile);

        // then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionWhenNegativeWage() {
        // given
        String pathToFile = "negative_wage.txt";

        // when
        String result = prim.findMST(pathToFile);

        // then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionWhenWrongFileFormat() {
        // given
        String pathToFile = "wrong_format.txt";

        // when
        String result = prim.findMST(pathToFile);

        // then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionWhenFileNotFound() {
        // given
        String pathToFile = "not_found.txt";

        // when
        String result = prim.findMST(pathToFile);

        // then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionWhenLoopInGraph() {
        // given
        String pathToFile = "loop.txt";

        // when
        String result = prim.findMST(pathToFile);

        // then
        assert false;
    }
    
}
