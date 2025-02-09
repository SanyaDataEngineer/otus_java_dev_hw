package ru.otus.java.basic.src.otus_java_dz2;

import java.util.Arrays;

public class Main2 {
    public static void main(String[] args) {

        printStringNTimes(3, "Привет!");

        int[] arr1 = {1, 8, 3, 9, 2};
        sumElementsGreaterThanFive(arr1);

        int[] arr2 = new int[5];
        fillArrayWithNumber(7, arr2);

        int[] arr3 = {1, 2, 3, 4, 5};
        increaseEachElementByNumber(3, arr3);

        int[] arr4 = {1, 2, 3, 4, 5, 6, 7, 8};
        compareHalvesOfArray(arr4);
    }

    public static void printStringNTimes(int n, String str) {
        for (int i = 0; i < n; i++) {
            System.out.println(str);
        }
    }

    public static void sumElementsGreaterThanFive(int[] array) {
        int sum = 0;
        for (int element : array) {
            if (element > 5) {
                sum += element;
            }
        }
        System.out.println("Сумма элементов массива, больших 5: " + sum);
    }

    public static void fillArrayWithNumber(int value, int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
        System.out.println("Массив заполнен числом " + value + ": " + Arrays.toString(array));
    }

    public static void increaseEachElementByNumber(int value, int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] += value;
        }
        System.out.println("Каждый элемент массива увеличен на " + value + ": " + Arrays.toString(array));
    }

    public static void compareHalvesOfArray(int[] array) {
        int mid = array.length / 2;
        int firstHalfSum = 0;
        int secondHalfSum = 0;

        for (int i = 0; i < mid; i++) {
            firstHalfSum += array[i];
        }

        for (int i = mid; i < array.length; i++) {
            secondHalfSum += array[i];
        }

        if (firstHalfSum > secondHalfSum) {
            System.out.println("Первая половина массива имеет большую сумму: " + firstHalfSum);
        } else if (secondHalfSum > firstHalfSum) {
            System.out.println("Вторая половина массива имеет большую сумму: " + secondHalfSum);
        } else {
            System.out.println("Суммы обеих половин массива равны: " + firstHalfSum);
        }
    }
}