package dev.chunghyun;

public interface Bar {
    default void printNameUpperCase() {
        System.out.println("BAR");
    }
}
