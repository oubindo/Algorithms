package boggle;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class BoggleSolver {

    private static final char Q_LETTER = 'Q';

    private static final String QU_STRING = "QU";

    private FastPrefixTrieST<Integer> dict;


    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){
        List<String> dictList = Arrays.asList(dictionary);
        Collections.shuffle(dictList);
        int[] points = {0, 0, 0, 1, 1, 2, 3, 5, 11};
        for (String s: dictList){
            if (s.length() >= points.length - 1){
                dict.put(s, points[points.length - 1]);
            } else {
                dict.put(s, points[s.length()]);
            }
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        if (board == null)
            throw new java.lang.IllegalArgumentException("Board is Null");
        Set<String> foundWords = new HashSet<>();
        for (int row = 0; row < board.rows(); row++){
            for (int col = 0; col < board.cols(); col++){
                // current watching string
                String str = addLetter("", board.getLetter(row, col));
                // array with visited dices
                boolean marked[][] = new boolean[board.rows()][board.cols()];
                marked[row][col] = true;
                dfs(foundWords, str, marked, row, col, board);
            }
        }
        return foundWords;
    }

    private void dfs(Set<String> words, String str, boolean[][] marked, int startRow, int startCol, BoggleBoard board) {
        if (isValidWord(str)) words.add(str);

        for (int row = Math.max(0, startRow - 1); row <= Math.min(board.rows() - 1, startRow + 1); row++){
            for (int col = Math.max(0, startCol - 1); col <= Math.min(board.cols() - 1,startCol + 1); col++) {
                if (marked[row][col]) continue;    // 没有查找过
                if (!dict.hasPrefix(str)) continue;   //
                // prepare to recursive call
                marked[row][col] = true;
                dfs(words, addLetter(str, board.getLetter(row, col)), marked, row, col, board);
                // roll back after recursive call
                marked[row][col] = false;
            }
        }
    }

    private boolean isValidWord(String str) {
        if (str == null) return false;
        if (dict.contains(str) && str.length() > 2) return true;
        else return false;
    }

    // 可以引入StringBuilder来改进String产生的问题，这里不是重点不做要求。
    private String addLetter(String s, char letter) {
        if (letter == Q_LETTER) return s + QU_STRING;
        else return s + letter;
    }


    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        if (word == null || word.length() == 0)
            throw new java.lang.IllegalArgumentException("You want to score empty string!");
        Integer score = dict.get(word);
        if (score == null)
            return 0;
        else
            return score;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }


}
