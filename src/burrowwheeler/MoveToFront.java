package burrowwheeler;

import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MoveToFront {

    private static int CHAR_BITS = 8;
    private static int ALPHABET_SIZE = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void transform(){
        List<Character> ansiList = createANSIList();
        while (!BinaryStdIn.isEmpty()){
            char curChar = BinaryStdIn.readChar();
            int alphabetPosi = 0;
            Iterator<Character> moveToFrontIterator = ansiList.iterator();
            while (moveToFrontIterator.hasNext()) {
                if (moveToFrontIterator.next().equals(Character.valueOf(curChar))) {
                    BinaryStdOut.write(alphabetPosi, CHAR_BITS);
                    char toFront = ansiList.get(alphabetPosi);
                    ansiList.remove(alphabetPosi);
                    ansiList.add(0, toFront);
                    break;
                }
                alphabetPosi++;
            }
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void inverseTransform(){
        List<Character> ansiList = createANSIList();
        while (!BinaryStdIn.isEmpty()) {
            int curCharPosition = BinaryStdIn.readChar();
            BinaryStdOut.write(ansiList.get(curCharPosition), CHAR_BITS);
            char toFront = ansiList.get(curCharPosition);
            ansiList.remove(curCharPosition);
            ansiList.add(0, toFront);
        }
        BinaryStdOut.close();
    }

    private static List<Character> createANSIList() {
        List<Character> ansiList = new LinkedList<Character>();
        for (int alphabetPosition = 0; alphabetPosition < ALPHABET_SIZE ; alphabetPosition++)
            ansiList.add((char) alphabetPosition);
        return ansiList;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args){
        if (args.length == 0)
            throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
        if (args[0].equals("-"))
            transform();
        else if (args[0].equals("+"))
            inverseTransform();
        else
            throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
    }
}
