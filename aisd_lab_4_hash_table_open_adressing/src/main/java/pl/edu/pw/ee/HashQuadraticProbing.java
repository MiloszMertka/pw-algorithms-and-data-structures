package pl.edu.pw.ee;

public class HashQuadraticProbing<T extends Comparable<T>> extends HashOpenAdressing<T> {

    private final double a;
    private final double b;

    public HashQuadraticProbing() {
        super();

        a = 0.5;
        b = 0.5;
    }

    public HashQuadraticProbing(int size, double a, double b) {
        super(size);

        validateParameter(a);
        validateParameter(b);
        validateParameterIsNotZero(b);

        this.a = a;
        this.b = b;
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();

        int hash = (int) (((key % m) + (a * i) + (b * i * i)) % m);

        hash = hash < 0 ? -hash : hash;

        return hash;
    }

    private void validateParameter(double parameter) {
        if (Double.isNaN(parameter) || Double.isInfinite(parameter)) {
            throw new IllegalArgumentException("Parameter cannot be Infinite or NaN!");
        }
    }

    private void validateParameterIsNotZero(double parameter) {
        if (parameter == 0) {
            throw new IllegalArgumentException("Parameter cannot be equal to 0!");
        }
    }

}
