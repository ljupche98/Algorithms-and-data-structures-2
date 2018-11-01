import java.util.*;

public class C7
{
	public static final int maxn = 100000 + 17;
	public static final int maxw = 10000;
	public static final int maxp = 10000;
	
	public static int N, V;
	public static int[] w = new int[maxn];
	public static int[] p = new int[maxn];
	
	public static Map<Integer, Integer> m = new HashMap<Integer, Integer>();
	
	public static void DFS(int idx, int cw, int cp)
	{
		if (cw > V) return;
		
		if (idx == N) {
			if (m.get(cw) != null) m.put(cw, Math.max(m.get(cw), cp));
			else m.put(cw, cp);
			return;
		}
		
		DFS(idx + 1, cw, cp);
		DFS(idx + 1, cw + w[idx], cp + p[idx]);
	}
	
	public static void print()
	{
		for (Map.Entry<Integer, Integer> x : m.entrySet())
			System.out.printf("(%d, %d) ", x.getKey(), x.getValue());
		System.out.printf("\n");
	}
	
	public static void main(String[] args)
	{
		if (args.length < 1) return;
		
		if (args[0].equals("0")) {
			N = (int) (Math.random() * maxn) + 1;
			
			for (int i = 0; i < N; i++) w[i] = (int) (Math.random() * maxw) + 1;
			for (int i = 0; i < N; i++) p[i] = (int) (Math.random() * maxp) + 1;
			V = (int) (Math.random() * maxn * maxw) + 1;
		} else {
			Scanner cin = new Scanner(System.in);
			
			N = Integer.valueOf(args[0]);
			
			for (int i = 0; i < N; i++) w[i] = cin.nextInt();
			for (int i = 0; i < N; i++) p[i] = cin.nextInt();
			V = cin.nextInt();
		}
	
	/**
		System.out.printf("%d\n", N);
		for (int i = 0; i < N; i++) System.out.printf("%d ", w[i]); System.out.printf("\n");
		for (int i = 0; i < N; i++) System.out.printf("%d ", p[i]); System.out.printf("\n");
		System.out.printf("%d\n", V);
	**/
	
	/// DFS(0, 0, 0);
		
		m.put(0, 0);
		
		for (int i = 0; i < N; i++) {
			System.out.printf("%d: ", i); print();
			
			ArrayList<Integer> wl = new ArrayList<Integer>();
			ArrayList<Integer> pl = new ArrayList<Integer>();
			
			Iterator<Integer> itr = m.keySet().iterator();
			while (itr.hasNext()) {
				int key = itr.next();
				int nw = w[i] + key, np = p[i] + m.get(key);
				
				wl.add(nw);
				pl.add(np);
				
				if (m.get(nw) != null) System.out.printf("Odstranimo (%d, %d)\n", nw, Math.min(m.get(nw), np));
			}
			
			for (int j = 0; j < wl.size(); j++) {
				if (wl.get(j) > V) continue;
				
				if (m.get(wl.get(j)) == null) m.put(wl.get(j), pl.get(j));
				else m.put(wl.get(j), Math.max(m.get(wl.get(j)), pl.get(j)));
			}
			
			wl.clear();
			pl.clear();
			
			itr = m.keySet().iterator();
			while (itr.hasNext()) {
				int cw = itr.next(), cp = m.get(cw);
				wl.add(cw);
				pl.add(cp);
			}
			
			for (int k = 0; k < wl.size(); k++)
				for (int j = k + 1; j < wl.size(); j++)
					if (pl.get(j) <= pl.get(k))
						if (m.get(wl.get(j)) != null) {
							System.out.printf("Odstranimo (%d, %d)\n", wl.get(j), m.get(wl.get(j)));
							m.remove(wl.get(j));
						}
		}
		
		print();
	}
}