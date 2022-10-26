package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

import java.util.ArrayList;
import java.util.List;

public class HashListChaining<T extends Comparable<T>> implements HashTable<T> {

    private final Elem nil = null;
    private final List<Elem> hashElems;
    private int nElem;

    private class Elem {

        private T value;
        private Elem next;

        Elem(T value, Elem nextElem) {
            this.value = value;
            this.next = nextElem;
        }
    }

    public HashListChaining(int size) {
        checkIfSizeIsGreaterThanZero(size);
        hashElems = new ArrayList<>(size);
        initializeHash(size);
    }

    public List<Elem> getHashElems() {
        return hashElems;
    }

    @Override
    public void add(T value) {
        checkIfValueIsNotNull(value);

        addValueToHashElems(value);
    }

    @Override
    public T get(T value) {
        checkIfValueIsNotNull(value);

        Elem elem = findElemByValue(value);
        return elem != nil ? elem.value : null;
    }

    @Override
    public void delete(T value) {
        checkIfValueIsNotNull(value);

        int hashId = computeHashId(value);
        Elem currentElem = hashElems.get(hashId);
        Elem previousElem = nil;

        while (currentElem != nil && !currentElem.value.equals(value)) {
            previousElem = currentElem;
            currentElem = currentElem.next;
        }

        deleteElemFromHashElems(currentElem, previousElem, hashId);
    }

    public double countLoadFactor() {
        int size = hashElems.size();
        return (double) nElem / size;
    }

    private void initializeHash(int size) {
        for (int i = 0; i < size; i++) {
            hashElems.add(nil);
        }
    }

    private int countHashId(int hashCode) {
        int n = hashElems.size();
        int absoluteHashCode = Math.abs(hashCode != Integer.MIN_VALUE ? hashCode : hashCode + 1);
        return absoluteHashCode % n;
    }

    private int computeHashId(T value) {
        int hashCode = value.hashCode();
        return countHashId(hashCode);
    }

    private void addValueToHashElems(T value) {
        int hashId = computeHashId(value);
        Elem oldElem = findElemByValue(value);

        if (oldElem != nil) {
            oldElem.value = value;
        } else {
            hashElems.set(hashId, new Elem(value, hashElems.get(hashId)));
            nElem++;
        }
    }

    private Elem findElemByValue(T value) {
        int hashId = computeHashId(value);
        Elem elem = hashElems.get(hashId);

        while (elem != nil && !elem.value.equals(value)) {
            elem = elem.next;
        }

        return elem;
    }

    private void deleteElemFromHashElems(Elem currentElem, Elem previousElem, int hashId) {
        if (currentElem != nil) {
            if (previousElem == nil) {
                hashElems.set(hashId, currentElem.next);
            } else {
                previousElem.next = currentElem.next;
            }

            nElem--;
        }
    }

    private void checkIfValueIsNotNull(T value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }
    }

    private void checkIfSizeIsGreaterThanZero(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size cannot be lower than or equal 0");
        }
    }

}
