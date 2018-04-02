import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HetmansVariable2 {

    int N;
    int bt_curr_val;
    Tuple<Integer> curr_tuple;

    ArrayList<Tuple<Integer>> tried;

    public ArrayList<ArrayList<Tuple<Integer>>> domains;
    int id;

    public HetmansVariable2(int id, int nx) {
        this.id = id;
        N = nx;
        bt_curr_val = -1;
        curr_tuple=new Tuple<>(-1,-1);

        domains = new ArrayList<>();
        domains.add(new ArrayList<Tuple<Integer>>());

        //  domains.get(0).add(new Tuple<>(0,0));

        fillDomain(nx);

        tried = new ArrayList<>();
    }

    private void fillDomain(int nx) {
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < nx; j++) {
                domains.get(0).add(new Tuple<>(i, j));
            }
        }
    }

    public void addNewDomain(ArrayList<Tuple<Integer>> newd) {
        domains.add(newd);
//        domains.add(new ArrayList<Integer>());
//        int[] range = IntStream.iterate(1, n -> {
//            domains.get(domains.size()-1).add(n);return n + 1;}).limit(N).toArray();
    }

    public ArrayList<Tuple<Integer>> getLastDomain() {
        return domains.get(domains.size() - 1);
    }

    public HetmansVariable2 clone() {
        HetmansVariable2 hv = new HetmansVariable2(this.id, this.N);
        hv.bt_curr_val = this.bt_curr_val;
        hv.domains = this.domains;
        return hv;
    }

    public void addNewUpdatedDomain(int i, int j) {

        ArrayList<Tuple<Integer>> newdomain = new ArrayList<>(domains.get(domains.size() - 1));

        HashSet<Tuple<Integer>> toRemove = new HashSet<Tuple<Integer>>();

        //row, col
        for (Tuple<Integer> t : newdomain) {
            if (t.a == i || t.b == j) {
                toRemove.add(t);
            }
        }

        newdomain.removeAll(toRemove);


        //diagonals
        int a;
        int b;
        for (a = i, b = j; a >= 0 && b >= 0; a--, b--) {
            removeTuple(a,b,newdomain);
        }


        for (a = i, b = j; a >= 0 && b < N; a--, b++) {
            removeTuple(a,b,newdomain);
        }

        for (a = i, b = j; b >= 0 && a < N; a++, b--) {
            removeTuple(a,b,newdomain);
        }

        for (a = i, b = j; b < N && a < N; a++, b++) {
            removeTuple(a,b,newdomain);
        }

        domains.add(newdomain);
    }


    public void removeTuple(int i, int j, ArrayList<Tuple<Integer>> domain) {
        Tuple<Integer> tupletoremove = null;
        for (Tuple<Integer> t : domain) {
            if (t.a == i && t.b == j) {
                tupletoremove = t;
            }
        }
        domain.remove(tupletoremove);
    }

}