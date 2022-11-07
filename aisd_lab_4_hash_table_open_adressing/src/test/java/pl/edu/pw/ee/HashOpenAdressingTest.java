package pl.edu.pw.ee;

import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public abstract class HashOpenAdressingTest {
    protected HashOpenAdressing<String> hashTable;
    protected HashOpenAdressing<Integer> integerHashTable;

    @Before
    public abstract void setUp();

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenAddValueIsNull() {
        // given
        String value = null;

        // when
        hashTable.put(value);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenGetValueIsNull() {
        // given
        String value = null;

        // when
        hashTable.get(value);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenDeleteValueIsNull() {
        // given
        String value = null;

        // when
        hashTable.delete(value);

        // then
        assert false;
    }

    @Test
    public void itShouldAddValueToHashTable() {
        // given
        String value = "test";

        // when
        hashTable.put(value);

        // then
        String addedValue = hashTable.get(value);
        assertEquals(addedValue, value);
    }

    @Test
    public void itShouldAddValuesWithCollisionToHashTable() {
        // given
        Integer firstValue = new Integer(123);
        Integer secondValue = new Integer(123);
        Integer thirdValue = new Integer(123);

        // when
        integerHashTable.put(firstValue);
        integerHashTable.put(secondValue);
        integerHashTable.put(thirdValue);

        // then
        Integer firstAddedValue = integerHashTable.get(firstValue);
        Integer secondAddedValue = integerHashTable.get(secondValue);
        Integer thirdAddedValue = integerHashTable.get(thirdValue);
        assertEquals(firstAddedValue, firstValue);
        assertEquals(secondAddedValue, secondValue);
        assertEquals(thirdAddedValue, thirdValue);
    }

    @Test
    public void itShouldReturnNullWhenValueIsNotFound() {
        // given
        String value = "test";

        // when
        String result = hashTable.get(value);

        // then
        assertNull(result);
    }

    @Test
    public void itShouldDeleteValueFromHashTable() {
        // given
        String value = "test";
        hashTable.put(value);

        // when
        hashTable.delete(value);

        // then
        String result = hashTable.get(value);
        assertNull(result);
    }

    @Test
    public void itShouldDeleteValueWithCollisionFromHashTable() {
        // given
        Integer firstValue = new Integer(123);
        Integer secondValue = new Integer(123);
        Integer thirdValue = new Integer(123);
        integerHashTable.put(firstValue);
        integerHashTable.put(secondValue);
        integerHashTable.put(thirdValue);

        // when
        integerHashTable.delete(secondValue);

        // then
        Integer firstAddedValue = integerHashTable.get(firstValue);
        Integer secondAddedValue = integerHashTable.get(secondValue);
        Integer thirdAddedValue = integerHashTable.get(thirdValue);
        assertEquals(firstValue, firstAddedValue);
        assertNull(secondAddedValue);
        assertEquals(thirdValue, thirdAddedValue);
    }

    @Test
    public void itShouldNotThrowExceptionWhenElemToDeleteNotFound() {
        // given
        String value = "test";
        hashTable.put(value);

        // when
        hashTable.delete(value);

        // then
        String result = hashTable.get(value);
        assertNull(result);
    }

    @Test
    public void itShouldAddObjectWithMaxIntHashCode() {
        // given
        Integer value = Integer.MAX_VALUE;

        // when
        integerHashTable.put(value);

        // then
        Integer addedValue = integerHashTable.get(value);
        assertEquals(value, addedValue);
    }

    @Test
    public void itShouldAddObjectWithMinIntHashCode() {
        // given
        Integer value = Integer.MIN_VALUE;

        // when
        integerHashTable.put(value);

        // then
        Integer addedValue = integerHashTable.get(value);
        assertEquals(value, addedValue);
    }

    @Test
    public void itShouldCorrectlyAddNewElemsWhenNotExistInHashTable() {
        // given
        String newElem = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(hashTable);
        hashTable.put(newElem);
        int nOfElemsAfterPut = getNumOfElems(hashTable);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
    }

    @Test
    public void itShouldCalculatePositiveHashWhenKeyIsMinIntValue() {
        // given
        int key = Integer.MIN_VALUE;
        int i = 0;

        // when
        int result = hashTable.hashFunc(key, i);

        // then
        boolean isPositive = result >= 0;
        assertTrue(isPositive);
    }

    private int getNumOfElems(HashTable<?> hash) {
        String fieldNumOfElems = "nElems";
        try {
            Field field = hash.getClass().getSuperclass().getDeclaredField(fieldNumOfElems);
            field.setAccessible(true);

            int numOfElems = field.getInt(hash);

            return numOfElems;

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
