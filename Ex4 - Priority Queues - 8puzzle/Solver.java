import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class Solver {
    
    private MinPQ<Node> pq;
    private MinPQ<Node> pq2;
    private boolean isSolvable;
    private int moves;
    private ArrayList<Board> solution;
    
    private class Node implements Comparable<Node> {
        private Board board;
        private Node previous;
        private int moves;

        public Node(Board board, Node previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
        }

        public int compareTo(Node that) {
            return (this.board.manhattan() + this.moves) - (that.board.manhattan() - that.moves);
        }
    }

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        pq = new MinPQ<>();
        pq2 = new MinPQ<>();

        pq.insert(new Node(initial, null, 0));
        pq2.insert(new Node(initial.twin(), null, 0));

        while (true) {
            Node temp = pq.delMin();
            if (temp.board.isGoal()) {
                isSolvable = true;
                moves = temp.moves;
                solution = new ArrayList<>();
                while (temp != null) {
                    solution.add(0, temp.board);
                    temp = temp.previous;
                }
                break;
            }
            for (Board b: temp.board.neighbors())
                if (temp.previous == null || !b.equals(temp.previous.board))
                    pq.insert(new Node(b, temp, temp.moves + 1));

            temp = pq2.delMin();
            if (temp.board.isGoal()) {
                isSolvable = false;
                moves = -1;
                solution = null;
                break;
            }
            for (Board b: temp.board.neighbors())
                if (temp.previous == null || !b.equals(temp.previous.board))
                    pq2.insert(new Node(b, temp, temp.moves + 1));
        }
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return isSolvable;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        return moves;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        return solution;
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
