package dz_hw_7;

import java.util.Arrays;

public class dz_hw_7 {
    public static int sumOfPositiveElements(int[][] matrix) {
        int sum = 0;
        for (int[] row : matrix) {
            for (int num : row) {
                if (num > 0) {
                    sum += num;
                }
            }
        }
        return sum;
    }

    public static void printSquare(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    public static void zeroDiagonal(int[][] matrix) {
        int length = Math.min(matrix.length, matrix[0].length);
        for (int i = 0; i < length; i++) {
            matrix[i][i] = 0;
        }
    }

    public static int findMax(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        for (int[] row : matrix) {
            for (int num : row) {
                if (num > max) {
                    max = num;
                }
            }
        }
        return max;
    }

    public static int sumSecondRow(int[][] matrix) {
        if (matrix.length < 2 || matrix[1].length == 0) {
            return -1;
        }
        int sum = 0;
        for (int num : matrix[1]) {
            sum += num;
        }
        return sum;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {-1, 4, 5}, {6, 7, 8}};

        System.out.println("Сумма положительных элементов: " + sumOfPositiveElements(matrix));
        printSquare(3);

        // Обнуление диагонали
        zeroDiagonal(matrix);
        System.out.println("Матрица после обнуления диагонали:");
        printMatrix(matrix);

        System.out.println("Максимальный элемент: " + findMax(matrix));
        System.out.println("Сумма второй строки: " + sumSecondRow(matrix));
    }
}