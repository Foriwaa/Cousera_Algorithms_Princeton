import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Outcast {
 // constructor takes a WordNet object
    private WordNet wordnet;
    private String outCast;
   // private int maxDist = Integer.MIN_VALUE;
    private int dist;
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }
 // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDist = -1;
        outCast = "";
        for(String s1 : nouns) {
            dist = 0;
            for(String s2 : nouns) {
                if(!s1.equals(s2)){
               dist += wordnet.distance(s1, s2);
                }
            }
            if(dist > maxDist) {
                maxDist = dist;
                outCast = s1;
            }
        }
        return outCast;
    }
 // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    } 
 }
