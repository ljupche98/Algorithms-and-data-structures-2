import java.util.*;

public class C1 {
	public static int[] generateTable(int N) {
		int arr[] = new int[N];
		for (int i = 0; i < N; i++) arr[i] = i + 1;
		return arr;
	}
	
	public static int findLinear(int[] arr, int v) {
		for (int i = 0; i < arr.length; i++)
			if (arr[i] == v) return i;
		return -1;
	}
	
	public static int findBinary(int[] arr, int l, int r, int v) {
		int mid;
		while (l <= r) {
			mid = (l + r) >> 1;
			if (arr[mid] == v) return mid;
			if (arr[mid] > v) r = mid - 1;
			else 			  l = mid + 1;
		}
		return -1;
	}
	
	public static long timeLinear(int N) {
		int[] arr = generateTable(N);
		Random rand = new Random();
		long time = System.nanoTime();
		
		for (int i = 0; i < 1000; i++) {
			int rint = rand.nextInt(N) + 1;
			assert 1 <= rint && rint <= N && rint == arr[findLinear(arr, rint)];
		}
		
		return (System.nanoTime() - time) / 1000;
	}
	
	public static long timeBinary(int N) {
		int[] arr = generateTable(N);
		Random rand = new Random();
		long time = System.nanoTime();
		
		for (int i = 0; i < 1000; i++) {
			int rint = rand.nextInt(N) + 1;
			assert 1 <= rint && rint <= N && rint == arr[findBinary(arr, 0, arr.length - 1, rint)];
		}
		
		return (System.nanoTime() - time) / 1000;
	}
	
	public static void main(String[] args) {
		System.out.printf("\tn\t|\tlinearno\t|\tdvojisko\t|\n");
		System.out.printf("----------------+-----------------------+------------------------\n");
		for (int i = (int) 1e3; i <= 1e5; i += 1e3)
			System.out.printf("\t%d\t|\t%d\t\t|\t%d\t\t|\n", i, timeLinear(i), timeBinary(i));
	}
}