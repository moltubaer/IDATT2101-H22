import java.lang.Math;

public class StockPrices {

	public static String buyStocks(int[] array) {
		int currentProfit= 0;									
		int currentMax = 0;										
		int buy = -1;											
		int sell = -1;											

		// n(n-1)
		for(int i = 0; i < array.length; i++) {
			currentProfit = 0;									
			for(int j = i; j < array.length; j++) {
				currentProfit += array[j];						
				if  (currentProfit > currentMax) {				
					currentMax = currentProfit;					
					buy = i;
					sell = j+1;
				}
			}
		}
		return "Max earnings: " + currentMax + ", Buy at day: " + buy + ", Sell at day: " + sell;	
	}

	public static void main(String[] args) {
		// int array[] = {-1, 3, -9, 2, 2, -1, 2, -1, -5, 1, 1};

		int n = 100;
		int array[] = new int[n];
		for (int i = 0; i < n; i++) {
			if (Math.random() < 0.5) {
				array[i] = (int) Math.round(Math.random() * (-10));
			} else {
				array[i] = (int) Math.round(Math.random() * 10);
			}
		}

		long startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			buyStocks(array);
		}
		long endTime   = System.nanoTime();
		long totalTime = (endTime - startTime) /1000;
		System.out.println("Run time: " + totalTime + " milliseconds");
	}
}








