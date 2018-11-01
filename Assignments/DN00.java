import java.util.*;

public class DN00
{
	public static void main(String[] args)
	{
		boolean done = false;
		
		if (args.length > 0)
		{
			if (args[0].equals("smisel"))
			{
				done = true;
				System.out.println("Zivljenje je lepo, ce programiras.");
				System.exit(42);
			}
			else if (args[0].equals("eho"))
			{
				done = true;
				for (int i = 1; i < args.length; i++)
					System.out.print(args[i] + " ");
				System.out.println();
			}
			else if (args[0].equals("macka"))
			{
				done = true;
				Scanner cin = new Scanner(System.in);
				while (cin.hasNextLine())
					System.out.println(cin.nextLine());
			}
		}
		
		if (!done)
		{
			System.out.println("Podaj argumente prijatelj.");
			System.exit(1);
		}
	}
}