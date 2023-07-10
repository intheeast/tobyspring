package com.intheeast.learningtest.jdk.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;


public class DynamicProxyTest {
	@Test
	public void simpleProxy() {
		Hello hello = new HelloTarget();
		assertEquals(hello.sayHello("Toby"), "Hello Toby");
		assertEquals(hello.sayHi("Toby"), "Hi Toby");
		assertEquals(hello.sayThankYou("Toby"), "Thank You Toby");
		
		Hello proxiedHello = (Hello)Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class[] { Hello.class},
				new UppercaseHandler(new HelloTarget()));
		
//		Hello proxiedHello = new HelloUppercase(new HelloTarget());
		assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
		assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
		assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY");
	}
	
	static class HelloUppercase implements Hello {
		Hello hello;
		
		public HelloUppercase(Hello hello) {
			this.hello = hello;
		}

		public String sayHello(String name) {
			return hello.sayHello(name).toUpperCase();
		}

		public String sayHi(String name) {
			return hello.sayHi(name).toUpperCase();
		}

		public String sayThankYou(String name) {
			return hello.sayThankYou(name).toUpperCase();
		}
		
	}
	
	static class UppercaseHandler implements InvocationHandler {
		Object target;

		private UppercaseHandler(Object target) {
			this.target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			Object ret = method.invoke(target, args);
			if (ret instanceof String && method.getName().startsWith("say")) {
				return ((String)ret).toUpperCase();
			}
			else {
				return ret;
			}
		}
	}
	
	/*
	@Test
	public void proxyFactoryBean() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		pfBean.addAdvice(new UppercaseAdvice());

		Hello proxiedHello = (Hello) pfBean.getObject();
		
		assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
		assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
		assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY");
	}
	
	static class UppercaseAdvice implements MethodInterceptor {
		public Object invoke(MethodInvocation invocation) throws Throwable {
			String ret = (String)invocation.proceed();
			return ret.toUpperCase();
		}
	}
	*/

	@Test
	public void proxyFactoryBean() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		pfBean.addAdvice(new UppercaseAdvice());

		Hello proxiedHello = (Hello) pfBean.getObject();
		
		assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
		assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
		assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY");
	}
	
	static class UppercaseAdvice implements MethodInterceptor {
		public Object invoke(MethodInvocation invocation) throws Throwable {
			String ret = (String)invocation.proceed();
			return ret.toUpperCase();
		}
	}
	
	@Test
	public void pointcutAdvisor() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("sayH*"); 
		
		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
		
		Hello proxiedHello = (Hello) pfBean.getObject();
		
		assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
		assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
		assertEquals(proxiedHello.sayThankYou("Toby"), "Thank You Toby"); 
	}
	
	static interface Hello {
		String sayHello(String name);
		String sayHi(String name);
		String sayThankYou(String name);
	}
	
	static class HelloTarget implements Hello {
		public String sayHello(String name) {
			return "Hello " + name;
		}

		public String sayHi(String name) {
			return "Hi " + name;
		}

		public String sayThankYou(String name) {
			return "Thank You " + name;
		}
	}

}
