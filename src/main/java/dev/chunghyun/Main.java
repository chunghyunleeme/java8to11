package dev.chunghyun;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Main main = new Main();
        main.run();

        Function<String, Greeting> chunghyunGreeting = Greeting::new;
        Greeting chunghyun = chunghyunGreeting.apply("chunghyun");
        System.out.println(chunghyun.getName());

        Supplier<Greeting> newGreeting = Greeting::new;
        newGreeting.get();

        Greeting greeting = new Greeting();
        UnaryOperator<String> hello = greeting::hello;
        hello.apply("chunghyun");

        UnaryOperator<String> hi = Greeting::hi;
        hi.apply("chunghyun");

        String[] names = {"neo", "chunghyun", "dev"};
        Arrays.sort(names, String::compareToIgnoreCase);
        System.out.println(Arrays.toString(names));


        Foo foo = new DefaultFoo("chunghyun");
        foo.printName();
        foo.printNameUpperCase();

        Foo.printAnyThing();


        List<String> nameList = new ArrayList<>();
        nameList.add("chunghyun");
        nameList.add("neo");
        nameList.add("sam");
        nameList.add("marco");

        nameList.forEach(System.out::println);

        // Spliterator는 쪼갤 수 있는 기능을 가지고 있는 Iterator
        Spliterator<String> spliterator = nameList.spliterator();
        Spliterator<String> spliterator1 = spliterator.trySplit();
        while (spliterator.tryAdvance(System.out::println));
        System.out.println("----------");
        while (spliterator1.tryAdvance(System.out::println));


        long c = nameList.stream().map(String::toUpperCase)
                .filter(s -> s.startsWith("C"))
                .count();
        System.out.println("count of startsWith C: " + c);

        nameList.stream().map(String::toUpperCase)
                .filter(s -> s.startsWith("C"))
                .collect(Collectors.toSet());

        System.out.println("==========");
        nameList.removeIf(s -> s.startsWith("c"));
        nameList.forEach(System.out::println);

        System.out.println("==========");
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        nameList.sort(compareToIgnoreCase.reversed());
        nameList.forEach(System.out::println);


        System.out.println("==========");
        List<String> collect = nameList.stream().map(String::toUpperCase)
                .collect(Collectors.toList());
        collect.forEach(System.out::println);

        for(String name: nameList) {
            if(name.startsWith("c")) {
                System.out.println(name);
            }
        }

        // parallelStream을 사용하면 내부적으로 spliterator를 사용하여 병렬적으로 순회
        // 다중 스레드로 처리 -> 무조건 좋은건 아님(ex. 컨텍스트 스위칭) 데이터가 방대할 경우에는 유리
        collect = nameList.parallelStream().map(s -> {
            System.out.println(s + " " + Thread.currentThread().getName());
            return s.toUpperCase();
        }).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    private void run() {
        int baseNumber = 10;

        // 로컬 클래스
        class LocalClass {
            void printBaseNumber() {
                int baseNumber = 19;
                System.out.println(baseNumber); // 19
            }
        }
        LocalClass localClass = new LocalClass();
        localClass.printBaseNumber();

        // 익명 클래스
        IntConsumer intConsumer = new IntConsumer() {
            @Override
            public void accept(int baseNumber) {
                System.out.println(baseNumber); // 20
            }
        };
        intConsumer.accept(20);

        IntConsumer printInt = i -> System.out.println(i + baseNumber);
        printInt.accept(11);
    }
}