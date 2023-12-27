package codeforces.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


// failing, RTE on test case 4.
public class SlothNaptime {

	private static class FastScanner {
		private BufferedReader br;
		private StringTokenizer st;

		public FastScanner(InputStream stream) {
			try {
				br = new BufferedReader(new InputStreamReader(stream));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public void close() {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static int level[], spTab[][];

	public static void main(String[] args) {

		level = null;
		spTab = null;

		FastScanner sc = new FastScanner(System.in);

		List<List<Integer>> adj = getAdjFromInput(sc, false);
		if (adj.size() == 1) { // if there is only one node, sloth stays there always, irrespective of energy
			int q_ = sc.nextInt();
			for (int q = 0; q < q_; q++) {
				int a = sc.nextInt();
				sc.nextInt();
				sc.nextInt();
				System.out.println(a);
			}
			sc.close();
			return;
		}

		fillSparseTable(adj);

		int q_ = sc.nextInt();
		for (int q = 0; q < q_; q++) {
			int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
			int finalPosition = processQuery(a - 1, b - 1, c);
			System.out.println(finalPosition + 1);
		}
		sc.close();

	}

	private static int processQuery(int start, int end, int ener) {
		// path to go from start to end is start -> lca -> end
		int lca = lca(start, end);
		int d1 = Math.abs(level[start] - level[lca]), d2 = Math.abs(level[lca] - level[end]);
		int dis = d1 + d2;
		if (ener >= dis) // you can easily reach end
			return end;
		if (ener <= d1) // you will be at a position between start and lca
			return jump(start, ener);
		return jump(end, dis - ener); // you will be at a position between lca and end
	}

	private static int lca(int n1, int n2) {
		if (level[n1] != level[n2]) {
			if (level[n1] < level[n2]) {
				int temp = n1;
				n1 = n2;
				n2 = temp;
			}

			int jumpsReq = level[n1] - level[n2];
			n1 = jump(n1, jumpsReq);
		}
		if (n1 == n2)
			return n1;
		for (int j = level[n1]; j >= 0; j--) {
			int jn1 = jump(n1, j), jn2 = jump(n2, j);
			if (jn1 == jn2)
				continue;
			n1 = jn1;
			n2 = jn2;
		}

		return spTab[n1][0];
	}

	private static int jump(int n1, int jumpsReq) {
		if (jumpsReq == 0)
			return n1;
		int digits = (int) Math.ceil(Math.log(jumpsReq) / Math.log(2) + 1);
		for (int j = 0; j < digits; j++) {
			if (((1 << j) & jumpsReq) != 0) {
				n1 = spTab[n1][j];
			}
		}
		return n1;
	}

	private static void fillLevels(int r, int p, int l, List<List<Integer>> adj) {
		level[r] = l;
		for (int v : adj.get(r)) {
			if (v == p)
				continue;
			fillLevels(v, r, l + 1, adj);
		}
	}

	private static void fillSparseTable(List<List<Integer>> adj) {
		int n = adj.size();

		level = new int[n];
		fillLevels(0, -1, 0, adj);

		int maxLevel = -1;
		for (int i : level)
			maxLevel = Math.max(maxLevel, i);

		int width = (int) Math.ceil(Math.log(maxLevel) / Math.log(2) + 1);
		spTab = new int[n][width];
//		dfs(0, -1, 0, adj);
		
		for(int u = 0; u < n; u++)
		{
			for(int v : adj.get(u))
			{
				if(level[u] < level[v])
					spTab[v][0] = u;
			}
		}
		
		for (int col = 1; col < spTab[0].length; col++) {
			for (int row = 0; row < spTab.length; row++) {
				int firstJumpParent = spTab[row][col - 1];
				if (firstJumpParent == -1)
					spTab[row][col] = -1;
				else
					spTab[row][col] = spTab[firstJumpParent][col - 1];

			}
		}
	}

	private static void dfs(int r, int p, int l, List<List<Integer>> adj) {
		spTab[r][0] = p;
		for (int v : adj.get(r)) {
			if (v == p)
				continue;
			dfs(v, r, l + 1, adj);
		}

	}

	private static List<List<Integer>> getAdjFromInput(FastScanner sc, boolean directed) {
		boolean close = sc == null;
		if (sc == null)
			sc = new FastScanner(System.in);

		int n = sc.nextInt(); // total number of nodes
		int m = n - 1; // number of edges

		List<List<Integer>> adj = new ArrayList<>();
		for (int i = 0; i < n; i++)
			adj.add(new ArrayList<>());

		for (int i = 0; i < m; i++) {
			int u = sc.nextInt(), v = sc.nextInt();
			adj.get(u - 1).add(v - 1);
			if (!directed)
				adj.get(v - 1).add(u - 1);
		}
		if (close)
			sc.close();
		return adj;

	}

}