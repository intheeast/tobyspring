package com.intheeast.learningtest.spring.factorybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanConfig {
	
	// MessageFactoryBean 타입으로
	// message라는 빈 이름으로 사용할 수 없다.
	@Bean
	public MessageFactoryBean message() {
		MessageFactoryBean messageFactoryBean = new MessageFactoryBean();
		messageFactoryBean.setText("Factory Bean");
		return messageFactoryBean;
	}

}
