package seamcarver;

import edu.princeton.cs.algs4.Picture;

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
        computeEnergies();
    }

    private void computeEnergies() {
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
    // 与findVerticalSeam一个道理，直接将图片traverse一下
    public int[] findHorizontalSeam() {
        energyTo = new double[height()][width()];
        edgeTo = new int[height()][width()];

        // 初始化距离
        for (int row = 0; row < height(); row++){
            for (int col = 0; col < width(); col++){
                if (col == 0){
                    energyTo[row][col] = 1000;
                } else{
                    energyTo[row][col] = Double.POSITIVE_INFINITY;
                }
            }
        }

        // 遍历进行relax操作, 最后一列没必要计算在
        for (int row = 0; row < height() - 1; row++){
            for (int col = 0; col < width() - 1; col++){
                if (row > 0) relax(row, col, row - 1, col + 1, Direction.HORIZONTAL);
                relax(row, col, row, col + 1, Direction.HORIZONTAL);
                if (row  < height() - 1) relax(row, col, row + 1, col + 1, Direction.HORIZONTAL);
            }
        }

        printNums("edgeTo", edgeTo);
        System.out.println("---------------------");
        printNums("energyTo", energyTo);
        System.out.println("---------------------");
        // 找到最小的路径
        double min = Double.POSITIVE_INFINITY;
        int minRow = -1;
        for (int i = 0; i < height(); i++){
            if (energyTo[i][width() - 1] < min){
                min = energyTo[i][width() - 1];
                minRow = i;
            }
        }
        int[] seam = new int[width()];
        seam[width() - 1] = minRow;
        int prevX = edgeTo[minRow][width() - 1];
        for (int h = width() - 2; h >= 0; h--) {
            seam[h] = prevX;
            prevX = edgeTo[prevX][h];
        }
        return seam;
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

        // 遍历进行relax操作
        for (int col = 0; col < width(); col++){
            for (int row = 0; row < height() - 1; row++){
                if (col > 0) relax(row, col, row + 1, col - 1, Direction.VERTICAL);
                relax(row, col, row + 1, col, Direction.VERTICAL);
                if (col  < width() - 1) relax(row, col, row + 1, col + 1, Direction.VERTICAL);
            }
        }

        printNums("edgeTo", edgeTo);
        System.out.println("---------------------");
        printNums("energyTo", energyTo);
        System.out.println("---------------------");
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


    private void relax(int row1, int col1, int row2,  int col2, Direction direction){
        if (energyTo[row2][col2] > energyTo[row1][col1] + energies[row2][col2]){
            energyTo[row2][col2] = energyTo[row1][col1] + energies[row2][col2];
            if (direction == Direction.VERTICAL) {
                edgeTo[row2][col2] = col1;
            } else {
                edgeTo[row2][col2] = row1;
            }
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length < 1 || validateSeam(seam, Direction.HORIZONTAL))
            throw new IllegalArgumentException();

        Picture original = mPicture;
        Picture transpose = new Picture(original.height(), original.width());

        for (int w = 0; w < transpose.width(); w++) {
            for (int h = 0; h < transpose.height(); h++) {
                transpose.set(w, h, original.get(h, w));
            }
        }

        this.mPicture = transpose;
        removeVerticalSeam(seam);
        original = mPicture;
        transpose = new Picture(original.height(), original.width());

        for (int w = 0; w < transpose.width(); w++) {
            for (int h = 0; h < transpose.height(); h++) {
                transpose.set(w, h, original.get(h, w));
            }
        }
        this.mPicture = transpose;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length < 1 || validateSeam(seam, Direction.VERTICAL))
            throw new IllegalArgumentException();

        Picture original = this.mPicture;
        Picture carved = new Picture(original.width() - 1, original.height());

        for (int h = 0; h < carved.height(); h++){
            for (int w = 0; w < seam[h]; w++) {
                carved.set(w, h, original.get(w, h));
            }
            for (int w = seam[h]; w < carved.width(); w++) {
                carved.set(w, h, original.get(w + 1, h));
            }
        }
        this.mPicture = carved;
    }

    private boolean validateSeam(int[] seam, Direction direction) {
       if ((direction == Direction.HORIZONTAL && seam.length != width())
               || (direction == Direction.VERTICAL && seam.length != height())) return false;
       for (int i = 0; i < seam.length - 1; i++){
           if (Math.abs(seam[i + 1] - seam[i]) > 1) return false;
       }
       return true;
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

    private void printNums(String name, double[][] nums) {
        System.out.println(name);
        for (int i = 0; i < nums.length; i++){
            for (int j = 0; j < nums[0].length; j++){
                System.out.print(nums[i][j] + "   ");
            }
            System.out.println();
        }
    }


    private void printNums(String name, int[][] nums) {
        System.out.println(name);
        for (int i = 0; i < nums.length; i++){
            for (int j = 0; j < nums[0].length; j++){
                System.out.print(nums[i][j] + "   ");
            }
            System.out.println();
        }
    }
}
