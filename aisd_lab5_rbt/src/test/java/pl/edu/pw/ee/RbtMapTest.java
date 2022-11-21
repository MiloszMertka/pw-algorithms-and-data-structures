package pl.edu.pw.ee;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RbtMapTest {

    private RbtMap<String, String> rbtMap;

    @Before
    public void setUp() {
        rbtMap = new RbtMap<>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenSetKeyIsNull() {
        // given
        String key = null;
        String value = "test";

        // when
        rbtMap.setValue(key, value);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenSetValueIsNull() {
        // given
        String key = "key";
        String value = null;

        // when
        rbtMap.setValue(key, value);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenGetKeyIsNull() {
        // given
        String key = null;

        // when
        rbtMap.getValue(key);

        // then
        assert false;
    }

    @Test
    public void itShouldPutKeyValuePairIntoMap() {
        // given
        String key = "key";
        String value = "test";

        // when
        rbtMap.setValue(key, value);

        // then
        String result = rbtMap.getValue(key);
        assertEquals(value, result);
    }

    @Test
    public void itShouldGetValueByKey() {
        // given
        String key = "key";
        String value = "test";
        rbtMap.setValue(key, value);

        // when
        String result = rbtMap.getValue(key);

        // then
        assertEquals(value, result);
    }

}
