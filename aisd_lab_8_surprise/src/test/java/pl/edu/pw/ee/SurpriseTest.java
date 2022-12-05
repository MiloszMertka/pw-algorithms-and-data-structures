package pl.edu.pw.ee;

import static junit.framework.Assert.*;
import org.junit.Test;

public class SurpriseTest {

    private final Surprise surprise = new Surprise();

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenFieldsNull() {
        // given
        int[] fields = null;

        // when
        surprise.countMaxSum(fields);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenFieldsSizeTooBig() {
        // given
        int[] fields = new int[999999];

        // when
        surprise.countMaxSum(fields);

        // then
        assert false;
    }

    @Test
    public void itShouldCorrectlyCalcMaxSumWhenAllFieldsNotNegative() {
        // given
        int[] fields = {4, 5, 3, 2, 0, 1};

        // when
        int result = surprise.countMaxSum(fields);

        // then
        int expected = 15;
        assertEquals(expected, result);
    }

    @Test
    public void itShouldCorrectlyCalcMaxSumWhenSomelFieldsNegative() {
        // given
        int[] fields = {4, 5, -3, -2, 0, 1};

        // when
        int result = surprise.countMaxSum(fields);

        // then
        int expected = 10;
        assertEquals(expected, result);
    }

    @Test
    public void itShouldCorrectlyCalcMaxSumWhenLongNegativeChain() {
        // given
        int[] fields = {4, 5, -3, -2, -10, -1};

        // when
        int result = surprise.countMaxSum(fields);

        // then
        int expected = 8;
        assertEquals(expected, result);
    }

    @Test
    public void itShouldCorrectlyCalcMaxSumWhenLongChain() {
        // given
        int[] fields = {4, 5, -3, -2, -10, -1, -8, -7, 10, 12, 5, 4, -1, -2};

        // when
        int result = surprise.countMaxSum(fields);

        // then
        int expected = 37;
        assertEquals(expected, result);
    }
    
    @Test
    public void itShouldCorrectlyCalcMaxSumWhenAllNegativeValues() {
        // given
        int[] fields = {-4, -5, -3, -2, -10, -1, -8, -7};

        // when
        int result = surprise.countMaxSum(fields);

        // then
        int expected = -12;
        assertEquals(expected, result);
    }
    
    @Test
    public void itShouldCorrectlyCalcMaxSumWhenLargerFieldsArray() {
        // given
        int[] fields = {-3, 0, 0, -1, 7, 12, 0, -3, 0, 1, 1, -2, -7, 2, 3, 5, 0, 0, 0, 1, -1, -1, -10, 0, 3, 20, -2, 10};

        // when
        int result = surprise.countMaxSum(fields);

        // then
        int expected = 62;
        assertEquals(expected, result);
    }
    
    @Test
    public void itShouldCorrectlyCalcMaxSumWhenComplexFieldsArray() {
        // given
        int[] fields = {3, -2, -1, -1, -1, -2, -4, -10, -10, -10, -8, 10, 3, -2, -1, -1, -1, -2, -4, -10, -10, -10, -8, 10};

        // when
        int result = surprise.countMaxSum(fields);

        // then
        int expected = 22;
        assertEquals(expected, result);
    }

}
