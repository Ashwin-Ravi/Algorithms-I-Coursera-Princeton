import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final int trialCount;
    private final double[] results;
    private double meanResult;
    private double stdDivResult;
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {
       if (n <= 0 || trials <= 0) 
       {
           throw new IllegalArgumentException("Both inputs must be greater than 0");
       }
       results = new double[trials];
       trialCount = trials;
  
       for (int i = 0; i < trials; i++) 
       {
           results[i] = performexperiments(n);
       }
       stdDivResult = StdStats.stddev(results);
       meanResult = StdStats.mean(results);
   }
   
   public double mean()                          // sample mean of percolation threshold
   {
       return meanResult;
   }
   
   public double stddev()                        // sample standard deviation of percolation threshold
   {
      return stdDivResult;
   }
   
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {
       return (meanResult - (1.96 * stdDivResult / Math.sqrt(trialCount)));
   }
   
   public double confidenceHi()                  // high endpoint of 95% confidence interval
   {
       return (meanResult + (1.96 * stdDivResult / Math.sqrt(trialCount)));
   }

   private double performexperiments(int gridLength) 
   {
       Percolation perc = new Percolation(gridLength);
       int sitesOpened = 0;
  
       while (!perc.percolates()) 
       {
           while (true)
           {
               int i = StdRandom.uniform(1, gridLength + 1);
               int j = StdRandom.uniform(1, gridLength + 1);
               if (!perc.isOpen(i, j)) 
               {
                   perc.open(i, j);
                   sitesOpened++;
                   break;
               }
           }
       }
       double result = (double) sitesOpened / (double) (gridLength * gridLength);
       return result;
   }
   
   public static void main(String[] args)        // test client (described below)
   {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
   }
}