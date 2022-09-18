// Java program to implement
// dual pivot QuickSort
import java.util.Random;

class DualPivotQuicksort {
 
static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}
 
static void dualPivotQuickSort(int[] arr, int low, int high) {
    if (low < high)
    {
         
        // piv[] stores left pivot and right pivot.
        // piv[0] means left pivot and
        // piv[1] means right pivot
        int[] piv;
        piv = partition(arr, low, high);
         
        dualPivotQuickSort(arr, low, piv[0] - 1);
        dualPivotQuickSort(arr, piv[0] + 1, piv[1] - 1);
        dualPivotQuickSort(arr, piv[1] + 1, high);
    }
}
 
static int[] partition(int[] arr, int low, int high) {
    if (arr[low] > arr[high])
        swap(arr, low, high);
         
    // p is the left pivot, and q
    // is the right pivot.
    int j = low + 1;
    int g = high - 1, k = low + 1,
        p = arr[low], q = arr[high];
         
    while (k <= g)
    {
         
        // If elements are less than the left pivot
        if (arr[k] < p)
        {
            swap(arr, k, j);
            j++;
        }
         
        // If elements are greater than or equal
        // to the right pivot
        else if (arr[k] >= q)
        {
            while (arr[g] > q && k < g)
                g--;
                 
            swap(arr, k, g);
            g--;
             
            if (arr[k] < p)
            {
                swap(arr, k, j);
                j++;
            }
        }
        k++;
    }
    j--;
    g++;
     
    // Bring pivots to their appropriate positions.
    swap(arr, low, j);
    swap(arr, high, g);
 
    // Returning the indices of the pivots
    // because we cannot return two elements
    // from a function, we do that using an array.
    return new int[] { j, g };
}

private static String sortCheck(int[] t) {
	for (int i = 0; i < t.length - 2; i++) {
		if (t[i] > t[i+1]) {
			return "The array is not sorted";
		}
	}
	return "The array is sorted";
}

private static int sumCheck(int[] arr) {
	int sum = 0;
	for (int i = 0; i < arr.length; i++) {
		sum += arr[i];
	}
	return sum;
}

private static int[] generateTable(int n) {
    int[] t = new int[n];
    Random random = new Random();
    for (int i = 0; i < t.length; i++) {
        t[i] = random.nextInt(1000);   // Random number between 0 and 999
    }
    return t;
}

private static int[] generateDuplicateTable(int n) {
    int[] t = new int[n];
    Random random = new Random();
    for (int i = 0; i < t.length; i++) {
        t[i] = random.nextInt(3);   // Random number between 0 and 2
    }
    return t;
}

private static int[] generateSortedTable(int n) {
    int[] t = new int[n];
    Random random = new Random();
    for (int i = 0; i < t.length; i++) {
        t[i] = i;
    }
    return t;
}

public static void main(String[] args)
{
    // int[] array = generateTable(40000);
    // int[] array = generateDuplicateTable(10000);
    int[] array = generateSortedTable(10000);
    int n = array.length;
	
    // Sumcheck of unsorted array
    int sumCheck1 = sumCheck(array);

    // Run time measurement
    long startTime = System.nanoTime();
    for (int i = 0; i < 1; i++) {
        dualPivotQuickSort(array, 0, (n-1));
    }
    long endTime   = System.nanoTime();
    long totalTime = (endTime - startTime);
    System.out.println("Run time: " + (totalTime) + " nanoseconds");
    System.out.println("Run time: " + (totalTime/1000) + " microseconds");

    // Sumcheck of sorted array
	int sumCheck2 = sumCheck(array);
	if (sumCheck1 == sumCheck2) {
		System.out.println(sumCheck1 + " = " + sumCheck2 + ", the arrarys sum check has not been altered");
	} else {
		System.out.println(sumCheck1 + " =/= " + sumCheck2 + ", the arrarys sum check has been altered");
	}

    // Checks correct order of sorted array
    System.out.println(sortCheck(array));


}
}
 
// This code is contributed by Gourish Sadhu