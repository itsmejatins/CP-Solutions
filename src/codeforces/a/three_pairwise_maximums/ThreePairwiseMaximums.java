package codeforces.a.three_pairwise_maximums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// Solution fails. Debug remaining
public class ThreePairwiseMaximums {
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
			int arr[] = new int[3];
			for (int i = 0; i < 3; i++)
				arr[i] = sc.nextInt();
			Arrays.sort(arr);
			if (arr[2] > arr[0] && arr[2] > arr[1])
				System.out.println("NO");
			else {
				System.out.println("YES");
				System.out.println(arr[1] - 1 + " " + arr[1] + " " + arr[2]);
			}

		}
	}
}
