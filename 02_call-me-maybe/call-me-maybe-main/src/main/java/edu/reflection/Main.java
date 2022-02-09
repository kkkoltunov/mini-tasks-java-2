package edu.reflection;

import java.util.*;

public class Main {
	private Main() {

	}

	private final static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args){
		String output = "Введите имя класса или \"exit\": ";
		String className = readString(output);
		while (!className.equals("exit")) {
			CallMethod.Call(className);
			className = readString(output);
		}
		System.out.println("Goodbye!");
	}

	public static String readString(String output) {
		System.out.print(output);
		String input = "";

		while (scanner.hasNext()) {
			input = scanner.next();
			if (!input.isEmpty() && !input.isBlank()) {
				return input;
			}

			System.out.print(output);
		}

		return input;
	}
}
