import java.util.*;

public class DN02
{
	public static void init(char[][] x, char y)
	{
		for (int i = 0; i < x.length; i++)
			for (int j = 0; j < x[0].length; j++)
				x[i][j] = y;
	}
	
	public static String multiply(String x, String y)
	{
		assert y.length() == 1;
		
		if (y.equals("0")) return "0";
		
		int rem = 0;
		String z = "";
		
		for (int i = x.length() - 1; i >= 0; i--) {
			z = (char) (((x.charAt(i) - '0') * (y.charAt(0) - '0') + rem) % 10 + '0') + z;
			rem = ((x.charAt(i) - '0') * (y.charAt(0) - '0') + rem) / 10;
		}
		
		if (rem > 0) z = (char) (rem + '0') + z;
		
		return z;
	}
	
	public static String removez(String x)
	{
		for (int i = 0; i < x.length(); i++)
			if (x.charAt(i) != '0')
				return x.substring(i);
		return "0";
	}
	
	public static String sumn(char[][] x)
	{
		int rem = 0;
		String ans = "";
		
		for (int j = x[0].length - 1; j >= 0; j--) {
			int cs = 0;
			for (int i = 0; i < x.length; i++) cs += x[i][j] - '0';
			ans = (char) ((cs + rem) % 10 + '0') + ans;
			rem = (cs + rem) / 10;
		}
		
		if (rem > 0) ans = (char) (rem + '0') + ans;
		
		ans = removez(ans);
		
		return ans;
	}
	
	public static String subn(char[][] x)
	{		
		int rem = 0;
		String ans = "";
		
		for (int j = x[0].length - 1; j >= 0; j--) {
			int cs = (x[0][j] - '0') - (x[1][j] - '0') - rem;
			
			if (cs >= 0) {
				rem = 0;
				ans = (char) (cs + '0') + ans;
			} else {
				rem = 1;
				ans = (char) (cs + 10 + '0') + ans;
			}
		}
		
		ans = removez(ans);
		
		return ans;
	}
	
	public static void multiplyNaive(String x, String y)
	{
		String mr = "", ans = "";
		char[][] mults = new char[y.length()][2 * x.length()];
		init(mults, '0');
		
		for (int i = y.length() - 1; i >= 0; i--) {
			String cr = multiply(x, Character.toString(y.charAt(i)));
			mr = cr + "\n" + mr;
			
			for (int j = cr.length() - 1; j >= 0; j--)
				mults[i][mults[i].length + i + j + 1 - y.length() - cr.length()] = cr.charAt(j);
			
			/// mults[i].length - 1 - (y.length() - 1 - i) - (cr.length() - 1 - j)
			/// mults[i].length - y.length() + 1 + i - cr.length() + j
		}
		
		ans = sumn(mults);
		
		for (int i = 0; i < ans.length(); i++) mr += '-';

		System.out.println(mr + "\n" + ans);
	}
	
	public static int findIdx(int len, int trg)
	{
		if (trg >= len) return 0;
		return len - trg;
	}
	
	public static String DC(String x, String y)
	{
		x = removez(x); y = removez(y);
		System.out.println(x + " " + y);
		
		if (x.equals("0") || y.equals("0")) return "0";
		if (x.length() == 1) return multiply(y, x);
		if (y.length() == 1) return multiply(x, y);
		
		int len = Math.max(x.length(), y.length()); len += (len % 2 != 0 ? 1 : 0);
		
		int r = findIdx(x.length(), len / 2);
		String x0 = x.substring(r), x1 = "0";
		if (r > 0) x1 = x.substring(0, r);
		
		r = findIdx(y.length(), len / 2);
		String y0 = y.substring(r), y1 = "0";
		if (r > 0) y1 = y.substring(0, r);
		
		String x0y0 = DC(x0, y0); System.out.println(x0y0);
		String x0y1 = DC(x0, y1); System.out.println(x0y1);
		String x1y0 = DC(x1, y0); System.out.println(x1y0);
		String x1y1 = DC(x1, y1); System.out.println(x1y1);
		
		char[][] sumx = new char[3][x1y1.length() + len + 2];
		
		init(sumx, '0');
		for (int i = x0y1.length() - 1; i >= 0; i--) sumx[0][sumx[0].length + i - x0y1.length()] = x0y1.charAt(i);
		for (int i = x1y0.length() - 1; i >= 0; i--) sumx[1][sumx[1].length + i - x1y0.length()] = x1y0.charAt(i);
		
		String cr = sumn(sumx);
		
		assert cr.length() + len / 2 <= x1y1.length() + len;
		
		init(sumx, '0');
		for (int i = x1y1.length() - 1; i >= 0; i--) sumx[0][sumx[0].length - 1 - len - (x1y1.length() - 1 - i)] = x1y1.charAt(i);
		for (int i = cr.length() - 1; i >= 0; i--)   sumx[1][sumx[1].length - 1 - len / 2 - (cr.length() - 1 - i)] = cr.charAt(i);
		for (int i = x0y0.length() - 1; i >= 0; i--) sumx[2][sumx[2].length - 1 - (x0y0.length() - 1 - i)] = x0y0.charAt(i);
				
		return sumn(sumx);
	}
	
	public static String DCF(String x, String y)
	{
		x = removez(x); y = removez(y);
		System.out.println(x + " " + y);
		
		if (x.equals("0") || y.equals("0")) return "0";
		if (x.length() == 1) return multiply(y, x);
		if (y.length() == 1) return multiply(x, y);
		
		int len = Math.max(x.length(), y.length()); len += (len % 2 != 0 ? 1 : 0);
		
		int r = findIdx(x.length(), len / 2);
		String x0 = x.substring(r), x1 = "0";
		if (r > 0) x1 = x.substring(0, r);
		
		r = findIdx(y.length(), len / 2);
		String y0 = y.substring(r), y1 = "0";
		if (r > 0) y1 = y.substring(0, r);
		
		String x0y0 = DCF(x0, y0); System.out.println(x0y0);
		String x1y1 = DCF(x1, y1); System.out.println(x1y1);
		
		char[][] sumx = new char[3][x1y1.length() + len + 2];
		
		init(sumx, '0');
		for (int i = x0.length() - 1; i >= 0; i--) sumx[0][sumx[0].length + i - x0.length()] = x0.charAt(i);
		for (int i = x1.length() - 1; i >= 0; i--) sumx[1][sumx[1].length + i - x1.length()] = x1.charAt(i);
		String x0x1 = sumn(sumx);
		
		init(sumx, '0');
		for (int i = y0.length() - 1; i >= 0; i--) sumx[0][sumx[0].length + i - y0.length()] = y0.charAt(i);
		for (int i = y1.length() - 1; i >= 0; i--) sumx[1][sumx[1].length + i - y1.length()] = y1.charAt(i);
		String y0y1 = sumn(sumx);
		
		
		String cr = DCF(x0x1, y0y1); System.out.println(cr);
		
		init(sumx, '0');
		for (int i = x1y1.length() - 1; i >= 0; i--) sumx[0][sumx[0].length - 1 - len - (x1y1.length() - 1 - i)] = x1y1.charAt(i);
		for (int i = cr.length() - 1; i >= 0; i--) 	 sumx[1][sumx[1].length - 1 - len / 2 - (cr.length() - 1 - i)] = cr.charAt(i);
		for (int i = x0y0.length() - 1; i >= 0; i--) sumx[2][sumx[2].length - 1 - (x0y0.length() - 1 - i)] = x0y0.charAt(i);
		String crr = sumn(sumx);
		
		init(sumx, '0');
		for (int i = x1y1.length() - 1; i >= 0; i--) sumx[0][sumx[0].length + i - x1y1.length()] = x1y1.charAt(i);
		for (int i = x0y0.length() - 1; i >= 0; i--) sumx[1][sumx[1].length + i - x0y0.length()] = x0y0.charAt(i);
		String sub = sumn(sumx);
		
		init(sumx, '0');
		for (int i = crr.length() - 1; i >= 0; i--) sumx[0][sumx[0].length + i - crr.length()] = crr.charAt(i);
		for (int i = sub.length() - 1; i >= 0; i--) sumx[1][sumx[1].length - 1 - len / 2 - (sub.length() - 1 - i)] = sub.charAt(i);
		
		String ans = subn(sumx);
		
		return ans;
	}
	
	public static int[][] readMx(Scanner cin)
	{
		int[][] x = new int[cin.nextInt()][cin.nextInt()];
		
		for (int i = 0; i < x.length; i++)
			for (int j = 0; j < x[i].length; j++)
				x[i][j] = cin.nextInt();
		
		return x;
	}
	
	public static int[][] multiply(int[][] x, int[][] y, boolean print)
	{
		assert x[0].length == y.length;
		
		int[][] z = new int[x.length][y[0].length];
		
		for (int i = 0; i < z.length; i++)
			for (int j = 0; j < z[i].length; j++)
				for (int k = 0; k < x[0].length; k++)
					z[i][j] += x[i][k] * y[k][j];
		
		if (print) {
			System.out.println("DIMS: " + z.length + "x" + z[0].length);
			
			for (int i = 0; i < z.length; i++, System.out.println())
				for (int j = 0; j < z[i].length; j++)
					System.out.print(z[i][j] + " ");
		}
		
		return z;
	}
	
	public static int[][] merge(int[][] x, int[][] y, int f)
	{
		assert x.length == y.length && x[0].length == y[0].length;
		
		int[][] z = new int[x.length][x[0].length];
		
		for (int i = 0; i < z.length; i++)
			for (int j = 0; j < z[i].length; j++)
				z[i][j] = x[i][j] + f * y[i][j];
			
		return z;
	}
	
	public static int nextPow(int x)
	{
		int y = 1;
		while (y < x) y <<= 1;
		return y;
	}
	
	public static int[][] inc(int[][] x, int y)
	{
		int[][] z = new int[y][y];
		
		for (int i = 0; i < x.length; i++)
			for (int j = 0; j < x[i].length; j++)
				z[i][j] = x[i][j];
			
		return z;
	}
	
	public static void copy(int[][] x, int[][] y, int xs, int ys)
	{
		for (int i = 0; i < x.length; i++)
			for (int j = 0; j < x[i].length; j++)
				y[xs + i][ys + j] = x[i][j];
	}
	
	public static int[][] combine(int[][] x, int[][] y, int[][] z, int[][] w)
	{
		int[][] u = new int[x.length << 1][x[0].length << 1];
		
		copy(x, u, 0, 0);
		copy(y, u, 0, x[0].length);
		copy(z, u, x.length, 0);
		copy(w, u, x.length, x[0].length);
		
		return u;
	}
	
	public static int[][] div(int[][] x, int xs, int xf, int ys, int yf)
	{
		int[][] y = new int[x.length / 2][x[0].length / 2];
		
		for (int i = xs; i < xf; i++)
			for (int j = ys; j < yf; j++)
				y[i - xs][j - ys] = x[i][j];
			
		return y;
	}
	
	public static int[][] DC(int[][] x, int[][] y, int w, boolean print)
	{
		if (print) {
			x = inc(x, nextPow(Math.max(Math.max(x.length, x[0].length), Math.max(y.length, y[0].length))));
			y = inc(y, nextPow(Math.max(Math.max(x.length, x[0].length), Math.max(y.length, y[0].length))));
		}
		
		int[][] z;
		
		if (x.length <= w) z = multiply(x, y, false);
		else {
			int[][][][] X = new int[2][2][][], Y = new int[2][2][][];
			
			for (int i = 0; i < 2; i++)
				for (int j = 0; j < 2; j++) {
					X[i][j] = div(x, (i == 0 ? 0 : x.length / 2), (i == 0 ? x.length / 2 : x.length), (j == 0 ? 0 : x.length / 2), (j == 0 ? x.length / 2 : x.length));
					Y[i][j] = div(y, (i == 0 ? 0 : y.length / 2), (i == 0 ? y.length / 2 : y.length), (j == 0 ? 0 : y.length / 2), (j == 0 ? y.length / 2 : y.length));
				}
			
			z = combine(merge(DC(X[0][0], Y[0][0], w, false), DC(X[0][1], Y[1][0], w, false), 1), merge(DC(X[0][0], Y[0][1], w, false), DC(X[0][1], Y[1][1], w, false), 1), merge(DC(X[1][0], Y[0][0], w, false), DC(X[1][1], Y[1][0], w, false), 1), merge(DC(X[1][0], Y[0][1], w, false), DC(X[1][1], Y[1][1], w, false), 1));			
		}
		
		int s = 0;
		
		if (print) System.out.println("DIMS: " + z.length + "x" + z[0].length);
		
		for (int i = 0; i < z.length; i++) {
			for (int j = 0; j < z[i].length; j++)
				if (print) System.out.print(z[i][j] + " ");
				else s += z[i][j];
			
			if (print) System.out.println();
		}
		
		if (!print) System.out.println(s);
		
		return z;
	}
	
	public static int[][] DCF(int[][] x, int[][] y, int w, boolean print)
	{
		if (print) {
			x = inc(x, nextPow(Math.max(Math.max(x.length, x[0].length), Math.max(y.length, y[0].length))));
			y = inc(y, nextPow(Math.max(Math.max(x.length, x[0].length), Math.max(y.length, y[0].length))));
		}
		
		int[][] z;
		
		if (x.length <= w) z = multiply(x, y, false);
		else {
			int[][][][] X = new int[2][2][][], Y = new int[2][2][][];
			
			for (int i = 0; i < 2; i++)
				for (int j = 0; j < 2; j++) {
					X[i][j] = div(x, (i == 0 ? 0 : x.length / 2), (i == 0 ? x.length / 2 : x.length), (j == 0 ? 0 : x.length / 2), (j == 0 ? x.length / 2 : x.length));
					Y[i][j] = div(y, (i == 0 ? 0 : y.length / 2), (i == 0 ? y.length / 2 : y.length), (j == 0 ? 0 : y.length / 2), (j == 0 ? y.length / 2 : y.length));
				}
			
			int[][][] P = new int[7][][];
			P[0] = DCF(X[0][0], merge(Y[0][1], Y[1][1], -1), w, false);
			P[1] = DCF(merge(X[0][0], X[0][1], 1), Y[1][1], w, false);
			P[2] = DCF(merge(X[1][0], X[1][1], 1), Y[0][0], w, false);
			P[3] = DCF(X[1][1], merge(Y[1][0], Y[0][0], -1), w, false);
			P[4] = DCF(merge(X[0][0], X[1][1], 1), merge(Y[0][0], Y[1][1], 1), w, false);
			P[5] = DCF(merge(X[0][1], X[1][1], -1), merge(Y[1][0], Y[1][1], 1), w, false);
			P[6] = DCF(merge(X[0][0], X[1][0], -1), merge(Y[0][0], Y[0][1], 1), w, false);
			z = combine(merge(merge(merge(P[3], P[4], 1), P[5], 1), P[1], -1), merge(P[0], P[1], 1), merge(P[2], P[3], 1), merge(merge(merge(P[0], P[4], 1), P[2], -1), P[6], -1));
		}
		
		int s = 0;
		
		if (print) System.out.println("DIMS: " + z.length + "x" + z[0].length);
		
		for (int i = 0; i < z.length; i++) {
			for (int j = 0; j < z[i].length; j++)
				if (print) System.out.print(z[i][j] + " ");
				else s += z[i][j];
			
			if (print) System.out.println();
		}
		
		if (!print) System.out.println(s);
		
		return z;
	}
	
	public static void main(String[] args)
	{
		Scanner cin = new Scanner(System.in);
		
		if (args[0].equals("num")) {
			if (args[1].equals("os")) multiplyNaive(removez(cin.next()), removez(cin.next()));
			if (args[1].equals("dv")) System.out.println(DC(removez(cin.next()), removez(cin.next())));
			if (args[1].equals("ka")) System.out.println(DCF(removez(cin.next()), removez(cin.next())));
		}
		
		if (args[0].equals("mat")) {
			if (args[1].equals("os")) multiply(readMx(cin), readMx(cin), true);
			if (args[1].equals("dv")) DC(readMx(cin), readMx(cin), Integer.valueOf(args.length > 2 ? args[2] : "1"), true);
			if (args[1].equals("st")) DCF(readMx(cin), readMx(cin), Integer.valueOf(args.length > 2 ? args[2] : "1"), true);
		}
	}
}