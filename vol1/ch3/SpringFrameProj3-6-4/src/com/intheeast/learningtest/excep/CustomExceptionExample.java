package com.intheeast.learningtest.excep;



public class CustomExceptionExample {
	
//	static class CustomException extends Exception {
//    public CustomException(String message) {
//        super(message);
//    }
//}

	public static void main(String[] args) {
        try {
            int result = divide(10, 0);
            System.out.println("Result: " + result);
        } catch (CustomException e) {
            System.out.println("Caught CustomException: " + e.getMessage());
        } finally {
            System.out.println("Finally block executed");
        }
    }

    public static int divide(int dividend, int divisor) throws CustomException {
        if (divisor == 0) {
            throw new CustomException("Divisor cannot be zero");
        }
        return dividend / divisor;
    }

}
