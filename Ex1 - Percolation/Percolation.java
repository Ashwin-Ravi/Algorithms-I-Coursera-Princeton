import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation 
{
    private final int gridLength;
    private boolean[] openCells;
    private final int virtualTopCell;
    private final int virtualBottomCell;
    private final WeightedQuickUnionUF fullness;
    private final WeightedQuickUnionUF percolation;
    private int openSiteCount;
    
   public Percolation(int n)                // create n-by-n grid, with all sites blocked
   {
       if (n < 1) {
            throw new IllegalArgumentException("Invalid n < 1");
        }
       openCells = new boolean[n * n + 2];
       virtualTopCell = n * n;
       virtualBottomCell = n * n + 1;
       fullness = new WeightedQuickUnionUF(n * n + 1);
       percolation = new WeightedQuickUnionUF(n * n + 2);
       openSiteCount = 0;
       openCells[virtualTopCell] = true;
       openCells[virtualBottomCell] = true;
       gridLength = n;
       
   }
   
   public void open(int row, int col)    // open site (row, col) if it is not open already
   {
       checkIndexBounds(row, col);
       if (!isOpen(row, col))
       {
           openSiteCount++;
           openCells[(row - 1) * gridLength + (col - 1)] = true;
       }
       if (row == 1) 
       {
                fullness.union(col - 1, virtualTopCell);
                percolation.union(col - 1, virtualTopCell);     
       }
       if (row == gridLength) 
           percolation.union((row - 1) * gridLength + (col - 1), virtualBottomCell);
       
       if (row > 1   && isOpen(row - 1, col)) 
       {
                fullness.union((row-1)*gridLength + col-1, (row-2)*gridLength + col-1);
                percolation.union((row-1)*gridLength + col-1, (row-2)*gridLength + col-1);
       }
       if (row < gridLength && isOpen(row+1, col)) 
       {
                fullness.union((row-1)*gridLength+col-1, row*gridLength+col-1);
                percolation.union((row-1)*gridLength+col-1, row*gridLength+col-1);
       }
       if (col > 1   && isOpen(row, col-1)) 
       {
                fullness.union((row-1)*gridLength+col-1, (row-1)*gridLength+col-2);
                percolation.union((row-1)*gridLength+col-1, (row-1)*gridLength+col-2);
       }
       if (col < gridLength && isOpen(row, col+1)) 
       {
                fullness.union((row-1)*gridLength+col-1, (row-1)*gridLength+col);
                percolation.union((row-1)*gridLength+col-1, (row-1)*gridLength+col);
       }
    } 
   
   
   public boolean isOpen(int row, int col)  // is site (row, col) open?
   {
       checkIndexBounds(row, col);
       return openCells[(row - 1) * gridLength + (col - 1)];
   }
   
   public boolean isFull(int row, int col)  // is site (row, col) full?
   {
       checkIndexBounds(row, col);
       return fullness.connected((row - 1) * gridLength + (col - 1), virtualTopCell);
   }
   
   public int numberOfOpenSites()       // number of open sites
   {
       return openSiteCount;
   }
   
   public boolean percolates()              // does the system percolate?
   {
       return percolation.connected(virtualTopCell, virtualBottomCell);
   }
   
   private void checkIndexBounds(int row, int col)
    {
        if (row < 1 || row > gridLength || col < 1 || col > gridLength)
        {
            throw new IllegalArgumentException("Attempting to access a cell outside of the grid");
        }
    }
}