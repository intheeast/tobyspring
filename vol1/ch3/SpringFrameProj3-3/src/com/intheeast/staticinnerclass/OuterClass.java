package com.intheeast.staticinnerclass;

public class OuterClass {
    private static int outerField;

    public static void outerMethod() {
        // Static Inner 클래스의 인스턴스 생성
        InnerClass inner = new InnerClass();
        inner.innerMethod();
    }

    public static class InnerClass {
        private int innerField;

        public void innerMethod() {
            outerField = 10; // 외부 클래스의 정적 멤버 접근
            innerField = 20; // 내부 클래스의 멤버 접근
            System.out.println("Outer Field: " + outerField);
            System.out.println("Inner Field: " + innerField);
        }
    }

    public static void main(String[] args) {
        // Static Inner 클래스의 인스턴스 생성
        OuterClass.InnerClass inner = new OuterClass.InnerClass();
        inner.innerMethod();
        
        OuterClass.outerMethod();        
    }
}