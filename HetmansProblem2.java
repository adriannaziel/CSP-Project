import java.util.ArrayList;

public class HetmansProblem2 {
    int N;

    public HetmansProblem2(int ps){
        N= ps;
    }




    public boolean canInsertValue(boolean[][] board, int i , int j){
        for(int k =0; k<N; k++){
            if(board[i][k]){
                return false;
            }

        }

        //in j column
        for(int k =0; k<N; k++){
            if(board[k][j]){
                return false;
            }
        }

        //diagonals
        int a;
        int b;
        for (a=i, b=j; a>=0 && b>=0; a--, b--)
            if (board[a][b] ){
                return false;
            }


        for (a=i, b=j; a>=0 && b<N; a--, b++)
            if (board[a][b]){
                return false;
            }

        for (a=i, b=j; b>=0 && a<N; a++, b--)
            if (board[a][b]){
                return false;
            }

        for (a=i, b=j; b<N && a<N; a++, b++)
            if (board[a][b]){
                return false;
            }
        return true;
    }

    public ArrayList<HetmansVariable2> backtrack(boolean[][] board){

        ArrayList<HetmansVariable2> result = new ArrayList<>();

        int set = 1;
        int toset = N;
        int back=0;
        int loops = 0;


        HetmansVariable2 hv = new HetmansVariable2(set,N);

        while (set <= toset){
            loops+=1;

//            if(pos>=N*N){
//                System.out.println("BRAK ROZWIAZANIA");
//                return null;
//            }

           // System.out.println("........................................");
            //System.out.println(set);

           Tuple<Integer> applied = applyBTWihoutAll(hv,board);
          //  System.out.println("applied " + applied);

            if(!applied.equals(new Tuple<>(-1,-1))){
                System.out.println("ok");
               // System.out.println(hv.curr_tuple);
                set+=1;
                board[hv.curr_tuple.a][hv.curr_tuple.b] = true;

                result.add(hv.clone());
                hv = new HetmansVariable2(set,N);
               // System.out.println("new hv:" + hv.curr_tuple);
               // System.out.println("result: " + result.size());
               // for(HetmansVariable2 h : result){
                //    System.out.print("id: " + h.id + " pos: " + h.curr_tuple + " ");
               // }
                //System.out.println();

                printMatrix(board);
            }

            else{
                System.out.println("nawrot");
                back+=1;
                set -=1;
                hv.tried = new ArrayList<>();
                hv.curr_tuple = new Tuple<>(-1,-1);

                if (result.size()==0){
                    System.out.println("brak!");
                    return null;
                }
                hv = result.get(result.size()-1);
               // System.out.println("er " + hv.curr_tuple);
                board[hv.curr_tuple.a][hv.curr_tuple.b] = false;
                result.remove(result.size()-1);

                printMatrix(board);

            }




        }//while

        System.out.println("liczba nawrotow: " + back + " petli " + loops);
        return result;

    }


//    public ArrayList<HetmansVariable2> backtrackAll(boolean[][] board){
//
//        int all = 0;
//        boolean count = true;
//
//        ArrayList<HetmansVariable2> result = new ArrayList<>();
//
//        int set = 1;
//        int toset = N;
//        int back=0;
//
//
//        HetmansVariable2 hv = new HetmansVariable2(set,N);
//
//        while (count) {
//
//            while (set <= toset) {
//
////            if(pos>=N*N){
////                System.out.println("BRAK ROZWIAZANIA");
////                return null;
////            }
//
//                System.out.println("........................................");
//                System.out.println(set);
//
//                Tuple<Integer> applied = applyBTWihoutAll(hv, board);
//                System.out.println("applied " + applied);
//
//                if (!applied.equals(new Tuple<>(-1, -1))) {
//                    System.out.println("ok");
//                    System.out.println(hv.curr_tuple);
//                    set += 1;
//                    board[hv.curr_tuple.a][hv.curr_tuple.b] = true;
//
//                    result.add(hv.clone());
//                    hv = new HetmansVariable2(set, N);
//                    System.out.println("new hv:" + hv.curr_tuple);
//                    System.out.println("result: " + result.size());
//                    for (HetmansVariable2 h : result) {
//                        System.out.print("id: " + h.id + " pos: " + h.curr_tuple + " ");
//                    }
//                    System.out.println();
//
//                    printMatrix(board);
//                } else {
//                    System.out.println("nawrot");
//                    back += 1;
//                    set -= 1;
//                    hv.tried = new ArrayList<>();
//                    hv.curr_tuple = new Tuple<>(-1, -1);
//
//                    if (result.size() == 0) {
//                        System.out.println("brak!");
//                        return null;
//                    }
//                    hv = result.get(result.size() - 1);
//                    System.out.println("er " + hv.curr_tuple);
//                    board[hv.curr_tuple.a][hv.curr_tuple.b] = false;
//                    result.remove(result.size() - 1);
//
//                    printMatrix(board);
//
//                }
//
//
//            }//while
//
//            System.out.println("liczba nawrotow: " + back);
//            System.out.println("ALL: "+ all);
//            back += 1;
//            set -= 1;
//            hv.tried = new ArrayList<>();
//            hv.curr_tuple = new Tuple<>(-1, -1);
//
//            if (result.size() == 0) {
//                System.out.println("brak!");
//                return null;
//            }
//            hv = result.get(result.size() - 1);
//            System.out.println("er " + hv.curr_tuple);
//            board[hv.curr_tuple.a][hv.curr_tuple.b] = false;
//            result.remove(result.size() - 1);
//
//            printMatrix(board);
//        }
//        return null;
//    }




    public Tuple<Integer> applyBTWihoutAll(HetmansVariable2 hv, boolean[][] board){
        Tuple<Integer> applied = new Tuple<>(-1,-1);  // bool?
       // SquareVariable sv = board[i][j];
        //for(int value :sv.domains.get(sv.domains.size()-1)){
        for(int nr = 0; nr<hv.getLastDomain().size();nr++){
            Tuple<Integer> value = hv.getLastDomain().get(nr) ;
            if( !hv.tried.contains(nr)){
                if(canInsertValue(board,value.a,value.b)){
                    hv.curr_tuple = value;
                    hv.tried.add(nr);
                    applied = value;
                    return applied;
                }
            }
        }

        return applied;
    }



    public ArrayList<HetmansVariable2> forwardcheck(boolean[][] board){
        int set = 0;
        int toset = N;

        int return_count=0;
        int loops_count = 0;

       // int row=0;
      //  int col = 0;

        ArrayList<HetmansVariable2> result = new ArrayList<>();
        for(int i =0; i<N;i++){
            result.add(new HetmansVariable2(i,N));
        }

        int id = 0;

        while (set < toset){


            loops_count+=1;
          //  System.out.println("........................................");
        //    System.out.println("id to set: " +id);
         //   System.out.println("rc: " + row + " " + col);

//            if(row == -1 || col == -1){ // &&
//                System.out.println("Nie ma rozwiazania");
//                return null;
//            }

           // Integer a = board[row][col].domains.size();
           // System.out.println( "cv:" + board[row][col].curr_val + " ds: " + a  + "wd: "  +board[row][col].domains.get(a-1).size() );


            Tuple<Integer> applied = applyFC(result.get(id)); /// result last 0 a, b


            if(applied.a != -1 || applied.b != -1){
                System.out.println("ok");
               // System.out.println("aplied:" + applied);
                set +=1;
                id +=1;
                board[applied.a][applied.b] = true;
                //updateValuesInDomains(board,applied,row,col);
                for(HetmansVariable2 h : result){
                    h.addNewUpdatedDomain(applied.a, applied.b);
                }

               // Tuple<Integer> nextfield = getNextField(row,col);
               // row = nextfield.a;
               // col = nextfield.b;

                printMatrix(board);
            }//if

            else{
                System.out.println("nawrot");
                return_count +=1;

                set -=1 ;
                id -=1;

                if(id == -1){
                    System.out.println("Brak rozwiazania");
                    return null;
                }

                board[result.get(id).curr_tuple.a][result.get(id).curr_tuple.b]=false;

               // Tuple<Integer> prevfield = getPrevField(row,col);
                //row = prevfield.a;
               // col = prevfield.b;
               // fixDomainsAfterReturn(board,result.get(id).curr_tuple.a,result.get(id).curr_tuple.b);
                for(HetmansVariable2 h : result){
                    h.domains.remove(h.domains.size()-1);
                }
                result.get(id).removeTuple(result.get(id).curr_tuple.a,result.get(id).curr_tuple.b, result.get(id).getLastDomain());
                result.get(id).curr_tuple = new Tuple<>(-1,-1);

            }//else


        }//while

        System.out.println("FC: liczba nawrotow: " + return_count + " liczba wywolan petli: " + loops_count);
        return result;


    }

    public Tuple<Integer> applyFC(HetmansVariable2 hv){
        Tuple<Integer> applied = new Tuple<>(-1,-1);  // bool?
        if(!hv.getLastDomain().isEmpty()){
          //  System.out.println("last domain  " + hv.getLastDomain());
            applied = hv.getLastDomain().get(0);
            hv.curr_tuple = applied;
        }
        return applied;
    }






//    private void fixDomainsAfterReturn(HetmansVariable2[][] board, int row, int col) {
//        for (int m = 0 ; m<N; m++) {
//            for (int n = 0; n < N; n++) {
//                if ((m == row || n == col) && !(m== row && n==col)) {
//                    board[m][n].domains.remove(board[m][n].domains.size() - 1);
//                    System.out.println("usunieto dziedzine " + m + " " + n  + " liczba: " + board[m][n].domains.size() + " dziedzina ost: " + board[m][n].getLastDomain().toString());
//                }
//            }
//        }
//
//    }

    public void printMatrix(boolean[][] board){
        for (int i = 0; i<N; i++){
            for(int j =0 ; j< N;j++){
                System.out.print("[" +board[i][j] + "]");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean[][] getBoard(){
        boolean[][] board = new boolean[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                board[i][j] = false;
            }
        }
        return board;
    }



//    public void updateValuesInDomains(SquareVariable[][]board, Tuple<Integer> value, int row , int col) {
//
//        for (int m = 0; m < N; m++) {
//            for (int n = 0; n < N; n++) {
//                if (m == row || n == col) {
//                    ArrayList<Integer> newdomain = new ArrayList<>(board[m][n].getLastDomain());
//                    newdomain.remove(value);
//                    board[m][n].addNewDomain(newdomain);
//                    System.out.println("w dziedzinie " + m + " " + n + " usunieto " + value + " liczba: " + board[m][n].domains.size() + " dziedzina: " + board[m][n].getLastDomain().toString());
//                }
//            }
//        }
//    }


    public static void main(String[] args) {
        HetmansProblem2 hetmansProblem2 = new HetmansProblem2(6);
      //  boolean[][] t = hetmansProblem2.getBoard();
       // t[0][0]=true;
       // hetmansProblem2.printMatrix(t);
       // System.out.println(hetmansProblem2.canInsertValue(t,0,1));
        hetmansProblem2.backtrack(hetmansProblem2.getBoard());
       hetmansProblem2.forwardcheck(hetmansProblem2.getBoard());


    }
}
