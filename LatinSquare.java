import java.util.ArrayList;

public class LatinSquare {

    int N;
    SquareVariable[][] board ;

    public LatinSquare(int problem_size){
        N = problem_size;
    }

    public void backtrack(){ // bool? lista? params?
        fillBoard();


        int set = 1;
        int toset = N*N;
        int back=0;

        int row=0;
        int col = 0;
        //int lastsetval = -1;

        while (set <= toset){
            System.out.println("........................................");
            System.out.println("set: " +set);
            System.out.println("rc: " + row + " " + col);

            if(row == -1 || col == -1){ // &&
                System.out.println("Nie ma rozwiazania");
                return;
            }

            int applied = applyBTWihoutAll(board[row][col], row, col, board[row][col].tried); /// moze row i col w sv?

            System.out.println("aplied:" + applied);

            if(applied != -1){
                System.out.println("ok");
                System.out.println( "cv:" + board[row][col].curr_val + " ts: " +board[row][col].tried.size() );
                set +=1;
                //board[row][col].tried.add()
                //lastsetval = board[row][col].curr_val;
                 Tuple<Integer> nextfield = getNextField(row,col);
                 row = nextfield.a;
                 col = nextfield.b;


                //System.out.println(row + " " + col);
                printMatrix();
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




    public int applyBTWihoutAll(SquareVariable sv, int i, int j, ArrayList<Integer> wihout){
        int applied = -1;  // bool?
        for(int value :sv.domain.get(sv.domain.size()-1)){
            if(applied == -1 && !wihout.contains(value)){
                if(canInsertValue(value,i,j)){
                    sv.curr_val = value;
                    sv.tried.add(value);
                    applied = value;
                    return applied;
                }
            }
        }

        return applied;
    }



    public boolean canInsertValue(int value, int i , int j){
        boolean canInsert = true;

        for(int k =0; k<N; k++){
            if(board[i][k].curr_val == value){
               // return false; to mozna poprawic w profilerze
                canInsert = false;
            }
        }

        //in j column
        for(int k =0; k<N; k++){
            if(board[k][j].curr_val == value){
                //return false;
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




    public void fwdcheck(){ // bool? lista? params?

        fillBoard();

        int set = 1;
        int toset = N*N;//mozna pogorszyc mnozac w kodzie v

        int back=0;

        int row=0;
        int col = 0;
        //int lastsetval = -1;

        while (set <= toset){
            System.out.println("........................................");
            System.out.println("set: " +set);
            System.out.println("rc: " + row + " " + col);

            if(row == -1 || col == -1){ // &&
                System.out.println("Nie ma rozwiazania");
                return;
            }

            Integer a = board[row][col].domain.size();
            System.out.println( "cv:" + board[row][col].curr_val + " ds: " + a  + "wd: "  +board[row][col].domain.get(a-1).size() );


            int applied = applyFC(board[row][col], row, col); /// moze row i col w sv?

            System.out.println("aplied:" + applied);

            if(applied != -1){
                System.out.println("ok");
                set +=1;

                Tuple<Integer> nextfield = getNextField(row,col);
                row = nextfield.a;
                col = nextfield.b;

                printMatrix();
            }//if

            else{
                System.out.println("nawrot");
                back +=1;
                set -=1 ;
                board[row][col].domain.remove(board[row][col].domain.size()-1);
                board[row][col].addNewDomain();
                board[row][col].curr_val = 0;

                Tuple<Integer> prevfield = getPrevField(row,col);
                row = prevfield.a;
                col = prevfield.b;



            }//else


        }//while

        System.out.println("liczba nawrotow: " + back);

    }






    public void updateValues(int value, int i , int j){

        for(int k =0; k<N; k++){
            ArrayList<ArrayList<Integer>> domains = board[i][k].domain;
            domains.get(domains.size()-1).remove((Integer)value);
        }

        //in j column
        for(int k =0; k<N; k++){
            ArrayList<ArrayList<Integer>> domains = board[k][j].domain;
            domains.get(domains.size()-1).remove((Integer)value);
        }
    }


    public int applyFC(SquareVariable sv, int i, int j){
        int applied = -1;  // bool?
        if(!sv.domain.get(sv.domain.size()-1).isEmpty()){
            applied = sv.domain.get(sv.domain.size()-1).get(0);
            sv.curr_val = applied;
            updateValues(applied,i,j);
        }
        return applied;
    }







    public void fillBoard() {
        board = new SquareVariable[N][N];
        for(int i =0; i<N;i++){
            for(int j=0;j<N;j++){
                board[i][j]= new SquareVariable(N);
            }
        }
    }

    public void printMatrix(){
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

        ls.backtrack();

        ls.fwdcheck();

    }
}
