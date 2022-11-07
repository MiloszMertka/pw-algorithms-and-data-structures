package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public abstract class HashOpenAdressing<T extends Comparable<T>> implements HashTable<T> {

    private static final int DEFAULT_INITIAL_SIZE = 2039;

    private final T nil = null;
    private final double correctLoadFactor;
    private int size;
    private int nElems;
    private T[] hashElems;
    private boolean[] deletedElems;

    HashOpenAdressing() {
        this(DEFAULT_INITIAL_SIZE);
    }

    HashOpenAdressing(int size) {
        validateHashInitSize(size);

        this.size = size;
        this.hashElems = (T[]) new Comparable[this.size];
        this.deletedElems = new boolean[this.size];
        this.correctLoadFactor = 0.75;
    }

    @Override
    public void put(T newElem) {
        validateInputElem(newElem);
        resizeIfNeeded();

        addElemToGivenHashTable(hashElems, newElem);
        nElems++;
    }

    @Override
    public T get(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (isElemNotNil(hashId)) {
            if (isCurrentElemEqualToGivenElem(hashId, elem)) {
                return elem;
            }

            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }

        return null;
    }

    @Override
    public void delete(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (isElemNotNil(hashId)) {
            if (isCurrentElemEqualToGivenElem(hashId, elem)) {
                deletedElems[hashId] = true;
                nElems--;
            }

            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }
    }

    private void addElemToGivenHashTable(T[] hashTable, T elem) {
        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (isElemNotNil(hashId, hashTable) && !deletedElems[hashId]) {
            if (isCurrentElemEqualToGivenElem(hashId, elem, hashTable)) {
                break;
            }

            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }

        hashTable[hashId] = elem;
    }

    private boolean isElemNotNil(int hashId) {
        return hashElems[hashId] != nil;
    }

    private boolean isElemNotNil(int hashId, T[] hashTable) {
        return hashTable[hashId] != nil;
    }

    private boolean isCurrentElemEqualToGivenElem(int currentHashId, T elem) {
        return hashElems[currentHashId] == elem && !deletedElems[currentHashId];
    }

    private boolean isCurrentElemEqualToGivenElem(int currentHashId, T elem, T[] hashTable) {
        return hashTable[currentHashId] == elem && !deletedElems[currentHashId];
    }

    private void validateHashInitSize(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Initial size of hash table cannot be lower than 1!");
        }
    }

    private void validateInputElem(T newElem) {
        if (newElem == null) {
            throw new IllegalArgumentException("Input elem cannot be null!");
        }
    }

    abstract int hashFunc(int key, int i);

    int getSize() {
        return size;
    }

    private void resizeIfNeeded() {
        double loadFactor = countLoadFactor();

        if (loadFactor >= correctLoadFactor) {
            doubleResize();
        }
    }

    private double countLoadFactor() {
        return (double) nElems / size;
    }

    private void doubleResize() {
        this.size *= 2;

        T[] newHashElems = (T[]) new Comparable[this.size];
        deletedElems = new boolean[this.size];

        for (T elem : hashElems) {
            if (elem != null) {
                addElemToGivenHashTable(newHashElems, elem);
            }
        }

        hashElems = newHashElems;
    }

}
