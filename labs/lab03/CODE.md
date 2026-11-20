# Лабораторная работа №3. Полный код варианта №2

## `src/main/java/ru/gubaidulin/cities/City.java`

```java
package ru.gubaidulin.cities;

import java.util.Arrays;

public class City {
    private final String name;
    private City[] destinations = new City[0];
    private int[] costs = new int[0];

    // Создаёт город без дорог.
    public City(String name) {
        this(name, new City[0], new int[0]);
    }

    // Создаёт город с указанными дорогами.
    public City(String name, City[] destinations, int[] costs) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Введите название города.");
        }
        if (destinations == null || costs == null || destinations.length != costs.length) {
            throw new IllegalArgumentException("Для каждой дороги укажите город и стоимость.");
        }
        this.name = name.strip();
        for (int i = 0; i < destinations.length; i++) {
            addOneWayRoute(destinations[i], costs[i]);
        }
    }

    // Возвращает название города.
    public String getName() {
        return name;
    }

    // Возвращает копию массива соседей.
    public City[] getDestinations() {
        return destinations.clone();
    }

    // Ищет индекс дороги в указанный город.
    private int findRoute(City destination) {
        for (int i = 0; i < destinations.length; i++) {
            if (destinations[i] == destination) {
                return i;
            }
        }
        return -1;
    }

    // Проверяет, есть ли дорога в указанный город.
    boolean hasRoute(City destination) {
        return findRoute(destination) >= 0;
    }

    // Добавляет исходящую дорогу.
    public void addRoute(City destination, int cost) {
        addOneWayRoute(destination, cost);
    }

    // Записывает дорогу в одном направлении.
    final void addOneWayRoute(City destination, int cost) {
        if (destination == null || destination == this) {
            throw new IllegalArgumentException("Выберите другой город назначения.");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("Введите неотрицательную стоимость.");
        }
        if (findRoute(destination) >= 0) {
            throw new IllegalArgumentException("Между этими городами уже есть дорога в этом направлении.");
        }
        int size = destinations.length;
        destinations = Arrays.copyOf(destinations, size + 1);
        costs = Arrays.copyOf(costs, size + 1);
        destinations[size] = destination;
        costs[size] = cost;
    }

    // Удаляет дорогу, если она существует.
    public boolean removeRoute(City destination) {
        int index = findRoute(destination);
        if (index < 0) {
            return false;
        }
        for (int i = index; i < destinations.length - 1; i++) {
            destinations[i] = destinations[i + 1];
            costs[i] = costs[i + 1];
        }
        destinations = Arrays.copyOf(destinations, destinations.length - 1);
        costs = Arrays.copyOf(costs, costs.length - 1);
        return true;
    }

    // Сравнивает наборы дорог с учётом города назначения и стоимости.
    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof City other) || destinations.length != other.destinations.length) {
            return false;
        }
        for (int i = 0; i < destinations.length; i++) {
            int index = other.findRoute(destinations[i]);
            if (index < 0 || costs[i] != other.costs[index]) {
                return false;
            }
        }
        return true;
    }

    // Вычисляет хеш дорог независимо от их порядка.
    @Override
    public final int hashCode() {
        int result = 0;
        for (int i = 0; i < destinations.length; i++) {
            result += 31 * System.identityHashCode(destinations[i]) + costs[i];
        }
        return result;
    }

    // Выводит название города, соседей и стоимость дорог.
    @Override
    public String toString() {
        String result = name + " -> [";
        for (int i = 0; i < destinations.length; i++) {
            if (i > 0) {
                result += ", ";
            }
            result += destinations[i].name + ":" + costs[i];
        }
        return result + "]";
    }
}
```

## `src/main/java/ru/gubaidulin/cities/Route.java`

```java
package ru.gubaidulin.cities;

import java.util.Arrays;

public class Route {
    private City start;
    private City end;

    // Создаёт маршрут между двумя городами.
    public Route(City start, City end) {
        setStart(start);
        setEnd(end);
    }

    // Меняет начальный город.
    public void setStart(City start) {
        if (start == null) {
            throw new IllegalArgumentException("Укажите начальный город.");
        }
        this.start = start;
    }

    // Меняет конечный город.
    public void setEnd(City end) {
        if (end == null) {
            throw new IllegalArgumentException("Укажите конечный город.");
        }
        this.end = end;
    }

    // Ищет путь с наименьшим количеством дорог обходом в ширину.
    public City[] getCities() {
        City[] queue = new City[8];
        int[] previous = new int[8];
        queue[0] = start;
        previous[0] = -1;
        int size = 1;
        for (int current = 0; current < size; current++) {
            if (queue[current] == end) {
                return buildPath(queue, previous, current);
            }
            for (City neighbor : queue[current].getDestinations()) {
                if (!wasVisited(queue, size, neighbor)) {
                    if (size == queue.length) {
                        queue = Arrays.copyOf(queue, size * 2);
                        previous = Arrays.copyOf(previous, size * 2);
                    }
                    queue[size] = neighbor;
                    previous[size] = current;
                    size++;
                }
            }
        }
        return new City[0];
    }

    // Проверяет, был ли город уже добавлен в очередь.
    private boolean wasVisited(City[] queue, int size, City city) {
        for (int i = 0; i < size; i++) {
            if (queue[i] == city) {
                return true;
            }
        }
        return false;
    }

    // Восстанавливает города пути по индексам предшественников.
    private City[] buildPath(City[] queue, int[] previous, int last) {
        int length = 0;
        for (int i = last; i >= 0; i = previous[i]) {
            length++;
        }
        City[] path = new City[length];
        for (int i = length - 1; i >= 0; i--) {
            path[i] = queue[last];
            last = previous[last];
        }
        return path;
    }

    // Выводит названия городов в порядке маршрута.
    @Override
    public String toString() {
        City[] path = getCities();
        if (path.length == 0) {
            return "Маршрут не найден.";
        }
        String result = path[0].getName();
        for (int i = 1; i < path.length; i++) {
            result += " -> " + path[i].getName();
        }
        return result;
    }
}
```

## `src/main/java/ru/gubaidulin/cities/TwoWayCity.java`

```java
package ru.gubaidulin.cities;

public class TwoWayCity extends City {
    // Создаёт город для двусторонних дорог.
    public TwoWayCity(String name) {
        super(name);
    }

    // Добавляет прямую и обратную дороги.
    @Override
    public void addRoute(City destination, int cost) {
        if (destination != null && destination.hasRoute(this)) {
            throw new IllegalArgumentException("Обратная дорога уже существует. Сначала удалите её.");
        }
        super.addRoute(destination, cost);
        destination.addOneWayRoute(this, cost);
    }
}
```

## `src/main/java/ru/gubaidulin/geometry/Point.java`

```java
package ru.gubaidulin.geometry;

public class Point implements Cloneable {
    private final int x;
    private final int y;

    // Сохраняет две координаты.
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Возвращает координату X.
    public int getX() {
        return x;
    }

    // Возвращает координату Y.
    public int getY() {
        return y;
    }

    // Сравнивает координаты точек одного вида.
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Point other = (Point) object;
        return x == other.x && y == other.y;
    }

    // Вычисляет хеш по координатам X и Y.
    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    // Создаёт копию точки с сохранением её класса.
    @Override
    public Point clone() {
        try {
            return (Point) super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError(exception);
        }
    }

    // Возвращает две координаты в фигурных скобках.
    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }
}
```

## `src/main/java/ru/gubaidulin/geometry/Point3D.java`

```java
package ru.gubaidulin.geometry;

public class Point3D extends Point {
    private final int z;

    // Сохраняет координаты X, Y и Z.
    public Point3D(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    // Сравнивает все три координаты.
    @Override
    public boolean equals(Object object) {
        return super.equals(object) && z == ((Point3D) object).z;
    }

    // Учитывает Z при вычислении хеша.
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + z;
    }

    // Возвращает три координаты в фигурных скобках.
    @Override
    public String toString() {
        return "{" + getX() + "," + getY() + "," + z + "}";
    }
}
```

## `src/main/java/ru/gubaidulin/main/Main.java`

```java
package ru.gubaidulin.main;

import java.util.Scanner;
import ru.gubaidulin.cities.City;
import ru.gubaidulin.cities.Route;
import ru.gubaidulin.cities.TwoWayCity;
import ru.gubaidulin.geometry.Point;
import ru.gubaidulin.geometry.Point3D;
import ru.gubaidulin.numbers.Fraction;

public class Main {
    private final Scanner scanner;

    // Сохраняет сканер для чтения с клавиатуры.
    public Main(Scanner scanner) {
        this.scanner = scanner;
    }

    // Запускает меню или вычисляет степень по аргументам.
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                System.out.println("Результат: " + NumberOperations.power(args[0], args[1]));
                return;
            } catch (NumberFormatException exception) {
                System.out.println("Для степени нужны два целых числа. Повторите ввод в меню 9.");
            }
        } else if (args.length != 0) {
            System.out.println("Укажите два целых числа или запустите программу без аргументов.");
        }

        try (Scanner scanner = new Scanner(System.in)) {
            new Main(scanner).runMenu();
        }
    }

    // Показывает меню, пока пользователь не выберет выход.
    private void runMenu() {
        while (true) {
            System.out.println(this);
            switch (readInt("Выберите пункт: ", 0, 9)) {
                case 0 -> { return; }
                case 1 -> showFraction();
                case 2 -> showRoads();
                case 3 -> showRoute();
                case 4 -> showPoint3D();
                case 5 -> showSum();
                case 6 -> showCityComparison();
                case 7 -> showPackages();
                case 8 -> showClone();
                case 9 -> showPower();
            }
        }
    }

    // Возвращает список пунктов меню.
    @Override
    public String toString() {
        return """
                Лабораторная работа №3. Вариант 2
                1. Дробь, запрет наследования и Number (2.1.4, 2.3.1, 2.4.2)
                2. Дороги (2.1.10)
                3. Маршрут (2.2.5)
                4. Трёхмерная точка (2.3.5)
                5. Сложение (2.5.1)
                6. Сравнение городов (2.6.5)
                7. Пакеты и главный метод (2.7.1, 2.7.2)
                8. Клонирование точки (2.8.4)
                9. Возведение в степень (2.7.3)
                0. Выход""";
    }

    // Показывает нормализацию знака и преобразования Number.
    private void showFraction() {
        System.out.println("Пример: 3/(-5) = " + new Fraction(3, -5));
        if (readManualChoice()) {
            Fraction fraction = readFraction();
            System.out.println("Дробь: " + fraction);
            System.out.println("int: " + fraction.intValue() + ", long: " + fraction.longValue());
            System.out.println("float: " + fraction.floatValue() + ", double: " + fraction.doubleValue());
        } else {
            System.out.println("Как Number: " + new Fraction(3, -5).doubleValue());
        }
    }

    // Показывает создание, добавление и удаление дорог.
    private void showRoads() {
        City b = new City("B");
        City a = new City("A", new City[]{b}, new int[]{5});
        System.out.println(a);
        try {
            a.addRoute(b, 9);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
        a.removeRoute(b);
        System.out.println("После удаления: " + a);
        if (readManualChoice()) {
            City start = new City(readText("Название первого города: "));
            City end = new City(readText("Название второго города: "));
            start.addRoute(end, readInt("Стоимость: ", 0, Integer.MAX_VALUE));
            System.out.println(start);
            if (readInt("Удалить дорогу? 1 — да, 0 — нет: ", 0, 1) == 1) {
                start.removeRoute(end);
                System.out.println(start);
            }
        }
    }

    // Показывает маршрут F — D и поиск на введённой карте.
    private void showRoute() {
        City[] example = createExampleMap();
        Route route = new Route(example[5], example[3]);
        System.out.println("Пример из задания: " + route);
        if (readManualChoice()) {
            City[] cities = readMap();
            route.setStart(readCity(cities, "Номер начального города: "));
            route.setEnd(readCity(cities, "Номер конечного города: "));
            System.out.println(route);
        }
    }

    // Создаёт карту из задачи 1.3.3 предыдущей работы.
    private City[] createExampleMap() {
        City a = new City("A");
        City b = new City("B");
        City c = new City("C");
        City d = new City("D");
        City e = new City("E");
        City f = new City("F");
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
        f.addRoute(b, 1);
        f.addRoute(e, 2);
        return new City[]{a, b, c, d, e, f};
    }

    // Читает города и направленные дороги между ними.
    private City[] readMap() {
        int size = readInt("Сколько городов (1–100): ", 1, 100);
        City[] cities = new City[size];
        for (int i = 0; i < size; i++) {
            cities[i] = new City(readText("Название города " + (i + 1) + ": "));
        }
        int count = readInt("Сколько дорог: ", 0, size * (size - 1));
        for (int i = 0; i < count;) {
            System.out.println("Дорога " + (i + 1));
            City from = readCity(cities, "Откуда (номер): ");
            City to = readCity(cities, "Куда (номер): ");
            int cost = readInt("Стоимость: ", 0, Integer.MAX_VALUE);
            try {
                from.addRoute(to, cost);
                i++;
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage() + " Повторите ввод дороги.");
            }
        }
        return cities;
    }

    // Выбирает город по номеру из массива.
    private City readCity(City[] cities, String prompt) {
        return cities[readInt(prompt, 1, cities.length) - 1];
    }

    // Показывает наследование координат у трёхмерной точки.
    private void showPoint3D() {
        Point point = new Point3D(4, 2, 5);
        System.out.println("Пример: " + point);
        if (readManualChoice()) {
            int x = readInt("X: ");
            int y = readInt("Y: ");
            int z = readInt("Z: ");
            System.out.println(new Point3D(x, y, z));
        }
    }

    // Вычисляет три суммы из PDF и сумму введённых значений.
    private void showSum() {
        System.out.println("2 + 3/5 + 2.3 = " + NumberOperations.sum(2, new Fraction(3, 5), 2.3));
        System.out.println("3.6 + 49/12 + 3 + 3/2 = "
                + NumberOperations.sum(3.6, new Fraction(49, 12), 3, new Fraction(3, 2)));
        System.out.println("1/3 + 1 = " + NumberOperations.sum(new Fraction(1, 3), 1));
        if (readManualChoice()) {
            Number[] numbers = new Number[readInt("Количество слагаемых (1–100): ", 1, 100)];
            for (int i = 0; i < numbers.length; i++) {
                System.out.println("Слагаемое " + (i + 1));
                switch (readInt("1 — целое, 2 — вещественное, 3 — дробь: ", 1, 3)) {
                    case 1 -> numbers[i] = readInt("Введите целое число: ");
                    case 2 -> numbers[i] = readDouble();
                    case 3 -> numbers[i] = readFraction();
                }
            }
            System.out.println("Сумма: " + NumberOperations.sum(numbers));
        }
    }

    // Сравнивает обычный город с городом, создающим обратную дорогу.
    private void showCityComparison() {
        compareCities(5, 5);
        if (readManualChoice()) {
            int first = readInt("Стоимость A → C: ", 0, Integer.MAX_VALUE);
            int second = readInt("Стоимость B → C: ", 0, Integer.MAX_VALUE);
            compareCities(first, second);
        }
    }

    // Создаёт два города с дорогами и выводит результат equals.
    private void compareCities(int firstCost, int secondCost) {
        City a = new City("A");
        City b = new TwoWayCity("B");
        City c = new City("C");
        a.addRoute(c, firstCost);
        b.addRoute(c, secondCost);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println("A.equals(B): " + a.equals(b));
        System.out.println("B.equals(A): " + b.equals(a));
    }

    // Показывает расположение классов третьей лабораторной.
    private void showPackages() {
        System.out.println("ru.gubaidulin.cities — города и маршруты");
        System.out.println("ru.gubaidulin.geometry — точки");
        System.out.println("ru.gubaidulin.numbers — дроби");
        System.out.println("ru.gubaidulin.main — Main и NumberOperations");
        System.out.println("Сумма из NumberOperations: " + NumberOperations.sum(1, new Fraction(1, 2)));
    }

    // Клонирует пример точки или точку с введёнными координатами.
    private void showClone() {
        printClone(new Point(2, 5));
        if (readManualChoice()) {
            printClone(new Point(readInt("X: "), readInt("Y: ")));
        }
    }

    // Проверяет координаты копии и выводит результат.
    private void printClone(Point original) {
        Point copy = original.clone();
        System.out.println("Оригинал: " + original + ", копия: " + copy);
        System.out.println("Координаты совпадают: " + original.equals(copy));
        System.out.println("Это разные объекты: " + (original != copy));
    }

    // Показывает пример и вычисляет степень чисел из консоли.
    private void showPower() {
        System.out.println("Пример: 2^3 = " + NumberOperations.power("2", "3"));
        if (readManualChoice()) {
            int base = readInt("Основание: ");
            int exponent = readInt("Показатель: ");
            System.out.println("Результат: " + NumberOperations.power("" + base, "" + exponent));
        }
    }

    // Читает дробь и повторяет запрос нулевого знаменателя.
    private Fraction readFraction() {
        int numerator = readInt("Числитель: ");
        int denominator = readInt("Знаменатель: ");
        while (denominator == 0) {
            System.out.println("Введите знаменатель, отличный от нуля.");
            denominator = readInt("Знаменатель: ");
        }
        return new Fraction(numerator, denominator);
    }

    // Спрашивает, нужен ли ручной ввод после примера.
    private boolean readManualChoice() {
        return readInt("Ручной ввод? 1 — да, 0 — нет: ", 0, 1) == 1;
    }

    // Читает непустую строку.
    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = scanner.nextLine().strip();
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("Введите непустую строку.");
        }
    }

    // Читает целое число из полного диапазона int.
    private int readInt(String prompt) {
        return readInt(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    // Читает целое число и проверяет его диапазон.
    private int readInt(String prompt, int min, int max) {
        while (true) {
            String text = readText(prompt);
            try {
                int value = Integer.parseInt(text);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException exception) {
                // Общая подсказка ниже подходит и для ошибки формата.
            }
            System.out.println("Введите целое число от " + min + " до " + max + ".");
        }
    }

    // Читает вещественное число с точкой или запятой.
    private double readDouble() {
        while (true) {
            String text = readText("Введите вещественное число: ").replace(',', '.');
            try {
                double number = Double.parseDouble(text);
                if (Double.isFinite(number)) {
                    return number;
                }
            } catch (NumberFormatException exception) {
                // После сообщения программа снова запрашивает число.
            }
            System.out.println("Введите число, например 2,3.");
        }
    }
}
```

## `src/main/java/ru/gubaidulin/main/NumberOperations.java`

```java
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
```

## `src/main/java/ru/gubaidulin/numbers/Fraction.java`

```java
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
```
