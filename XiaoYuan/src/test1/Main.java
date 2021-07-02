package test1;

import java.util.Scanner;

/**
 * ClassName:Main
 * Package:xiao
 * Description:
 *
 * @Author:HP
 * @date:2021/6/30 10:47
 */
public class Main {


    public static void main(String[] args) {
        int[][] A = { { 1, 2, 3 }, { 1, 1, 2 }, { 0, 1, 2 } };

        Scanner s = new Scanner(System.in);
        char[] mingwen = s.nextLine().toCharArray();
        char[] miwen = new char[mingwen.length];
        int row = 3, col = mingwen.length / 3, index = 0;
        while (index < col) {
            int sum;
            for (int i = 0; i < row; i++) {
                sum = 0;
                for (int j = 0; j < row; j++) {
                    sum += A[i][j] * (int) (mingwen[j+index*row] - 97);
                }
                sum %= 26;
                if (sum < 0) sum += 26;
                miwen[i + index*row] = (char) (sum + 97);
            }
            index++;
        }

        for (char c : miwen) {
            System.out.print(c);
        }
    }
}
