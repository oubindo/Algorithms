package textbook.week3;

/**
 * Least-significant-digit-first string sort
 * 说明：书上的源代码，但是需要修改，这里不做修改，以作参考
 * - Consider characters from right to left.
 * - Stably sort using dth character as the key (using key-indexed counting).
 */

public class LSDStringSort {
    public static void sort(String[] a, int w){
        int R = 256;
        int N = a.length;
        String[] aux = new String[N];
        for (int d = w-1; d >= 0; d--)
        {
            int[] count = new int[R+1];
            // 首先计算每个字母出现的次数
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;

            // 进行从上到下的累加
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // 按累加顺序 将排序后的string放入temp数组
            for (int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];

            // 得到一次排序结果
            for (int i = 0; i < N; i++){
                a[i] = aux[i];
                System.out.println(a[i]);
            }

        }
    }

    public static void main(String[] args){
        LSDStringSort.sort(new String[]{"add", "saf", "esd"}, 3);
    }

}
