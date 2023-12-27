package codeforces.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// https://codeforces.com/problemset/problem/1371/A

public class MagicalSticks
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

		int t_ = sc.nextInt();
		for (int i = 0; i < t_; i++)
		{
			int n = sc.nextInt();
			System.out.println((n + 1) >> 1);
			// or System.out.println((int)Math.ceil(n / 2.0));
			
			// if n is even, pick leftmost and rightmost
			// if n is odd, leave the rightmost and do as in even.
		}
	}

}
