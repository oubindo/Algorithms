package wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.io.IOException;

public class Outcast {
    private WordNet mWordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet){
        this.mWordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns){
        String bestNoun = "";
        int bestDist = 0;
        for (int i = 0; i < nouns.length; i++) {
            int currentDist = 0;
            for (int j = 0; j < nouns.length; j++) {
                currentDist += mWordnet.distance(nouns[i], nouns[j]);
            }
            if (bestDist < currentDist) {
                bestDist = currentDist;
                bestNoun = nouns[i];
            }
        }
        return bestNoun;
    }

    // see test client below
    public static void main(String[] args) throws IOException {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
