/**
 * Implementation of K largest numbers using quicksort partition and Priority queue methods
 * @author Akash Chand axc173730
 * @author Prateek Sarna
 */

import java.util.PriorityQueue;
import java.util.Random;

public class SP11 {

	public static Random random = new Random();
	public static int numTrials = 20;
	private static final int Threshold = 6;
	public static void main(String[] args) {
		int n = 128000000;  int choice = 1;
		int k=n/2;
		if(args.length > 0) { n = Integer.parseInt(args[0]); }
		if(args.length > 1) { choice = Integer.parseInt(args[1]); }
		int[] arr = new int[n];
		for(int i=0; i<n; i++) {
			arr[i] = i;
		}
		Timer timer = new Timer();
		switch(choice) {
		case 1:
			for(int i=0; i<numTrials; i++) {
				Shuffle.shuffle(arr);
				kLargestPQ(arr, k);
			}
			break;
		case 2:
			for(int i=0; i<numTrials; i++) {
				Shuffle.shuffle(arr);
				select(arr, k);
			}
			break;
		}
		timer.end();
		timer.scale(numTrials);

		System.out.println("Choice: " + choice + "\n" + timer);
	}
	/**
	 * Insertion sort
	 * @param arr - array to be sorted
	 */
	public static void insertionSort(int[] arr) {
		insertionSort(arr, 0, arr.length-1);
	}
	/**
	 * Insertion sort 
	 * @param arr - array to be sorted
	 * @param left - from index 
	 * @param r - to index
	 */
	private static void insertionSort(int[] arr, int left, int r) {

		for(int i=left+1; i<=r;i++) {

			int p = arr[i];
			int prev = i-1;
			while(prev>=left && p<arr[prev]) {
				arr[prev+1] = arr[prev];
				--prev;
			}
			arr[prev+1]=p;
		}

	}
	/**
	 * select k largest elements.
	 * The k largest elements are stored from arr.length-k-1 to arr.length-1 after the completion of the function call
	 * @param arr -  array in consideration
	 * @param k - k number of largest elements of the array
	 */
	public static void select(int[] arr, int k) {
		select(arr,0, arr.length,k);
	}

	/**
	 * select k largest elements.
	 * The k largest elements are stored from arr.length-k-1 to arr.length-1 after the completion of the function call
	 * @param arr array in consideration
	 * @param p from index
	 * @param n number of elements to be considered from p
	 * @param k number of largest elements that need to be found
	 * @return
	 */
	private static int select(int[] arr, int p, int n, int k) {
		if(n<Threshold) {
			insertionSort(arr,p, p+n-1);
			return n-k;
		}else {
			int q = partition(arr, p, p+n-1);
			int left = q-p;
			int right = n-left-1;
			if(right>=k)
				return select(arr,q+1,right,k);
			else if(right+1==k)
				return q;
			else
				return select(arr, p, left,k-right-1);
		}
		
	}
	/**
	 * Randomized partion method- partitions the array to the following :
	 * [elements less than pivot] [pivot element] [elements greater than pivot]
	 * @param arr array in consideration
	 * @param p from index
	 * @param r to index
	 * @return index of the pivot element
	 */
	private static int partition(int[] arr, int p, int r) {
		int i = p-1, j=p;
		int randomIndex = r==p?p:p+new Random().nextInt(r-p);
		
		swap(arr, randomIndex, r);
		while(j<r) {
			if(arr[j]<=arr[r]) {
				i++;
				swap(arr, i, j);
			}
			j++;
		}
		swap(arr, i+1, r);
		return i+1;
	}
	/**
	 * Swaps two elements of the array
	 * @param arr - array in consideration
	 * @param i - index of one of the element
	 * @param j - index of the second element
	 */
	private static void swap(int arr[], int i, int j) {
		int temp = arr[i];
		arr[i]=arr[j];
		arr[j] = temp;
	}
	/**
	 * K largest number using priority queues
	 * @param arr - array in consideration
	 * @param k - k number of largest numbers in the array 
	 */
	public static PriorityQueue<Integer> kLargestPQ(int []arr, int k) {
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for(int x : arr) {
			if(pq.size()<k) {
				pq.add(x);
			}else {
				if(x>pq.peek()) {
					pq.add(x);
					pq.remove();
				}
			}
		}

		return pq;
	}
	
	/** Timer class for roughly calculating running time of programs
	 *  @author rbk
	 *  Usage:  Timer timer = new Timer();
	 *          timer.start();
	 *          timer.end();
	 *          System.out.println(timer);  // output statistics
	 */

	public static class Timer {
		long startTime, endTime, elapsedTime, memAvailable, memUsed;
		boolean ready;

		public Timer() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public void start() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public Timer end() {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime-startTime;
			memAvailable = Runtime.getRuntime().totalMemory();
			memUsed = memAvailable - Runtime.getRuntime().freeMemory();
			ready = true;
			return this;
		}

		public long duration() { if(!ready) { end(); }  return elapsedTime; }

		public long memory()   { if(!ready) { end(); }  return memUsed; }

		public void scale(int num) { elapsedTime /= num; }

		public String toString() {
			if(!ready) { end(); }
			return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
		}
	}

	/** @author rbk : based on algorithm described in a book
	 */


	/* Shuffle the elements of an array arr[from..to] randomly */
	public static class Shuffle {

		public static void shuffle(int[] arr) {
			shuffle(arr, 0, arr.length-1);
		}

		public static<T> void shuffle(T[] arr) {
			shuffle(arr, 0, arr.length-1);
		}

		public static void shuffle(int[] arr, int from, int to) {
			int n = to - from  + 1;
			for(int i=1; i<n; i++) {
				int j = random.nextInt(i);
				swap(arr, i+from, j+from);
			}
		}

		public static<T> void shuffle(T[] arr, int from, int to) {
			int n = to - from  + 1;
			Random random = new Random();
			for(int i=1; i<n; i++) {
				int j = random.nextInt(i);
				swap(arr, i+from, j+from);
			}
		}

		static void swap(int[] arr, int x, int y) {
			int tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		static<T> void swap(T[] arr, int x, int y) {
			T tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		public static<T> void printArray(T[] arr, String message) {
			printArray(arr, 0, arr.length-1, message);
		}

		public static<T> void printArray(T[] arr, int from, int to, String message) {
			System.out.print(message);
			for(int i=from; i<=to; i++) {
				System.out.print(" " + arr[i]);
			}
			System.out.println();
		}
		
		
	}
	
}
