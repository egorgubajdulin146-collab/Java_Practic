package ru.student.labs.lab02;

import java.math.BigInteger;
import java.util.*;

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
                    case 1 -> people(mode == 1);
                    case 2 -> names(mode == 1);
                    case 3 -> namedPeople(mode == 1);
                    case 4, 5 -> cities(mode == 1);
                    case 6 -> fractions(mode == 1);
                }
            }
        } catch (NoSuchElementException ex) {
            System.out.println("\nВвод завершён.");
        }
        System.out.println("Программа завершена.");
    }

    // Ввод строки с клавиатуры.
    private static String line(String prompt) {
        System.out.print(prompt);
        return IN.nextLine().strip();
    }

    // Ввод текста с проверкой, что он не пустой.
    private static String readText(String prompt) {
        while (true) {
            String value = line(prompt);
            if (!value.isBlank() && value.codePoints().noneMatch(Character::isISOControl)) {
                return value;
            }
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

    // Ввод большого целого числа с проверкой на ноль, если он запрещён.
    private static BigInteger readBigInteger(String prompt, boolean nonZero) {
        while (true) {
            try {
                BigInteger value = new BigInteger(line(prompt));
                if (!nonZero || value.signum() != 0) return value;
                System.out.println("Здесь ноль недопустим.");
            } catch (NumberFormatException ex) {
                System.out.println("Введите целое число, например: -12, 0 или 25.");
            }
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
            Map<City, Integer> routes = new LinkedHashMap<>();
            routes.put(b, 1);
            routes.put(e, 2);
            City f = new City("F", routes);
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
            for (City city : List.of(a, b, c, d, e, f)) System.out.println(city);
            return;
        }

        int count = readInt("Количество городов (1–100): ", 1, 100);
        List<City> cities = new ArrayList<>();
        Set<String> usedNames = new HashSet<>();
        while (cities.size() < count) {
            String name = readText("Название города №" + (cities.size() + 1) + ": ");
            if (!usedNames.add(name.toLowerCase(Locale.ROOT))) {
                System.out.println("Город с таким названием уже есть.");
                continue;
            }
            cities.add(new City(name));
        }
        System.out.println("Пути направленные. Обратный путь вводится отдельно.");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + " — " + cities.get(i).getName());
        }
        for (City from : cities) {
            int paths = readInt("Количество исходящих путей из " + from.getName() + ": ",
                    0, count - 1);
            Set<City> destinations = new HashSet<>();
            while (destinations.size() < paths) {
                int index = readInt("Номер города назначения: ", 1, count) - 1;
                City to = cities.get(index);
                if (to == from || destinations.contains(to)) {
                    System.out.println("Выберите другой город без повторения пути.");
                    continue;
                }
                int cost = readInt("Стоимость (целое число, не меньше 0): ",
                        0, Integer.MAX_VALUE);
                from.addRoute(to, cost);
                destinations.add(to);
            }
        }
        for (City city : cities) System.out.println(city);
    }

    // Ввод числителя и знаменателя дроби.
    private static Fraction readFraction(String label, boolean nonZero) {
        System.out.println(label);
        BigInteger numerator = readBigInteger("Числитель: ", nonZero);
        BigInteger denominator = readBigInteger("Знаменатель (не 0): ", true);
        return new Fraction(numerator, denominator);
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
        System.out.println(f1 + " + " + f2 + " = " + f1.sum(f2));
        System.out.println(f1 + " - " + f2 + " = " + f1.minus(f2));
        System.out.println(f1 + " * " + f2 + " = " + f1.multiply(f2));
        try {
            System.out.println(f1 + " / " + f2 + " = " + f1.div(f2));
        } catch (ArithmeticException ex) {
            System.out.println(f1 + " / " + f2 + ": " + ex.getMessage());
        }
        System.out.println(f1 + " + " + number + " = " + f1.sum(number));
        System.out.println(f1 + " - " + number + " = " + f1.minus(number));
        System.out.println(f1 + " * " + number + " = " + f1.multiply(number));
        try {
            System.out.println(f1 + " / " + number + " = " + f1.div(number));
        } catch (ArithmeticException ex) {
            System.out.println(f1 + " / " + number + ": " + ex.getMessage());
        }
        System.out.println("f1.sum(f2).div(f3).minus(5) = " + f1.sum(f2).div(f3).minus(5));
        System.out.println("Исходные дроби: f1 = " + f1 + ", f2 = " + f2 + ", f3 = " + f3);
    }
}

// Задание 1.2: имя хранится строкой.
class SimplePerson {
    private final String name;
    private final int height;

    // Создание человека с проверкой имени и роста.
    public SimplePerson(String name, int height) {
        if (name == null || name.isBlank()
                || name.codePoints().anyMatch(Character::isISOControl)) {
            throw new IllegalArgumentException("Имя должно быть непустым текстом.");
        }
        if (height <= 0) throw new IllegalArgumentException("Рост должен быть положительным.");
        this.name = name.strip();
        this.height = height;
    }

    // Строка с именем и ростом человека.
    @Override
    public String toString() {
        return name + ", рост: " + height;
    }
}

// Задание 1.3. Порядок аргументов: фамилия, личное имя, отчество.
class FullName {
    private final String surname;
    private final String firstName;
    private final String patronymic;

    // Создание имени: достаточно заполнить хотя бы одну часть.
    public FullName(String surname, String firstName, String patronymic) {
        this.surname = normalize(surname);
        this.firstName = normalize(firstName);
        this.patronymic = normalize(patronymic);
        if (this.surname.isEmpty() && this.firstName.isEmpty() && this.patronymic.isEmpty()) {
            throw new IllegalArgumentException("Укажите хотя бы одну часть имени.");
        }
    }

    // Убираем пробелы по краям, а отсутствующую часть имени заменяем пустой строкой.
    private static String normalize(String value) {
        if (value == null) return "";
        if (value.codePoints().anyMatch(Character::isISOControl)) {
            throw new IllegalArgumentException("В имени недопустимы управляющие символы.");
        }
        return value.strip();
    }

    // Собираем имя в одну строку, пропуская незаполненные части.
    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(" ");
        if (!surname.isEmpty()) result.add(surname);
        if (!firstName.isEmpty()) result.add(firstName);
        if (!patronymic.isEmpty()) result.add(patronymic);
        return result.toString();
    }
}

// Задание 2.2: имя хранится объектом FullName.
class Person {
    private final FullName name;
    private final int height;

    // Создание человека с готовым полным именем и ростом.
    public Person(FullName name, int height) {
        if (name == null) throw new IllegalArgumentException("Имя не должно быть null.");
        if (height <= 0) throw new IllegalArgumentException("Рост должен быть положительным.");
        this.name = name;
        this.height = height;
    }

    // Строка с полным именем и ростом человека.
    @Override
    public String toString() {
        return name + ", рост: " + height;
    }
}

// Задания 3.3 и 4.8.
class City {
    private final String name;
    private final Map<City, Integer> routes = new LinkedHashMap<>();

    // Создание города только по названию, пока без путей.
    public City(String name) {
        this(name, Collections.emptyMap());
    }

    // Создание города по названию и готовому набору путей.
    public City(String name, Map<City, Integer> routes) {
        if (name == null || name.isBlank()
                || name.codePoints().anyMatch(Character::isISOControl)) {
            throw new IllegalArgumentException("Название города должно быть непустым текстом.");
        }
        if (routes == null) throw new IllegalArgumentException("Набор путей не должен быть null.");
        this.name = name.strip();
        for (Map.Entry<City, Integer> route : routes.entrySet()) {
            if (route.getValue() == null) {
                throw new IllegalArgumentException("Стоимость не должна быть null.");
            }
            addRoute(route.getKey(), route.getValue());
        }
    }

    // Получение названия города.
    public String getName() {
        return name;
    }

    // Добавление пути в другой город с проверкой стоимости и повторов.
    public void addRoute(City destination, int cost) {
        if (destination == null) throw new IllegalArgumentException("Город назначения не задан.");
        if (destination == this) throw new IllegalArgumentException("Путь в тот же город недопустим.");
        if (cost < 0) throw new IllegalArgumentException("Стоимость не может быть отрицательной.");
        if (routes.containsKey(destination)) throw new IllegalArgumentException("Такой путь уже есть.");
        routes.put(destination, cost);
    }

    // Строка с названием города, его путями и их стоимостью.
    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(", ", name + " -> [", "]");
        for (Map.Entry<City, Integer> route : routes.entrySet()) {
            result.add(route.getKey().name + ":" + route.getValue());
        }
        return result.toString();
    }
}

// Задание 5.5. BigInteger сохраняет точность при больших числах.
class Fraction {
    private final BigInteger numerator;
    private final BigInteger denominator;

    // Создание дроби из двух обычных целых чисел.
    public Fraction(int numerator, int denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    // Создание и сокращение дроби с проверкой знаменателя.
    public Fraction(BigInteger numerator, BigInteger denominator) {
        if (numerator == null || denominator == null) {
            throw new IllegalArgumentException("Числитель и знаменатель должны быть заданы.");
        }
        if (denominator.signum() == 0) throw new IllegalArgumentException("Знаменатель равен нулю.");
        if (denominator.signum() < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
        BigInteger gcd = numerator.gcd(denominator);
        this.numerator = numerator.divide(gcd);
        this.denominator = denominator.divide(gcd);
    }

    // Проверяем, что дробь передали, а не оставили null.
    private static Fraction checked(Fraction value) {
        if (value == null) throw new IllegalArgumentException("Дробь не должна быть null.");
        return value;
    }

    // Сложение двух дробей.
    public Fraction sum(Fraction other) {
        checked(other);
        return new Fraction(numerator.multiply(other.denominator)
                .add(other.numerator.multiply(denominator)), denominator.multiply(other.denominator));
    }

    // Вычитание другой дроби из этой.
    public Fraction minus(Fraction other) {
        checked(other);
        return new Fraction(numerator.multiply(other.denominator)
                .subtract(other.numerator.multiply(denominator)), denominator.multiply(other.denominator));
    }

    // Умножение двух дробей.
    public Fraction multiply(Fraction other) {
        checked(other);
        return new Fraction(numerator.multiply(other.numerator), denominator.multiply(other.denominator));
    }

    // Деление на другую дробь с проверкой деления на ноль.
    public Fraction div(Fraction other) {
        checked(other);
        if (other.numerator.signum() == 0) throw new ArithmeticException("Деление на ноль невозможно.");
        return new Fraction(numerator.multiply(other.denominator), denominator.multiply(other.numerator));
    }

    // Прибавление целого числа к дроби.
    public Fraction sum(int number) { return sum(new Fraction(number, 1)); }

    // Вычитание целого числа из дроби.
    public Fraction minus(int number) { return minus(new Fraction(number, 1)); }

    // Умножение дроби на целое число.
    public Fraction multiply(int number) { return multiply(new Fraction(number, 1)); }

    // Деление дроби на целое число.
    public Fraction div(int number) { return div(new Fraction(number, 1)); }

    // Дробь в виде строки: числитель/знаменатель.
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}
