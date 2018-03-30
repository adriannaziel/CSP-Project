import java.util.ArrayList;

public class LatinSquare {

    int N;

    public LatinSquare(int problem_size){
        N = problem_size;
    }

    public SquareVariable[][] backtrack(SquareVariable[][] board){ 



        int set = 1;
        int toset = N*N;
        int back=0;

        int row=0;
        int col = 0;

        while (set <= toset){
            System.out.println("........................................");
            System.out.println("set: " +set);
            System.out.println("rc: " + row + " " + col);

            if(row == -1 || col == -1){ // &&
                System.out.println("Nie ma rozwiazania");
                return null;
            }

            int applied = applyBTWihoutAll(board, row, col); /// moze row i col w sv? ??

            System.out.println("aplied:" + applied);

            if(applied != -1){
                System.out.println("ok");
                System.out.println( "cv:" + board[row][col].curr_val + " ts: " +board[row][col].tried.size() );
                set +=1;
                 Tuple<Integer> nextfield = getNextField(row,col);
                 row = nextfield.a;
                 col = nextfield.b;


                //System.out.println(row + " " + col);
                printMatrix(board);
            }//if

            else{
                System.out.println("nawrot");
                back +=1;
                set -=1 ;
                board[row][col].tried = new ArrayList<>();
                board[row][col].curr_val = 0;
                System.out.println( "cv:" + board[row][col].curr_val + " ts: " +board[row][col].tried.size() );

                Tuple<Integer> prevfield = getPrevField(row,col);
                row = prevfield.a;
                col = prevfield.b;



            }//else


        }//while

        System.out.println("liczba nawrotow: " + back);
        return board;

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
        for(int value :sv.domains.get(sv.domains.size()-1)){
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




    public SquareVariable[][] fwdcheck(SquareVariable[][] board){


        int set = 1;
        int toset = N*N;

        int back=0;

        int row=0;
        int col = 0;

        while (set <= toset){
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
                updateValues(board,applied,row,col);

                Tuple<Integer> nextfield = getNextField(row,col);
                row = nextfield.a;
                col = nextfield.b;

                printMatrix(board);
            }//if

            else{
                System.out.println("nawrot");
                back +=1;
                set -=1 ;
                board[row][col].curr_val = 0;

                Tuple<Integer> prevfield = getPrevField(row,col);
                row = prevfield.a;
                col = prevfield.b;
                removeAffectedDomains(board,row,col);


            }//else


        }//while

        System.out.println("liczba nawrotow: " + back);
        return board;

    }





    private void removeAffectedDomains(SquareVariable[][] board, int row, int col) {
        for (int m = 0 ; m<N; m++) {
            for (int n = 0; n < N; n++) {
                if ((m == row || n == col) && !(m== row && n==col)) {
                    //   ArrayList<Integer> newdomain = new ArrayList<>(board[m][n].getLastDomain());
                    // newdomain.remove((Integer)value);
                    board[m][n].domains.remove(board[m][n].domains.size() - 1);
                    System.out.println("usunieto dziedzine " + m + " " + n  + " liczba: " + board[m][n].domains.size() + " dziedzina ost: " + board[m][n].getLastDomain().toString());
                }
            }
        }

    }




    public void updateValues(SquareVariable[][]board, int value, int row , int col){

        for (int m = 0 ; m<N; m++){
            for(int n=0; n<N; n++){
                if(m==row || n == col){
                    ArrayList<Integer> newdomain = new ArrayList<>(board[m][n].getLastDomain());
                    newdomain.remove((Integer)value);
                    board[m][n].addNewDomain(newdomain);
                    System.out.println("w dziedzinie " + m + " " + n + " usunieto " + value + " liczba: " + board[m][n].domains.size() + " dziedzina: " + board[m][n].getLastDomain().toString());
                }
            }
        }



//        for(int 11k =0; k<N; k++){
//           ArrayList<Integer> newdomain = board[i][k].getLastDomain();
//           newdomain.remove((Integer)value);
//           board[i][k].addNewDomain(newdomain);
//          //  board[i][k].getLastDomain().remove((Integer)value);
//        }
//
//        //in j column
//        for(int k =0; k<N; k++){
//          //  ArrayList<ArrayList<Integer>> domains = board[k][j].domains;
//            board[k][j].getLastDomain().remove((Integer)value);
//        }
    }


//
//    public void restoreAfterReturn(SquareVariable[][]board, int row , int col) {
//
//        for (int m = 0; m < N; m++) {
//            for (int n = 0; n < N; n++) {
//                if (m == row || n == col) {
//                    board[m][n].domains.remove(board[m][n].domains.size() - 1);
//                }
//            }
//        }
//    }


    public int applyFC(SquareVariable[][] board, int i, int j){ // nie wystarczy podac tylko sv?
        int applied = -1;  // bool?
        SquareVariable sv= board[i][j];
        if(!sv.getLastDomain().isEmpty()){
            System.out.println("last domain get 0 " + sv.getLastDomain().get(0));
            applied = sv.getLastDomain().get(0);
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
        LatinSquare ls = new LatinSquare(6);
        //ls.printMatrix();

       // ls.backtrack(ls.getEmptyBoard());

      ls.fwdcheck(ls.getEmptyBoard());

    }
}
