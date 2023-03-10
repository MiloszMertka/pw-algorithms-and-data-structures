package pl.edu.pw.ee;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashLinearProbingTest extends HashOpenAdressingTest {

    @Override
    public void setUp() {
        hashTable = new HashLinearProbing<>();
        integerHashTable = new HashLinearProbing<>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenSizeIsNegative() {
        // given
        int size = -1;

        // when
        HashOpenAdressing<String> unusedHashTable = new HashLinearProbing<>(size);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenSizeIsEqualZero() {
        // given
        int size = 0;

        // when
        HashOpenAdressing<String> unusedHashTable = new HashLinearProbing<>(size);

        // then
        assert false;
    }

    @Test
    public void itShouldCorrectlyCalculateHashId() {
        // given
        int key = 123;
        int i = 2;
        int size = 10;
        HashOpenAdressing<String> linearProbingHashTable = new HashLinearProbing<>(size);

        // when
        int result = linearProbingHashTable.hashFunc(key, i);

        // then
        int expected = 5;
        assertEquals(expected, result);
    }

}
