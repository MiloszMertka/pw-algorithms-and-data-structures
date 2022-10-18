package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HeapExtension;
import pl.edu.pw.ee.services.HeapInterface;

import java.util.ArrayList;
import java.util.List;

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
            heapify(getLastLeafIndex(), getRootIndex());
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
        checkIfStartIndexIsOutOfBounds(startId);
        checkIfEndIndexIsOutOfBounds(endId);

        if (startId == endId) {
            return;
        }

        if (startId > endId) {
            heapUp(startId, endId);
        }

        heapDown(startId, endId);
    }

    private void heapUp(int startId, int endId) {
        int childId = startId;
        int parentId = getParentId(childId);

        while (parentId >= endId) {
            if (checkIfParentIsLarger(parentId, childId)) {
                swap(childId, parentId);
                childId = parentId;
                parentId = getParentId(childId);
            }  else {
                break;
            }
        }
    }

    private void heapDown(int startId, int endId) {
        int parentId = startId;

        while (true) {
            int largestNodeId = findLargestNodeId(parentId, endId);

            if (largestNodeId != parentId) {
                swap(largestNodeId, parentId);
                parentId = largestNodeId;
            } else {
                break;
            }
        }
    }

    private int findLargestNodeId(int parentId, int endId) {
        int largestNodeId = parentId;
        int leftChildId = getLeftChildId(parentId);
        int rightChildId = getRightChildId(parentId);

        if (checkIfChildIsLarger(leftChildId, endId, largestNodeId)) {
            largestNodeId = leftChildId;
        }

        if (checkIfChildIsLarger(rightChildId, endId, largestNodeId)) {
            largestNodeId = rightChildId;
        }

        return largestNodeId;
    }

    private int getParentId(int childId) {
        return (childId - 1) / 2;
    }
    private int getLeftChildId(int parentId) {
        return 2 * parentId + 1;
    }

    private int getRightChildId(int parentId) {
        return 2 * parentId + 2;
    }

    private boolean checkIfParentIsLarger(int parentId, int childId) {
        return data.get(childId).compareTo(data.get(parentId)) > 0;
    }

    private boolean checkIfChildIsLarger(int childId, int endId, int largestId) {
        return childId < endId && data.get(childId).compareTo(data.get(largestId)) > 0;
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

    private void checkIfStartIndexIsOutOfBounds(int index) {
        if (index < 0 || index >= data.size()) {
            throw new ArrayIndexOutOfBoundsException("start index is out of bounds");
        }
    }

    private void checkIfEndIndexIsOutOfBounds(int index) {
        if (index < -1 || index > data.size()) {
            throw new ArrayIndexOutOfBoundsException("end index is out of bounds");
        }
    }

}
