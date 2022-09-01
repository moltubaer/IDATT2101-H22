import java.lang.Math;

public class Recursion {
	// 1*T(n-1) + cn^0 = T(n-1) + 1 = T(n)
	public static int calculateExponent1(int x, int n) {
		if (n == 0) {
			return 1;
		} else {
			return x * calculateExponent1(x, (n-1));
		}
	}

	// Partall
	// 1*T(n/2) + cn^0 = T(n/2) + 1 = T(n)
	// Oddetall
	// 1*T((n-1)/2) + cn^0 = T((n-1)/2) + 1 = T(n)
	// Kombinert kjøretid: Theta(n^k * log(n)) = Theta(n^0 * log(n)) = Theta(log(n))
	public static double calculateExponent2(double x, int n) {
		if (n == 0) {
			return 1;
		} else if (n % 2 == 1){		// Oddetall
			return x * calculateExponent2(x*x, (n-1)/2);
		} else if (n % 2 == 0) {	// Partall
			return calculateExponent2(x*x, n/2);
		}
		return 0;
	}
	
	public static double javaPow(double x, int n) {
		return Math.pow(x, n);
	}

	public static void main(String[] args) {

		System.out.println("Kontrolltest");
		System.out.println("Method 1 - 2^12: " + calculateExponent1(2, 12));
		System.out.println("Method 1 - 3^14: " + calculateExponent1(3, 14));
		System.out.println("Method 2 - 2^12: " + calculateExponent2(2, 12));
		System.out.println("Method 2 - 3^14: " + calculateExponent2(3, 14));
		System.out.println("");


		int x1 = 2;
		int n1 = 1000;
		long startTime1 = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			calculateExponent1(x1, n1);
		}
		long endTime1   = System.nanoTime();
		long totalTime1 = (endTime1 - startTime1) /1000;

		double x2 = 2;
		int n2 = 1000;
		long startTime2 = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			calculateExponent2(x2, n2);
		}
		long endTime2   = System.nanoTime();
		long totalTime2 = (endTime2 - startTime2) /1000;

		double x3 = 2;
		int n3 = 1000;
		long startTime3 = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			calculateExponent2(x3, n3);
		}
		long endTime3   = System.nanoTime();
		long totalTime3 = (endTime3 - startTime3) /1000;

		System.out.println("Run time method 1: " + totalTime1 + " milliseconds");
		System.out.println("Run time method 2: " + totalTime2 + " milliseconds");
		System.out.println("Run time method 3: " + totalTime3 + " milliseconds");
	}
}








