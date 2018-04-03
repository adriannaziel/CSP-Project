
import java.util.ArrayList;
import java.util.stream.IntStream;

public class SquareVariable {

    int N;
    int curr_val;

    ArrayList<Integer> tried ;

    public ArrayList<ArrayList<Integer>> domains;

    public SquareVariable(int nx){
        N=nx;
        curr_val = 0;
        domains = new ArrayList<>();
        domains.add(new ArrayList<>());
        IntStream.iterate(1, n -> {
            domains.get(0).add(n);return n + 1;}).limit(N).toArray();

        tried = new ArrayList<>();
    }

    public void addNewDomain(ArrayList<Integer> newd){
        domains.add(newd);
    }

    public ArrayList<Integer> getLastDomain() {
        return domains.get(domains.size()-1);
    }


}
