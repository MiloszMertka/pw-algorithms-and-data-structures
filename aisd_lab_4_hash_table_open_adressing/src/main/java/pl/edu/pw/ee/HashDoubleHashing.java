package pl.edu.pw.ee;

public class HashDoubleHashing<T extends Comparable<T>> extends HashOpenAdressing<T> {

    public HashDoubleHashing() {
        super();
    }

    public HashDoubleHashing(int size) {
        super(size);

        validateSizeIsNotThree(size);
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();

        int f = key % m;
        int g = 1 + (key % (m - 3));

        if (g == 0) {
            g++;
        }

        int hash = (f + (i * g)) % m;

        hash = hash < 0 ? -hash : hash;

        return hash;
    }

    private void validateSizeIsNotThree(int size) {
        if (size == 3) {
            throw new IllegalArgumentException("Size cannot be equal to 3!");
        }
    }

}
