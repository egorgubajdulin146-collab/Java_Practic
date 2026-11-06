package ru.student.labs.lab02;

// Задание 5.5: действия с дробями.
public class Fraction {
    private final int numerator;
    private final int denominator;

    // Создание дроби из двух целых чисел.
    public Fraction(int numerator, int denominator) {
        this((long) numerator, (long) denominator);
    }

    // Сокращение дроби и проверка, что результат помещается в int.
    private Fraction(long numerator, long denominator) {
        if (denominator == 0) throw new IllegalArgumentException("Знаменатель равен нулю.");
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        long divisor = gcd(numerator, denominator);
        numerator /= divisor;
        denominator /= divisor;
        if (numerator < Integer.MIN_VALUE || numerator > Integer.MAX_VALUE
                || denominator > Integer.MAX_VALUE) {
            throw new ArithmeticException("Числитель или знаменатель после сокращения не помещается в int.");
        }
        this.numerator = (int) numerator;
        this.denominator = (int) denominator;
    }

    // Поиск наибольшего общего делителя обычным циклом.
    private static long gcd(long a, long b) {
        if (a < 0) a = -a;
        while (b != 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    // Проверка, что вторая дробь задана.
    private static void check(Fraction other) {
        if (other == null) throw new IllegalArgumentException("Дробь не должна быть null.");
    }

    // Сложение двух дробей. long нужен для промежуточных произведений.
    public Fraction sum(Fraction other) {
        check(other);
        long top = (long) numerator * other.denominator + (long) other.numerator * denominator;
        long bottom = (long) denominator * other.denominator;
        return new Fraction(top, bottom);
    }

    // Вычитание другой дроби из этой.
    public Fraction minus(Fraction other) {
        check(other);
        long top = (long) numerator * other.denominator - (long) other.numerator * denominator;
        long bottom = (long) denominator * other.denominator;
        return new Fraction(top, bottom);
    }

    // Умножение двух дробей.
    public Fraction multiply(Fraction other) {
        check(other);
        long top = (long) numerator * other.numerator;
        long bottom = (long) denominator * other.denominator;
        return new Fraction(top, bottom);
    }

    // Деление на другую дробь с проверкой деления на ноль.
    public Fraction div(Fraction other) {
        check(other);
        if (other.numerator == 0) throw new ArithmeticException("Деление на ноль невозможно.");
        long top = (long) numerator * other.denominator;
        long bottom = (long) denominator * other.numerator;
        return new Fraction(top, bottom);
    }

    // Прибавление целого числа к дроби.
    public Fraction sum(int number) {
        return sum(new Fraction(number, 1));
    }

    // Вычитание целого числа из дроби.
    public Fraction minus(int number) {
        return minus(new Fraction(number, 1));
    }

    // Умножение дроби на целое число.
    public Fraction multiply(int number) {
        return multiply(new Fraction(number, 1));
    }

    // Деление дроби на целое число.
    public Fraction div(int number) {
        return div(new Fraction(number, 1));
    }

    // Дробь в виде строки: числитель/знаменатель.
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}
