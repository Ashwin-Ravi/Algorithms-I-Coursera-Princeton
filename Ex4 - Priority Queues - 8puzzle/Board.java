import java.util.ArrayList;
public class Board {
    private int N;
    private int[][] blocks;
    
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        N = blocks.length;
        this.blocks = new int[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                this.blocks[i][j] = blocks[i][j];
            }
        }
     }
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension n
    {
        return N;
    }
    public int hamming()                   // number of blocks out of place
    {
        int hamDist = 0;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                if (blocks[i][j] != (i * N + j + 1) && blocks[i][j] != 0){
                    hamDist++;
                }
            }
        }
        return hamDist;
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int manDist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0)
                    continue;
                manDist += (Math.abs(((blocks[i][j] - 1) / N) - i) + Math.abs(((blocks[i][j] - 1) % N) - j));
            }
        }    
        return manDist;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != (i * N + j + 1) && blocks[i][j] != 0){
                    return false;
                }
            }
        }
        return true;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] newboard = new int[N][N];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                newboard[i][j] = blocks[i][j];
            }
        }
        if (newboard[0][0] != 0 && newboard[0][1] != 0){
            newboard[0][0] = blocks[0][1];
            newboard[0][1] = blocks[0][0];
        }
        else
        {
            newboard[1][0] = blocks[1][1];
            newboard[1][1] = blocks[1][0];
        }
        return new Board(newboard);
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) 
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        if (N != ((Board) y).N) 
            return false;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                if (blocks[i][j] != ((Board) y).blocks[i][j])
                    return false;
            }
        }
        return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        int blankRow = 0;
        int blankCol = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (blocks[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
        ArrayList<Board> neighlist = new ArrayList<>();
        if (blankRow > 0) {
            Board neighboard = new Board(blocks);
            neighboard.exchange(blankRow, blankCol, blankRow - 1, blankCol);
            neighlist.add(neighboard);
        }
        if (blankRow < N - 1) {
            Board neighboard = new Board(blocks);
            neighboard.exchange(blankRow, blankCol, blankRow + 1, blankCol);
            neighlist.add(neighboard);
        }
        if (blankCol > 0) {
            Board neighboard = new Board(blocks);
            neighboard.exchange(blankRow, blankCol, blankRow, blankCol - 1);
            neighlist.add(neighboard);
        }
        if (blankCol < N -1) {
            Board neighboard = new Board(blocks);
            neighboard.exchange(blankRow, blankCol, blankRow, blankCol + 1);
            neighlist.add(neighboard);
        }
        return neighlist;
    }
    
   public String toString()               // string representation of this board (in the output format specified below)
   {
        StringBuilder str = new StringBuilder();
        str.append(N);
        str.append('\n');
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++){
                str.append(' ');
                str.append(blocks[i][j]);
                str.append(' ');
            }
            str.append('\n');
        }
        return str.toString();
   }

   
   private void exchange(int row1, int col1, int row2, int col2) {
        int exchval = blocks[row1][col1];
        blocks[row1][col1] = blocks[row2][col2];
        blocks[row2][col2] = exchval;
    }
  //  public static void main(String[] args) // unit tests (not graded)
}