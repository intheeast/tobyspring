package com.intheeast.learningtest.spring.pointcut;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class PointcutExpressionTest {
	
	@Test
	public void pointcut() throws Exception {
		tagetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true);
		tagetClassPointcutMatches("execution(* hello(..))", true, true, false, false, false, false);
		tagetClassPointcutMatches("execution(* hello())", true, false, false, false, false, false);
		tagetClassPointcutMatches("execution(* hello(String))", false, true, false, false, false, false);
		tagetClassPointcutMatches("execution(* meth*(..))", false, false, false, false, true, true);
		
		tagetClassPointcutMatches("execution(* *(int,int))", false, false, true, true, false, false);
		tagetClassPointcutMatches("execution(* *())", true, false, false, false, true, true);
		
		tagetClassPointcutMatches("execution(* com.intheeast.learningtest.spring.pointcut.Target.*(..))", true, true, true, true, true, false);
		tagetClassPointcutMatches("execution(* com.intheeast.learningtest.spring.pointcut.*.*(..))", true, true, true, true, true, true);
		tagetClassPointcutMatches("execution(* com.intheeast.learningtest.spring.pointcut..*.*(..))", true, true, true, true, true, true);
		tagetClassPointcutMatches("execution(* com..*.*(..))", true, true, true, true, true, true);
		
		//tagetClassPointcutMatches("execution(* com..*.*(..))", false, false, false, false, false, false);
		tagetClassPointcutMatches("execution(* *..Target.*(..))", true, true, true, true, true, false);
		tagetClassPointcutMatches("execution(* *..Tar*.*(..))", true, true, true, true, true, false);
		tagetClassPointcutMatches("execution(* *..*get.*(..))", true, true, true, true, true, false);
		tagetClassPointcutMatches("execution(* *..B*.*(..))", false, false, false, false, false, true);
		
		tagetClassPointcutMatches("execution(* *..TargetInterface.*(..))", true, true, true, true, false, false);

		tagetClassPointcutMatches("execution(* *(..) throws Runtime*)", false, false, false, true, false, true);

		tagetClassPointcutMatches("execution(int *(..))", false, false, true, true, false, false);
		tagetClassPointcutMatches("execution(void *(..))", true, true, false, false, true, true);
	}
		
	public void tagetClassPointcutMatches(String expression, boolean... expected) throws Exception {
		pointcutMatches(expression, expected[0], Target.class, "hello");
		pointcutMatches(expression, expected[1], Target.class, "hello", String.class);
		pointcutMatches(expression, expected[2], Target.class, "plus", int.class, int.class);
		pointcutMatches(expression, expected[3], Target.class, "minus", int.class, int.class);
		pointcutMatches(expression, expected[4], Target.class, "method");
		pointcutMatches(expression, expected[5], Bean.class, "method");
	}


	public void pointcutMatches(String expression, Boolean expected, Class<?> clazz, String methodName, Class<?>... args) throws Exception {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);
		
		assertEquals(pointcut.getClassFilter().matches(clazz) 
				   && pointcut.getMethodMatcher().matches(clazz.getMethod(methodName, args), null),
				   expected);
	}
	
	
	@Test
	public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(
				"execution(* minus(int,int))");
		
		assertEquals(pointcut.getClassFilter().matches(Target.class) &&
				   pointcut.getMethodMatcher().matches(
					  Target.class.getMethod("minus", int.class, int.class), null), true);
		
		assertEquals(pointcut.getClassFilter().matches(Target.class) &&
				   pointcut.getMethodMatcher().matches(
					  Target.class.getMethod("plus", int.class, int.class), null), false);

		assertEquals(pointcut.getClassFilter().matches(Bean.class) &&
				pointcut.getMethodMatcher().matches(
						Target.class.getMethod("method"), null), false);
	}

}
