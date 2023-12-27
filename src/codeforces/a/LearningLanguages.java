package codeforces.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

// https://codeforces.com/problemset/problem/277/A

public class LearningLanguages
{

	static class DSU
	{
		int parent[], rank[];
		int nComponents = 0;

		public DSU(int n)
		{
			parent = new int[n];
			Arrays.fill(parent, -1);
			rank = new int[n];
			Arrays.fill(rank, -1);
		}

		public void createSet(int i)
		{
			parent[i] = i;
			rank[i] = 0;
			nComponents++;
		}

		public int getParent(int i)
		{
			if (parent[i] != i)
			{
				parent[i] = getParent(parent[i]);
			}
			return parent[i];
		}

		public void union(int x, int y)
		{
			int pX = getParent(x);
			int pY = getParent(y);

			if (pX == pY)
				return;

			if (rank[pX] > rank[pY])
			{
				parent[pY] = pX;
				nComponents--;
			}
			else
			{
				parent[pX] = pY;
				if (rank[pX] == rank[pY])
				{
					rank[pY]++;
				}
				nComponents--;
			}

		}
	}

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
		int n, m;

		FastScanner sc = new FastScanner(System.in);
		n = sc.nextInt();
		m = sc.nextInt();

		List<List<Integer>> list = new ArrayList<>(m + 1);

		for (int i = 0; i < m + 1; i++)
			list.add(new ArrayList<>());

		for (int n_ = 0; n_ < n; n_++)
		{
			int k = sc.nextInt();
			if (k == 0)
				list.get(0).add(n_);
			for (int k_ = 0; k_ < k; k_++)
			{
				int l = sc.nextInt();
				list.get(l).add(n_);
			}
		}

		if (list.get(0).size() == n)
		{
			System.out.println(n);
			return;
		}

		DSU dsu = new DSU(n);
		for (int i = 0; i < n; i++)
			dsu.createSet(i);

		for (List<Integer> people : list)
		{
			for (int i = 0; i < people.size() - 1; i++)
				dsu.union(people.get(i), people.get(i + 1));
		}

		int ans = -1;
		if (dsu.nComponents == 1)
			ans = 0;
		else if (list.get(0).size() == 0)
			ans = dsu.nComponents - 1;
		else
			ans = dsu.nComponents - 1 - 1 + list.get(0).size();
		System.out.println(ans);

	}
}