package edu.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class CallMethod {
	public CallMethod(String example) {

	}

	public static void Sort(ArrayList<Method> maybeMethods) {
		maybeMethods.sort((o1, o2) -> {
			var annotationsFirstMethod = o1.getDeclaredAnnotations();
			var annotationsSecondMethod = o2.getDeclaredAnnotations();

			int orderValueOfFirstMethod = 0;
			for (java.lang.annotation.Annotation value : annotationsFirstMethod) {
				if (value.annotationType().equals(Order.class)) {
					orderValueOfFirstMethod = ((Order) value).value();
				}
			}

			int orderValueOfSecondMethod = 0;
			for (java.lang.annotation.Annotation annotation : annotationsSecondMethod) {
				if (annotation.annotationType().equals(Order.class)) {
					orderValueOfFirstMethod = ((Order) annotation).value();
				}
			}

			return Integer.compare(orderValueOfSecondMethod, orderValueOfFirstMethod);
		});
	}

	public static void Call(String className) {
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.println("Класса '" + className + "' нет!");
			return;
		}

		Constructor<?> constructor;
		try {
			constructor = clazz.getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			System.out.println("У класса '" + className + "' нет конструктора без параметров!");
			return;
		}

		Object instance = null;
		try {
			instance = constructor.newInstance();
		} catch (InstantiationException e) {
			System.out.println("У класса нет конструктора без параметров или объект класса представляет абстрактный " +
					"класс, интерфейс, класс массива, примитивный тип или void!");
			return;
		} catch (IllegalAccessException e) {
			System.out.println("Нет доступа!");
			return;
		} catch (InvocationTargetException e) {
			System.out.println("Базовый метод вызвал исключение!");
			return;
		}
		System.out.println(instance);

		Method[] methods = CarlyRaeJepsen.class.getDeclaredMethods();
		ArrayList<Method> maybeMethods = new ArrayList<>();
		for (Method method : methods) {
			method.setAccessible(true);
			var annotations = method.getDeclaredAnnotations();
			for (java.lang.annotation.Annotation annotation : annotations) {
				if (annotation.annotationType().equals(CallMe.class) && ((CallMe) annotation).maybe()) {
					maybeMethods.add(method);
				}
			}
		}

		Sort(maybeMethods);

		for (Method maybeMethod : maybeMethods) {
			new InvokeMethodRunnable(maybeMethod, instance).run();
		}
	}
}
