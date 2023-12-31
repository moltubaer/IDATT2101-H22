// Java implementation of QuickSort 
import java.io.*;
import java.util.Random;

class Quicksort {
      
// A utility function to swap two elements
static void swap(int[] arr, int i, int j)
{
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}

/* This function takes last element as pivot, places
   the pivot element at its correct position in sorted
   array, and places all smaller (smaller than pivot)
   to left of pivot and all greater elements to right
   of pivot */
static int partition(int[] arr, int low, int high)
{
    
    // pivot
    int pivot = arr[high]; 
      
    // Index of smaller element and
    // indicates the right position
    // of pivot found so far
    int i = (low - 1); 
  
    for(int j = low; j <= high - 1; j++)
    {
        // If current element is smaller 
        // than the pivot
        if (arr[j] < pivot) 
        {
            // Increment index of 
            // smaller element
            i++; 
            swap(arr, i, j);
        }
    }
    swap(arr, i + 1, high);
    return (i + 1);
}
  
/* The main function that implements QuickSort
          arr[] --> Array to be sorted,
          low --> Starting index,
          high --> Ending index
 */
static void quickSort(int[] arr, int low, int high)
{
    if (low < high) 
    {
        // pi is partitioning index, arr[p]
        // is now at right place 
        int pi = partition(arr, low, high);
  
        // Separately sort elements before
        // partition and after partition
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

// Function to print an array 
static void printArray(int[] arr, int size)
{
    for(int i = 0; i < size; i++)
        System.out.print(arr[i] + " ");
          
    System.out.println();
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
    // int[] arr = generateTable(40000);
    // int[] arr = generateDuplicateTable(10000);
    int[] arr = generateSortedTable(10000);
    int n = arr.length;

    // Sumcheck of unsorted array
    int sumCheck1 = sumCheck(arr);

    // Run time measurement
    long startTime = System.nanoTime();
    for (int i = 0; i < 1; i++) {
        quickSort(arr, 0, n - 1);
    }
    long endTime   = System.nanoTime();
    long totalTime = (endTime - startTime);
    // long totalTime = (endTime - startTime)/10;
    System.out.println("Run time: " + (totalTime) + " nanoseconds");
    System.out.println("Run time: " + (totalTime/1000) + " microseconds");

    // Sumcheck of sorted array
	int sumCheck2 = sumCheck(arr);
	if (sumCheck1 == sumCheck2) {
		System.out.println(sumCheck1 + " = " + sumCheck2 + ", the arrarys sum check has not been altered");
	} else {
		System.out.println(sumCheck1 + " =/= " + sumCheck2 + ", the arrarys sum check has been altered");
	}

    // Checks correct order of sorted array
    System.out.println(sortCheck(arr));


}
}
