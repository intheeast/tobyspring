package com.intheeast.instanceinnerclass;

public class OuterClass {
    private int outerData;

    public OuterClass(int outerData) {
        this.outerData = outerData;
    }

    public void outerMethod() {
        System.out.println("Outer Method");
    }

    public class InnerClass {
        private int innerData;

        public InnerClass(int innerData) {
            this.innerData = innerData;
        }

        public void innerMethod() {
            System.out.println("Inner Method");
            System.out.println("Outer Data: " + outerData);
            outerMethod();
        }
    }

    public static void main(String[] args) {
        OuterClass outerObj = new OuterClass(10);
        OuterClass.InnerClass innerObj = outerObj.new InnerClass(20);
        innerObj.innerMethod();
    }
}
