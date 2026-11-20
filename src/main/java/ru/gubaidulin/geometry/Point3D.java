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
