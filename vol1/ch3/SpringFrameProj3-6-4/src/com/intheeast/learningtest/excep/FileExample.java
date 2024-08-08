package com.intheeast.learningtest.excep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileExample {

	public static void main(String[] args) {
        BufferedReader reader = null;
        try {
        	//src/main/resources/example.txt
            reader = new BufferedReader(new FileReader("src/main/resources/example.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing the file: " + e.getMessage());
            }
        }
    }

}
