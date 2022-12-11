package pl.edu.pw.ee;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HuffmanTest {

    private static final String TEST_FILES_PATH = "test_files/";
    private static final String SOURCE_FILENAME = "source.txt";
    private static final String DECOMPRESSED_FILENAME = "decompressed.txt";

    private Huffman huffmanImpl;

    @Before
    public void setUp() {
        huffmanImpl = new Huffman();
    }

    @Test
    public void itShouldReturnCorrectBytesCountWhenCompressing() {
        // given
        String path = TEST_FILES_PATH + "simple/";
        boolean compress = true;

        // when
        int result = huffmanImpl.huffman(path, compress);

        // then
        int expected = 49;
        assertEquals(expected, result);
    }

    @Test
    public void itShouldReturnCorrectCharsCountWhenDecompressing() {
        // given
        String path = TEST_FILES_PATH + "simple/";
        boolean compress = false;
        huffmanImpl.huffman(path, !compress);

        // when
        int result = huffmanImpl.huffman(path, compress);

        // then
        int expected = 19;
        assertEquals(expected, result);
    }

    @Test
    public void itShouldCorrectlyCompressAndDecompressFile() {
        // given
        String path = TEST_FILES_PATH + "complex/";
        boolean compress = true;

        // when
        huffmanImpl.huffman(path, compress);
        huffmanImpl.huffman(path, !compress);

        // then
        boolean result = compareSourceAndDecompressedFiles(path);
        assertTrue(result);
    }

    @Test
    public void itShouldCorrectlyCompressAndDecompressVeryComplexFile() {
        // given
        String path = TEST_FILES_PATH + "very_complex/";
        boolean compress = true;

        // when
        huffmanImpl.huffman(path, compress);
        huffmanImpl.huffman(path, !compress);

        // then
        boolean result = compareSourceAndDecompressedFiles(path);
        assertTrue(result);
    }

    @Test
    public void itShouldCorrectlyCompressAndDecompressLargeFile() {
        // given
        String path = TEST_FILES_PATH + "lalka/";
        boolean compress = true;

        // when
        huffmanImpl.huffman(path, compress);
        huffmanImpl.huffman(path, !compress);

        // then
        boolean result = compareSourceAndDecompressedFiles(path);
        assertTrue(result);
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionWhenNonAsciiCharacterInFile() {
        // given
        String path = TEST_FILES_PATH + "non_ascii/";
        boolean compress = true;

        // when
        huffmanImpl.huffman(path, compress);

        // then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionWhenSourceFileNotFound() {
        // given
        String path = TEST_FILES_PATH + "not_found/";
        boolean compress = true;

        // when
        huffmanImpl.huffman(path, compress);

        // then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionWhenCompressedFileNotFound() {
        // given
        String path = TEST_FILES_PATH + "not_found/";
        boolean compress = false;

        // when
        huffmanImpl.huffman(path, compress);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenPathIsNull() {
        // given
        String path = null;
        boolean compress = false;

        // when
        huffmanImpl.huffman(path, compress);

        // then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionWhenEmptyFile() {
        // given
        String path = TEST_FILES_PATH + "empty/";
        boolean compress = true;

        // when
        huffmanImpl.huffman(path, compress);

        // then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionWhenDecompressMissingTree() {
        // given
        String path = TEST_FILES_PATH + "missing_tree/";
        boolean compress = false;

        // when
        huffmanImpl.huffman(path, compress);

        // then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldThrowExceptionWhenSingleLetter() {
        // given
        String path = TEST_FILES_PATH + "single_letter/";
        boolean compress = true;

        // when
        huffmanImpl.huffman(path, compress);

        // then
        assert false;
    }

    private boolean compareSourceAndDecompressedFiles(String pathToRootDir) {
        File sourceFile = new File(pathToRootDir + SOURCE_FILENAME);
        File decompressedFile = new File(pathToRootDir + DECOMPRESSED_FILENAME);

        try (Reader sourceReader = new BufferedReader(new FileReader(sourceFile));
             Reader decompressedReader = new BufferedReader(new FileReader(decompressedFile))
        ) {
            int sourceChar;
            int decompressedChar;
            while ((sourceChar = sourceReader.read()) != -1) {
                decompressedChar = decompressedReader.read();

                if (sourceChar != decompressedChar) {
                    return false;
                }
            }

            return decompressedReader.read() == -1;
        } catch (IOException | SecurityException exception) {
            throw new IllegalStateException("Exception while reading files!");
        }
    }

}
