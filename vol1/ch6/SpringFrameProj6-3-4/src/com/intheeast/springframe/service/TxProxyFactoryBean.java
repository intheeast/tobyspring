package com.intheeast.springframe.service;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class TxProxyFactoryBean implements FactoryBean<Object> {
	Object target;
	PlatformTransactionManager transactionManager;
	String pattern;
	Class<?> serviceInterface;
	
	public void setTarget(Object target) {
		this.target = target;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setServiceInterface(Class<?> serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	// FactoryBean 인터페이스 구현 메서드
	// 인스턴스를 반환하는 메서드입니다.
	public Object getObject() throws Exception {
		TransactionHandler txHandler = new TransactionHandler();
		txHandler.setTarget(target);
		txHandler.setTransactionManager(transactionManager);
		txHandler.setPattern(pattern);
		//Class<? extends TxProxyFactoryBean> whatclass = this.getClass();
		
		// Create Dynamic Proxy!!!
		return Proxy.newProxyInstance(
			getClass().getClassLoader(), 
			new Class[] { serviceInterface }, 
			txHandler);
	}

	public Class<?> getObjectType() {
		return serviceInterface;
	}

	public boolean isSingleton() {
		return false;
	}
}
