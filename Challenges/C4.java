import java.util.*;

public class C4
{
	public static int N;
	public static Complex arr[] = new Complex[1 << 17];
	
	public static Complex[] FFT_Rec(Complex[] val, int n)
	{
		if (n == 1) return val;
		
		Complex[] x0 = new Complex[n / 2];
		Complex[] x1 = new Complex[n / 2];
		for (int i = 0; i < n; i++)
			if (i % 2 == 0) x0[i / 2] = val[i].copy();
			else			x1[i / 2] = val[i].copy();

		Complex[] y0 = FFT_Rec(x0, n / 2);
		Complex[] y1 = FFT_Rec(x1, n / 2);
		
		Complex[] w  = new Complex[n];
		for (int i = 0; i < n; i++)
			w[i] = new Complex(Math.cos((2.0 * Math.PI * i) / n), Math.sin((2.0 * Math.PI * i) / n));
		
		Complex[] cr = new Complex[n];
		for (int i = 0; i < n / 2; i++) {
			cr[i] = y0[i].add(w[i].multiply(y1[i])).copy();
			cr[i + n / 2] = y0[i].add(w[i].multiply(y1[i]).multiply(-1.0)).copy();
		}
		
		for (int i = 0; i < n; i++) cr[i].print(i == n - 1);
		
		return cr;		
	}
	
	public static void main(String[] args)
	{
		if (args.length == 0) return;
		while ((1 << N) < Integer.valueOf(args[0])) N++;
		
		Scanner cin = new Scanner(System.in);
		for (int i = 0; i < Integer.valueOf(args[0]); i++) arr[i] = new Complex(cin.nextDouble(), 0.0);
		for (int i = Integer.valueOf(args[0]); i < (1 << N); i++) arr[i] = new Complex(0.0, 0.0);
		
		FFT_Rec(arr, 1 << N);
	}
}

class Complex
{
	public double re;
	public double im;
	
	public Complex(double r, double i)
	{
		this.re = r;
		this.im = i;
	}
	
	public void print(boolean nl)
	{
		System.out.print(this.re + (this.im >= 0 ? "+" : "") + this.im + "i" + " ");
		if (nl) System.out.println();
	}
	
	public void printRoots(int n)
	{
		for (int i = 0; i < n; i++)
			(new Complex(Math.cos((angle() + 2.0 * Math.PI * i) / n), Math.sin((angle() + 2.0 * Math.PI * i) / n))).multiply(Math.pow(length(), 1.0 / n)).print(false);
	}
	
	public Complex copy()
	{
		return new Complex(this.re, this.im);
	}
	
	public static Complex parse(String line)
	{
		return new Complex(Double.parseDouble(line.substring(0, Math.max(line.lastIndexOf("+"), line.lastIndexOf("-")))), (line.lastIndexOf("+") > 0 ? 1 : -1) * Double.parseDouble(line.substring(Math.max(line.lastIndexOf("+"), line.lastIndexOf("-")) + 1, line.length() - 1)));
	}
	
	public static Complex getNthPrimitiveRoot(int n)
	{
		return new Complex(Math.cos(2.0 * Math.PI / (double) n), Math.sin(2.0 * Math.PI / (double) n));
	}
	
	public Complex power(double n)
	{
		return new Complex(Math.cos(angle() * n), Math.sin(angle() * n));
	}
	
	public double angle()
	{
		return Math.atan(this.im / this.re);
	}
	
	public double length()
	{
		return Math.sqrt(multiply(conjugate()).re);
	}
	
	public Complex add(Complex x)
	{
		return new Complex(this.re + x.re, this.im + x.im);
	}
	
	public Complex multiply(Complex x)
	{
		return new Complex(this.re * x.re - this.im * x.im, this.re * x.im + this.im * x.re);
	}
	
	public Complex multiply(double x)
	{
		return new Complex(this.re * x, this.im * x);
	}
	
	public Complex conjugate()
	{
		return new Complex(this.re, -this.im);
	}
	
	public Complex divide(Complex x)
	{
		return multiply(x.conjugate()).multiply(1.0 / (x.multiply(x.conjugate()).re));
	}
}