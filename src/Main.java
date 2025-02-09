package ru.otus.java.basic.src.otus_java_dz1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите целое число от 1 до 5:");
            int choice = getValidInput(scanner, 1, 5);

            switch (choice) {
                case 1 -> greetings();
                case 2 -> checkSign(3, 4, -6);
                case 3 -> selectColor();
                case 4 -> compareNumbers();
                case 5 -> addOrSubtractAndPrint(1, 15, true);
                default -> System.out.println("Что-то пошло не так. Попробуйте снова.");
            }
        }
    }
    private static void greetings() {
        System.out.println("Hello");
        System.out.println("World");
        System.out.println("from");
        System.out.println("Java");
    }
    private static void checkSign(int a, int b, int c) {
        int sum = a + b + c;
        if (sum >= 0) {
            System.out.println("Сумма положительная");
        } else {
            System.out.println("Сумма отрицательная");
        }
    }
    private static void selectColor() {
        final int DATA = 15;
        if (DATA <= 10) {
            System.out.println("Красный");
        } else if (DATA <= 20) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
    }
    private static void compareNumbers() {
        final int A = 12;
        final int B = -5;
        if (A >= B) {
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }
    private static void addOrSubtractAndPrint(int initValue, int delta, boolean increment) {
        int result = increment ? initValue + delta : initValue - delta;
        System.out.println("Результат: " + result);
    }
    private static int getValidInput(Scanner scanner, int minInclusive, int maxInclusive) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= minInclusive && input <= maxInclusive) {
                    return input;
                } else {
                    System.out.printf("Введено неправильное значение. Введите число от %d до %d:\n", minInclusive, maxInclusive);
                }
            } catch (InputMismatchException e) {
                System.out.println("Ввод не является числом. Повторите попытку:");
                scanner.next();
            }
        }
    }
}