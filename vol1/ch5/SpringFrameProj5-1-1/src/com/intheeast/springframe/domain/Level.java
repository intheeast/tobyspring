package com.intheeast.springframe.domain;



public enum Level {
	BASIC(1), SILVER(2), GOLD(3);	
	
	//public static final int BASIC = 1; // constant : 끊임없는, 변경할 수 없다.
	//public static final int SILVER = 2; // constant : 끊임없는, 변경할 수 없다.
	//public static final int GOLD = 3; // constant : 끊임없는, 변경할 수 없다.

	private final int value;
		
	Level(int value) {
		this.value = value;
	}

	// users 테이블의 level 필드의 데이터타입이 tinyint(정수)...
	// Level이란 enum 클래스를 users 테이블의 level 필드에 저장하기 위해서
	// 정수 값으로 변환이 필요...
	public int intValue() {
		return value;
	}
	
	public static Level valueOf(int value) {
		switch(value) {
		case 1: return BASIC;
		case 2: return SILVER;
		case 3: return GOLD;
		default: throw new AssertionError("Unknown value: " + value);
		}
	}
}
