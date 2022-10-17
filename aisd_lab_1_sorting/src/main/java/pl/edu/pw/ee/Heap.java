package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.List;
import pl.edu.pw.ee.services.HeapInterface;

public class Heap<T extends Comparable<T>> implements HeapInterface<T> {

    private final List<T> heap = new ArrayList<>();

    @Override
    public void put(T item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        heap.add(item);
        heapUp();
    }

    @Override
    public T pop() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("cannot get item from empty heap");
        }

        int rootIndex = 0;
        T item = heap.remove(rootIndex);

        heapDown();

        return item;
    }

    private void heapUp() {

    }

    private void heapDown() {

    }

}
