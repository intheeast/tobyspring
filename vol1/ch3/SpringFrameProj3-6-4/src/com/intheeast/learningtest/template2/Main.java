package com.intheeast.learningtest.template2;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
        FileProcessor processor = new FileProcessor("src/main/resources/data.txt");

        // 파일 내용을 모두 소문자로 변환하여 반환하는 콜백 메서드 구현
        String result = processor.processFile(new FileCallback() {
            @Override
            public String doWithLine(String line) {
                return line.toLowerCase() + "\n";
            }
        });

        System.out.println(result);
    }

}
