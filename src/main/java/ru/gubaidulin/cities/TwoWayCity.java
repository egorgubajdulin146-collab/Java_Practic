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
