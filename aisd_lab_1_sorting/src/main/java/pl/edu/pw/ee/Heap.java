package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pl.edu.pw.ee.services.HeapExtension;
import pl.edu.pw.ee.services.HeapInterface;

public class Heap<T extends Comparable<T>> implements HeapInterface<T>, HeapExtension {

    private List<T> data;

    public Heap(List<T> data) {
        this.data = data;

        if (this.data == null) {
            this.data = new ArrayList<>();
        }
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public void put(T item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        data.add(item);

        if (data.size() > 1) {
            heapify(getRootIndex(), getLastLeafIndex() + 1);
            System.out.println(Arrays.toString(data.toArray()));
        }
    }

    @Override
    public T pop() {
        checkIfDataIsNotEmpty();

        int rootIndex = getRootIndex();
        int lastLeafIndex = getLastLeafIndex();

        swap(rootIndex, lastLeafIndex);
        T item = data.remove(lastLeafIndex);

        if (data.size() > 1) {
            lastLeafIndex = getLastLeafIndex();
            heapify(rootIndex, lastLeafIndex);
        }

        return item;
    }

    @Override
    public void build() {
        int lastLeafIndex = getLastLeafIndex();

        for (int i = lastLeafIndex / 2 - 1; i >= 0; i--) {
            heapify(i, lastLeafIndex + 1);
        }
    }

    @Override
    public void heapify(int startId, int endId) {
        checkIfIndexIsOutOfBounds(startId);
        checkIfIndexIsOutOfBounds(endId - 1);

        if (startId >= endId) {
            throw new IllegalArgumentException("startId cannot be greater than or equal to endId");
        }

        int parentId = startId;

        while (true) {
            int largestId = parentId;
            int leftChildId = 2 * largestId + 1;
            int rightChildId = 2 * largestId + 2;

            if (leftChildId < endId && data.get(leftChildId).compareTo(data.get(largestId)) > 0) {
                largestId = leftChildId;
            }

            if (rightChildId < endId && data.get(rightChildId).compareTo(data.get(largestId)) > 0) {
                largestId = rightChildId;
            }

            if (largestId != parentId) {
                swap(largestId, parentId);
                parentId = largestId;
            } else {
                break;
            }
        }
    }

    private int getRootIndex() {
        checkIfDataIsNotEmpty();

        return 0;
    }

    private int getLastLeafIndex() {
        checkIfDataIsNotEmpty();

        return data.size() - 1;
    }

    private void swap(int firstIndex, int secondIndex) {
        T firstValue = data.get(firstIndex);
        data.set(firstIndex, data.get(secondIndex));
        data.set(secondIndex, firstValue);
    }

    private void checkIfDataIsNotEmpty() {
        if (data.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("heap is empty");
        }
    }

    private void checkIfIndexIsOutOfBounds(int index) {
        if (index < 0 || index >= data.size()) {
            throw new ArrayIndexOutOfBoundsException("index is out of bounds");
        }
    }

}
