package Main;

public class Par<X,Y> {
    public final X x;
    public final Y y;

    public X getFirst() {
        return x;
    }

    public Y getSecond() {
        return y;
    }

    public Par(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x +","+ y +")";
    }
}
