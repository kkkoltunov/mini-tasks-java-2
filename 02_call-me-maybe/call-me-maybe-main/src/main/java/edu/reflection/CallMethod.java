package edu.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CallMethod {
	public CallMethod(String example) {

	}

	public static void sort(List<Method> maybeMethods) {
		maybeMethods.sort((o1, o2) -> {
			int orderValueOfFirstMethod = 0;
			Order order1 = o1.getAnnotation(Order.class);
			if (order1 != null) {
				orderValueOfFirstMethod = order1.value();
			}

			int orderValueOfSecondMethod = 0;
			Order order2 = o2.getAnnotation(Order.class);
			if (order1 != null) {
				orderValueOfSecondMethod = order2.value();
			}

			return Integer.compare(orderValueOfSecondMethod, orderValueOfFirstMethod);
		});
	}

	public static void call(String className) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Class<?> clazz = Class.forName(className);
		Constructor<?> constructor = clazz.getDeclaredConstructor();

		Object instance = constructor.newInstance();
		System.out.println(instance);

		Method[] methods = clazz.getDeclaredMethods();
		List<Method> maybeMethods = new ArrayList<>();
		for (Method method : methods) {
			method.setAccessible(true);
			var annotations = method.getDeclaredAnnotations();
			for (java.lang.annotation.Annotation annotation : annotations) {
				if (annotation.annotationType().equals(CallMe.class) && ((CallMe) annotation).maybe()) {
					maybeMethods.add(method);
				}
			}
		}

		sort(maybeMethods);
		for (Method maybeMethod : maybeMethods) {
			new InvokeMethodRunnable(maybeMethod, instance).run();
		}
	}
}
