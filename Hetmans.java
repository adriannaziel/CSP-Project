
import java.util.ArrayList;

public class Hetmans {

    int N ;

    public Hetmans(int n){
        N=n;
    }




    public HetmanVariable[][] getEmptyBoard(){
        HetmanVariable[][] board = new HetmanVariable[N][N];
        for(int i =0; i<N;i++){
            for(int j=0;j<N; j++){
                board[i][j] = new HetmanVariable();
            }
        }
        return board;
    }



//    public void setNonAvailable(int[][]board, int i, int j){
//        //in i row
//        for(int k =0; k<N; k++){
//            if(board[i][k] == 0){
//                board[i][k] = -1;
//            }
//
//        }
//
//        //in j column
//        for(int k =0; k<N; k++){
//            if(board[k][j] == 0){
//                board[k][j] = -1;
//            }
//        }
//
//        //diagonals
//        int a;
//        int b;
//        for (a=i, b=j; a>=0 && b>=0; a--, b--)
//            if (board[a][b] == 0){
//                board[a][b] = -1;
//            }
//
//
//        for (a=i, b=j; a>=0 && b<N; a--, b++)
//            if (board[a][b] == 0){
//                board[a][b] = -1;
//            }
//        /* Check lower diagonal on left side */
//        for (a=i, b=j; b>=0 && a<N; a++, b--)
//            if (board[a][b] == 0){
//                board[a][b] = -1;
//            }
//
//        for (a=i, b=j; b<N && a<N; a++, b++)
//            if (board[a][b] == 0){
//                board[a][b] = -1;
//            }
//
//    }


    public boolean canInsertValue(HetmanVariable[][]board, int i, int j){

        for(int k =0; k<N; k++){
            if(board[i][k].curr_val){
                return false;
            }

        }

        //in j column
        for(int k =0; k<N; k++){
            if(board[k][j].curr_val){
                return false;
            }
        }

        //diagonals
        int a;
        int b;
        for (a=i, b=j; a>=0 && b>=0; a--, b--)
            if (board[a][b].curr_val ){
                return false;
            }


        for (a=i, b=j; a>=0 && b<N; a--, b++)
            if (board[a][b].curr_val){
                return false;
            }

        for (a=i, b=j; b>=0 && a<N; a++, b--)
            if (board[a][b].curr_val){
                return false;
            }

        for (a=i, b=j; b<N && a<N; a++, b++)
            if (board[a][b].curr_val){
                return false;
            }
        return true;
    }


    public ArrayList<Tuple<Integer>> backtrack(HetmanVariable[][] board){

        ArrayList<Tuple<Integer> > result = new ArrayList<>();

        int set = 1;
        int toset = N;
        int back=0;

        int row=0;
        int col = 0;

        while (set <= toset){
            System.out.println("........................................");
            System.out.println("set: " +set);
            System.out.println("rc: " + row + " " + col + " wstawionych: " + result.size());

            if((row == -1 || col == -1) && set<=1){
                System.out.println("nie znaleniono rozwiazania");
                return null;
            }

          if(row== -1 || col == -1){
              System.out.println("nawrot");
              back +=1;
                row = result.get(result.size()-1).a;
                col = result.get(result.size()-1).b;

                board[row][col].curr_val = false;
                result.remove(result.size()-1);
                Tuple<Integer> next = getNextField(row,col);
                row = next.a;
                col = next.b;
                set -=1;
          }
          else {
                boolean applied = applyBT(board,row,col);

                if(applied){
                    result.add(new Tuple<>(row,col));
                    set +=1 ;
                    printMatrix(board);
                }
                else {
                    //?
                }
                Tuple<Integer> next = getNextField(row,col);
                row = next.a;
                 col = next.b;
            }


        }//while

        System.out.println("liczba nawrotow: " + back);
        return result;

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




    public boolean applyBT(HetmanVariable[][] board, int i, int j){
        boolean applied = false;  // bool?
                if(canInsertValue(board, i,j)){
                    board[i][j].curr_val=true;
                    applied = true;
                }
        return applied;
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


    public void printMatrix(HetmanVariable[][] board){
        for (int i = 0; i<N; i++){
            for(int j =0 ; j< N;j++){
                System.out.print("[" + board[i][j].curr_val + "]");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static void main(String[] args) {
        Hetmans hetmans = new Hetmans(5);
        ArrayList<Tuple<Integer>> board = hetmans.backtrack(hetmans.getEmptyBoard());


    }

}
