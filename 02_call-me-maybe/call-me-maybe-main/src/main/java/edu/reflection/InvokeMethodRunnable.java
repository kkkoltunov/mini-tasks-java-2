package edu.reflection;

import java.lang.reflect.Method;

public record InvokeMethodRunnable(Method method, Object target) implements Runnable {

	@Override
	public void run() {
		try {
			method.invoke(target);
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
	}
}