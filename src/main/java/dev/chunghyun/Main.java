package dev.chunghyun;

import java.time.Duration;
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


        System.out.println("==========");
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add((new OnlineClass(3, "spring mvc", false)));
        springClasses.add((new OnlineClass(4, "spring core", false)));
        springClasses.add((new OnlineClass(5, "rest api developer", false)));


        System.out.println("spring 으로 시작하는 수업");
        springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .forEach(oc -> System.out.println(oc.getId()));

        System.out.println("close 되지 않은 수업");
        springClasses.stream()
                .filter(Predicate.not(OnlineClass::isClosed))
                .forEach(oc -> System.out.println(oc.getId()));

        System.out.println("수업 이름만 모아서 스트림 만들기");
        springClasses.stream()
                .map(OnlineClass::getTitle)
                .forEach(System.out::println);

        List<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "The Java, Test", true));
        javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
        javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

        List<List<OnlineClass>> chunghyunEvents = new ArrayList<>();
        chunghyunEvents.add(springClasses);
        chunghyunEvents.add(javaClasses);

        System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
        chunghyunEvents.stream()
                .flatMap(Collection::stream)
                .forEach(oc -> System.out.println(oc.getId()));

        System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
        Stream.iterate(10, i -> i + 1)
                .skip(10)
                .limit(10)
                .forEach(System.out::println);

        System.out.println("자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
        boolean test = javaClasses.stream().anyMatch(oc -> oc.getTitle().contains("Test"));
        System.out.println(test);

        System.out.println("스프링 수업 중에 제목에 spring이 들어간 제목만 모아서 List로 만들기");
        List<String> spring = springClasses.stream()
                .filter(oc -> {
                    System.out.println("filter: " + oc.getTitle());
                    return oc.getTitle().contains("spring");
                })
                .map(oc -> {
                    System.out.println("map: " + oc.getTitle());
                    return oc.getTitle();
                })
                .collect(Collectors.toList());
        spring.forEach(System.out::println);


        System.out.println("==========");
        OnlineClass spring_boot = new OnlineClass(1, "spring boot", true);
        Optional<Progress> progress = spring_boot.getProgress();
        progress.ifPresent(p -> System.out.println(p.getStudyDuration()));

        Optional<OnlineClass> optional = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

        System.out.println(optional.isPresent());
        System.out.println(optional.isEmpty());
        // get을 바로 사용하면 값이 없을 때, NoSuchElementException 발생
        if(optional.isPresent()) {
            System.out.println(optional.get().getTitle());
        }

        // 아래와 같이 될 수 있으면 get을 사용 안하는게 더 나음
        optional.ifPresent(oc -> System.out.println(oc.getTitle()));

        OnlineClass onlineClass = optional.orElse(createNewClass());
        System.out.println(onlineClass.getTitle());

        // 없으면 동적으로 무슨 작업을 해야한다면 orElseGet이 적절해 보임 -> orElse는 있어도 일단 실행은 됨(가져오지만 않을 뿐);
        OnlineClass onlineClass1 = optional.orElseGet(Main::createNewClass);
        System.out.println(onlineClass1.getTitle());


//        OnlineClass onlineClass2 = optional.orElseThrow(IllegalStateException::new);

        System.out.println(optional.filter(OnlineClass::isClosed).isPresent());

        // Optional 안에 들어있는 인스턴스가 optional일때 flatMap
        optional.flatMap(OnlineClass::getProgress);
    }

    private static OnlineClass createNewClass() {
        System.out.println("creating new online class");
        return new OnlineClass(10, "New Class", false);
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