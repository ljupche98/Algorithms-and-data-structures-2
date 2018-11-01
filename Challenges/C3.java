import java.util.*;

class Complex
{
	public double re;
	public double im;
	
	public Complex(double r, double i)
	{
		this.re = r;
		this.im = i;
	}
	
	public void print()
	{
		System.out.println(this.re + (this.im >= 0 ? "+" : "") + this.im + "i");
	}
	
	public void printRoots(int n)
	{
		for (int i = 0; i < n; i++)
			(new Complex(Math.cos((angle() + 2.0 * Math.PI * i) / n), Math.sin((angle() + 2.0 * Math.PI * i) / n))).multiply(Math.pow(length(), 1.0 / n)).print();
	}
	
	public static Complex parse(String line)
	{
		return new Complex(Double.parseDouble(line.substring(0, Math.max(line.lastIndexOf("+"), line.lastIndexOf("-")))), (line.lastIndexOf("+") > 0 ? 1 : -1) * Double.parseDouble(line.substring(Math.max(line.lastIndexOf("+"), line.lastIndexOf("-")) + 1, line.length() - 1)));
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

public class C3
{
	public static void main(String[] args)
	{
		Scanner cin = new Scanner(System.in);
		
		while (true)
		{
			System.out.println("Vtipkaj ukaz (+, -, *, /, w):");
			
			switch (cin.next())
			{
				case "+": Complex.parse(cin.next()).add(Complex.parse(cin.next())).print();					break;
				case "-": Complex.parse(cin.next()).add(Complex.parse(cin.next()).multiply(-1.0)).print(); 	break;
				case "*": Complex.parse(cin.next()).multiply(Complex.parse(cin.next())).print();			break;
				case "/": Complex.parse(cin.next()).divide(Complex.parse(cin.next())).print(); 				break;
				case "w": (new Complex(1.0, 0.0)).printRoots(Integer.parseInt(cin.next())); 				break;
			}
		}
	}
}