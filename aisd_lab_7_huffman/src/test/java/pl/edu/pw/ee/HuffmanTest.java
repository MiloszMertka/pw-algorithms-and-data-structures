package pl.edu.pw.ee;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HuffmanTest {

    private Huffman huffmanImpl;

    @Before
    public void setUp() {
        huffmanImpl = new Huffman();
    }

    @Test
    public void itShouldCompressFileInGivenDir() {
        // given
        String path = "test_files/";
        boolean compress = true;

        // when
        int result = huffmanImpl.huffman(path, compress);

        // then
        int expected = 18;
        assertEquals(expected, result);
    }

}
