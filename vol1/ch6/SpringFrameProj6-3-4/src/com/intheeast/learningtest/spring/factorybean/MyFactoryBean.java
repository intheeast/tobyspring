package com.intheeast.learningtest.spring.factorybean;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<MyObject> {

    @Override
    public MyObject getObject() throws Exception {
        // 객체 생성 및 초기화 로직
        MyObject obj = new MyObject();
        // ...
        return obj;
    }

    @Override
    public Class<?> getObjectType() {
        return MyObject.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}