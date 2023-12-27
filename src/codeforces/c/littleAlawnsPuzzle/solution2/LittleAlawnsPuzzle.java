package codeforces.c.littleAlawnsPuzzle.solution2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// two times slower than solution 1
// solution as per the tutorial
public class LittleAlawnsPuzzle {

	static class FastScanner {
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
				System.err.println("Error in closing fast scanner");
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		FastScanner sc = new FastScanner(System.in);
		int t_ = sc.nextInt();
		for (int t = 0; t < t_; t++) {
			int n = sc.nextInt();
			int arr[][] = new int[2][n];
			for (int r = 0; r < 2; r++) {
				for (int c = 0; c < n; c++) {
					arr[r][c] = sc.nextInt() - 1;
				}
			}

			int ans = solve(arr);
			System.out.println(ans);
		}
	}

	private static int solve(int[][] arr) {
		int n = arr[0].length;
		List<List<Integer>> adj = new ArrayList<>(n);
		for (int i = 0; i < n; i++)
			adj.add(new ArrayList<>());
		for (int c = 0; c < n; c++) {
			adj.get(arr[0][c]).add(arr[1][c]);
			adj.get(arr[1][c]).add(arr[0][c]);
		}

		boolean visited[] = new boolean[n];
		int ans = 1, mod = (int) (1e9 + 7);
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				dfs(i, -1, adj, visited);
				ans = (int) ((ans * 2l) % mod);
			}
		}
		return ans;
	}

	private static void dfs(int u, int p, List<List<Integer>> adj, boolean[] visited) {
		visited[u] = true;
		for (int v : adj.get(u)) {
			if (v == p)
				continue;
			if (!visited[v])
				dfs(v, u, adj, visited);
		}
	}
}