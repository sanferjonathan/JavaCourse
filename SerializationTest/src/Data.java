import java.io.Serializable;

public class Data implements Serializable {

    private int n;

    public Data(int i) {
        this.n = i;
    }

    public String toString() {
        return Integer.toString(n);
    }

    public int multiply(int i) {
        n *= i;
        return n;
    }
}
