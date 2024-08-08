package com.intheeast.springframe.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class MyInvocationHandler implements InvocationHandler {
    private final Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before method: " + method.getName());

        // 실제 대상 객체의 메서드 호출
        Object result = method.invoke(target, args);

        System.out.println("After method: " + method.getName());
        return result;
    }
}