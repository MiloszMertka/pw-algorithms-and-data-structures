package pl.edu.pw.ee;

import pl.edu.pw.ee.exceptions.NotImplementedException;
import pl.edu.pw.ee.services.HashTable;

public abstract class HashOpenAdressing<T extends Comparable<T>> implements HashTable<T> {

    private final T nil = null;
    private final T del = null;
    private int size;
    private int nElems;
    private T[] hashElems;
    private final double correctLoadFactor;

    HashOpenAdressing() {
        this(2039); // initial size as random prime number
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

        int key = newElem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (hashElems[hashId] != nil && i < size) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }

        hashElems[hashId] = newElem;
        nElems++;
    }

    @Override
    public T get(T elem) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(T elem) {
        // TODO Auto-generated method stub

    }

    private void addElemToGivenHashTable(T[] hashTable, T elem) {
        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (hashTable[hashId] != nil && i < size) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }

        hashElems[hashId] = elem;
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
