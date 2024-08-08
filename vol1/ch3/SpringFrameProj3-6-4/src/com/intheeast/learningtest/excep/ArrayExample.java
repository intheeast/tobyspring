package com.intheeast.learningtest.excep;

public class ArrayExample {

	public static void main(String[] args) {
        int[] numbers = {1, 2, 3};
        try {
            for (int i = 0; i <= numbers.length; i++) {
                System.out.println(numbers[i]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index out of bounds: " + e.getMessage());
        } finally {
            System.out.println("Finally block executed");
        }
    }

}
