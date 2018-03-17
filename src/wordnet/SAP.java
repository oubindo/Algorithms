package wordnet;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class SAP {
    private Digraph mGraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if (G == null){
            throw new IllegalArgumentException("Argument is Null");
        }
        mGraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        if (v < 0 || v >= mGraph.V() || w < 0 || w >= mGraph.V()){
            throw new IndexOutOfBoundsException();
        }
        Map<Integer, Integer> ancestorV = getAncestorLength(v);
        Map<Integer, Integer> ancestorW = getAncestorLength(w);
        int dist = -1;
        for (Map.Entry<Integer, Integer> items : ancestorV.entrySet()) {
            if (ancestorW.containsKey(items.getKey())) {
                int currentDist = ancestorW.get(items.getKey())
                        + items.getValue();
                if (dist < 0 || currentDist < dist)
                    dist = currentDist;
            }
        }
        return dist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        if (v < 0 || v >= mGraph.V() || w < 0 || w >= mGraph.V()){
            throw new IndexOutOfBoundsException();
        }
        Map<Integer, Integer> ancestorV = getAncestorLength(v);
        Map<Integer, Integer> ancestorW = getAncestorLength(w);
        int dist = -1, anc = -1;
        for (Map.Entry<Integer, Integer> items : ancestorV.entrySet()) {
            if (ancestorW.containsKey(items.getKey())) {
                int currentDist = ancestorW.get(items.getKey()) + items.getValue();
                if (dist < 0 || currentDist < dist){
                    dist = currentDist;
                    anc = items.getKey();
                }
            }
        }
        return dist;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        int dist = -1;
        for (Integer eV : v) {
            for (Integer eW : w) {
                int currentDist = length(eV, eW);
                if (currentDist > 0 && (dist < 0 || currentDist < dist))
                    dist = currentDist;
            }
        }
        return dist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        int dist = -1, anc = -1;
        for (Integer eV : v) {
            for (Integer eW : w) {
                int currentDist = length(eV, eW);
                if (currentDist > 0 && (dist < 0 || currentDist < dist)) {
                    dist = currentDist;
                    anc = ancestor(eV, eW);
                }
            }
        }
        return anc;
    }

    private Map<Integer, Integer> getAncestorLength(int v){
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        queue.add(v);
        map.put(v, 0);
        while (!queue.isEmpty()) {
            int head = queue.poll();
            int currentDist = map.get(head);
            for (Integer w : mGraph.adj(head)) {
                if (!map.containsKey(w) || map.get(w) > currentDist + 1) {
                    queue.add(w);
                    map.put(w, currentDist + 1);
                }
            }
        }
        return map;
    }

    // do unit testing of this class
    public static void main(String[] args){
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
