// Java program to implement
// dual pivot QuickSort
import java.util.Random;

class DualPivotQuicksort {
 
static void swap(int[] arr, int i, int j)
{
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}
 
static void dualPivotQuickSort(int[] arr,
                               int low, int high)
{
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
 
static int[] partition(int[] arr, int low, int high)
{
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
        t[i] = random.nextInt(100);
    }
    return t;
}

// Driver code
public static void main(String[] args)
{
    // int[] array = { 24, 8, 42, 75, 29, 77, 38, 57 };
    int[] array = generateTable(1000);
    int length = array.length;

    System.out.println("n = " + array.length);

    // System.out.print("Unsorted array: ");
    // System.out.println();
    // for (int i = 0; i < array.length; i++) {
    //     System.out.print(array[i] + " ");
	// }
    // System.out.println();

	int sumCheck1 = sumCheck(array);
	dualPivotQuickSort(array, 0, (length-1));
	int sumCheck2 = sumCheck(array);

	if (sumCheck1 == sumCheck2) {
		System.out.println(sumCheck1 + " = " + sumCheck2 + ", the arrarys sum check has not been altered");
	} else {
		System.out.println(sumCheck1 + " =/= " + sumCheck2 + ", the arrarys sum check has been altered");
	}


    // System.out.print("Sorted array: ");
    // System.out.println();
    // for (int i = 0; i < array.length; i++) {
    //     System.out.print(array[i] + " ");
	// }
    // System.out.println();

    System.out.println(sortCheck(array));

    long startTime = System.nanoTime();
    for (int i = 0; i < 1000; i++) {
        dualPivotQuickSort(array, 0, 7);
    }
    long endTime   = System.nanoTime();
    long totalTime = (endTime - startTime) /1000;
    System.out.println("Run time: " + totalTime + " milliseconds");

}
}
 
// This code is contributed by Gourish Sadhu