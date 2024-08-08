package com.intheeast.instanceinnerclass;

public class OuterClass2 {
    private int outerData;
    private InnerClass innerObj;

    public OuterClass2(int outerData) {
        this.outerData = outerData;
    }

    public void outerMethod() {
    	Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello, World!");
            }
        };
        
        Thread thread = new Thread(runnable);
        
        System.out.println("Outer Method");
    }

    public void createInnerObject() {
        innerObj = new InnerClass(20);
        innerObj.innerMethod();
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
        OuterClass2 outerObj = new OuterClass2(10);
        outerObj.createInnerObject();
    }
}

