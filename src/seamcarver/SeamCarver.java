package seamcarver;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

import java.awt.*;

public class SeamCarver {
    private Picture mPicture;
    private double[][] energies;  // 按照定义算出来的energy，暂存下来避免多次计算
    private double[][] energyTo;  // 寻找最优路径时到达某个点时候的总energy
    private int[][] edgeTo;       // 寻找最优路径时到达的点

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.mPicture = new Picture(picture);
        energies = new double[height()][width()];
        for (int row = 0; row < height(); row++){
            for (int col = 0; col < width(); col++){
                if (row == 0 || row == height() - 1 || col == 0 || col == width() - 1){
                    energies[row][col] = 1000;
                } else {
                    energies[row][col] = Math.sqrt(computeDeltaX(col, row) + computeDeltaY(col, row));
                }
            }
        }
    }

    private double computeDeltaY(int col, int row) {
        Color next = mPicture.get(col, row - 1);
        Color last = mPicture.get(col, row + 1 );
        double deltaY2 = Math.pow(next.getRed() - last.getRed(), 2) + Math.pow(next.getGreen() - last.getGreen(), 2)
                +Math.pow(next.getBlue() - last.getBlue(), 2);
        return deltaY2;
    }

    private double computeDeltaX(int col, int row) {
        Color next = mPicture.get(col + 1, row);
        Color last = mPicture.get(col - 1, row);
        double deltaX2 = Math.pow(next.getRed() - last.getRed(), 2) + Math.pow(next.getGreen() - last.getGreen(), 2)
                +Math.pow(next.getBlue() - last.getBlue(), 2);
        return deltaX2;
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
        if (x < 0 || x > width() || y < 0 || y > height())
            throw new IllegalArgumentException();
        return energies[y][x];
    }

    // sequence of indices for horizontal seam
    // 为了方便应用
    public int[] findHorizontalSeam() {
        //
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        energyTo = new double[height()][width()];
        edgeTo = new int[height()][width()];

        // 初始化距离
        for (int row = 0; row < height(); row++){
            for (int col = 0; col < width(); col++){
                if (row == 0){
                    energyTo[row][col] = 1000;
                } else{
                    energyTo[row][col] = Double.POSITIVE_INFINITY;
                }
            }
        }

        // 遍历进行relax操作, 最后一列没必要计算在内
        for (int col = 0; col < width() - 1; col++){
            for (int row = 0; row < height(); row++){
                relax(row, col, row + 1, col);
                if (col > 0) relax(row, col, row + 1, col - 1);
                if (col  < width() - 1) relax(row, col, row + 1, col + 1);
            }
        }

        // 找到最小的路径
        double min = Double.POSITIVE_INFINITY;
        int minCol = -1;
        for (int i = 0; i < width(); i++){
            if (energyTo[height() - 1][i] < min){
                min = energyTo[height() - 1][i];
                minCol = i;
            }
        }
        int[] seam = new int[height()];
        seam[height() - 1] = minCol;
        int prevY = edgeTo[height() - 1][minCol];
        for (int h = height() - 2; h >= 0; h--) {
            seam[h] = prevY;
            prevY = edgeTo[h][prevY];
        }
        return seam;
    }


    private void relax(int col1, int row1, int col2, int row2){
        if (energyTo[row2][col2] > energyTo[row1][col1] + energies[row2][col2]){
            energyTo[row2][col2] = energyTo[row1][col1] + energies[row2][col2];
            edgeTo[row2][col2] = col2;
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || height() <= 1 || validateSeam(seam, Direction.HORIZONTAL))
            throw new IllegalArgumentException();

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || width() <= 1 || validateSeam(seam, Direction.VERTICAL))
            throw new IllegalArgumentException();

    }

    // TODO throw exception when called with an array of the wrong length or if the array is not a valid seam
    private boolean validateSeam(int[] seam, Direction direction) {
        return false;
    }

    private enum Direction{
        HORIZONTAL, VERTICAL
    }

    private class Pixer{
        public int row = 0;
        public int col = 0;
        public Pixer(int col, int row){
            this.col = col;
            this.row = row;
        }
    }

}
