import java.util.*;

public class C8
{
	public static final int INF = 1 << 25;
	public static final int maxn = 1 << 17;
	
	public static int N, M;
	public static int[][] dist = new int[2][maxn];
	public static int[][] edge = new int[maxn << 1][3];
	
	public static void print(int x)
	{
		System.out.print("h" + x + ": ");
		
		for (int i = 0; i < N; i++)
			System.out.print((dist[x % 2][i] == INF ? "Inf" : dist[x % 2][i]) + " ");
		
		System.out.println();
	}
	
	public static void main(String[] args)
	{
		if (args.length < 1) return;
		
		Scanner cin = new Scanner(System.in);
		
		N = Integer.valueOf(args[0]);
		
		while (cin.hasNext()) {
			edge[M][0] = cin.nextInt();
			edge[M][1] = cin.nextInt();
			edge[M++][2] = cin.nextInt();
		}
		
		for (int i = 0; i < N; i++) dist[0][i] = dist[1][i] = INF;
		
		dist[0][0] = 0;
		print(0);
		
		for (int k = 1; k < N; k++) {
			for (int i = 0; i < M; i++)
				dist[k % 2][edge[i][1]] = Math.min(dist[k % 2][edge[i][1]], Math.min(dist[1 - k % 2][edge[i][1]], dist[1 - k % 2][edge[i][0]] + edge[i][2]));
			
			print(k);
		}
	}
}