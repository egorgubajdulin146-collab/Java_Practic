package ru.gubaidulin.numbers;

public final class Fraction extends Number {
    private final long numerator;
    private final long denominator;

    // Создаёт дробь и переносит знак в числитель.
    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Введите знаменатель, отличный от нуля.");
        }
        this.numerator = denominator < 0 ? -(long) numerator : numerator;
        this.denominator = Math.abs((long) denominator);
    }

    // Возвращает целую часть как int.
    @Override
    public int intValue() {
        return (int) longValue();
    }

    // Возвращает целую часть как long.
    @Override
    public long longValue() {
        return numerator / denominator;
    }

    // Возвращает значение с точностью float.
    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    // Возвращает значение дроби как double.
    @Override
    public double doubleValue() {
        return (double) numerator / denominator;
    }

    // Выводит числитель и знаменатель через косую черту.
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}
