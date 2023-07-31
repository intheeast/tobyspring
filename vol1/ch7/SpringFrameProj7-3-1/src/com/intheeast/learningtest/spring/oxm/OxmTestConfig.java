package com.intheeast.learningtest.spring.oxm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class OxmTestConfig {
//	<bean id="unmarshaller" class="org.springframework.oxm.castor.CastorMarshaller">
//	  <property name="mappingLocation" value="com/intheeast/learningtest/spring/oxm/mapping.xml" />
//  </bean>
	@Bean
	public Jaxb2Marshaller unmarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		//jaxb2Marshaller
		return jaxb2Marshaller;
	}
}
