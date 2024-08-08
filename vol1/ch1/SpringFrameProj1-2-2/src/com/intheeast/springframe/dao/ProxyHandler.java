package com.intheeast.springframe.dao;

import java.lang.reflect.Proxy;

public class ProxyHandler {
	public static void main(String[] args) {
        MyInterface realObject = new MyInterfaceImpl();
        MyInterface proxyInstance = (MyInterface) Proxy.newProxyInstance(
            MyInterface.class.getClassLoader(),//
            new Class[] { MyInterface.class },
            new MyInvocationHandler(realObject)
        );

        proxyInstance.doSomething();
    }

}
