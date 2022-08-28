import java.util.*;

public class Class {

	public static void myMethod() {

	}

	public static void main(String[] args) {

		
		long startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			myMethod();

		}
		long endTime   = System.nanoTime();
		long totalTime = (endTime - startTime) /1000;
		System.out.println("Run time: " + totalTime + " milliseconds");
	}
}








