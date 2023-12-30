package spoj;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://www.spoj.com/problems/PRIME1/

public class Prime1 {
	static class SuperFastScanner {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;

		public SuperFastScanner() {
			din = new DataInputStream(System.in);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public SuperFastScanner(String file_name) throws IOException {
			din = new DataInputStream(new FileInputStream(file_name));
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public String readLine() throws IOException {
			byte[] buf = new byte[64]; // line length
			int cnt = 0, c;
			while ((c = read()) != -1) {
				if (c == '\n') {
					if (cnt != 0) {
						break;
					} else {
						continue;
					}
				}
				buf[cnt++] = (byte) c;
			}
			return new String(buf, 0, cnt);
		}

		public int nextInt() throws IOException {
			int ret = 0;
			byte c = read();
			while (c <= ' ') {
				c = read();
			}
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');

			if (neg)
				return -ret;
			return ret;
		}

		public long nextLong() throws IOException {
			long ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}

		public double nextDouble() throws IOException {
			double ret = 0, div = 1;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();

			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');

			if (c == '.') {
				while ((c = read()) >= '0' && c <= '9') {
					ret += (c - '0') / (div *= 10);
				}
			}

			if (neg)
				return -ret;
			return ret;
		}

		private void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		public void close() throws IOException {
			if (din == null)
				return;
			din.close();
		}
	}

	private static boolean sieve[];
	private static int N = 1_000_000_000; // The upper range. Algorithm can handle range up to 10^12

	public static void main(String[] args) throws IOException {
		initSieve();
		SuperFastScanner sc = new SuperFastScanner();
		int t_ = sc.nextInt();
		for (int t = 0; t < t_; t++) {
			int m = Math.max(sc.nextInt(), 2), n = sc.nextInt();
			printPrimes(m, n);
			if (t != t_ - 1)
				System.out.println();
		}
		sc.close();
	}

	private static void printPrimes(int m, int n) {
		if (n < m)
			return;
		List<Integer> primes = generatePrimes(n);
		boolean dummy[] = new boolean[n - m + 1];
		Arrays.fill(dummy, true);
		for (int prime : primes) {
			int firstMultiple = (int) (Math.ceil((double) m / prime)) * prime;
			for (int i = (int) Math.max(firstMultiple, prime * prime); i <= n; i += prime)
				dummy[i - m] = false;
		}
		for (int i = 0; i < dummy.length; i++) {
			if (dummy[i])
				System.out.println(i + m);
		}
	}

	private static List<Integer> generatePrimes(int n) {
		List<Integer> primes = new ArrayList<>();
		for (int i = 2; i * i <= n; i++) {
			if (sieve[i])
				primes.add(i);
		}
		return primes;
	}

	private static void initSieve() {
		int n = (int) Math.ceil(Math.sqrt(N));
		sieve = new boolean[n + 1];
		sieve[0] = false;
		sieve[1] = false;
		Arrays.fill(sieve, true);
		for (int i = 2; i * i <= n; i++) {
			if (sieve[i] == false)
				continue;
			for (int j = i * i; j <= n; j += i)
				sieve[j] = false;
		}

	}
}
