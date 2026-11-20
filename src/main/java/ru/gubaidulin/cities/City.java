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
