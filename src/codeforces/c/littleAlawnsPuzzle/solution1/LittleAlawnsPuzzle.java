package codeforces.c.littleAlawnsPuzzle.solution1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

// two times faster than solution 2
// second solution is the tutorial one.
public class LittleAlawnsPuzzle {

	static class Dsu {
		private int parent[], rank[];
		private int setsCount = 0;
		private Map<Integer, Integer> setsSizeMap; // (id, size)
		private boolean wantSetsSizeMap = false;

		public Dsu(int n) {
			parent = new int[n];
			Arrays.fill(parent, -1);

			rank = new int[n];
			Arrays.fill(rank, -1);

		}

		public Dsu(int n, boolean wantSetsSizeMap) {
			this(n);
			if (wantSetsSizeMap) {
				setsSizeMap = new HashMap<>();
				this.wantSetsSizeMap = true;
			}
		}

		public void createSet(int i) {
			parent[i] = i;
			rank[i] = 0;
			setsCount++;
			if (wantSetsSizeMap) {
				setsSizeMap.put(i, 1);
			}
		}

		public int getParent(int i) {
			if (parent[i] != i) {
				parent[i] = getParent(parent[i]);
			}
			return parent[i];
		}

		public void union(int x, int y) {
			int pX = getParent(x);
			int pY = getParent(y);

			if (pX == pY)
				return;

			if (rank[pX] > rank[pY]) {
				if (wantSetsSizeMap) {
					int a = setsSizeMap.get(pX), b = setsSizeMap.get(pY);
					setsSizeMap.put(pX, a + b);
					setsSizeMap.remove(pY);
				}
				parent[pY] = pX;
				setsCount--;
			} else {
				if (wantSetsSizeMap) {
					int a = setsSizeMap.get(pX), b = setsSizeMap.get(pY);
					setsSizeMap.put(pY, a + b);
					setsSizeMap.remove(pX);
				}
				parent[pX] = pY;
				if (rank[pX] == rank[pY]) {
					rank[pY]++;
				}
				setsCount--;
			}

		}

		public int getSetsCount() {
			return setsCount;
		}

		public Map<Integer, Integer> getSetsSizeMap() {
			if (wantSetsSizeMap)
				return setsSizeMap;
			else
				throw new RuntimeException("map not prepared");
		}

		public Map<Integer, List<Integer>> getSets() {
			Map<Integer, List<Integer>> map = new HashMap<>();

			for (int node = 0; node < parent.length; node++) {
				int p = getParent(node);
				map.putIfAbsent(p, new ArrayList<>());
				map.get(p).add(p);
			}

			return map;
		}
	}

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
		Dsu dsu = new Dsu(n);
		for (int i = 0; i < n; i++)
			dsu.createSet(i);

		for (int c = 0; c < n; c++)
			dsu.union(arr[0][c], arr[1][c]);

		int cyclesCount = dsu.getSetsCount();

		return binaryExponentiation(2, cyclesCount, (int) (1e9 + 7));
	}

	private static int binaryExponentiation(long a, long b, int mod) {
		long ans = 1;
		if (a == 1 || b == 0)
			return 1;
		if (a == 0)
			return 0;
		if (b == 1)
			return (int) a;

		while (b > 0) {
			if ((b & 1) != 0) {
				ans = (ans * a) % mod;
				b--;
			} else {
				a = (a * a) % mod;
				b = b >> 1;
			}
		}
		return (int) ans;
	}
}