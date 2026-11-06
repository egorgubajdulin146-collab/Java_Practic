package ru.student.labs.lab02;

// Задание 2.2: имя хранится объектом FullName.
public class Person {
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
