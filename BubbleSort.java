import java.util.*;

public class BubbleSort {
	int[] array = new int[] {5, 1, 4, 2, 8, 4, 7, 9, 0, -5};

	public void changePositions(int originalPosition, int newPosition) {
		int temp = array[originalPosition];
		array[originalPosition] = array[newPosition];
		array[newPosition] = temp;
		System.out.println(Arrays.toString(array));
	}

    public static void main(String[] args) {
		BubbleSort bubble = new BubbleSort();

		boolean fullySorted = false;
		int passCount = 1;
		boolean noChanges = true;

		System.out.println("\t" + Arrays.toString(bubble.array));
		do {

			if (!fullySorted) {
				System.out.println("Pass nr: " + passCount);
			}

			for (int i = 0; i < bubble.array.length-1; i++) {
				if (bubble.array[i] > bubble.array[i+1]) {
					// array.changePositions(i, i+1);
					noChanges = false;

					int originalPosition = i;
					int newPosition = i+1;
					int temp = bubble.array[originalPosition];
					bubble.array[originalPosition] = bubble.array[newPosition];
					bubble.array[newPosition] = temp;
					System.out.println(i + "\t" + Arrays.toString(bubble.array));
				}

			}
			if (noChanges) {
				fullySorted = true;
			}
			passCount++;
			noChanges = true;
		}
		while (!fullySorted);
		System.out.println("\t" + Arrays.toString(bubble.array));

	}
}