package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public class HashListChaining<T extends Comparable<T>> implements HashTable<T> {

    private final Elem nil = null;
    private final Elem[] hashElems;
    private int nElem;

    private class Elem<T extends Comparable<T>> {

        private T value;
        private Elem next;

        Elem(T value, Elem nextElem) {
            this.value = value;
            this.next = nextElem;
        }
    }

    public HashListChaining(int size) {
        hashElems = new Elem[size];
        initializeHash();
    }

    @Override
    public void add(T value) {
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem oldElem = hashElems[hashId];
        while (oldElem != nil && !oldElem.equals(value)) {
            oldElem = oldElem.next;
        }
        if (oldElem != nil) {
            oldElem.value = value;
        } else {
            hashElems[hashId] = new Elem(value, hashElems[hashId]);
            nElem++;
        }
    }

    @Override
    public T get(T value) {
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem elem = hashElems[hashId];

        while (elem != nil && !elem.value.equals(value)) {
            elem = elem.next;
        }

        return elem != nil ? elem.value : nil;
    }

    @Override
    public void delete(T value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem currentElem = hashElems[hashId];
        Elem previousElem = nil;

        while (currentElem != nil && !currentElem.value.equals(value)) {
            previousElem = currentElem;
            currentElem = currentElem.next;
        }

        if (currentElem != nil) {
            if (previousElem == nil) {
                hashElems[hashId] = currentElem.next;
            }

            previousElem.next = currentElem.next;
        }

    }

    public double countLoadFactor() {
        double size = hashElems.length;

        if (size <= 0) {
            throw new IllegalStateException("hash table size cannot be lower than or equal 0");
        }

        return nElem / size;
    }

    private void initializeHash() {
        int n = hashElems.length;

        for (int i = 0; i < n; i++) {
            hashElems[i] = nil;
        }
    }

    private int countHashId(int hashCode) {
        int n = hashElems.length;

        if (n <= 0) {
            throw new IllegalStateException("hash table size cannot be lower than or equal 0");
        }

        return Math.abs(hashCode) % n;
    }

}
