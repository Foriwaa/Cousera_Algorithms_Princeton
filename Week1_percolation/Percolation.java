import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {  
	private  WeightedQuickUnionUF uf; 
	private  WeightedQuickUnionUF nuf; 
	private int size;
	private boolean[][] grid; 
	private int numOS ;
	private  int source; 
	private  int sink ;
	
/* throw
 * {throw new IndexOutOfBoundsException("Index out of bounds"); }
 * throw new IllegalArgumentException();
 */
	
	
	//create n-by-n grid, with all sites blocked, use boolean array
   public Percolation(int n)  { 
	   //this.n = n; 
	   size = n ;
	   int sizeQUF = (n*n)+2 ; // N+2
	   uf = new WeightedQuickUnionUF(sizeQUF) ;  //from 0=source 1...N=grid N+1=sink
	   nuf = new WeightedQuickUnionUF(sizeQUF) ; // with only source
	   source = 0 ; 
	   sink = sizeQUF-1 ;
	   numOS = 0 ;
 
	   if (size <= 0)
	   { throw new IllegalArgumentException(); }
	   else { grid = new boolean[size][size]; }
   }
  
   
   //translate 2d boxes to 1d grid for UF
   private int trans(int i, int j){
	 int val = ((i-1)*size) + j ; //for corner(1,1)
	 //int val = (i*size) + j+1; //for corner(0,0)
	 return val ;
   }
   
	/*open site and keep track of 
	 *open site connected to virtual source   
	 *values not in range IllegalArgumentException    
	 */
 //open site (row, col) if it is not open already
   public    void open(int row, int col){
	   if (row <= 0 || row > size || col <=0 || col > size) 
	   {throw new IllegalArgumentException() ; }
	   else {
		   
	   /*should open be allowed to be called more than once for the same spot
	    if (grid[row][col] == true) //already open
	    {return;}
	    else  //not open 
       {
       */
		if (isOpen(row,col)) { return;  }
	   grid[row-1][col-1] = true ;  //open site
	   int p = trans(row,col) ;
	   numOS++;//counter for number of open sites
	   
	   // connect to source if first row 
	   if(row == 1)
	   {uf.union(p,source) ; 
	    nuf.union(p,source);}
	   
	   //connecting row n-1 to sink back washing prob
	   if(row==size)
	 { uf.union(sink, p);}
	   
	   //connect surrounding open sites to the newly open site 
	   if(col != 1 && isOpen(row,col-1)==true ) // left, check first column has no left
	   {
		   int q = trans(row,col-1) ;
		   uf.union(p,q) ;
		   nuf.union(p,q) ;
	   }
	   if(row !=1 && isOpen(row-1,col)==true) // top, first row has no top
	   {
		   int q=trans(row-1,col) ;
		   uf.union(p,q) ;
		   nuf.union(p,q) ;
	   }
	   if(col != size && isOpen(row,col+1)==true) // right, last col has no right
	   {
		   int q = trans(row,col+1) ;
		   uf.union(p,q) ;
		   nuf.union(p,q) ;
	   } 
	   if(row !=size && isOpen(row+1,col)==true) //bottom, last row has no bottom
	   {
		   int q = trans(row+1,col) ;
		   uf.union(p,q) ;
		   nuf.union(p,q) ;
	   }
	
   }
	   }
   
   /*
    *is site (row, col) open?
    *return the value in the 2d boolean array
    */
   public boolean isOpen (int row, int col)  {
	   if (row <= 0 || row > size || col <= 0 || col > size) 
	   {throw new IllegalArgumentException() ; } 
	   else {
		return grid[row-1][col-1] ;
	}	
   }
   /*
    *is site (row, col) full? connected to an open site in the top row
    *connectedness to the top
    */
   public boolean isFull(int row, int col)    {
	   if (row <= 0 || row > size || col <=0 || col > size) 
	   {throw new IllegalArgumentException() ; }
	   
	  if(isOpen(row, col)!= true) //not open, not connected to top
	   { return false;}
	 
	   //else it is open and may be connected to the top , use source
	   else {
	  int p = trans(row,col) ;
	  return nuf.connected(p,source) ;   //use nuf instead that has only source
	   }
	
         }
   
   public     int numberOfOpenSites()      // number of open sites
   {
	  return numOS ;
   }
   
   public boolean percolates()             // does the system percolate?
   {	   
  return uf.connected(source,sink) ;
   }

   public static void main(String[] args)  // test client (optional)
   {
 
   }

}
