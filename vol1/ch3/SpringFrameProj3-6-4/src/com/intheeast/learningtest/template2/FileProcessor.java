package com.intheeast.learningtest.template2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor {
	private String filePath;

    public FileProcessor(String filePath) {
        this.filePath = filePath;
    }

    public String processFile(FileCallback callback) throws IOException {
        
    	// "hello world" -> String literal
    	// String literal -> immutable!!!
    	//String hello = "hello world";
    	
    	StringBuilder result = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            result.append(callback.doWithLine(line));
        }

        reader.close();
        return result.toString();
    }

}
