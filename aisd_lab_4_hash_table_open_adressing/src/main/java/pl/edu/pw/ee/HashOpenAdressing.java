package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public abstract class HashOpenAdressing<T extends Comparable<T>> implements HashTable<T> {

    private static final int DEFAULT_INITIAL_SIZE = 2039;

    private final T nil = null;
    private final T del = (T) new Comparable<T>() {
        @Override
        public int compareTo(T o) {
            return 0;
        }
    };
    private final double correctLoadFactor;
    private int size;
    private int nElems;
    private T[] hashElems;

    HashOpenAdressing() {
        this(DEFAULT_INITIAL_SIZE);
    }

    HashOpenAdressing(int size) {
        validateHashInitSize(size);

        this.size = size;
        this.hashElems = (T[]) new Comparable[this.size];
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
        int initialHashId = hashId;

        while (isElemNotNil(hashId)) {
            if (isCurrentElemEqualToGivenElem(hashId, elem)) {
                return elem;
            }

            i = (i + 1) % size;
            hashId = hashFunc(key, i);

            if (hashId == initialHashId) {
                return null;
            }
        }

        return null;
    }

    @Override
    public void delete(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        int initialHashId = hashId;

        while (isElemNotNil(hashId)) {
            if (isCurrentElemEqualToGivenElem(hashId, elem)) {
                hashElems[hashId] = del;
                nElems--;
            }

            i = (i + 1) % size;
            hashId = hashFunc(key, i);

            if (hashId == initialHashId) {
                return;
            }
        }
    }

    private void addElemToGivenHashTable(T[] hashTable, T elem) {
        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        int initialHashId = hashId;

        while (isElemNotNil(hashId, hashTable) && hashElems[hashId] != del) {
            if (isCurrentElemEqualToGivenElem(hashId, elem, hashTable)) {
                break;
            }

            i = (i + 1) % size;
            hashId = hashFunc(key, i);

            if (hashId == initialHashId) {
                doubleResize();
                i = 0;
            }
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
        return hashElems[currentHashId] == elem && hashElems[currentHashId] != del;
    }

    private boolean isCurrentElemEqualToGivenElem(int currentHashId, T elem, T[] hashTable) {
        return hashTable[currentHashId] == elem && hashElems[currentHashId] != del;
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

        for (T elem : hashElems) {
            if (elem != null) {
                addElemToGivenHashTable(newHashElems, elem);
            }
        }

        hashElems = newHashElems;
    }

}
