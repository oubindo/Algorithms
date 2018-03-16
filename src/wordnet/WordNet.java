package wordnet;



public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if (synsets == null || hypernyms == null){
            throw new IllegalArgumentException("Argument is Null");
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return null;
    }

    // is the word a WordNet noun?
    // 要求O(k)的复杂度
    public boolean isNoun(String word){
        if (word == null){
            throw new IllegalArgumentException("Argument is Null");
        }
    }

    // distance between nounA and nounB (defined below)
    // TODO: 2018/3/16 如果nounA和nounB不是WordNet的nouns的话就抛出IllegalArgumentException异常
    public int distance(String nounA, String nounB){
        if (nounA == null || nounB == null){
            throw new IllegalArgumentException("Argument is Null");
        }
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    // TODO: 2018/3/16 如果nounA和nounB不是WordNet的nouns的话就抛出IllegalArgumentException异常
    public String sap(String nounA, String nounB){
        if (nounA == null || nounB == null){
            throw new IllegalArgumentException("Argument is Null");
        }
    }


    // do unit testing of this class
    public static void main(String[] args){


    }


}
