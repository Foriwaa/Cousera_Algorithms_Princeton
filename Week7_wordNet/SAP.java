import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;


public class SAP {
    
    private Digraph copyG;
    private int V;
    private int[] val;
    private int[] valI;
   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G) {
      if (G == null) {
          throw  new IllegalArgumentException();
      }
       
      V = G.V();
      copyG = new Digraph(G);
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
       if ( v < 0 || v > V-1 || w < 0 || w > V-1) {
           throw  new IllegalArgumentException();
       }
       int[] l = pathHelper(v,w);
       
       return l[1];
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
       //throw  new IllegalArgumentException();
       /* if (v = null || w = null) {
       throw  new IllegalArgumentException();
   }*/
   if ( v < 0 || v > V-1 || w < 0 || w > V-1) {
       throw  new IllegalArgumentException();
   }
   int[] l = pathHelper(v,w);
   
   return l[0];
   }
   
   
   //ancestral path helper 
   private int[] pathHelper(int v, int w) {
       //do dfs on both v and w
       //get the marked array for both usind has pathto
       BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(copyG, v);
       BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(copyG, w);
       int sPath = Integer.MAX_VALUE;
       int ancestor = -1;
       val = new int[2];
       //go through the marked array using haspathTo
       //if both v & w have a path to i => it is a common ancestor
       for(int i = 0; i < V; i++) {
          if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
              int path = vBFS.distTo(i) + wBFS.distTo(i);
              if (path < sPath) {
                  sPath = path;
                  ancestor = i;
              }
              
          }
       }
       
       if(sPath == Integer.MAX_VALUE) { //no path found
          val[0] = -1;
          val[1] = -1;
           
           
       }
       else{ //common ancestor found and min path found
           val[0] = ancestor;
           val[1] = sPath;
           
           
           
       }
       return val;
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
       //throw  new IllegalArgumentException();
        if (v == null || w == null) {
       throw  new IllegalArgumentException();
   }
  
   int[] l = pathHelperIterable(v,w);
   
   return l[1];
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
       //throw  new IllegalArgumentException();
       if (v == null || w == null) {
           throw  new IllegalArgumentException();
       }
      
       int[] l = pathHelperIterable(v,w);
       
       return l[0];
   }
   
   
   
 //ancestral path helper  from iterable sources
   private int[] pathHelperIterable(Iterable<Integer> v, Iterable<Integer> w) {
       //do dfs on both v and w
       //get the marked array for both using haspathto
       BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(copyG, v);
       BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(copyG, w);
       int sPath = Integer.MAX_VALUE;
       int ancestor = -1;
       valI = new int[2];
       //go through the marked array using haspathTo
       //if both v & w have a path to i => it is a common ancestor
       for(int i = 0; i < V; i++) {
          if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
              int path = vBFS.distTo(i) + wBFS.distTo(i);
              if (path < sPath) {
                  sPath = path;
                  ancestor = i;
              }
              
          }
       }
       
       if(sPath == Integer.MAX_VALUE) { //no path found
          valI[0] = -1;
          valI[1] = -1;
           
          
       }
       else{ //common ancestor found and min path found
           valI[0] = ancestor;
           valI[1] = sPath;
           
           
           
       }
       
      return valI;
       
   }

   // do unit testing of this class
   public static void main(String[] args) {
       In in = new In(args[0]);
       Digraph G = new Digraph(in);
       SAP sap = new SAP(G);
       while (!StdIn.isEmpty()) {
           int v = StdIn.readInt();
           int w = StdIn.readInt();
           int length   = sap.length(v, w);
           int ancestor = sap.ancestor(v, w);
           StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
       }
       
   }
}
