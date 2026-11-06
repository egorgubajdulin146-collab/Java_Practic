package ru.student.labs.lab02;

import java.util.Scanner;

public class Main {
    private static final Scanner IN = new Scanner(System.in);

    // Главное меню и выбор задания.
    public static void main(String[] args) {
        try {
            while (true) {
                System.out.println("\nЛабораторная №2. Вариант 2");
                System.out.println("1 — 1.2. Человек");
                System.out.println("2 — 1.3. Имена");
                System.out.println("3 — 2.2. Человек с именем");
                System.out.println("4 — 3.3. Города");
                System.out.println("5 — 4.8. Конструкторы городов");
                System.out.println("6 — 5.5. Дроби");
                System.out.println("0 — Выход");
                int task = readInt("Ваш выбор: ", 0, 6);
                if (task == 0) break;
                int mode = readInt("1 — пример из задания, 2 — свой ввод: ", 1, 2);
                switch (task) {
                    case 1:
                        people(mode == 1);
                        break;
                    case 2:
                        names(mode == 1);
                        break;
                    case 3:
                        namedPeople(mode == 1);
                        break;
                    case 4:
                    case 5:
                        cities(mode == 1);
                        break;
                    case 6:
                        fractions(mode == 1);
                        break;
                }
            }
        } catch (IllegalStateException ex) {
            System.out.println("\n" + ex.getMessage());
        }
        System.out.println("Программа завершена.");
    }

    // Ввод строки с клавиатуры.
    private static String line(String prompt) {
        System.out.print(prompt);
        if (!IN.hasNextLine()) throw new IllegalStateException("Ввод завершён.");
        return IN.nextLine().strip();
    }

    // Ввод текста с проверкой, что он не пустой.
    private static String readText(String prompt) {
        while (true) {
            String value = line(prompt);
            boolean correct = !value.isBlank();
            for (int i = 0; i < value.length(); i++) {
                if (Character.isISOControl(value.charAt(i))) correct = false;
            }
            if (correct) return value;
            System.out.println("Введите непустой текст без управляющих символов.");
        }
    }

    // Ввод целого числа в заданных пределах.
    private static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(line(prompt));
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) {
            }
            System.out.printf("Введите целое число от %d до %d.%n", min, max);
        }
    }

    // Ввод фамилии, имени и отчества.
    private static FullName readName() {
        while (true) {
            System.out.println("Необязательные части имени можно пропустить, нажав Enter.");
            String surname = line("Фамилия: ");
            String firstName = line("Личное имя: ");
            String patronymic = line("Отчество: ");
            try {
                return new FullName(surname, firstName, patronymic);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // Пример или свой ввод людей с обычным именем и ростом.
    private static void people(boolean demo) {
        if (demo) {
            System.out.println(new SimplePerson("Клеопатра", 152));
            System.out.println(new SimplePerson("Пушкин", 167));
            System.out.println(new SimplePerson("Владимир", 189));
            return;
        }
        int count = readInt("Количество людей (1–100): ", 1, 100);
        for (int i = 1; i <= count; i++) {
            System.out.println("Человек №" + i);
            System.out.println(new SimplePerson(readText("Имя: "),
                    readInt("Рост в сантиметрах: ", 1, Integer.MAX_VALUE)));
        }
    }

    // Пример или свой ввод полных и неполных имён.
    private static void names(boolean demo) {
        if (demo) {
            System.out.println(new FullName(null, "Клеопатра", null));
            System.out.println(new FullName("Пушкин", "Александр", "Сергеевич"));
            System.out.println(new FullName("Маяковский", "Владимир", null));
            return;
        }
        int count = readInt("Количество имён (1–100): ", 1, 100);
        for (int i = 1; i <= count; i++) {
            System.out.println("Имя №" + i);
            System.out.println(readName());
        }
    }

    // Пример или свой ввод людей с фамилией, именем, отчеством и ростом.
    private static void namedPeople(boolean demo) {
        if (demo) {
            FullName cleopatra = new FullName(null, "Клеопатра", null);
            FullName pushkin = new FullName("Пушкин", "Александр", "Сергеевич");
            FullName mayakovsky = new FullName("Маяковский", "Владимир", null);
            System.out.println(new Person(cleopatra, 152));
            System.out.println(new Person(pushkin, 167));
            System.out.println(new Person(mayakovsky, 189));
            return;
        }
        int count = readInt("Количество людей (1–100): ", 1, 100);
        for (int i = 1; i <= count; i++) {
            System.out.println("Человек №" + i);
            FullName name = readName();
            int height = readInt("Рост в сантиметрах: ", 1, Integer.MAX_VALUE);
            System.out.println(new Person(name, height));
        }
    }

    // Схема городов из задания или ввод своих городов и путей.
    private static void cities(boolean demo) {
        if (demo) {
            City a = new City("A");
            City b = new City("B");
            City c = new City("C");
            City d = new City("D");
            City e = new City("E");
            City[] neighbors = {b, e};
            int[] costs = {1, 2};
            City f = new City("F", neighbors, costs);
            a.addRoute(b, 5);
            a.addRoute(f, 1);
            a.addRoute(d, 6);
            b.addRoute(a, 5);
            b.addRoute(c, 3);
            c.addRoute(b, 3);
            c.addRoute(d, 4);
            d.addRoute(a, 6);
            d.addRoute(c, 4);
            d.addRoute(e, 2);
            e.addRoute(f, 2);
            System.out.println("Схема из PDF. Учитываются направления стрелок.");
            System.out.println("A–E созданы по названию, F — по названию и набору путей.");
            City[] cities = {a, b, c, d, e, f};
            for (int i = 0; i < cities.length; i++) {
                System.out.println(cities[i]);
            }
            return;
        }

        int count = readInt("Количество городов (1–100): ", 1, 100);
        City[] cities = new City[count];
        for (int i = 0; i < count; i++) {
            while (true) {
                String name = readText("Название города №" + (i + 1) + ": ");
                boolean duplicate = false;
                for (int j = 0; j < i; j++) {
                    if (cities[j].getName().equalsIgnoreCase(name)) duplicate = true;
                }
                if (duplicate) {
                    System.out.println("Город с таким названием уже есть.");
                } else {
                    cities[i] = new City(name);
                    break;
                }
            }
        }
        System.out.println("Пути направленные. Обратный путь вводится отдельно.");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + " — " + cities[i].getName());
        }
        for (int i = 0; i < count; i++) {
            City from = cities[i];
            int paths = readInt("Количество исходящих путей из " + from.getName() + ": ",
                    0, count - 1);
            for (int j = 0; j < paths; j++) {
                while (true) {
                    int index = readInt("Номер города назначения: ", 1, count) - 1;
                    City to = cities[index];
                    if (to == from || from.hasRoute(to)) {
                        System.out.println("Выберите другой город без повторения пути.");
                        continue;
                    }
                    int cost = readInt("Стоимость (целое число, не меньше 0): ",
                            0, Integer.MAX_VALUE);
                    from.addRoute(to, cost);
                    break;
                }
            }
        }
        for (int i = 0; i < count; i++) {
            System.out.println(cities[i]);
        }
    }

    // Ввод дроби с проверкой знаменателя и допустимого размера чисел.
    private static Fraction readFraction(String label, boolean nonZero) {
        System.out.println(label);
        while (true) {
            int numerator = readInt("Числитель: ", Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (nonZero && numerator == 0) {
                System.out.println("Здесь ноль недопустим.");
                continue;
            }
            int denominator;
            do {
                denominator = readInt("Знаменатель (не 0): ", Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (denominator == 0) System.out.println("Здесь ноль недопустим.");
            } while (denominator == 0);
            try {
                return new Fraction(numerator, denominator);
            } catch (ArithmeticException ex) {
                System.out.println(ex.getMessage() + " Введите дробь заново.");
            }
        }
    }

    // Пример или свой ввод дробей и вывод результатов всех операций.
    private static void fractions(boolean demo) {
        Fraction f1, f2, f3;
        int number;
        if (demo) {
            f1 = new Fraction(1, 3);
            f2 = new Fraction(2, 3);
            f3 = new Fraction(3, 4);
            number = 2;
        } else {
            f1 = readFraction("Первая дробь f1", false);
            f2 = readFraction("Вторая дробь f2", false);
            f3 = readFraction("Третья дробь f3 (не ноль для цепочки операций)", true);
            number = readInt("Целое число для операций: ", Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        String[] signs = {"+", "-", "*", "/", "+", "-", "*", "/"};
        for (int i = 0; i < signs.length; i++) {
            String expression = f1 + " " + signs[i] + " ";
            if (i < 4) expression += f2;
            else expression += number;
            try {
                Fraction result;
                switch (i) {
                    case 0:
                        result = f1.sum(f2);
                        break;
                    case 1:
                        result = f1.minus(f2);
                        break;
                    case 2:
                        result = f1.multiply(f2);
                        break;
                    case 3:
                        result = f1.div(f2);
                        break;
                    case 4:
                        result = f1.sum(number);
                        break;
                    case 5:
                        result = f1.minus(number);
                        break;
                    case 6:
                        result = f1.multiply(number);
                        break;
                    default:
                        result = f1.div(number);
                }
                System.out.println(expression + " = " + result);
            } catch (ArithmeticException ex) {
                System.out.println(expression + ": " + ex.getMessage());
            }
        }
        try {
            System.out.println("f1.sum(f2).div(f3).minus(5) = " + f1.sum(f2).div(f3).minus(5));
        } catch (ArithmeticException ex) {
            System.out.println("Не удалось вычислить цепочку: " + ex.getMessage());
        }
        System.out.println("Исходные дроби: f1 = " + f1 + ", f2 = " + f2 + ", f3 = " + f3);
    }
}
