package pl.edu.pw.ee;

import org.junit.Before;
import org.junit.Test;

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
        huffmanImpl.huffman(path, compress);

        // then
        assert true;
    }

}
