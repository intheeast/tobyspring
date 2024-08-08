package com.intheeast.learningtest.excep;

public class TryCatchFinallyExample {

	public static void main(String[] args) {
        try {
            int result = divide(10, 0);
            
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        } finally {
            System.out.println("Finally block executed");
        }
    }

    public static int divide(int dividend, int divisor) {
        return dividend / divisor;
    }

}
