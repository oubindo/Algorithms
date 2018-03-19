package seamcarver.test;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import seamcarver.SeamCarver;

public class PrintEnergy {
    public static void main(String[] args) {
        Picture picture = new Picture("src/seamcarver/test/image1.png");
        StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture.width(), picture.height());

        SeamCarver sc = new SeamCarver(picture);

        System.out.println(Math.pow(5-9, 2));
        StdOut.printf("Printing energy calculated for each pixel.\n");

        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++)
                StdOut.println(sc.energy(col, row));
            StdOut.println();
        }
    }
}
