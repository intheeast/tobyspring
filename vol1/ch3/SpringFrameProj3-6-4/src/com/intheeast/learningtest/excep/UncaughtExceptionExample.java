package com.intheeast.learningtest.excep;

public class UncaughtExceptionExample {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		/*
		@FunctionalInterface
    	public interface UncaughtExceptionHandler {
        
        	void uncaughtException(Thread t, Throwable e);
    	}
		*/		
		Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {			
			
            System.out.println("Uncaught exception occurred in thread: " 
		+ thread.getName());
            throwable.printStackTrace();
            // 추가적인 예외 처리 또는 종료 등의 동작을 수행할 수 있습니다.
        });
        
		// 예외가 발생하는 스레드
        Thread thread = new Thread(() -> {
            throw new RuntimeException("Uncaught exception occurred!");
        });         

//      thread.setDefaultUncaughtExceptionHandler(
//		
//		new Thread.UncaughtExceptionHandler() {
//			
//			@Override
//			public void uncaughtException(Thread t, Throwable e) {
//				System.out.println("Uncaught exception occurred in thread: " 
//						+ t.getName());
//				
//				e.printStackTrace();						
//			}
//		}				
//);           
        
        thread.start();
        
        //while(true) {}
    }
}
