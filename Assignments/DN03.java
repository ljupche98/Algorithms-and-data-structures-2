import java.util.*;

public class DN03
{
	public static int N, M, RP, RT, RTT;
	public static int[] DP = new int[1 << 17];

	public static int[] vdeg;
	public static int[][] graph;
	public static String[] RRS;
	
	public static void read()
	{
		Scanner cin = new Scanner(System.in);
		
		N = cin.nextInt();
		M = cin.nextInt();
		
		vdeg = new int[N];
		graph = new int[N][];
		int[][] edge = new int[M][2];
		
		for (int i = 0; i < M; i++) {
			int x = cin.nextInt();
			int y = cin.nextInt();
			vdeg[x]++; vdeg[y]++;
			edge[i][0] = x; edge[i][1] = y;
		}
		
		int[] ptr = new int[N];
		
		for (int i = 0; i < N; i++) graph[i] = new int[vdeg[i]];
		for (int i = 0; i < M; i++) {
			graph[edge[i][0]][ptr[edge[i][0]]++] = edge[i][1];
			graph[edge[i][1]][ptr[edge[i][1]]++] = edge[i][0];
		}
	}
	
	public static void print()
	{
		if (RTT < RT)
			for (int i = 0; i < RP; i++)
				System.out.printf("%s", RRS[i]);
		else {
			int x = RP;
			
			do {
				System.out.printf("%s", RRS[x]);
				x = (x + 1) % RT;
			} while (x != RP);
		}
	}
	
	public static void DFS(int[] dist, int u)
	{
		for (int i = 0; i < vdeg[u]; i++)
			if (dist[u] + 1 < dist[graph[u][i]]) {
				dist[graph[u][i]] = dist[u] + 1;
				DFS(dist, graph[u][i]);
			}
	}	
	
	public static void TCS()
	{
		int mx = 0;
		int[] dist = new int[N];
		
		for (int i = 1; i < N; i++) dist[i] = 1 << 17;
		DFS(dist, 0);
		
		for (int i = 0; i < N; i++) mx = Math.max(mx, dist[i]);
		
		for (int k = 0; k <= mx; k++) {
			int pt = 0;
			int[] vx = new int[N];
			
			System.out.printf("%d : ", k);
			for (int i = 0; i < N; i++)
				if (dist[i] == k) {
					vx[pt++] = i;
					System.out.printf("%d ", i);
				}
			System.out.printf("\n");
			
			for (int i = 0; i < pt; i++)
				for (int j = 0; j < vdeg[vx[i]]; j++)
					if (dist[vx[i]] % 2 == dist[graph[vx[i]][j]] % 2) {
						System.out.printf("NOK\n");
						return;
					}
		}
		
		System.out.printf("OK\n");
	}
	
	public static void CNV()
	{
		int mx = 0;
		int[] col = new int[N];
		for (int i = 0; i < N; i++) col[i] = -1;
		
		for (int i = 0; i < N; i++) {
			boolean[] us = new boolean[mx + 1];
			
			for (int j = 0; j < vdeg[i]; j++)
				if (col[graph[i][j]] != -1)
					us[col[graph[i][j]]] |= true;
			
			boolean add = false;
			
			for (int j = 0; j < mx + 1; j++)
				if (!us[j]) {
					col[i] = j;
					add = j == mx;
					System.out.printf("%d : %d\n", i, j);
					break;
				}
		
			if (add) mx++;
		}
	}
	
	public static boolean NBFR(int i, int n, int[] aux)
	{
		if (i == aux.length) {
			RTT++;
			
			if (RT > 0) RRS[RP] = "";
			
			for (int j = 0; j < aux.length; j++)
				if (RT > 0) RRS[RP] += Integer.toString(aux[j]) + " ";
				else System.out.printf("%d ", aux[j]);
			
			for (int j = 0; j < aux.length; j++)
				for (int k = 0; k < vdeg[j]; k++)
					if (aux[j] == aux[graph[j][k]]) {
						if (RT > 0) RRS[RP] += "NOK\n";
						else System.out.printf("NOK\n");
						
						if (RT > 0) RP = (RP + 1) % RT;
						return false;
					}
			
			if (RT > 0) RRS[RP] += "OK\n";
			else System.out.printf("OK\n");
			
			if (RT > 0) RP = (RP + 1) % RT;
			return true;
		}
	
		for (int j = 0; j < n; j++) {
			aux[i] = j;
			if (NBFR(i + 1, n, aux)) return true;
			aux[i] = 0;
		}
		
		return false;
	}
	
	public static void NBF()
	{
		int x = 2;
		int[] aux = new int[N];
		
		while (true) {
			if (RT > 0) {
				RRS[RP] = "k = " + Integer.toString(x) + "\n";
				RP = (RP + 1) % RT;
				RTT++;
			} else System.out.printf("k = %d\n", x);
			
			if (NBFR(0, x, aux)) break;
			x++;
		}
		
		if (RT > 0) print();
	}
	
	public static boolean BTSR(int i, int n, int[] aux)
	{
		if (i == aux.length) return true;
		
		for (int k = 0; k < Math.min(n, i + 1); k++) {
			boolean ok = true;
			
			for (int l = 0; l < vdeg[i]; l++)
				if (graph[i][l] < i)
					if (aux[graph[i][l]] == k) {
						aux[i] = k;
						
						if (RT > 0) RRS[RP] = "";
						
						for (int j = 0; j <= i; j++)
							if (RT > 0) RRS[RP] += Integer.toString(aux[j]) + " ";
							else System.out.printf("%d ", aux[j]);
						
						if (RT > 0) RRS[RP] += "NOK\n";
						else System.out.printf("NOK\n");
						
						RTT++;
						if (RT > 0) RP = (RP + 1) % RT;
						
						aux[i] = 0;
						ok = false;
						break;
					}
			
			if (ok) {
				aux[i] = k;
				
				if (RT > 0) RRS[RP] = "";
				
				for (int j = 0; j <= i; j++)
					if (RT > 0) RRS[RP] += Integer.toString(aux[j]) + " ";
					else System.out.printf("%d ", aux[j]);
				
				if (RT > 0) RRS[RP] += "OK\n";
				else System.out.printf("OK\n");
				
				RTT++;
				if (RT > 0) RP = (RP + 1) % RT;
				
				if (BTSR(i + 1, n, aux)) return true;
				aux[i] = 0;
			}
		}
		
		return false;
	}
	
	public static void BTS()
	{
		int x = 2;
		int[] aux = new int[N];
		
		while (true) {
			if (RT > 0) {
				RRS[RP] = "k = " + Integer.toString(x) + "\n";
				RP = (RP + 1) % RT;
				RTT++;
			} else System.out.printf("k = %d\n", x);
			
			if (BTSR(0, x, aux)) break;
			x++;
		}
		
		if (RT > 0) print();
	}
	
	public static int bit(int x)
	{
		int cr = 0;
		
		while (x > 0) {
			cr += x & 1;
			x >>= 1;
		}
		
		return cr;
	}
	
	public static void DPSR(int i, int k, int mask)
	{
		if (k == 0) {
			System.out.printf("{} : 0\n");
			DP[0] = 0;
			return;
		}
		
		if (k == 1 && bit(mask) == k) {
			DP[mask] = 1;
			return;
		}
		
		if (bit(mask) == k) {
			int p = 0;
			int[] w = new int[32];
			
			for (int j = 0; j < 32; j++)
				if ((mask & (1 << j)) != 0)
					w[p++] = j;
			
			for (int j = 0; j < (1 << p); j++) {
				int cm = 0;
				
				for (int c = 0; c < p; c++)
					if ((j & (1 << c)) != 0)
						cm |= (1 << w[c]);
				
				boolean ok = true;
				
				for (int x = 0; x < 32 && ok; x++)
					if ((cm & (1 << x)) != 0)
						for (int y = 0; y < vdeg[x]; y++)
							if ((cm & (1 << graph[x][y])) != 0) {
								ok = false;
								break;
							}
				
				if (ok) DP[mask] = Math.min(DP[mask], 1 + DP[mask ^ cm]);
			}
			
			return;
		}
		
		if (i == N) return;
		
		DPSR(i + 1, k, mask | (1 << i));
		if (N - i - 1 + bit(mask) >= k) DPSR(i + 1, k, mask);
	}
	
	public static void DPS()
	{
		int x = 0;
		for (int i = 0; i < 1 << 17; i++) DP[i] = 1 << 17;
		
		while (x <= N) {
			DPSR(0, x, 0);
			x++;
		}
		
		for (x = 1; x <= N; x++)
			for (int y = 0; y < 1 << N; y++)
				if (bit(y) == x) {
					int kk = 0;
					
					System.out.printf("{");
					for (int j = 0; j < 32; j++)
						if ((y & (1 << j)) != 0) {
							if (kk > 0) System.out.printf(",");
							System.out.printf("%d", j);
							kk++;
						}
					
					System.out.printf("} : %d\n", DP[y]);
				}
	}
	
	public static void main(String[] args)
	{
		if (args.length < 1) return;
		
		read();
		
		if (args.length >= 3)
			if (args[1].equals("-n")) {
				RT = Integer.valueOf(args[2]);
				RRS = new String[RT];
			}
		
		if (args[0].equals("2c")) TCS();
		if (args[0].equals("gr")) CNV();
		if (args[0].equals("ex")) NBF();
		if (args[0].equals("bt")) BTS();
		if (args[0].equals("dp")) DPS();
	}
}