package codeforces.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// https://codeforces.com/problemset/problem/510/A

public class FoxAndSnake
{
	static class FastScanner
	{
		private BufferedReader br;
		private StringTokenizer st;

		public FastScanner(InputStream stream)
		{
			try
			{
				br = new BufferedReader(new InputStreamReader(stream));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public String next()
		{
			while (st == null || !st.hasMoreTokens())
			{
				try
				{
					st = new StringTokenizer(br.readLine());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		public int nextInt()
		{
			return Integer.parseInt(next());
		}

	}

	public static void main(String[] args)
	{
		FastScanner sc = new FastScanner(System.in);
		int n = sc.nextInt(), m = sc.nextInt();

		String one = "#".repeat(m);
		String two = ".".repeat(m - 1) + "#";
		String three = "#" + ".".repeat(m - 1);

		for (int i = 0; i < n; i++)
		{
			if ((i & 1) == 0)
				System.out.print(one);
			else if (i % 4 == 1)
				System.out.print(two);
			else if (i % 4 == 3)
				System.out.print(three);
			if (i != n - 1)
				System.out.println();
		}
	}
}
