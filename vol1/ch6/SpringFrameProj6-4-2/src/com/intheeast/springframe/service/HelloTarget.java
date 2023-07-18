package com.intheeast.springframe.service;

public class HelloTarget implements Hello {

	@Override
	public String sayHello(String name) {
		System.out.println("Hello " + name);
		return "Hello " + name;
	}

	@Override
	public String sayHi(String name) {		
		System.out.println("Hi " + name);
		return "Hi " + name;
	}

	@Override
	public String sayThanks(String name) {
		System.out.println("Thanks " + name);
		return "Thanks " + name;
	}

}
