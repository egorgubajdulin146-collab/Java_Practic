package ru.student.labs.lab01;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/*
 * Студент: Губайдулин Егор
 * Группа: ИТ-18
 * Курс: 2
 * Дисциплина: Языки программирования Java
 * Лабораторная работа №1, вариант 2
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main lab = new Main();

        System.out.println("Губайдулин Егор");
        System.out.println("Группа ИТ-18, 2 курс");
        System.out.println("Языки программирования Java");
        System.out.println("Лабораторная работа №1, вариант 2");

        while(true) {
            System.out.println("1 — Методы");
            System.out.println("2 — Условия");
            System.out.println("3 — Циклы");
            System.out.println("4 — Массивы");
            System.out.println("0 — Выход");
            System.out.print("Выберите задание: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Нужно ввести номер пункта");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println(lab.isUpperCase('D'));
                    System.out.println(lab.isInRange(5, 1, 3));
                    System.out.println(lab.isDivisor(3, 6));
                    System.out.println(lab.isEqual(3, 3, 3));

                    int result = lab.lastNumSum(5, 11);
                    result = lab.lastNumSum(result, 123);
                    result = lab.lastNumSum(result, 14);
                    result = lab.lastNumSum(result, 1);

                    System.out.println(result);
                    break;

                case 2:
                    System.out.println(lab.sum3(5, 7, 2));
                    System.out.println(lab.sum2(5, 7));
                    System.out.println(lab.age(31));
                    System.out.println(lab.day(5));
                    lab.printDays("четверг");
                    break;

                case 3:
                    System.out.println(lab.equalNum(1111));
                    lab.square(4);
                    lab.leftTriangle(4);
                    lab.rightTriangle(4);
                    lab.guessGame();
                    break;

                case 4:
                    int[] arr1 = {1, 2, 3, 4, 5};
                    int[] arr2 = {1, -2, 3, 2, -5, 2};

                    lab.reverse(arr1);
                    System.out.println(Arrays.toString(arr1));

                    System.out.println(
                            Arrays.toString(lab.reverseBack(arr1))
                    );

                    System.out.println(
                            Arrays.toString(lab.concat(arr1, arr2))
                    );

                    System.out.println(
                            Arrays.toString(lab.findAll(arr2, 2))
                    );

                System.out.println(
                        Arrays.toString(lab.deleteNegative(arr2))
                );
                break;

                case 0:
                    System.out.println("Программа завершена");
                    return;

                default:
                    System.out.println("Такого задания нет");
            }
        }
    }


    // Задание 1

    public boolean isUpperCase(char x) {
        return x >= 'A' && x <= 'Z';
    }

    public boolean isInRange(int a, int b, int num) {
        return (num >= a && num <= b) || (num >= b && num <= a);
    }

    public boolean isDivisor(int a, int b) {
        return (a != 0 && b % a == 0) || (b != 0 && a % b == 0);
    }

    public boolean isEqual(int a, int b, int c) {
        return a == b && b == c;
    }

    public int lastNumSum(int a, int b) {
        return Math.abs(a % 10) + Math.abs(b % 10);
    }

    // Задание 2

    public boolean sum3(int x, int y, int z) {
        if (x + y == z) {
            return true;
        }

        if (x + z == y) {
            return true;
        }

        return y + z == x;
    }

    public int sum2(int x, int y) {
        int suma = x + y;

        if(suma >= 10 && suma <= 19) {
            return 20;
        }

        return suma;
    }

    public String age(int x) {
        int lastDigit = x % 10;
        int lastTwoDigits = x % 100;

        if (lastTwoDigits >= 11 && lastTwoDigits <= 14) {
            return x + " лет";
        }

        if (lastDigit == 1) {
            return x + " год";
        }

        if (lastDigit >= 2 && lastDigit <= 4) {
            return x + " года";
        }

        return x + " лет";
    }

    public String day(int x) {
        switch (x) {
            case 1:
                return "понедельник";

            case 2:
                return "вторник";

            case 3:
                return "среда";

            case 4:
                return "четверг";

            case 5:
                return "пятница";

            case 6:
                return "суббота";

            case 7:
                return "воскресенье";

            default:
                return "это не день недели";
        }
    }

    public void printDays(String x) {
        switch (x) {
            case "понедельник":
                System.out.println("понедельник");
            case "вторник":
                System.out.println("вторник");
            case "среда":
                System.out.println("среда");
            case "четверг":
                System.out.println("четверг");
            case "пятница":
                System.out.println("пятница");
            case "суббота":
                System.out.println("суббота");
            case "воскресенье":
                System.out.println("воскресенье");
                break;
            default:
                System.out.println("Это не день недели");
        }
    }

    // Задание 3

    public boolean equalNum(int x) {
        x = Math.abs(x);

        int lastDigit = x % 10;

        while (x > 0) {
            if (x % 10 != lastDigit) {
                return false;
            }

            x = x / 10;
        }

        return true;
    }

    public void square(int x) {
        for (int row = 1; row <= x; row++) {
            for (int column = 1; column <= x; column++) {
                System.out.print("*");
            }

            System.out.println();
        }
    }

    public void leftTriangle(int x) {
        for (int row = 1; row <= x; row++) {
            for (int star = 1; star <= row; star++) {
                System.out.print("*");
            }

            System.out.println();
        }
    }

    public void rightTriangle(int x) {
        for (int row = 1; row <= x; row++) {
            for (int space = 1; space <= x - row; space++) {
                System.out.print(" ");
            }

            for (int star = 1; star <= row; star++) {
                System.out.print("*");
            }

            System.out.println();
        }
    }

    public void guessGame() {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int secretNumber = random.nextInt(10);
        int attempts = 0;

        while (true) {
            System.out.print("Введите число от 0 до 9: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Нужно ввести целое число");
                scanner.next();
                continue;
            }

            int number = scanner.nextInt();

            if (number < 0 || number > 9) {
                System.out.println("Число должно быть от 0 до 9");
                continue;
            }

            attempts++;

            if (number == secretNumber) {
                System.out.println("Вы угадали!");
                System.out.println("Количество попыток: " + attempts);
                break;
            }

            System.out.println("Вы не угадали");
        }
    }

    // Задание 4

    public void reverse(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];

            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
    }

    public int[] reverseBack(int[] arr) {
        int[] result = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[arr.length - 1 - i];
        }

        return result;
    }

    public int[] concat(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];

        for (int i = 0; i < arr1.length; i++) {
            result[i] = arr1[i];
        }

        for (int i = 0; i < arr2.length; i++) {
            result[arr1.length + i] = arr2[i];
        }

        return result;
    }

    public int[] findAll(int[] arr, int x) {
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                count++;
            }
        }

        int[] result = new int[count];
        int resultIndex = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                result[resultIndex] = i;
                resultIndex++;
            }
        }

        return result;
    }

    public int[] deleteNegative(int[] arr) {
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 0) {
                count++;
            }
        }

        int[] result = new int[count];
        int resultIndex = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 0) {
                result[resultIndex] = arr[i];
                resultIndex++;
            }
        }

        return result;
    }


}
