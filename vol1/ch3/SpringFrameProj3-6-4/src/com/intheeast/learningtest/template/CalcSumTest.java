package com.intheeast.learningtest.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalcSumTest {
	Calculator calculator;
	String numFilepath;
	
	@BeforeEach
	public void setUp() {
		this.calculator = new Calculator();
		String getClass = getClass().toString();
		System.out.println("setUp:" + getClass);
		this.numFilepath = getClass().getResource("src/main/resources/numbers.txt").getPath();
		//System.out.println("setUp:numFilepath" + this.numFilepath);
	}
	
	@Test public void sumOfNumbers() throws IOException {
		assertEquals(calculator.calcSum(this.numFilepath), 10);
	}
	
	@Test public void multiplyOfNumbers() throws IOException {
		assertEquals(calculator.calcMultiply(this.numFilepath), 24);
	}
	
	@Test public void concatenateStrings() throws IOException {
		assertEquals(calculator.concatenate(this.numFilepath), "1234");
	}


}
