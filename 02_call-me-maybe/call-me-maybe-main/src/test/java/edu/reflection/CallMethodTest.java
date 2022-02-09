package edu.reflection;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CallMethodTest {

	@Test
	void callUndefinedClass() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		CallMethod.Call("NotExistClass");
		assertEquals("Класса 'NotExistClass' нет!\r\n", output.toString());
		System.setOut(null);
	}

	@Test
	void callClassWithoutNonParametricConsrtuctor() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		CallMethod.Call("edu.reflection.CallMethod");
		assertEquals("У класса 'edu.reflection.CallMethod' нет конструктора без параметров!\r\n", output.toString());
		System.setOut(null);
	}

	@Test
	void callClassWithPrivateConsrtuctor() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		CallMethod.Call("edu.reflection.Main");
		assertEquals("Нет доступа!\r\n", output.toString());
		System.setOut(null);
	}
}