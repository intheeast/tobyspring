package com.intheeast.learningtest.excep;

public class ChainedExceptionExample {
	
	public static void main(String[] args) {
        try {
            divideByZero();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void divideByZero() throws Exception {
        try {
            int result = 5 / 0;
        } catch (ArithmeticException e) {
            throw new Exception("Divide by zero", e);
        }
    }

}
