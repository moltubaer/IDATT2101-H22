public class StockPrices {

// Upper runtime	O(n) = n(n-1)
// Lower runtime	O_(n) = n(n-1)
// Total runtime	n(n-1)

	public static void buyStocks(int[] array) {
		int currentProfit= 0;
		int currentMax = 0;
		int buy = -1;
		int sell = -1;

		// System.out.println("");
		// System.out.println("i-j    earnings");

		for(int i = 0; i < array.length; i++) {
			currentProfit = 0;
			// System.out.println("");
			// System.out.println("i=" + i + " => " + array[i]);
			// System.out.println("-----------");
			for(int j = i; j < array.length; j++) {
				currentProfit += array[j];
				// System.out.println(i + "-" + j + "    " + currentProfit);
				if  (currentProfit > currentMax) {
					currentMax = currentProfit;
					buy = i;
					sell = j+1;
					// System.out.println("        Current max earning: " + currentMax + ", Buy at day: " + buy + ", Sell at day: " + sell);
				}
			}
		}
		// System.out.println("");
		// System.out.println("Max earnings: " + currentMax + ", Buy at day: " + buy + ", Sell at day: " + sell);
		// System.out.println("");
	}

	public static void main(String[] args) {
		int array[] = {-1, 3, -9, 2, 2, -1, 2, -1, -5};

		long startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			buyStocks(array);

		}
		long endTime   = System.nanoTime();
		long totalTime = (endTime - startTime) /1000;
		System.out.println("Run time: " + totalTime + " milliseconds");
	}
}








