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
