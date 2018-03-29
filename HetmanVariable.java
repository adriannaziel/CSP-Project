import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

public class HetmanVariable
{

    int N;
    boolean curr_val;

    ArrayList<Integer> tried ;

    public ArrayList<Boolean> domain;

    public HetmanVariable(){

        curr_val = false;
        domain = new ArrayList<>();
        domain.add(false);

       // tried = new ArrayList<>();
    }

    public void addNewDomain(){
        domain.add(false);
    }

//    public ArrayList<Integer> getDomain(int id) {
//        return domain.get(id);
//    }


}
