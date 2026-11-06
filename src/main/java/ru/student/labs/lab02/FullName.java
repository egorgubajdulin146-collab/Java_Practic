package ru.student.labs.lab02;

// Задание 1.3. Порядок аргументов: фамилия, личное имя, отчество.
public class FullName {
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
        for (int i = 0; i < value.length(); i++) {
            if (Character.isISOControl(value.charAt(i))) {
                throw new IllegalArgumentException("В имени недопустимы управляющие символы.");
            }
        }
        return value.strip();
    }

    // Собираем имя в одну строку, пропуская незаполненные части.
    @Override
    public String toString() {
        String result = surname;
        if (!firstName.isEmpty()) {
            if (!result.isEmpty()) result += " ";
            result += firstName;
        }
        if (!patronymic.isEmpty()) {
            if (!result.isEmpty()) result += " ";
            result += patronymic;
        }
        return result;
    }
}
