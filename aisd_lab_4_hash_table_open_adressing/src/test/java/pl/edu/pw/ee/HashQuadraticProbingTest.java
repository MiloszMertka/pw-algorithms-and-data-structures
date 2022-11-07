package pl.edu.pw.ee;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashQuadraticProbingTest extends HashOpenAdressingTest {

    @Override
    public void setUp() {
        hashTable = new HashQuadraticProbing<>();
        integerHashTable = new HashQuadraticProbing<>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenSizeIsNegative() {
        // given
        int size = -1;
        double a = 0.5;
        double b = 0.5;

        // when
        HashOpenAdressing<String> unusedHashTable = new HashQuadraticProbing<>(size, a, b);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenSizeIsEqualZero() {
        // given
        int size = 0;
        double a = 0.5;
        double b = 0.5;

        // when
        HashOpenAdressing<String> unusedHashTable = new HashQuadraticProbing<>(size, a, b);

        // then
        assert false;
    }

    @Test
    public void itShouldCorrectlyCalculateHashId() {
        // given
        int key = 123;
        int i = 2;
        int size = 10;
        double a = 0.5;
        double b = 0.5;
        HashOpenAdressing<String> quadraticProbingHashTable = new HashQuadraticProbing<>(size, a, b);

        // when
        int result = quadraticProbingHashTable.hashFunc(key, i);

        // then
        int expected = 6;
        assertEquals(expected, result);
    }

}
