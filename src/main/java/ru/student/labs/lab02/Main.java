package ru.student.labs.lab02;

public class Main {
    public static void main(String[] args) {
        Main lab = new Main();
        lab.table();
    }

    public void table() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                System.out.printf("%d * %d = %d\t", i, j, i * j);
            }
            System.out.println();
        }
    }
}
