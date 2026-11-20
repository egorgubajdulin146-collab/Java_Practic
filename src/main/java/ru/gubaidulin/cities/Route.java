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
