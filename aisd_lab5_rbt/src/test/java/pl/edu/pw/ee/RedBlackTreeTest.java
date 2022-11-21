package pl.edu.pw.ee;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RedBlackTreeTest {
    
    private RedBlackTree<String, String> rbt;

    @Before
    public void setUp() {
        rbt = new RedBlackTree<>();
    }
    
    @Test
    public void itShouldDoNothingWhenDeleteMaxOnEmptyTree() {
        // given
        
        // when
        rbt.deleteMax();
        
        // then
        assert true;
    }
    
    @Test
    public void itShouldInsertMultipleElements() {
        // given
        String[] keys = {"ka", "kb", "kc", "kd", "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn"};
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        
        // when
        for (int i = 0; i < keys.length; i++) {
            rbt.put(keys[i], values[i]);
        }
        
        // then
        for (int i = 0; i < keys.length; i++) {
            String result = rbt.get(keys[i]);
            assertEquals(values[i], result);
        }
    }
    
    @Test
    public void itShouldInsertMultipleElementsInReverseOrder() {
        // given
        String[] keys = {"ka", "kb", "kc", "kd", "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn"};
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        
        // when
        for (int i = keys.length - 1; i >= 0; i--) {
            rbt.put(keys[i], values[i]);
        }
        
        // then
        for (int i = keys.length - 1; i >= 0; i--) {
            String result = rbt.get(keys[i]);
            assertEquals(values[i], result);
        }
    }
    
    @Test
    public void itShouldInsertMultipleElementsInRandomOrder() {
        // given
        String[] keys = {"kn", "ke", "ka", "kd", "kb", "kf", "kg", "kh", "ki", "kj", "kl", "kk", "km", "ka"};
        String[] values = {"n", "e", "a", "d", "b", "f", "g", "h", "i", "j", "k", "l", "m", "a"};
        
        // when
        for (int i = keys.length - 1; i >= 0; i--) {
            rbt.put(keys[i], values[i]);
        }
        
        // then
        for (int i = keys.length - 1; i >= 0; i--) {
            String result = rbt.get(keys[i]);
            assertEquals(values[i], result);
        }
    }
    
    @Test
    public void itShouldDeleteMultipleElements() {
        // given
        String[] keys = {"ka", "kb", "kc", "kd", "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn"};
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        
        for (int i = 0; i < keys.length; i++) {
            rbt.put(keys[i], values[i]);
        }
        
        // when
        for (int i = 0; i < keys.length; i++) {
            rbt.deleteMax();
        }
        
        // then
        assert true;
    }
    
    @Test
    public void itShouldGetMultipleElements() {
        // given
        String[] keys = {"ka", "kb", "kc", "kd", "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn"};
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        
        for (int i = 0; i < keys.length; i++) {
            rbt.put(keys[i], values[i]);
        }
        
        // when
        // then
        for (int i = 0; i < keys.length; i++) {
            String result = rbt.get(keys[i]);
            assertEquals(values[i], result);
        }
    }
    
    @Test
    public void itShouldReturnNullWhenGetOnEmptyTree() {
        // given
        String key = "ka";
        
        // when
        String result = rbt.get(key);
        
        // then
        assertNull(result);
    }
    
    @Test
    public void itShouldDeleteMultipleElementsInsertedInReverseOrder() {
        // given
        String[] keys = {"ka", "kb", "kc", "kd", "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn"};
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        
        for (int i = keys.length - 1; i >= 0; i--) {
            rbt.put(keys[i], values[i]);
        }
        
        // when
        for (int i = keys.length - 1; i >= 0; i--) {
            rbt.deleteMax();
        }
        
        // then
        assert true;
    }
    
    @Test
    public void itShouldGetElementsPreOrder() {
        // given
        String[] keys = {"ka", "kb", "kc", "kd", "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn"};
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        
        for (int i = 0; i < keys.length; i++) {
            rbt.put(keys[i], values[i]);
        }
        
        // when
        String result = rbt.getPreOrder();
        
        // then
        String expected = "kh:h kd:d kb:b ka:a kc:c kf:f ke:e kg:g kl:l kj:j ki:i kk:k kn:n km:m";
        assertEquals(expected, result);
    }
    
    @Test
    public void itShouldGetElementsInOrder() {
        // given
        String[] keys = {"ka", "kb", "kc", "kd", "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn"};
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        
        for (int i = 0; i < keys.length; i++) {
            rbt.put(keys[i], values[i]);
        }
        
        // when
        String result = rbt.getInOrder();
        
        // then
        String expected = "ka:a kb:b kc:c kd:d ke:e kf:f kg:g kh:h ki:i kj:j kk:k kl:l km:m kn:n";
        assertEquals(expected, result);
    }
    
    @Test
    public void itShouldGetElementsPostOrder() {
        // given
        String[] keys = {"ka", "kb", "kc", "kd", "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn"};
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        
        for (int i = 0; i < keys.length; i++) {
            rbt.put(keys[i], values[i]);
        }
        
        // when
        String result = rbt.getPostOrder();
        
        // then
        String expected = "ka:a kc:c kb:b ke:e kg:g kf:f kd:d ki:i kk:k kj:j km:m kn:n kl:l kh:h";
        assertEquals(expected, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenKeyIsNullWhenPut() {
        // given
        String[] keys = {"ka", "kb", "kc", null, "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn"};
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        
        // when
        for (int i = 0; i < keys.length; i++) {
            rbt.put(keys[i], values[i]);
        }
        
        // then
        assert false;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenValueIsNullWhenPut() {
        // given
        String[] keys = {"ka", "kb", "kc", "kd", "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn"};
        String[] values = {"a", "b", "c", null, "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        
        // when
        for (int i = 0; i < keys.length; i++) {
            rbt.put(keys[i], values[i]);
        }
        
        // then
        assert false;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenKeyIsNullWhenGet() {
        // given
        String[] keys = {"ka", "kb", "kc", "kd", "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn"};
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        String key = null;
        
        for (int i = 0; i < keys.length; i++) {
            rbt.put(keys[i], values[i]);
        }
        
        // when
        rbt.get(key);
        
        // then
        assert false;
    }

}
