package com.intheeast.learningtest.excep;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MultipleExceptionsExample {
	
	public static void main(String[] args) {
        try {
            process();
        } catch (IOException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
    }

    public static void process() throws /*FileNotFoundException,*/ IOException {
        FileInputStream file = new FileInputStream("file.txt");
        // 파일에서 데이터 읽어오는 작업
        file.close();
    }

}
