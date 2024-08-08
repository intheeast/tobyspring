package com.intheeast.staticinnerclass;

public class OuterClass2 {
	private static int outerData = 10;
    private int instanceData = 20;

    public static void outerMethod() {
        System.out.println("Outer Method");
    }

    public void instanceMethod() {
        System.out.println("Instance Method");
    }

    public static class StaticInnerClass {
        private int innerData;

        public StaticInnerClass(int innerData) {
            this.innerData = innerData;
        }

        public void innerMethod() {
            System.out.println("Inner Method");
            System.out.println("Outer Data: " + outerData);
            outerMethod();

            // 정적 내부 클래스에서는 외부 클래스의 인스턴스 멤버에 직접 접근할 수 없습니다.
            // System.out.println("Instance Data: " + instanceData); // 컴파일 에러

            // 정적 내부 클래스에서는 외부 클래스의 인스턴스 메서드를 직접 호출할 수 없습니다.
            // instanceMethod(); // 컴파일 에러
        }
    }

    public static void main(String[] args) {
        OuterClass2 outerObj = new OuterClass2();
        OuterClass2.StaticInnerClass innerObj = new OuterClass2.StaticInnerClass(30);
        innerObj.innerMethod();
    }

}
