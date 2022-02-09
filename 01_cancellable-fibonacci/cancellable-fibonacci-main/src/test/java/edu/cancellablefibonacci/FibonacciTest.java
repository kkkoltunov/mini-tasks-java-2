package edu.cancellablefibonacci;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FibonacciTest {
	record KnownFibonacci(
			long sequenceNumber,
			long answer
	) {
	}

	@ParameterizedTest
	@MethodSource("knownFibonacciNumbers")
	void fibonacciOfCalculatesExpectedResults(KnownFibonacci knownFibonacci) {
		long result = Fibonacci.fibonacciOf(knownFibonacci.sequenceNumber());
		assertEquals(knownFibonacci.answer(), result);
	}

	public static Stream<KnownFibonacci> knownFibonacciNumbers() {
		return Stream.of(
				new KnownFibonacci(0, 0),
				new KnownFibonacci(1, 1),
				new KnownFibonacci(2, 1),
				new KnownFibonacci(19, 4181)
		);
	}

	@ParameterizedTest
	@ValueSource(longs = {-1, -2, Long.MIN_VALUE})
	void throwsIfArgumentIsLessThanZero(long l) {
		assertThrows(IllegalArgumentException.class, () -> Fibonacci.fibonacciOf(l));
	}
}
