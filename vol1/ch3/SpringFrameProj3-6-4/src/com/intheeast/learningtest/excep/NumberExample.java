package com.intheeast.learningtest.excep;

public class NumberExample {

	public static void main(String[] args) {
        String number = "1";//"abc";
        try {
            int value = Integer.parseInt(number);
            System.out.println("Parsed value: " + value);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing the number: " + e.getMessage());
        } finally {
            System.out.println("Finally block executed");
        }
    }

}
