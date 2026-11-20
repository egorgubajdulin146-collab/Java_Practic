package ru.gubaidulin.main;

import static java.lang.Integer.parseInt;
import static java.lang.Math.pow;

public class NumberOperations {
    // Закрывает создание объекта с вычислительными методами.
    private NumberOperations() {
    }

    // Складывает числа разных типов.
    public static double sum(Number... numbers) {
        double result = 0;
        for (Number number : numbers) {
            result += number.doubleValue();
        }
        return result;
    }

    // Преобразует строки в целые числа и вычисляет степень.
    public static double power(String base, String exponent) {
        return pow(parseInt(base), parseInt(exponent));
    }
}
