package com.intheeast.learningtest.spring.factorybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AppConfig {
	
	
    @Bean(name = "myObject"/*"myFactoryBean"*/)
    public MyFactoryBean myFactoryBean() {
        return new MyFactoryBean();
    }
    
    @Bean
    @Lazy
    public MyLazyBean myLazyBean() {
        return new MyLazyBean();
    }

//    @Bean(name = "myObject")
//    public MyObject myObject() throws Exception {
//        return myFactoryBean().getObject();
//    }
	
}