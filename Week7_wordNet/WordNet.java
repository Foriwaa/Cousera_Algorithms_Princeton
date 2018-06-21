
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import java.util.ArrayList;

public class WordNet {
    
    /*
     * private noun class
     * has String name
     * queue nounId
     * 
     */
 /*   private class Noun implements Comparable<Noun>{
        private String name;
        private Queue<Integer> nounId = new Queue<Integer>();
        
        public Noun(String name) {
            this.name = name;
            
        }
        public void  enqId(int id) {
            nounId.enqueue(id);
        }
        public Queue<Integer> nId() {
            return this.nounId;
        }
        @Override
        public int compareTo(Noun that) {
            // TODO Auto-generated method stub
            return this.name.compareTo(that.name);
        }
        
    }*/
    

   // private SET<Noun> synSET;
    private ST<String, Queue<Integer>> synST;
    private ArrayList<String> synArray;
    private int V;
    private int numSynsets;
    private int root;
    // private String[] fields;
    private Digraph diGraph;
    private SAP sap;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
      if (synsets == null || hypernyms == null) {
          throw new IllegalArgumentException() ;
      }
      
      V = synHelper(synsets);
      
      //use returned int as vertices for digraph
      
      diGraph = digHelper(hypernyms, V);
    
      //make a deep copy
     // diGraph = new Digraph(graph);
      
    //check if has a directed cycle => not DAG
      DirectedCycle dCycle = new DirectedCycle(diGraph);
      if(dCycle.hasCycle()) {
          throw new IllegalArgumentException() ;
      }
      
        int root =0;
        //check for only single root
        for(int i = 0; i < numSynsets; i++) {
            if ( diGraph.outdegree(i) == 0) {
                root++;
                if (root > 1) {
                    throw new IllegalArgumentException() ;
                }
            }
        }
        
        
      
      sap = new SAP(diGraph);
      
    }
    
    
    //Synset helper method puts the nouns in a set data structure
    //returns the #nouns for the vertices of the digraph
    private int synHelper(String synsets) {
        String[] fields;
        int vert = 0;
        synST = new ST<String, Queue<Integer>>();
        synArray = new ArrayList<String>();
        In syn = new In(synsets);
       String line = syn.readLine();
        
        
        while(line != null) {
             numSynsets++;
            fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            //get nouns from index 1, may be more than one noun
            //separated by a space
            String[] nouns = fields[1].split(" ");
            //put into the synSET
            for(String s: nouns) {
                //Noun n = new Noun(s);
                vert++;
            if (synST.contains(s)) { //get the noun from SET and add id to queue
              //ceil or floor both get noun equal to param
              //Queue<Integer> q = synST.get(s);
                synST.get(s).enqueue(id);;    
            }
            else { //put in noun in synSET and noun.id
                Queue<Integer> q = new Queue<Integer>();
                q.enqueue(id);
                synST.put(s, q);
                
            }
            
            }
            
         // put in id -> synset data structure
            synArray.add(fields[1]);
         //read nextline
          line = syn.readLine(); 
            
            
        }
        
        return vert;
       
    }
    
    private Digraph digHelper( String hyper, int val) {
        //root = 0;
        Digraph d = new Digraph(val);
        In hyp = new In(hyper);
        String line = hyp.readLine();
        
        while(line != null) {
        String[] fields = line.split(",");
        //[0] = v
        /*if field.length == 1, it is a root
            if(fields.length == 1) {
                root++;
            }*/
            
        int v = Integer.parseInt(fields[0]);
        //the rest = w
        for(int i = 1; i < fields.length; i++) {
            int w = Integer.parseInt(fields[i]);
            d.addEdge(v, w);
        }
        
        //read next line
        line = hyp.readLine();
                
        }
        return d;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
       Queue<String> q = new Queue<String>();
       for(String noun : synST) {
           q.enqueue(noun);
       }
      return  q; 
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if( word == null){
            throw new IllegalArgumentException();
        }
        //Noun n1 = new Noun(word);
        return synST.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();  
        }
        if( nounA == null || nounB == null){
          throw new IllegalArgumentException();
        }
       // Noun A = new Noun(nounA);
       // Noun B = new Noun(nounB);
        //Noun mA = synSET.floor(A);
       // Noun mB = synSET.floor(B);
        Queue<Integer> qA = synST.get(nounA);
        Queue<Integer> qB = synST.get(nounB);
        
        
        
        return sap.length(qA, qB);   
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();  
        }
        if( nounA == null || nounB == null){
            throw new IllegalArgumentException();
        }
       
        Queue<Integer> qA = synST.get(nounA);
        Queue<Integer> qB = synST.get(nounB);
       
        
        int i =  sap.ancestor(qA, qB); 
        return synArray.get(i);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        
    }
 }

