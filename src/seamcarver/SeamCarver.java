package seamcarver;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture mPicture;


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.mPicture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return mPicture;
    }

    // width of current picture
    public int width() {
        return mPicture.width();
    }

    // height of current picture
    public int height() {
        return mPicture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > mPicture.width() || y < 0 || y > mPicture.height())
            throw new IllegalArgumentException();
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || mPicture.height() <= 1 || validateSeam(seam, Direction.HORIZONTAL))
            throw new IllegalArgumentException();


    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || mPicture.width() <= 1 || validateSeam(seam, Direction.VERTICAL))
            throw new IllegalArgumentException();

    }

    private boolean validateSeam(int[] seam, Direction direction) {
        return false;
    }

    private enum Direction{
        HORIZONTAL, VERTICAL
    }

}
