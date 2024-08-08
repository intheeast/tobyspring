package com.intheeast.springframe.domain;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionExample {
	public static void main(String[] args) {
        try {
            // Calculator 객체 생성
            Calculator calculator = new Calculator();

            // Calculator 클래스의 Class 객체 가져오기
            Class<?> clazz = calculator.getClass();

            // 모든 메소드 가져오기
            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                // 메소드의 파라미터 타입 가져오기
                Class<?>[] parameterTypes = method.getParameterTypes();

                // 아래 코드는 에러가 발생합니다. 단지 참고사항일뿐!!!
                // 임시 파라미터 배열 생성 (예시 값으로 모두 2를 사용)
                Object[] parameters = new Object[parameterTypes.length];
                Arrays.fill(parameters, 2);

                // 메소드 호출
                Object result = method.invoke(calculator, parameters);

                // 결과 출력
                System.out.println("Result of " + method.getName() + ": " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
//    public static void main(String[] args) {
//        try {
//            // User 객체 생성
//            User user = new User();
//
//            // User 클래스의 Class 객체 가져오기
//            Class<?> clazz = user.getClass();
//            
//            Method[] methods = clazz.getDeclaredMethods();            
//
//            for (Method method : methods) {
//            	System.out.println("method name: " + method.getName());
//            	
//            	Class<?>[] parameterTypes = method.getParameterTypes();
//            	for (Object obj: parameterTypes) {
//            		System.out.println("para type: " + obj.toString());
//            	}            	        	
//            }
//            
//            // setName 메소드의 Method 객체 가져오기
//            Method setNameMethod = clazz.getMethod("setName", String.class);
//            setNameMethod.invoke(user, "sung");
//
//            // getName 메소드의 Method 객체 가져오기
//            Method getNameMethod = clazz.getMethod("getName");
//
//            // Method 객체를 사용하여 메소드 호출
//            String name = (String) getNameMethod.invoke(user);
//
//            // 결과 출력
//            System.out.println("Name: " + name);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
