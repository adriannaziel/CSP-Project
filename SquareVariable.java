import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

public class SquareVariable {

    int N;
    int curr_val;

    ArrayList<Integer> tried ;

    public ArrayList<ArrayList<Integer>> domain;

    public SquareVariable(int nx){
        N=nx;
        curr_val = 0;
        domain = new ArrayList<>();
        domain.add(new ArrayList<Integer>());
        int[] range = IntStream.iterate(1, n -> {domain.get(0).add(n);return n + 1;}).limit(N).toArray();

        tried = new ArrayList<>();
    }

    public void addNewDomain(){
        domain.add(new ArrayList<Integer>());
        int[] range = IntStream.iterate(1, n -> {domain.get(domain.size()-1).add(n);return n + 1;}).limit(N).toArray();
    }

    public ArrayList<Integer> getDomain(int id) {
        return domain.get(id);
    }


}
