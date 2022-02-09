package edu.reflection;
// edu.reflection.CarlyRaeJepsen
public class CarlyRaeJepsen {
	@Order(3)
	@CallMe
	private void soCallMe() {
		System.out.println("So call me");
	}

	@Order(1)
	@CallMe
	void sayHello() {
		System.out.println("Hello");
	}

	@Order(2)
	@CallMe
	void hereIsMyNumber() {
		System.out.println("Here's my number");
	}

	@Order(4)
	@CallMe(maybe = false)
	void maybe() {
		System.out.println("Maybe");
	}
}
