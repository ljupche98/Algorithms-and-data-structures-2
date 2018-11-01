import java.util.*;

public class DN01
{
	public static final int[] eliminate = {0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000};
	
	public static int len;
	public static int[] arr;
	public static boolean up, trace;
	
	public static int min(int a, int b)
	{
		return a <= b ? a : b;
	}
	
	public static int[] copy(int[] a)
	{
		int[] b = new int[a.length];
		for (int i = 0; i < a.length; i++) b[i] = a[i];
		return b;
	}
	
	public static void input(String[] args)
	{
		assert args.length >= 3;
		trace = args[0].equals("trace"); up = args[2].equals("up"); arr = new int[args.length >= 4 ? Integer.valueOf(args[3]) : 1];
		
		Scanner cin = new Scanner(System.in);
		while (cin.hasNext()) {
			if (len < arr.length) {
				arr[len++] = cin.nextInt();
				continue;
			}
			
			int[] arrt = new int[arr.length * 2];
			for (int i = 0; i < arr.length; i++) arrt[i] = arr[i];
			arr = arrt;
		}
	}
	
	public static void bubbleSort()
	{
		int ini = 0, cmp = 0;
		
		for (int i = len - 1; i >= 0; i--) {
			if (trace) {
				for (int j = 0; j < len - 1 - i; j++) System.out.print(arr[j] + " "); System.out.print("| ");
				for (int j = len - 1 - i; j < len; j++) System.out.print(arr[j] + " "); System.out.println();
			}
			
			for (int j = len - 1; j > len - 1 - i; j--) { cmp++;
				if (up && arr[j] < arr[j - 1] || !up && arr[j] > arr[j - 1]) {
					int temp = arr[j]; arr[j] = arr[j - 1]; arr[j - 1] = temp; ini += 3;
				}
			}
		}
		
		if (!trace) System.out.println(cmp + " " + ini);
	}
	
	public static void selectionSort()
	{
		int ini = 0, cmp = 0;
		
		for (int i = 0; i < len; i++) {
			if (trace) {
				for (int j = 0; j < i; j++) System.out.print(arr[j] + " "); System.out.print("| ");
				for (int j = i; j < len; j++) System.out.print(arr[j] + " "); System.out.println();
			}
			
			int idx = i;
			for (int j = i + 1; j < len; j++) { cmp++;
				if (up && arr[idx] > arr[j] || !up && arr[idx] < arr[j]) {
					idx = j;
				}
			}
			
			int temp = arr[idx]; arr[idx] = arr[i]; arr[i] = temp;	
			if (i < len - 1) ini += 3;
		}
		
		if (!trace) System.out.println(cmp + " " + ini);
	}
	
	public static void insertionSort()
	{
		int ini = 0, cmp = 0;
		
		for (int i = 0; i < len; i++) {
			int j = i - 1, key = arr[i];
			
			while (j >= 0) { cmp++;
				if (up && arr[j] > key || !up && arr[j] < key) { ini += 3;
					arr[j + 1] = arr[j--];
				}
				else break;
			}
			
			arr[j + 1] = key;
			
			if (trace) {
				for (j = 0; j <= i; j++) System.out.print(arr[j] + " "); System.out.print("| ");
				for (j = i + 1; j < len; j++) System.out.print(arr[j] + " "); System.out.println();
			}
		}
		
		if (!trace) System.out.println(cmp + " " + ini);
	}
	
	public static void printHeap(int hlen)
	{
		int exp = 0; while ((1 << exp) <= hlen + 1) exp++;
		
		for (int i = 0; i < exp; i++) {
			for (int j = (1 << i); j < (1 << i + 1) && j <= hlen + 1; j++)
				System.out.print(arr[j - 1] + " ");
			if (i != exp - 1) System.out.print("| ");
		}
		
		System.out.println();
	}
	
	public static void heapify(int[] cres, int H, int idx)
	{
		int midx = idx;
		
		if (2 * idx + 1 <= H) { cres[0]++;
			if (up && arr[2 * idx + 1] > arr[midx] || !up && arr[2 * idx + 1] < arr[midx]) midx = 2 * idx + 1;
		}
		
		if (2 * idx + 2 <= H) { cres[0]++;
			if (up && arr[2 * idx + 2] > arr[midx] || !up && arr[2 * idx + 2] < arr[midx]) midx = 2 * idx + 2;
		}
		
		if (idx == midx) return;
		
		int temp = arr[midx]; arr[midx] = arr[idx]; arr[idx] = temp; cres[1] += 3;
		heapify(cres, H, midx);
	}
	
	public static void heapSort()
	{
		int[] cres = new int[2];
		
		for (int i = (len - 1) / 2; i >= 0; i--) heapify(cres, len - 1, i);
		
		for (int i = len - 1; i >= 0; i--) {
			if (trace) printHeap(i);
			int temp = arr[0]; arr[0] = arr[i]; arr[i] = temp; if (i != 0) cres[1] += 3;
			heapify(cres, i - 1, 0);
		}
		
		if (!trace) System.out.println(cres[0] + " " + cres[1]);
	}
	
	public static void quickSort(int[] cres, int l, int r)
    {
        if (l >= r) return;
          
        int i = l, j = r, pivot = arr[(l + r) >> 1]; cres[1]++;
          
        while (i <= j) {
            while (up && arr[i] < pivot || !up && arr[i] > pivot) {cres[0]++; i++;} cres[0]++;
            while (up && arr[j] > pivot || !up && arr[j] < pivot) {cres[0]++; j--;} cres[0]++;
            if (i <= j) {
                int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp; cres[1] += 3;
                i++; j--;
            }
        }
          
        if (trace) {
            for (int x = l; x <= j; x++) System.out.print(arr[x] + " "); System.out.print("| ");
            for (int x = j + 1; x < i; x++) System.out.print(arr[x] + " "); System.out.print("| ");
            for (int x = i; x <= r; x++) System.out.print(arr[x] + " "); System.out.println();
        }
          
        quickSort(cres, l, j);
        quickSort(cres, i, r);
    }
	
	public static int[] merge(int[] cres, int lf, int rf, int ls, int rs)
	{
		int it = 0;
		int[] res = new int[rs - lf + 1];
		
		while (lf <= rf && ls <= rs) {
			if (up && arr[lf] <= arr[ls] || !up && arr[lf] >= arr[ls]) { cres[0]++;
				res[it++] = arr[lf++];
			}
			
			if (up && arr[lf] > arr[ls] || !up && arr[lf] < arr[ls]) { cres[0]++;
				res[it++] = arr[ls++];
			}
		}
		
		while (lf <= rf) {
			res[it++] = arr[lf++];
		}
		
		while (ls <= rs) {
			res[it++] = arr[ls++];
		}
		
		return res;
	}
	
	public static void mergeSort(int[] cres, int l, int r)
	{
		if (l >= r) return;
		
		int mid = (l + r) / 2; cres[1]++;
		
		if (trace) {
			for (int i = l; i <= mid; i++) System.out.print(arr[i] + " "); System.out.print("| ");
			for (int i = mid + 1; i <= r; i++) System.out.print(arr[i] + " "); System.out.println();
		}
		
		mergeSort(cres, l, mid);
		mergeSort(cres, mid + 1, r);
		
		int[] sort = merge(cres, l, mid, mid + 1, r);
		for (int i = 0; i < sort.length; i++) arr[l + i] = sort[i]; cres[1] += sort.length;
		
		if (trace) {
			for (int i = 0; i < sort.length; i++) System.out.print(sort[i] + " "); System.out.println();
		}
	}
	
	public static int[] countingSort(int[] arrv)
	{
		int[] count = new int[1 << 8], range = new int [1 << 8 | 1];
		for (int i = 0; i < len; i++) count[arr[i]]++;
		for (int i = (up ? 1 : (1 << 8) - 2); i >= 0 && i < (1 << 8 | 1); i += (up ? 1 : -1))
			range[i] = range[i + (up ? -1 : 1)] + count[i + (up ? -1 : 1)];
		
		for (int i = (up ? 1 : 0); i < (1 << 8 | 1) - (up ? 0 : 1); i++) System.out.print(range[i] + " "); System.out.println();
		
		int[] sort = new int[len], sortidx = new int[len];
		for (int i = 0; i < len; i++) {
			sort[range[arr[i]]] = arrv[i];
			sortidx[range[arr[i]]++] = i;
		}
		
		for (int i = 0; i < len; i++) System.out.print(sort[i] + " "); System.out.println();
		
		return sortidx;
	}
	
	public static void bubbleSortEX()
	{
		bubbleSort();
		if (trace) return;
		bubbleSort(); up ^= true;
		bubbleSort();
	}
	
	public static void selectionSortEX()
	{
		selectionSort();
		if (trace) return;
		selectionSort(); up ^= true;
		selectionSort();
	}
	
	public static void insertionSortEX()
	{
		insertionSort();
		if (trace) return;
		insertionSort(); up ^= true;
		insertionSort();
	}
	
	public static void heapSortEX()
	{
		heapSort();
		if (trace) return;
		heapSort(); up ^= true;
		heapSort();
	}
	
	public static void quickSortEX(int l, int r)
	{
		int[] cres = new int[2];
		quickSort(cres, l, r);
		if (trace) return;
		System.out.println(cres[0] + " " + cres[1]); cres[0] = cres[1] = 0;
		quickSort(cres, l, r);
		System.out.println(cres[0] + " " + cres[1]); cres[0] = cres[1] = 0; up ^= true;
		quickSort(cres, l, r);
		System.out.println(cres[0] + " " + cres[1]);
	}
	
	public static void mergeSortEX(int l, int r)
	{
		int[] cres = new int[2];
		mergeSort(cres, l, r);
		if (trace) return;
		System.out.println(cres[0] + " " + ++cres[1]); cres[0] = cres[1] = 0;
		mergeSort(cres, l, r);
		System.out.println(cres[0] + " " + ++cres[1]); cres[0] = cres[1] = 0; up ^= true;
		mergeSort(cres, l, r);
		System.out.println(cres[0] + " " + ++cres[1]);
	}
	
	public static void countingSortEX()
	{
		countingSort(arr);
	}
	
	public static void radixSortEX()
	{
		for (int i = 0; i < 4; i++) {
			int[] arrc = copy(arr);
			
			for (int j = 0; j < len; j++) arr[j] = (arr[j] & eliminate[i]) >>> (8 * i);
			int[] sort = countingSort(arrc);
			
			int[] arrs = new int[len];
			for (int j = 0; j < len; j++) arrs[j] = arrc[sort[j]];
			arr = arrs;
		}
	}
	
	public static void main(String[] args)
	{
		input(args);
		if (args[1].equals("bs")) bubbleSortEX();
		if (args[1].equals("ss")) selectionSortEX();
		if (args[1].equals("is")) insertionSortEX();
		if (args[1].equals("hs")) heapSortEX();
		if (args[1].equals("qs")) quickSortEX(0, len - 1);
		if (args[1].equals("ms")) mergeSortEX(0, len - 1);
		if (args[1].equals("cs")) countingSortEX();
		if (args[1].equals("rs")) radixSortEX();
	}
}