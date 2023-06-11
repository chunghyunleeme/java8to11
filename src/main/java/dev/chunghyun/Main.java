package dev.chunghyun;

import java.util.function.*;

public class Main {
    public static void main(String[] args) {
        int baseNumber = 10;
        // 익명 내부 클래스
        RunSomething runSomething = number -> number + baseNumber;
        System.out.println(runSomething.doIt(1));

        Plus10 plus10 = new Plus10();
        System.out.println(plus10.apply(2));

        Function<Integer, Integer> function = number ->  number + 10;
        System.out.println(function.apply(3 ));

        Function<Integer, Integer> multiply2 = number -> number * 2;
        System.out.println(multiply2.apply(7));

        Function<Integer, Integer> minus1 = number -> number - 1;
        System.out.println(plus10.compose(multiply2).andThen(minus1).apply(3));

        Consumer<Integer> printT = i -> System.out.println(i);
        printT.accept(16);

        Supplier<Integer> get17 = () -> 17;
        System.out.println(get17.get());

        Predicate<Integer> isEven = number -> (number & 1) == 0;
        System.out.println(isEven.test(18));

        Predicate<String> startsWithChunghyun = s -> s.startsWith("chunghyun");
        System.out.println(startsWithChunghyun.test("ChunghyunLee"));

        UnaryOperator<Integer> unaryOperator = number -> number + 10;
        System.out.println(unaryOperator.apply(8));
    }
}