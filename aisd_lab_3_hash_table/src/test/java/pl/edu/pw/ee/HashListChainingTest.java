package pl.edu.pw.ee;

import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

import static org.junit.Assert.*;

public class HashListChainingTest {

    private static final int DEFAULT_SIZE = 10;

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenSizeIsNegative() {
        // given
        int size = -1;

        // when
        HashTable<String> hashTable = new HashListChaining<>(size);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenSizeIsEqualZero() {
        // given
        int size = 0;

        // when
        HashTable<String> hashTable = new HashListChaining<>(size);

        // then
        assert false;
    }

    @Test
    public void itShouldBeInitializedWithNil() {
        // given
        int size = 3;

        // when
        HashListChaining<String> hashTable = new HashListChaining<>(size);

        // then
        String[] expected = {null, null, null};
        assertArrayEquals(expected, hashTable.getHashElems().toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenAddValueIsNull() {
        // given
        String value = null;
        HashTable<String> hashTable = new HashListChaining<>(DEFAULT_SIZE);

        // when
        hashTable.add(value);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenGetValueIsNull() {
        // given
        String value = null;
        HashTable<String> hashTable = new HashListChaining<>(DEFAULT_SIZE);

        // when
        hashTable.get(value);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenDeleteValueIsNull() {
        // given
        String value = null;
        HashTable<String> hashTable = new HashListChaining<>(DEFAULT_SIZE);

        // when
        hashTable.delete(value);

        // then
        assert false;
    }

    @Test
    public void itShouldAddValueToHashTable() {
        // given
        String value = "test";
        HashTable<String> hashTable = new HashListChaining<>(DEFAULT_SIZE);

        // when
        hashTable.add(value);

        // then
        String addedValue = hashTable.get(value);
        assertEquals(addedValue, value);
    }

    @Test
    public void itShouldAddValuesWithCollisionToHashTable() {
        // given
        String firstValue = "test";
        String secondValue = "test1";
        String thirdValue = "test2";
        int size = 1;
        HashTable<String> hashTable = new HashListChaining<>(size);

        // when
        hashTable.add(firstValue);
        hashTable.add(secondValue);
        hashTable.add(thirdValue);

        // then
        String firstAddedValue = hashTable.get(firstValue);
        String secondAddedValue = hashTable.get(secondValue);
        String thirdAddedValue = hashTable.get(thirdValue);
        assertEquals(firstAddedValue, firstValue);
        assertEquals(secondAddedValue, secondValue);
        assertEquals(thirdAddedValue, thirdValue);
    }

    @Test
    public void itShouldReturnNullWhenValueIsNotFound() {
        // given
        String value = "test";
        HashTable<String> hashTable = new HashListChaining<>(DEFAULT_SIZE);

        // when
        String result = hashTable.get(value);

        // then
        assertNull(result);
    }

    @Test
    public void itShouldDeleteValueFromHashTable() {
        // given
        String value = "test";
        HashTable<String> hashTable = new HashListChaining<>(DEFAULT_SIZE);
        hashTable.add(value);

        // when
        hashTable.delete(value);

        // then
        String result = hashTable.get(value);
        assertNull(result);
    }

    @Test
    public void itShouldDeleteValueWithCollisionFromHashTable() {
        // given
        String firstValue = "test";
        String secondValue = "test1";
        String thirdValue = "test2";
        int size = 1;
        HashTable<String> hashTable = new HashListChaining<>(size);
        hashTable.add(firstValue);
        hashTable.add(secondValue);
        hashTable.add(thirdValue);

        // when
        hashTable.delete(secondValue);

        // then
        String firstAddedValue = hashTable.get(firstValue);
        String secondAddedValue = hashTable.get(secondValue);
        String thirdAddedValue = hashTable.get(thirdValue);
        assertEquals(firstValue, firstAddedValue);
        assertNull(secondAddedValue);
        assertEquals(thirdValue, thirdAddedValue);
    }

    @Test
    public void itShouldNotThrowExceptionWhenElemToDeleteNotFound() {
        // given
        String value = "test";
        HashTable<String> hashTable = new HashListChaining<>(DEFAULT_SIZE);
        hashTable.add(value);

        // when
        hashTable.delete(value);

        // then
        String result = hashTable.get(value);
        assertNull(result);
    }

    @Test
    public void itShouldCalculateLoadFactor() {
        // given
        String firstValue = "test";
        String secondValue = "test1";
        String thirdValue = "test2";
        int size = 10;
        HashListChaining<String> hashTable = new HashListChaining<>(size);
        hashTable.add(firstValue);
        hashTable.add(secondValue);
        hashTable.add(thirdValue);

        // when
        double loadFactor = hashTable.countLoadFactor();

        // then
        double expected = 0.3;
        double delta = 0;
        assertEquals(expected, loadFactor, delta);
    }

    @Test
    public void itShouldAddObjectWithMaxIntHashCode() {
        // given
        Integer value = Integer.MAX_VALUE;
        HashListChaining<Integer> hashTable = new HashListChaining<>(DEFAULT_SIZE);

        // when
        hashTable.add(value);

        // then
        Integer addedValue = hashTable.get(value);
        assertEquals(value, addedValue);
    }

    @Test
    public void itShouldAddObjectWithMinIntHashCode() {
        // given
        Integer value = Integer.MIN_VALUE;
        HashListChaining<Integer> hashTable = new HashListChaining<>(DEFAULT_SIZE);

        // when
        hashTable.add(value);

        // then
        Integer addedValue = hashTable.get(value);
        assertEquals(value, addedValue);
    }

}
