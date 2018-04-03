import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class LatinSquare {

    int N;

    public LatinSquare(int problem_size){
        N = problem_size;
    }

    public SquareVariable[][] backtrack(SquareVariable[][] board){

        int set = 1;
        int toset = N*N;
        int return_count=0;
        int loop_count = 0;


        int row=0;
        int col = 0;

        while (set <= toset){
            loop_count+=1;
            //System.out.println("........................................");
            //System.out.println("set: " +set);
           // System.out.println("rc: " + row + " " + col);

            if(row == -1 || col == -1){ // &&
                System.out.println("Nie ma rozwiazania");
                return null;
            }

            int applied = applyBTWihoutAll(board, row, col);

         //   System.out.println("aplied:" + applied);

            if(applied != -1){
                System.out.println("ok");
               // System.out.println( "cv:" + board[row][col].curr_val + " ts: " +board[row][col].tried.size() );
                set +=1;
                 Tuple<Integer> nextfield = getNextField(row,col);
                 row = nextfield.a;
                 col = nextfield.b;

                 printMatrix(board);

            }//if

            else{
                System.out.println("nawrot");
                return_count +=1;
                set -=1 ;
                board[row][col].tried = new ArrayList<>();
                board[row][col].curr_val = 0;
               // System.out.println( "cv:" + board[row][col].curr_val + " ts: " +board[row][col].tried.size() );

                Tuple<Integer> prevfield = getPrevField(row,col);
                row = prevfield.a;
                col = prevfield.b;



            }//else
        }//while

        System.out.println("BT: liczba nawrotow: " + return_count + " liczba wywolan petli: " + loop_count);
        return board;

    }



    public void backtrackAll(SquareVariable[][] board) {

        int all =0;

        int set = 1;
        int toset = N * N;

        int row = 0;
        int col = 0;

        boolean allb = true;

        while(allb) { /// ze wszystkie

            while (set <= toset) {
                if (row == -1 || col == -1) { // &&
                    System.out.println("LS BT Wszystkich rozwiazan: " + all);
                    return;
                }

                int applied = applyBTWihoutAll(board, row, col);


                if (applied != -1) {

                    set += 1;
                    Tuple<Integer> nextfield = getNextField(row, col);
                    row = nextfield.a;
                    col = nextfield.b;


                }//if

                else {
                    set -= 1;
                    board[row][col].tried = new ArrayList<>();
                    board[row][col].curr_val = 0;

                    Tuple<Integer> prevfield = getPrevField(row, col);
                    row = prevfield.a;
                    col = prevfield.b;


                }//else
            }//while
            all += 1;
            set -= 1;
            board[N - 1][N - 1].tried = new ArrayList<>();
            board[N - 1][N - 1].curr_val = 0;
            Tuple<Integer> prevfield = getPrevField(N - 1, N - 1);
            row = prevfield.a;
            col = prevfield.b;
        }

    }


    private Tuple<Integer> getPrevField(int row, int col) {

        int prevrow;//= row;
        int prevcol;// = col;

        if(col-1 < 0){
            prevrow =row -1;
            prevcol = N-1;
        }
        else {
            prevcol = col - 1;
            prevrow = row;
        }

        if(prevrow < 0 ){
            prevcol = prevrow = -1;
        }

        return new Tuple<>(prevrow,prevcol);
    }




    public int applyBTWihoutAll(SquareVariable[][] board, int i, int j){
        int applied = -1;  // bool?
        SquareVariable sv = board[i][j];
        //for(int value :sv.domains.get(sv.domains.size()-1)){
        for(int nr = 0; nr<sv.getLastDomain().size();nr++){
            int value = sv.getLastDomain().get(nr) ;
            if(applied == -1 && !sv.tried.contains(value)){
                if(canInsertValue(board, value,i,j)){
                    sv.curr_val = value;
                    sv.tried.add(value);
                    applied = value;
                    return applied;
                }
            }
        }

        return applied;
    }

    public int applyBTWihoutAllH1(SquareVariable[][] board, int i, int j){  // WYBOR WARTOSCI H1    LOSOWO   ZMNIAJSZA NAWROTY
        int applied = -1;  // bool?
        SquareVariable sv = board[i][j];
        ArrayList<Integer> curr_dom = new ArrayList<>(sv.getLastDomain());
        Collections.shuffle(curr_dom);
       // System.out.println("currdom: " + curr_dom.toString());
        for(int nr = 0; nr<curr_dom.size();nr++){
            int value = curr_dom.get(nr) ;
            if(applied == -1 && !sv.tried.contains(value)){
                if(canInsertValue(board, value,i,j)){
                    sv.curr_val = value;
                    sv.tried.add(value);
                    applied = value;
                    return applied;
                }
            }
        }

        return applied;
    }





    public boolean canInsertValue(SquareVariable[][] board, int value, int i , int j){
        boolean canInsert = true;

        for(int k =0; k<N; k++){
            if(board[i][k].curr_val == value){
                canInsert = false;
            }
        }

        //in j column
        for(int k =0; k<N; k++){
            if(board[k][j].curr_val == value){
                canInsert = false;
            }
        }

        return canInsert;
    }


    public Tuple<Integer> getNextField(int row, int col){
        int nextrow;//= row;
        int nextcol;// = col;

        if(col+ 1>= N){
            nextrow =row + 1;
            nextcol = 0;
        }
        else {
            nextcol = col + 1;
            nextrow = row;
        }

        if(nextrow >= N){
            nextcol = nextrow = -1;
        }

        return new Tuple<>(nextrow,nextcol);
    }







    ////////////////////////////////////////////// FWD



//    public void updateValues(SquareVariable[][]board, int value, int row , int col) {
//
//        for (int m = 0; m < N; m++) {
//            for (int n = 0; n < N; n++) {
//                if (m == row || n == col) {
//                    ArrayList<Integer> newdomain = new ArrayList<>(board[m][n].getLastDomain());
//                    newdomain.remove((Integer) value);
//                    board[m][n].addNewDomain(newdomain);
//                    System.out.println("w dziedzinie " + m + " " + n + " usunieto " + value + " liczba: " + board[m][n].domains.size() + " dziedzina: " + board[m][n].getLastDomain().toString());
//                }
//            }
//        }
//
//    }
//    private void removeAffectedDomains(SquareVariable[][] board, int row, int col) {
//        for (int m = 0 ; m<N; m++) {
//            for (int n = 0; n < N; n++) {
//                if ((m == row || n == col) && !(m== row && n==col)) {
//                    //   ArrayList<Integer> newdomain = new ArrayList<>(board[m][n].getLastDomain());
//                    // newdomain.remove((Integer)value);
//                    board[m][n].domains.remove(board[m][n].domains.size() - 1);
//                    System.out.println("usunieto dziedzine " + m + " " + n  + " liczba: " + board[m][n].domains.size() + " dziedzina ost: " + board[m][n].getLastDomain().toString());
//                }
//            }
//        }
//
//    }

//    public int applyFC3(SquareVariable[][] board, int i, int j){ // nie wystarczy podac tylko sv?
//        int applied = -1;  // bool?
//        SquareVariable sv= board[i][j];
//        if(!sv.getLastDomain().isEmpty()){
//            System.out.println("last domain get 0 " + sv.getLastDomain().get(0));
//            applied = sv.getLastDomain().get(0);
//            sv.curr_val = applied;
//        }
//        return applied;
//    }
//
//    public SquareVariable[][] fwdcheck2(SquareVariable[][] board){
//
//
//        int set = 1;
//        int toset = N*N;
//
//        int return_count=0;
//        int loops_count = 0;
//
//        int row=0;
//        int col = 0;
//
//        while (set <= toset){
//            loops_count+=1;
//            System.out.println("........................................");
//            System.out.println("set: " +set);
//            System.out.println("rc: " + row + " " + col);
//
//            if(row == -1 || col == -1){ // &&
//                System.out.println("Nie ma rozwiazania");
//                return null;
//            }
//
//            Integer a = board[row][col].domains.size();
//            System.out.println( "cv:" + board[row][col].curr_val + " ds: " + a  + "wd: "  +board[row][col].domains.get(a-1).size() );
//
//
//            // int applied = applyFC(board, row, col);
//            int applied = applyFC(board, row, col);
//
//            System.out.println("aplied:" + applied);
//
//            if(applied != -1){
//                System.out.println("ok");
//                set +=1;
//                updateValuesInDomains(board,applied,row,col);
//
//                Tuple<Integer> nextfield = getNextField(row,col);
//                row = nextfield.a;
//                col = nextfield.b;
//
//                printMatrix(board);
//            }//if
//
//            else{
//                System.out.println("nawrot");
//                return_count +=1;
//                set -=1 ;
//                board[row][col].curr_val = 0;
//
//                Tuple<Integer> prevfield = getPrevField(row,col);
//                row = prevfield.a;
//                col = prevfield.b;
//                fixDomainsAfterReturn(board,row,col);
//
//
//            }//else
//
//
//        }//while
//
//        System.out.println("FC: liczba nawrotow: " + return_count + " liczba wywolan petli: " + loops_count);
//        return board;
//
//    }
//
//    public SquareVariable[][] fwdcheck(SquareVariable[][] board){
//
//
//        int set = 1;
//        int toset = N*N;
//
//        int back=0;
//
//        int row=0;
//        int col = 0;
//
//        while (set <= toset){
//            System.out.println("........................................");
//            System.out.println("set: " +set);
//            System.out.println("rc: " + row + " " + col);
//
//            if(row == -1 || col == -1){ // &&
//                System.out.println("Nie ma rozwiazania");
//                return null;
//            }
//
//            Integer a = board[row][col].domains.size();
//            System.out.println( "cv:" + board[row][col].curr_val + " ds: " + a  + "wd: "  +board[row][col].domains.get(a-1).size() );
//
//
//            int applied = applyFC(board, row, col); /// moze row i col w sv?
//
//            System.out.println("aplied:" + applied);
//
//            if(applied != -1){
//                System.out.println("ok");
//                set +=1;
//                updateValuesInDomains(board,applied,row,col);
//
//                Tuple<Integer> nextfield = getNextField(row,col);
//                row = nextfield.a;
//                col = nextfield.b;
//
//                printMatrix(board);
//            }//if
//
//            else{
//                System.out.println("nawrot");
//                back +=1;
//                set -=1 ;
//                board[row][col].curr_val = 0;
//
//                Tuple<Integer> prevfield = getPrevField(row,col);
//                row = prevfield.a;
//                col = prevfield.b;
//                fixDomainsAfterReturn(board,row,col);
//
//
//            }//else
//
//
//        }//while
//
//        System.out.println("liczba nawrotow: " + back);
//        return board;
//
//    }



//    public void fwdcheckAll(SquareVariable[][] board) {
//        int all = 0;
//
//
//        int set = 1;
//        int toset = N * N;
//
//        int return_count = 0;
//        int loops_count = 0;
//
//        int row = 0;
//        int col = 0;
//        boolean count = true;
//
//        while (count){
//
//        while (set <= toset) {
//            loops_count += 1;
//            System.out.println("........................................");
//            System.out.println("set: " + set);
//            System.out.println("rc: " + row + " " + col);
//
//            if (row == -1 || col == -1) { // &&
//                System.out.println("Nie ma rozwiazania");
//                return;
//            }
//
//            Integer a = board[row][col].domains.size();
//            System.out.println("cv:" + board[row][col].curr_val + " ds: " + a + "wd: " + board[row][col].domains.get(a - 1).size());
//
//
//            int applied = applyFC(board, row, col);
//            //int applied = applyFCH1(board, row, col);
//
//            System.out.println("aplied:" + applied);
//
//            if (applied != -1) {
//                System.out.println("ok");
//                set += 1;
//                updateValuesInDomains(board, applied, row, col);
//
//                Tuple<Integer> nextfield = getNextField(row, col);
//                row = nextfield.a;
//                col = nextfield.b;
//
//                printMatrix(board);
//            }//if
//
//            else {
//                System.out.println("nawrot");
//                return_count += 1;
//                set -= 1;
//                board[row][col].curr_val = 0;
//
//                Tuple<Integer> prevfield = getPrevField(row, col);
//                row = prevfield.a;
//                col = prevfield.b;
//                fixDomainsAfterReturn(board, row, col);
//
//
//            }//else
//
//
//        }//while
//
//        System.out.println("FC: liczba nawrotow: " + return_count + " liczba wywolan petli: " + loops_count);
//        all+=1;
//
//            System.out.println("ROZWIAZAN: " + all);
//            set -= 2;
//            board[N-1][N-1].curr_val = 0;
//            fixDomainsAfterReturn(board, N-1, N-1);
//            Tuple<Integer> prevfield = getPrevField(N-1, N-1);
//            row = prevfield.a;
//            col = prevfield.b;
//            fixDomainsAfterReturn(board, row, col);
//
//
//
//        }
//
//    }


    public SquareVariable[][] forwardCheckWithH1(SquareVariable[][] board){ // najmniej w dziedzinie

        ArrayList<Tuple<Integer>> visited  = new ArrayList<>();

        int set = 1;
        int toset = N*N;

        int return_count=0;
        int loops_count = 0;

        Tuple<Integer> coords = getNextFieldH1(board,visited);

        int row = coords.a;
        int col = coords.b;

        while (set <= toset){
            loops_count+=1;
            System.out.println("........................................");
            System.out.println("set: " +set);
            System.out.println("rc: " + row + " " + col);

            if(row == -1 || col == -1){ // &&
                System.out.println("Nie ma rozwiazania");
                return null;
            }

            Integer a = board[row][col].domains.size();
            System.out.println( "cv:" + board[row][col].curr_val + " ds: " + a  + "wd: "  +board[row][col].domains.get(a-1).size() );


            int applied = applyFC(board, row, col); /// moze row i col w sv?

            System.out.println("aplied:" + applied);

            if(applied != -1){
                System.out.println("ok");
                set +=1;
                updateValuesInDomains(board,applied,row,col);

                visited.add(new Tuple<>(row,col));

                Tuple<Integer> nextfield = getNextFieldH1(board, visited);
                row = nextfield.a;
                col = nextfield.b;



                printMatrix(board);
            }//if

            else{
                System.out.println("nawrot");
                return_count +=1;
                set -=1 ;
                board[row][col].curr_val = 0;

                Tuple<Integer> prevfield = visited.get(visited.size()-1);
                visited.remove(visited.size()-1);
                row = prevfield.a;
                col = prevfield.b;
                fixDomainsAfterReturn(board,row,col);


            }//else


        }//while

        System.out.println("FC: liczba nawrotow: " + return_count + " liczba wywolan petli: " + loops_count);
        return board;

    }


    public SquareVariable[][] forwardCheck(SquareVariable[][] board){ // najmniej w dziedzinie

        ArrayList<Tuple<Integer>> visited  = new ArrayList<>();

        int set = 1;
        int toset = N*N;

        int return_count=0;
        int loops_count = 0;

        Tuple<Integer> coords =getNextFieldH1(board, visited);

        int row = coords.a;
        int col = coords.b;

        while (set <= toset){
            loops_count+=1;
         //   System.out.println("........................................");
        //    System.out.println("set: " +set);
        //    System.out.println("rc: " + row + " " + col);

            if(row == -1 || col == -1){ // &&
                System.out.println("Nie ma rozwiazania");
                return null;
            }

            Integer a = board[row][col].domains.size();
         //   System.out.println( "cv:" + board[row][col].curr_val + " ds: " + a  + "wd: "  +board[row][col].domains.get(a-1).size() );


            int applied = applyFC(board, row, col); /// moze row i col w sv?

            //System.out.println("aplied:" + applied);

            if(applied != -1){
                System.out.println("ok");
                set +=1;
                updateValuesInDomains(board,applied,row,col);

                visited.add(new Tuple<>(row,col));

             //   for(Tuple<Integer> t :visited){
              //      System.out.print(t.a + " " + t.b + " | ");
             //   }
                System.out.println();

                Tuple<Integer> nextfield = getNextFieldH1(board, visited);
                row = nextfield.a;
                col = nextfield.b;



                printMatrix(board);
            }//if

            else{
                System.out.println("nawrot");
                return_count +=1;
                set -=1 ;
                board[row][col].curr_val = 0;

                Tuple<Integer> prevfield = visited.get(visited.size()-1);
                visited.remove(visited.size()-1);
                row = prevfield.a;
                col = prevfield.b;
                fixDomainsAfterReturn(board,row,col);


            }//else


        }//while

        System.out.println("FC: liczba nawrotow: " + return_count + " liczba wywolan petli: " + loops_count);
        return board;

    }


    public SquareVariable[][] forwardCheckAll(SquareVariable[][] board){ // najmniej w dziedzinie

        ArrayList<Tuple<Integer>> visited  = new ArrayList<>();

        int set = 1;
        int toset = N*N;

        int return_count=0;
        int loops_count = 0;

        Tuple<Integer> coords =getNextFieldH1(board, visited);

        int row = coords.a;
        int col = coords.b;

        boolean count = true;
        int all =0;

        while(count) {
            while (set <= toset){
                loops_count+=1;
                //   System.out.println("........................................");
                //    System.out.println("set: " +set);
                //    System.out.println("rc: " + row + " " + col);

                if(row == -1 || col == -1){ // &&
                    System.out.println("Nie ma rozwiazania");
                    return null;
                }

                Integer a = board[row][col].domains.size();
                //   System.out.println( "cv:" + board[row][col].curr_val + " ds: " + a  + "wd: "  +board[row][col].domains.get(a-1).size() );


                int applied = applyFC(board, row, col); /// moze row i col w sv?

                //System.out.println("aplied:" + applied);

                if(applied != -1){
                    System.out.println("ok");
                    set +=1;
                    updateValuesInDomains(board,applied,row,col);

                    visited.add(new Tuple<>(row,col));

                    //   for(Tuple<Integer> t :visited){
                    //      System.out.print(t.a + " " + t.b + " | ");
                    //   }
                    System.out.println();

                    Tuple<Integer> nextfield = getNextFieldH1(board, visited);
                    row = nextfield.a;
                    col = nextfield.b;



                    printMatrix(board);
                }//if

                else{
                    System.out.println("nawrot");
                    return_count +=1;
                    set -=1 ;
                    board[row][col].curr_val = 0;

                    Tuple<Integer> prevfield = visited.get(visited.size()-1);
                    visited.remove(visited.size()-1);
                    row = prevfield.a;
                    col = prevfield.b;
                    fixDomainsAfterReturn(board,row,col);


                }//else


            }//while

            System.out.println("FC: liczba nawrotow: " + return_count + " liczba wywolan petli: " + loops_count);
            all+=1;
            System.out.println("all:" + all);
            set -= 1;
            Tuple<Integer> tmp = visited.get(visited.size()-1);
         //   board[tmp.a][tmp.b].curr_val = 0;

            Tuple<Integer> prevfield = visited.get(visited.size() - 1);
            visited.remove(visited.size() - 1);
            row = prevfield.a;
            col = prevfield.b;
            fixDomainsAfterReturn(board, row, col);
        }return board;

    }

    //najbardziej ograniczona zmienna
    private Tuple<Integer> getNextFieldH1(SquareVariable[][] board, ArrayList<Tuple<Integer>> visited) {
        Tuple<Integer> next = new Tuple<>(0,0);
        int minsize = board[0][0].getLastDomain().size();
     //   System.out.println("vis:" + visited);
        //System.out.println("t: " + next.a + " " + next.b+ " ms: " + minsize);


        for(int i =0 ; i<N;i++) {
            for (int j = 0; j < N; j++) {
                //System.out.println(i + " " + j + " s " + board[i][j].getLastDomain().size());
                if (board[i][j].getLastDomain().size() <= minsize && !containsTuple(i, j, visited)) {
                    minsize = board[i][j].getLastDomain().size();
                    next = new Tuple<>(i, j);
                }
            }
        }
            if(containsTuple(next.a, next.b,visited)){
                next = new Tuple<>(-1,-1);
            }
          //  System.out.println("t: " + next.a + " " + next.b+ " ms: " + minsize);
            return next;
        }





    private boolean containsTuple(int i, int j, ArrayList<Tuple<Integer>> visited) {
        boolean contains = false;
        for(Tuple<Integer>t : visited){
            if(t.a == i && t.b == j){
                contains = true;
            }
        }

        return contains;
    }


    private void fixDomainsAfterReturn(SquareVariable[][] board, int row, int col) {
        for (int m = 0 ; m<N; m++) {
            for (int n = 0; n < N; n++) {
                if ((m == row || n == col) && !(m== row && n==col)) {
                    board[m][n].domains.remove(board[m][n].domains.size() - 1);
                  //  System.out.println("usunieto dziedzine " + m + " " + n  + " liczba: " + board[m][n].domains.size() + " dziedzina ost: " + board[m][n].getLastDomain().toString());
                }
            }
        }

    }




    public void updateValuesInDomains(SquareVariable[][]board, int value, int row , int col) {

        for (int m = 0; m < N; m++) {
            for (int n = 0; n < N; n++) {
                if (m == row || n == col ) {
                    ArrayList<Integer> newdomain = new ArrayList<>(board[m][n].getLastDomain());
                    newdomain.remove((Integer) value);
                    board[m][n].addNewDomain(newdomain);
                 //   System.out.println("w dziedzinie " + m + " " + n + " usunieto " + value + " liczba: " + board[m][n].domains.size() + " dziedzina: " + board[m][n].getLastDomain().toString());
                }
            }
        }
    }



    public int applyFC(SquareVariable[][] board, int i, int j){ // nie wystarczy podac tylko sv?
        int applied = -1;  // bool?
        SquareVariable sv= board[i][j];
        if(!sv.getLastDomain().isEmpty()){
         //   System.out.println("last domain get 0 " + sv.getLastDomain().get(0));
            applied = sv.getLastDomain().get(0);
            sv.curr_val = applied;
        }
        return applied;
    }

// h1 wartosc losowo
    public int applyFCH1(SquareVariable[][] board, int i, int j){
        int applied = -1;  // bool?
        SquareVariable sv= board[i][j];
        if(!sv.getLastDomain().isEmpty()){
           // System.out.println("last domain get 0 " + sv.getLastDomain().get(0));
            applied = sv.getLastDomain().get(new Random().nextInt(sv.getLastDomain().size()));
            sv.curr_val = applied;
        }
        return applied;
    }


    public SquareVariable[][] getEmptyBoard() {
        SquareVariable[][] board = new SquareVariable[N][N];
        for(int i =0; i<N;i++){
            for(int j=0;j<N;j++){
                board[i][j]= new SquareVariable(N);
            }
        }
        return board;
    }

    public void printMatrix(SquareVariable[][] board){
        for (int i = 0; i<N; i++){
            for(int j =0 ; j< N;j++){
                System.out.print("[" + board[i][j].curr_val + "]");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static void main(String[] args) {
        LatinSquare ls = new LatinSquare(5);

        // ls.backtrack(ls.getEmptyBoard());

        //ls.backtrackAll(ls.getEmptyBoard());

    ls.forwardCheck(ls.getEmptyBoard());
    //  ls.fwdcheckAll(ls.getEmptyBoard());

    }
}