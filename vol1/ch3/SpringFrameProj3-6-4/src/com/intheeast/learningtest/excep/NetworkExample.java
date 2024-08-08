package com.intheeast.learningtest.excep;

import java.io.IOException;
import java.net.Socket;

public class NetworkExample {

	public static void main(String[] args) {
        Socket socket = null;
        try {
        	// OS 리소스 중, 네트웍 통신에 관련된 리소스[socket] 구조체 있음.
            socket = new Socket("example.com", 8080);
            // 네트워크 통신 수행
        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing the socket: " + e.getMessage());
                }
            }
        }
    }

}
