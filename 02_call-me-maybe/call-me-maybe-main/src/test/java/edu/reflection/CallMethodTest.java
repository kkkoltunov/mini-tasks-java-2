package edu.reflection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CallMethodTest {

	@Test
	void callUndefinedClass() {
		Throwable thrown = assertThrows(ClassNotFoundException.class,
				() -> CallMethod.call("NotExistClass"));
		assertEquals("NotExistClass", thrown.getMessage());
	}

	@Test
	void callClassWithoutNonParametricConsrtuctor() {
		Throwable thrown = assertThrows(NoSuchMethodException.class,
				() -> CallMethod.call("edu.reflection.CallMethod"));
		assertEquals("edu.reflection.CallMethod.<init>()", thrown.getMessage());
	}

	@Test
	void callClassWithPrivateConsrtuctor() {
		Throwable thrown = assertThrows(IllegalAccessException.class,
				() -> CallMethod.call("edu.reflection.Main"));
		assertEquals("class edu.reflection.CallMethod cannot access a member of " +
				"class edu.reflection.Main with modifiers \"private\"", thrown.getMessage());
	}
}