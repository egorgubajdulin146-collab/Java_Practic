package ru.student.labs.lab02;

// Задание 1.2: имя хранится строкой.
public class SimplePerson {
    private final String name;
    private final int height;

    // Создание человека с проверкой имени и роста.
    public SimplePerson(String name, int height) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя должно быть непустым текстом.");
        }
        for (int i = 0; i < name.length(); i++) {
            if (Character.isISOControl(name.charAt(i))) {
                throw new IllegalArgumentException("В имени недопустимы управляющие символы.");
            }
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
