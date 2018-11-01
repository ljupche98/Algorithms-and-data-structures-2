public class C6
{
	public static final int maxn = 1 << 10;
	public static final int INF  = 1 << 30;
	
	public static int N;
	public static int K;
	public static int[][] DP = new int[maxn][maxn];
	
	public static void main(String[] args)
	{
		if (args.length < 2) return;
		
		N = Integer.valueOf(args[0]);
		K = Integer.valueOf(args[1]);
	
		for (int i = 1; i < maxn; i++) DP[i][1] = i;
		for (int i = 1; i < maxn; i++) DP[1][i] = 1;
		
		for (int j = 2; j < maxn; j++)
			for (int i = 2; i < maxn; i++) {
				int minv = INF;
				for (int k = 1; k < i; k++) minv = Math.min(minv, 1 + Math.max(DP[i - k][j - 1], DP[k - 1][j]));
				DP[i][j] = minv;
			}
			
		/**
		for (int i = 0; i <= N; i++, System.out.println())
			for (int j = 1; j <= K; j++)
				System.out.printf("%3d ", DP[i][j]); /// System.out.print(DP[i][j] + " ");
		**/
		
		System.out.printf("%3s ", ""); for (int j = 1; j <= Math.min(DP[N][K], K); j++) System.out.printf("%3d ", j); System.out.println();
		
		for (int i = 0; i <= N; i++) {
			System.out.printf("%3d ", i);
			for (int j = 1; j <= Math.min(DP[N][K], K); j++)
				if (i == 0 && j == 1 || DP[i][j] >= j)
					System.out.printf("%3d ", DP[i][j]);
			System.out.printf("\n");
		}
	}
}