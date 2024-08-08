package com.intheeast.localinnerclass;

public class OuterClass {
	private int outerField;

    public void outerMethod() {
        int localVariable = 10;

        // 지역 내부 클래스 정의 및 인스턴스 생성
        class LocalInnerClass {
            public void innerMethod() {
                outerField = 20; // 외부 클래스의 멤버 접근
                System.out.println("Outer Field: " + outerField);
                System.out.println("Local Variable: " + localVariable);
            }
        }

        LocalInnerClass inner = new LocalInnerClass();
        inner.innerMethod();
    }

    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        outer.outerMethod();
    }

}
