public class Tuple<T> {
    T a;
    T b;

    public Tuple(T ax, T bx){
        a=ax;
        b=bx;
    }


    @Override
    public String toString() {
        return "("+a.toString()+","+b.toString()+")";
    }
}
