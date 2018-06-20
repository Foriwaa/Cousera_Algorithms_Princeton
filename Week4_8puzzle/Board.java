import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Board {
    //private  int[][] blocks;
    private  final int[][] blck; //Copy
    private final int n ;
    private static final int blank = 0;
    //private final int ham;
    //private final int man;
    //private final int[][] finalState;
/* construct a board from an n-by-n array of blocks
 * (where blocks[i][j] = block in row i, column j)
 * When building a board compute the man and ham
 * ansd store in an instance var
 */
	public Board(int[][] blocks)
	{ //throw exception
        if ( blocks == null){
            throw new IllegalArgumentException();
        }
		//this.blocks = blocks;
		 n = blocks.length;
        //blck= new int[n][n];
		//make a copy of blocks
       blck = ArrayCopy2D(blocks);
       
	}
	
	
	private int[][] ArrayCopy2D(int[][] src) 
	{  
	    int[][] bcopy = new int[src.length][];
	    for (int i = 0; i < src.length; i++) {
	        //System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
	        bcopy[i] = Arrays.copyOf(src[i], src[i].length);
	    }
	    return bcopy;
	}
	
    /*return tile at row i, column j (or 0 if blank)
	private int tileAt(int i, int j) {
	  return blck[i][j];  
	}*/
	
// board dimension n
    public int dimension()
    {
    	//return blck.length or  (blck[0].length);
    	return n;
    }
    
    /*
     * No, 0 represents the blank square. 
     * Do not treat it as a block when computing either the Hamming 
     * or Manhattan priority functions.
     */
    
    
 // number of blocks out of place
    //constant time with caching
    public int hamming()                   
    {
     //ni + j + 1 = ij
    int ham = 0;
    for(int i = 0; i < n; i++) {
        for(int j = 0; j < n; j++) {
            int val = (n*i) + j + 1; 
            if (blck[i][j] != 0  && blck[i][j] != val) {
                ham++;
            }
        }
    }
    	return ham;
    }
    
 /*
  *  8  = 3i + j + 1
  *  7/3 =2=i
  *  7 = 3i+j
  *  7%3=1=j
  *  8 goal dest = blck (2,1)
  *  horizontal dist = |curr(i) - goal(i)|
  *  vertical dist = |curr(j) - goal(j)|
  */
    
 // sum of Manhattan distances between blocks and goal
   ////constant time with caching
    public int manhattan()                 
    {
       int  man = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
               if (blck[i][j] != 0) { //
               int goali = (blck[i][j] - 1) / n;
               int goalj = (blck[i][j] - 1) % n;
               int dx = Math.abs(i - goali);
               int dy = Math.abs(j - goalj);
               int total = dx + dy;
               man += total;
               }
            }
        }
        
    	return man;
    }
    
    
    
// is this board the goal board?
    //fix a board with blck[n-1][j-1] != 0 will work
    public boolean isGoal()                
    {
      /*first check for corner tile
       if (this.blck[n-1][n-1] != blank) {
           return false;
       }
       else {
      for(int i = 0; i < n; i++){
           for(int j = 0; j < n; j++){
               if (i != n -1 && j != n-1) {
                   if (this.blck[i][j] != (n*i) + j + 1) {
                       return false;
                   }
               }
            }
         }
       }
    	return true;*/
    	return this.hamming() == 0; //it works
    }
    
    
// a board that is obtained by exchanging any pair of blocks
    public Board twin()
    {
        //create a new board
    	 int [][] twiny = ArrayCopy2D(blck);
         //Board b;
        //randomly switch any pair, non iterative twin method
    	 for(int i = 0; i < n; i++) {
             for(int j = 0; j < n-1; j++) {
                 if( !isBlank(twiny[i][j]) && !isBlank(twiny[i][j+1])) {
                     if ( n > 2){
                     
                     //dont swap blocks already in the right place
                         //horizontal adjacents
                       if( twiny[i][j] != ((n*i) + j + 1) && twiny[i][j+1] != ((n*i) + j + 2) )
                         return new Board(switched( twiny,i, j, i, j+1));
                        //Board  b = new Board(switched( twiny,i, j, i, j+1));
                    
                    //vertical adjacents
                     if(twiny[i][j] != ((n*i) + j + 1) && twiny[i+1][j] != ((n*(i+1)) + j + 1) )
                         //else
                         return new Board(switched( twiny,i, j, i+1, j));
                        // break;
                         
                     }
                     else{   //when n< 2
                          //b = new Board(switched( twiny,i, j, i, j+1));
                         return new Board(switched( twiny,i, j, i, j+1));
                         //break;
                         
                     }
                  
                 }
                 
             }
        }//return null;
        //vertical adjacents
        for(int j = 0; j < n; j++) {
            for(int i = 0; i < n-1; i++) {
                if( !isBlank(twiny[i][j]) && !isBlank(twiny[i+1][j])) {
                    if(twiny[i][j] != ((n*i) + j + 1) && twiny[i+1][j] != ((n*(i+1)) + j + 1) )
                    //else
                    return new Board(switched( twiny,i, j, i+1, j));
                }
            }
        } return new Board(switched( twiny,0, 0, 1, 0));
                
}
    
    
    
    //switch  horizon
    private int[][] switched(int[][] arr,int rowA, int colA, int rowB, int colB){
        int [][] switched = ArrayCopy2D(arr);
        int temp = switched[rowA][colA];
        switched[rowA][colA] = switched[rowB][colB];
        switched[rowB][colB] = temp;
        return switched;
    }
     /*switch vert
    private int[][] switchedV(int[][] arr,int rowA, int colA, int rowB, int colB){
        int [][] switched = ArrayCopy2D(arr);
        int temp = switched[rowA][colA];
        switched[rowA][colA] = switched[rowB][colB];
        switched[rowB][colB] = temp;
        return switched;
    }*/
 // is this board solvable?
 
  /*  private boolean isSolvable()  {
        return true;
    }*/
    
// does this board equal y?
    public boolean equals(Object y)
    {   
        if(y == this) {
            return true;
        }
        //check for null arg
        if (y == null) {
            return false;
        }
        if(this.getClass() != y.getClass()) {
            return false;
        }
    
        //cast to Board type
        Board yboard = (Board) y;
        if(this.dimension() != yboard.dimension()){
            return false;
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                    //return false if any pair is not equal
                    if (this.blck[i][j] != yboard.blck[i][j]) {
                        return false;
                    }
            }
       }
    //outside loop => all pairs are the same
    	return true;
    }
    
   //check for the blank tile 
   private boolean isBlank(int val) {
       return val == blank;
      
   }
   
   //return the index of blank
   private int[] blankPos() {
       int[] pos = new int[2];
       for(int i = 0; i < n; i++) {
           for(int j = 0; j < n; j++) {
               if (isBlank(this.blck[i][j])) {
                   pos[0] = i; //row
                   pos[1] = j; // col
               }
           }
       } return pos;
       
   }
   
 
   /*translate 2d blocks to 1d array
   private int trans(int i, int j){
     //int val = ((i-1)*n) + j ; //for corner(1,1)
     int val = (i*n) + j+1; //for corner(0,0)
     return val ;
   }  
   convert 2d to id
   private int[] to1D() {
       int n = 10;
       int size = (n*n) -1;
       int[] ar = new int[size];
       int m ;
       for(int i = 0; i < n; i++) {
           for(int j = 0; j < n; j++) {
               m = trans(i, j);
               ar[m] = this.blck[i][j];
            
           }
       }
     return ar;        
   } 
  */
/*
   private int Inv( int[] arr) {
       int count = 0;
       int m = arr.length;
       for(int i = 0; i < m-1; i++){
           for( int j = i + 1; j < m; j++) {
               if (arr[i] > arr[j]) {
                   count++;
               }
           }
       }
       return count % 2; //0 if even, 1 if odd
   }
*/
   
/*all neighboring boards
  return a queue of boards
   find block 0
   move left, right , top or bottom
    * 
    */
    public Iterable<Board> neighbors()     
    {
        int [][] nB = ArrayCopy2D(blck);
        int[] index = blankPos();
        int row = index[0];
        int col = index[1];
        Queue<Board> nBors = new Queue<Board>();
       //not last row, move below bloc upward
        if (row < n-1) { 
           nBors.enqueue(new Board(switched(nB,row, col, row+1, col)));
        }
        //not last col move right bloc to the left
        if (col < n-1) {
            nBors.enqueue(new Board(switched(nB, row, col, row, col+1)));
        }
        //if not first row move above bloc down
        if (row > 0) {
            nBors.enqueue(new Board(switched(nB, row, col, row - 1, col)));
        }
        //if nor first col move bloc on the left to the right
        if (col > 0) {
            nBors.enqueue(new Board(switched(nB, row, col, row, col - 1)));
        }
            
        
    	 return nBors;
    }
    
// string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blck[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

   // unit tests (not graded)
    public static void main(String[] args) 
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        System.out.println("Size is " + initial.dimension());
        System.out.println("Hamming distance is " + initial.hamming());
        System.out.println("Manhattan distance is " + initial.manhattan());
        StdOut.println("Is a goal board " + initial.isGoal());
        
        System.out.println("Initial board is : ");
        StdOut.print(initial.toString());
        System.out.println();
        
        System.out.println("Twin board is : ");
        StdOut.print(initial.twin().toString());
        
        System.out.println("Neighbors are : ");
        for (Board b : initial.neighbors()) {
            //StdOut.println(b.toString());
            //for (Board d : b.neighbors()) {
                StdOut.println("===========");
                StdOut.println(b.toString());
                StdOut.println("===========");
            //}
        }
        
        
    }
}
