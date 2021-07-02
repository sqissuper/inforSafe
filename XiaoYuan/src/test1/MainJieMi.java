package test1;

import java.util.Scanner;

/**
 * ClassName:MainJieMi
 * Package:xiao
 * Description:
 *
 * @Author:HP
 * @date:2021/6/30 10:50
 */
public class MainJieMi {
    public static void main(String[] args) {
        int[][] A_1 = {{0, 1, -1}, {2, -2, -1}, {-1, 1, 1}};
        Scanner s = new Scanner(System.in);
        char[] miwen = s.nextLine().toCharArray();
        char[] mingwen = new char[miwen.length];
        int row = 3, col = mingwen.length / 3, index = 0;

        while (index < col) {
            int sum;
            for (int i = 0; i < 3; i++) {
                sum = 0;
                for (int j = 0; j < 3; j++) {
                    sum += A_1[i][j] * (int)(miwen[j+index*row] - 97);
                }
                sum %= 26;
                if (sum < 0) sum += 26;
                mingwen[i+index*row] = (char)(sum+97);
            }
            index++;
        }
        for(char c : mingwen) {
            System.out.print(c);
        }
    }
}
