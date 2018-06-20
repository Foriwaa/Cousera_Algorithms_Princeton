import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;



public class Solver {
// find a solution to the initial board (using the A* algorithm)
//search node
    //2 PQs, stores all the diff configs
    //initial & goal board
    //solvable if min.board = goal
    //not solvable if twin = goal
    private boolean solved;
    private SearchNode solution;
    private int moves;
    //private Board initial;
    //private SearchNode p = null;
    //private final int mv = 0;
    public Solver(Board initial)
    {
        //throw exception
        if ( initial == null){
            throw new IllegalArgumentException();
        }
        //create goalboard
       
        
      MinPQ<SearchNode> inPQ = new MinPQ<SearchNode>();
      MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
        
      inPQ.insert(new SearchNode(initial, 0 , null));
      twinPQ.insert(new SearchNode(initial.twin(),0 ,null));
        
    /* can put everyboard dequeue from pq into a soln queue and return at soln method
    	dequeue min priority and run A*
     if InNode isGoal() return goa
      when one of the two queues finds the goal board: 
      if goal board came from the twin PQ 
      then the initial board is unsolvable,
     */
        SearchNode inNode = inPQ.delMin();
        SearchNode twinNode = twinPQ.delMin();
       
        
       //check if inNode  or twinNode is goal
       while( !(inNode.board).isGoal() && !(twinNode.board).isGoal()) {
           //enqueue neighbors
           
           //main board
           for(Board b : inNode.board.neighbors()) {
//if it is the first config or config not same as prev
               if(inNode.prevNode == null || !b.equals(inNode.prevNode.board)) {
                 
                   SearchNode sN = new SearchNode(b,inNode.moves +1, inNode );
                   /*sN.moves = inNode.moves +1;
                   sN.prevNode = inNode;*/
               inPQ.insert(sN);  
               }
               
           }
           
           
           //twin
           for(Board c : twinNode.board.neighbors() ) {
               if (twinNode.prevNode == null || !c.equals(twinNode.prevNode.board)) {
                   SearchNode tsN = new SearchNode(c,twinNode.moves + 1,twinNode);
                   /*tsN.moves = twinNode.moves + 1;
                   tsN.prevNode = twinNode;*/
                   twinPQ.insert(tsN); 
               }
                
           }
           
           // update inNode
           inNode = inPQ.delMin();
           // update twinNode
            twinNode = twinPQ.delMin();
       }
       
       //while loop exits if either main or twin is goal
       if (inNode.board.isGoal()) {
           solved = true;
           solution = inNode;
           this.moves = solution.moves;
       }
       // (twinNode.board.isGoal())
        else{
           solved = false;
           //this.moves = -1
           solution = null;
       }
      
    }
// is the initial board solvable?
    public boolean isSolvable()            
    {
    	return solved ;
    }
    
    

    

    
// min number of moves to solve initial board; -1 if unsolvable
//number of moves made to reach current config
    public int moves()                     
    {
     if (!isSolvable()) {return -1;}
     
    	//return solution.moves;
        return this.moves;
    }
    
// sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()      
    {
        if (!isSolvable()) { return null;}
       // return sol;
        //use a stack to get return in order
        Stack<Board> fin = new Stack<Board>();
        //prevNode == null for first config
        while( solution.prevNode != null){
            fin.push(solution.board);
            solution = solution.prevNode;
        }
        // push the last one on stack
        fin.push(solution.board);
            return fin;
    }
    
    /*
     * keeps board in mind
     * know how many moves taken to get to that board
     * have a priority for each node
     * previous pointer to work way back to starting board so 
     * previous search node
     * keep track of previous so as to not enqueue a neighbor that is the same 
     * as a config we have already seen
     * we can return the sequence of boards taken to reach solution
     */
    private static class SearchNode implements Comparable<SearchNode>{
     //cache in the node class 
     // implements comparable?   
        private final Board board;
        private final int moves;
        //public int moves;
        private final SearchNode prevNode;
        private final int pVal;
        
        public SearchNode(Board board, int moves, SearchNode prevNode) {
            this.board = board;
            this.moves = moves;
            this.prevNode = prevNode;
            pVal = moves + board.manhattan();
            //this.prevNode = prevNode;
            
        }
        
        /*compare  search nodes by priority value, pVal
        //for PQ deletion
        public SNode(Board b, int moves, SearchNode prevNode)
        {
           this.moves = moves;
           this.prevNode = prevNode;
           
        }*/
       // @Override
        public int compareTo(SearchNode that) {
            // TODO Auto-generated method stub
            Integer a = Integer.valueOf(this.pVal);
            Integer b = Integer.valueOf(that.pVal);
  
            return (a.compareTo( b));
        }
    }
    
// solve a slider puzzle (given below)
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
