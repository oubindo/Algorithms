package wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;

public class WordNet {

    // 保存数字和单词的对应关系, 因为存在以下情况：1.一个单词是别的单词的同义词，对应多个数字 2.一个单词有多重释义
    private Map<String, List<Integer>> mWordIndexMap;  // HashMap的增加，查找，删除都是O(logN)级别的
    // 正向保存数字和单词的对应关系
    private List<String> mWordList;

    private SAP mSap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null){
            throw new IllegalArgumentException("Argument is Null");
        }
        mWordIndexMap = new HashMap<>();
        mWordList = new ArrayList<>();

        //BufferedReader reader = new BufferedReader(new FileReader(new File(synsets)));
        In reader = new In(synsets);
        String str = null;
        while((str = reader.readLine()) != null) {
            String[] strs = str.split(",");
            String[] nouns = strs[1].split(" ");
            List<Integer> nums;
            for (String s:nouns){
                if (!mWordIndexMap.containsKey(s)){
                    nums = new LinkedList<>();
                } else {
                    nums = mWordIndexMap.get(s);
                }
                nums.add(Integer.parseInt(strs[0]));
                mWordIndexMap.put(s, nums);
            }
            mWordList.add(strs[1]);
        }
        reader.close();
        In hyperReader = new In(hypernyms);
        String str2 = null;
        Digraph digraph = new Digraph(mWordList.size());
        boolean[] notRoot = new boolean[mWordList.size()];
        while((str2 = hyperReader.readLine()) != null) {
            String[] strs = str2.split(",");
            int v = Integer.parseInt(strs[0]);
            notRoot[v] = true;
            for (int i = 0;i < strs.length; i++){
                digraph.addEdge(v, Integer.parseInt(strs[i]));
            }
        }
        hyperReader.close();
        if (!isDAG(digraph, notRoot)){
            throw new IllegalArgumentException();
        }
        mSap = new SAP(digraph);
    }

    private boolean isDAG(Digraph digraph, boolean[] notRoot) {
        DirectedCycle directedCycle = new DirectedCycle(digraph);
        if (directedCycle.hasCycle()) {
            System.out.println(directedCycle.cycle());
            return false;
        }

        // 查找是否有多个root
        int count = 0;
        for (boolean v: notRoot){
            if (!v) count++;
        }
        return count == 1;
    }
    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return mWordIndexMap.keySet();
    }

    // is the word a WordNet noun?
    // 要求O(k)的复杂度
    public boolean isNoun(String word){
        if (word == null){
            throw new IllegalArgumentException("Argument is Null");
        }
        return mWordIndexMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if (nounA == null || nounB == null){
            throw new IllegalArgumentException("Argument is Null");
        }
        return mSap.length(mWordIndexMap.get(nounA), mWordIndexMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if (!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException("Argument is Null");
        }
        int id = mSap.ancestor(mWordIndexMap.get(nounA), mWordIndexMap.get(nounB));
        return mWordList.get(id);
    }
}
