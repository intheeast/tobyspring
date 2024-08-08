package com.intheeast.learningtest.spring.factorybean;

public class MyLazyBean {

   public MyLazyBean() {
       System.out.println("MyLazyBean 생성자 호출");
   }

   public void doSomething() {
       System.out.println("메소드 doSomething 실행");
   }
}