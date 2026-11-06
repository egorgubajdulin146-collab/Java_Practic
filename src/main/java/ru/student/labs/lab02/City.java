package ru.student.labs.lab02;

// Задания 3.3 и 4.8: город и пути в другие города.
public class City {
    private final String name;
    private City[] destinations;
    private int[] costs;

    // Создание города только по названию.
    public City(String name) {
        this(name, new City[0], new int[0]);
    }

    // Создание города с готовыми массивами соседей и стоимостей.
    public City(String name, City[] destinations, int[] costs) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название города не должно быть пустым.");
        }
        for (int i = 0; i < name.length(); i++) {
            if (Character.isISOControl(name.charAt(i))) {
                throw new IllegalArgumentException("В названии недопустимы управляющие символы.");
            }
        }
        if (destinations == null || costs == null || destinations.length != costs.length) {
            throw new IllegalArgumentException("Массивы городов и стоимостей должны быть заданы и иметь одинаковую длину.");
        }
        this.name = name.strip();
        this.destinations = new City[0];
        this.costs = new int[0];
        for (int i = 0; i < destinations.length; i++) {
            addRoute(destinations[i], costs[i]);
        }
    }

    // Получение названия города.
    public String getName() {
        return name;
    }

    // Проверка, есть ли уже путь в этот город.
    public boolean hasRoute(City destination) {
        for (int i = 0; i < destinations.length; i++) {
            if (destinations[i] == destination) return true;
        }
        return false;
    }

    // Добавление пути в другой город.
    public void addRoute(City destination, int cost) {
        if (destination == null) throw new IllegalArgumentException("Город назначения не задан.");
        if (destination == this) throw new IllegalArgumentException("Путь в тот же город недопустим.");
        if (cost < 0) throw new IllegalArgumentException("Стоимость не может быть отрицательной.");
        if (hasRoute(destination)) throw new IllegalArgumentException("Такой путь уже есть.");

        int size = destinations.length;
        City[] newDestinations = new City[size + 1];
        int[] newCosts = new int[size + 1];
        for (int i = 0; i < size; i++) {
            newDestinations[i] = destinations[i];
            newCosts[i] = costs[i];
        }
        newDestinations[size] = destination;
        newCosts[size] = cost;
        destinations = newDestinations;
        costs = newCosts;
    }

    // Строка с названием города, его соседями и стоимостью путей.
    @Override
    public String toString() {
        String result = name + " -> [";
        for (int i = 0; i < destinations.length; i++) {
            if (i > 0) result += ", ";
            result += destinations[i].name + ":" + costs[i];
        }
        return result + "]";
    }
}
