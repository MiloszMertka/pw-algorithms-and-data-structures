package pl.edu.pw.ee;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class LongestCommonSubsequenceTest {

    private static final String DISPLAY_TEST_FILE = "test_files/display_test.txt";

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOutputStream = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void tearDown() {
        System.setOut(originalOutputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenStringIsNull() {
        // given
        String topStr = null;
        String leftStr = null;
        LongestCommonSubsequence LCS = new LongestCommonSubsequence(topStr, leftStr);

        // when
        LCS.findLCS();

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenStringIsEmpty() {
        // given
        String topStr = "test";
        String leftStr = "";
        LongestCommonSubsequence LCS = new LongestCommonSubsequence(topStr, leftStr);

        // when
        LCS.findLCS();

        // then
        assert false;
    }

    @Test
    public void itShouldCorrectlyFindLCS() {
        // given
        String topStr = "KANAPKI";
        String leftStr = "NAPISY";
        LongestCommonSubsequence LCS = new LongestCommonSubsequence(topStr, leftStr);

        // when
        String result = LCS.findLCS();

        // then
        String expected = "NAPI";
        assertEquals(expected, result);
    }

    @Test
    public void itShouldCorrectlyFindLCSWhenSpecialChars() {
        // given
        String topStr = "\tczęsto_z_\rodkrywaniem\n";
        String leftStr = "rzeczy_nie_trzeba\n_się_spieszyć\n";
        LongestCommonSubsequence LCS = new LongestCommonSubsequence(topStr, leftStr);

        // when
        String result = LCS.findLCS();

        // then
        String expected = "cz__raie\n";
        assertEquals(expected, result);
    }

    @Test
    public void itShouldCorrectlyFindLCSWhenIdenticalStrings() {
        // given
        String topStr = "to samo";
        String leftStr = "to samo";
        LongestCommonSubsequence LCS = new LongestCommonSubsequence(topStr, leftStr);

        // when
        String result = LCS.findLCS();

        // then
        String expected = "to samo";
        assertEquals(expected, result);
    }

    @Test
    public void itShouldCorrectlyFindLCSWhenSubstring() {
        // given
        String topStr = "program";
        String leftStr = "zaprogramowane";
        LongestCommonSubsequence LCS = new LongestCommonSubsequence(topStr, leftStr);

        // when
        String result = LCS.findLCS();

        // then
        String expected = "program";
        assertEquals(expected, result);
    }

    @Test
    public void itShouldReturnEmptyStringWhenNoLCS() {
        // given
        String topStr = "abcdef";
        String leftStr = "ghijkl";
        LongestCommonSubsequence LCS = new LongestCommonSubsequence(topStr, leftStr);

        // when
        String result = LCS.findLCS();

        // then
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void itShouldPrintMatrixCorrectly() throws IOException {
        // given
        String topStr = "często_z_odkrywaniem\t";
        String leftStr = "rzeczy_nie_trzeba\n_się_spieszyć";
        LongestCommonSubsequence LCS = new LongestCommonSubsequence(topStr, leftStr);

        // when
        LCS.display();

        // then
        String result = outputStream.toString();
        String expected = new String(Files.readAllBytes(Paths.get(DISPLAY_TEST_FILE)));
        assertEquals(expected, result);
    }

}
