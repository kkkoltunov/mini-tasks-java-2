package edu.reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Main {
	private Main() {

	}

	private final static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args){
		String output = "Введите имя класса или \"exit\": ";
		String className = readString(output);
		while (!className.equals("exit")) {
			try {
				CallMethod.call(className);
			} catch (ClassNotFoundException e) {
				System.out.println("Класса '" + className + "' нет!");
			} catch (NoSuchMethodException e) {
				System.out.println("У класса '" + className + "' нет конструктора без параметров!");
			} catch (InstantiationException e) {
				System.out.println("У класса нет конструктора без параметров или объект класса представляет абстрактный " +
						"класс, интерфейс, класс массива, примитивный тип или void!");
			} catch (IllegalAccessException e) {
				System.out.println("Нет доступа!");
			} catch (InvocationTargetException e) {
				System.out.println("Базовый метод вызвал исключение!");
			}
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
