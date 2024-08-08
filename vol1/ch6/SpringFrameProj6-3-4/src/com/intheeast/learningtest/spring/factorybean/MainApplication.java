package com.intheeast.learningtest.spring.factorybean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("스프링 컨테이너 초기화 완료");
        
        MyObject myObject = 
        		context.getBean(MyObject.class, "myObject");        

        MyLazyBean bean = context.getBean(MyLazyBean.class);
        bean.doSomething();
        
        context.close();
    }
}