package com.intheeast.learningtest.excep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RethrowExceptionExample {
	public static void main(String[] args) {
        try {
            processFile("file.txt");
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }

    public static void processFile(String fileName) throws IOException {
        try {
        	BufferedReader reader = 
        			new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // 예외를 처리하지 않고 다시 던짐
            throw e;
        }        
    }
}
