package com.intheeast.learningtest.excep;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RemoteExceptionExample {
	
	public static void main(String[] args) {
        try {
            connectToServer();
        } catch (UnknownHostException e) {
            System.out.println("Caught UnknownHostException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }

    public static void connectToServer() throws UnknownHostException, IOException {
        // 서버에 연결하는 작업    	
    	Socket socket = new Socket("www.example.com", 8080);	
		//Socket socket = new Socket("www.example12345.com", 8080);		
    }

}
