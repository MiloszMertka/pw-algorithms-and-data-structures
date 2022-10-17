package pl.edu.pw.ee;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class HeapTest {

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void itShouldThrowExceptionWhenHeapifyOutOfBounds() {
        // given
        List<Double> items = new ArrayList<>();
        Double[] itemsArray = {1.0, 2.0, 3.0, 4.0, 5.0};
        Collections.addAll(items, itemsArray);
        Heap<Double> heap = new Heap<>(items);

        // when
        heap.heapify(0, itemsArray.length);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenHeapifyStartIdIsGreaterThanEndId() {
        // given
        List<Double> items = new ArrayList<>();
        Double[] itemsArray = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0};
        Collections.addAll(items, itemsArray);
        Heap<Double> heap = new Heap<>(items);

        // when
        heap.heapify(itemsArray.length - 1, 0);

        // then
        assert false;
    }

    @Test
    public void itShouldHeapifySubtree() {
        // given
        List<Double> items = new ArrayList<>();
        Double[] itemsArray = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0};
        Collections.addAll(items, itemsArray);
        Heap<Double> heap = new Heap<>(items);

        // when
        heap.heapify(2, itemsArray.length - 2);

        // then
        Double[] expected = {1.0, 2.0, 6.0, 5.0, 4.0, 3.0, 7.0};
        assertArrayEquals(expected, heap.getData().toArray());
    }

    @Test
    public void itShouldBuildHeapFromList() {
        // given
        List<Double> items = new ArrayList<>();
        Double[] itemsArray = {0.5, 6.833, Double.MIN_VALUE, Double.MAX_VALUE, 0.0, -4.0, -Double.MIN_VALUE, -Double.MAX_VALUE};
        Collections.addAll(items, itemsArray);
        Heap<Double> heap = new Heap<>(items);

        // when
        heap.build();

        // then
        Double[] expected = {Double.MAX_VALUE, 6.833, 0.5, Double.MIN_VALUE, 0.0, -Double.MIN_VALUE, -4.0, -Double.MAX_VALUE};
        assertArrayEquals(expected, heap.getData().toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenTryingToAddNullToHeap() {
        // given
        Heap<Double> heap = new Heap<>(null);
        Double item = null;

        // when
        heap.put(item);

        // then
        assert false;
    }

    @Test
    public void itShouldAddItemsToHeap() {
        // given
        Heap<Double> heap = new Heap<>(null);
        //Double[] items = {0.5, 6.833, Double.MIN_VALUE, Double.MAX_VALUE, 0.0, -4.0, -Double.MIN_VALUE, -Double.MAX_VALUE};
        Double[] items = {0.5, 6.833, -4.0, 0.0, 7.6, 1.2, -5.0};

        // when
        for (Double item : items) {
            heap.put(item);
        }

        System.out.println(Arrays.toString(heap.getData().toArray()));

        // then
        Double[] expected = {6.833, 0.5, Double.MIN_VALUE, Double.MAX_VALUE, 0.0, -4.0, -Double.MIN_VALUE, -Double.MAX_VALUE};
        assertArrayEquals(expected, heap.getData().toArray());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void itShouldThrowExceptionWhenTryingToGetItemFromEmptyHeap() {
        // given
        Heap<Double> heap = new Heap<>(null);

        // when
        heap.pop();

        // then
        assert false;
    }

    @Test
    public void itShouldGetItemsFromHeap() {
        // given
        List<Double> items = new ArrayList<>();
        Double[] itemsArray = {0.5, 6.833, Double.MIN_VALUE, Double.MAX_VALUE, 0.0, -4.0, -Double.MIN_VALUE, -Double.MAX_VALUE};
        Collections.addAll(items, itemsArray);
        Heap<Double> heap = new Heap<>(items);
        heap.build();

        // when
        List<Double> result = new ArrayList<>();
        while (!heap.getData().isEmpty()) {
            result.add(heap.pop());
        }

        // then
        Double[] expected = {Double.MAX_VALUE, 6.833, 0.5, Double.MIN_VALUE, 0.0, -Double.MIN_VALUE, -4.0, -Double.MAX_VALUE};
        assertArrayEquals(expected, result.toArray());
    }

}
