
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
	private Percolation perc;
	private int T;
	private int N;
	private double m;
	private double s;
	private double cLo;
	private double cHi;
	private int opSite;
	
   public PercolationStats(int n, int trials)   // perform trials independent experiments on an n-by-n grid
   {
	  //call percolation(n) loop trials times
	   //for each call get numOS/n = perc thresh  store in array(time taken) or do the sum(maybe better)
	   T = trials;
	   N = n;
	   double[] p;
	   if (N <= 0 || T <=0 ) 
	   {throw new IllegalArgumentException() ; }
	   else {
	   p = new double[T] ;
	   double totalSites = N*N;
	   for(int i = 0 ; i < T ; i++)
	   {
		   perc = new Percolation(N) ;
		   //generate random row, col pairs and open until percolation
		   while(!perc.percolates()) 
		   {
		   int row = StdRandom.uniform(N)+1 ;
		   int col = StdRandom.uniform(N)+1   ;
		   perc.open(row,col) ;
		   }
		   opSite = perc.numberOfOpenSites() ;
		   p[i] = (double)opSite/totalSites ;
	   }
	   m = StdStats.mean(p) ;
	   s = StdStats.stddev(p);
	   cLo = m - 1.96*s/Math.sqrt(T);
	   cHi = m + 1.96*s/Math.sqrt(T);
	   }   
   }
   public double mean()                         // sample mean of percolation threshold
   {
	   //if percolates
	   //sum/trials
	   //store mean in variable and reuse for future calculation
	  
	   return m;
   }
   public double stddev()                       // sample standard deviation of percolation threshold
   {
	   return s;
   }
   public double confidenceLo()                // low  endpoint of 95% confidence interval
   {   //mean-SD
	   return cLo ;
   }
   public double confidenceHi()               // high endpoint of 95% confidence interval
   {  //mean+SD
	   return cHi;
   }
   public static void main(String[] args)
   {
	 
   }

}
