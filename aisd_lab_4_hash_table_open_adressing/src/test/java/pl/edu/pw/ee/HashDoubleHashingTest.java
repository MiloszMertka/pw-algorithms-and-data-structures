package pl.edu.pw.ee;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashDoubleHashingTest extends HashOpenAdressingTest {

    @Override
    public void setUp() {
        hashTable = new HashDoubleHashing<>();
        integerHashTable = new HashDoubleHashing<>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenSizeIsNegative() {
        // given
        int size = -1;

        // when
        HashOpenAdressing<String> unusedHashTable = new HashDoubleHashing<>(size);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenSizeIsEqualZero() {
        // given
        int size = 0;

        // when
        HashOpenAdressing<String> unusedHashTable = new HashDoubleHashing<>(size);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenSizeIsEqualThree() {
        // given
        int size = 3;

        // when
        HashOpenAdressing<String> unusedHashTable = new HashDoubleHashing<>(size);

        // then
        assert false;
    }

    @Test
    public void itShouldCorrectlyCalculateHashId() {
        // given
        int key = 123;
        int i = 2;
        int size = 10;
        HashOpenAdressing<String> doubleHashingHashTable = new HashDoubleHashing<>(size);

        // when
        int result = doubleHashingHashTable.hashFunc(key, i);

        // then
        int expected = 3;
        assertEquals(expected, result);
    }

    @Test
    public void itShouldCorrectlyCalculateHashIdWhenGFunctionEvaluatesToZero() {
        // given
        int key = -1;
        int i = 2;
        int size = 5;
        HashOpenAdressing<String> doubleHashingHashTable = new HashDoubleHashing<>(size);

        // when
        int result = doubleHashingHashTable.hashFunc(key, i);

        // then
        int expected = 1;
        assertEquals(expected, result);
    }

}
