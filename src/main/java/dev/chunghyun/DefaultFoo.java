package dev.chunghyun;

public class DefaultFoo implements Foo {
    String name;

    public DefaultFoo(String name) {
        this.name = name;
    }
    @Override
    public void printName() {
        System.out.println(this.name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    // 기본 메소드 재정의 가능
    // 충돌하는 경우(Foo, Bar에서 동일한 기본 메소드 제공하는 경우 직접 오버라이딩 해야함)
//    @Override
//    public void printNameUpperCase() {
//        System.out.println(this.name.toUpperCase());
//    }
}
