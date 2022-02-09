package edu.cancellablefibonacci;

import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
	private final static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<?> future = null;
		while (true) {
			int input = readIntNumber("Введите n или «exit»: ");
			if (input == -1) {
				if (future != null) {
					future.cancel(true);
				}
				executorService.shutdown();
				boolean unlock;
				do {
					unlock = executorService.awaitTermination(200, TimeUnit.MILLISECONDS);
				} while (!unlock);
				System.out.println("Пока!");
				break;
			}

			if (future != null && !future.isDone()) {
				future.cancel(true);
			}
			future = executorService.submit(() -> callable(input));
		}
	}

	public static void callable(int input) {
		try {
			long value = Fibonacci.fibonacciOf(input);
			System.out.println("fibonacciOf " + input + " = " + value);
		} catch (UncheckedInterruptedException ignored) {
			System.out.println("Вычисление отменено для " + input + "!");
		}
	}

	public static int readIntNumber(String output) {
		System.out.print(output);
		int input = -1;

		while (scanner.hasNext()) {
			if (scanner.hasNextInt()) {
				input = scanner.nextInt();
				if (input > 0) {
					break;
				}
			} else if (Objects.equals(scanner.next(), "exit")) {
				return input;
			}
			System.out.println("Необходимо ввести число больше нуля!");
			System.out.print(output);
		}

		return input;
	}
}
