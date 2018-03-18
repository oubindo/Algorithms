package wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class SAP {
    private Digraph mGraph;
    private AncestorBFS mBfs;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if (G == null){
            throw new IllegalArgumentException("Argument is Null");
        }
        mGraph = G;
    }

    private int[] shortest(int v, int w){
        validate(v, w);
        int[] result = new int[2];
        AncestorBFS vBfs = new AncestorBFS(mGraph, v);
        AncestorBFS wBfs = new AncestorBFS(mGraph, v);
        boolean[] vMask = vBfs.getAnestor();
        boolean[] wMask = wBfs.getAnestor();
        int shortLength = Integer.MAX_VALUE;
        int shortAncestor = Integer.MAX_VALUE;
        for (int i = 0; i < vMask.length; i++){
            if (vMask[i] && wMask[i]){
                int temp = vBfs.distTo(i) + wBfs.distTo(i);
                if (shortLength > temp){
                    shortLength = temp;
                    shortAncestor = i;
                }
            }
        }
        if (shortLength == Integer.MAX_VALUE){
            result[0] = -1;
            result[1] = -1;
            return result;
        }
        result[0] = shortLength;
        result[1] = shortAncestor;
        return result;
    }

    private int[] shortest(Iterable<Integer> v, Iterable<Integer> w){
        int[] result = new int[2];
        int shortLength = Integer.MAX_VALUE;
        int shortAncestor = Integer.MAX_VALUE;
        for (int vs : v){
            for (int ws : w){
                int[] tempResult = shortest(vs, ws);
                if (tempResult[0] != -1 && tempResult[0] < shortLength){
                    shortLength = tempResult[0];
                    shortAncestor = tempResult[1];
                }
            }
        }
        if (shortLength == Integer.MAX_VALUE){
            result[0] = -1;
            result[1] = -1;
            return result;
        }
        result[0] = shortLength;
        result[1] = shortAncestor;
        return result;
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        int[] result = shortest(v, w);
        return result[0];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        int[] result = shortest(v, w);
        return result[1];
    }

    private void validate(int v, int w) {
        if (v < 0 || v >= mGraph.V() || w < 0 || w >= mGraph.V()){
            throw new IndexOutOfBoundsException();
        }
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        int[] result = shortest(v, w);
        return result[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        int[] result = shortest(v, w);
        return result[1];
    }

    // do unit testing of this class
    public static void main(String[] args){
        In in = new In("src/wordnet/test/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        System.out.println("sss");
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

    }
}
