package edu.cancellablefibonacci;

import static java.lang.System.exit;

public abstract class Fibonacci {
	private Fibonacci() {
	}

	public static long fibonacciOf(long n) {
		if (Thread.currentThread().isInterrupted()) {
			throw new UncheckedInterruptedException("Thread was interrupt!");
		}
		if (n == 0) {
			return 0;
		}
		if (n <= 2) {
			return 1;
		}
		return Math.addExact(fibonacciOf(n - 1), fibonacciOf(n - 2));
	}
}
