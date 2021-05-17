import java.util.Arrays;

public class divmultiplication {
	static String toString(double[] arr) {
		String res = "";
		for (double i : arr) res = res + i + " ";
		return res;
	}
	
	static void print(double[] arr) {
		System.out.println(toString(arr));
	}
	
	static String toString(double[][] arr) {
		String res = "";
		for (double[] r : arr) res = res + toString(r) + "\n";
		return res;
	}
	
	static void print(double[][] arr) {
		System.out.println(toString(arr));
	}
	
	public static double[][] eye(int size) {
		double[][] res = new double[size][size];
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[i].length; j++) res[i][j] = 0;
			res[i][i] = 1;
		}
		return res;
	}
	
	static boolean powerOfTwo(double x) {
		double i = 1;
		for (i = 1; i <= 10 * x; i *= 2) {
			if (i == x) return true;
		}
		return false;
	}
	
	static double[][] cell(double x) {
		double[][] c = {{x}};
		return c;
	}
	
	static double[][] sub(double[][] mat, int x1, int y1, int x2, int y2) {
		if (x1 > x2) {
			int temp = x2;
			x2 = x1;
			x1 = temp;
		}
		
		if (y1 > y2) {
			int temp = y2;
			y2 = y1;
			y1 = temp;
		}
		int x = x2 - x1;
		int y = y2 - y1;
		
		double[][] res = new double[x][y];
		
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				res[i][j] = mat[x1 + i][y1 + j];
			}
		}
		return res;
	}
	
	static double[][] add(double[][] a, double[][] b) {
		if (a.length != b.length) return a;
		if (a[0].length != b[0].length) return a;
		int n = a.length;
		int m = a[0].length;
		double[][] res = new double[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) res[i][j] = a[i][j] + b[i][j];
		}
		return res;
	}
	
	static double[][] subtract(double[][] a, double[][] b) {
		if (a.length != b.length) return a;
		if (a[0].length != b[0].length) return a;
		int n = a.length;
		int m = a[0].length;
		double[][] res = new double[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) res[i][j] = a[i][j] - b[i][j];
		}
		return res;
	}
	
	public static double[][] standard(double[][] one, double[][] two) {
		if (one[0].length != two.length) return eye(one.length);
		double[][] res = new double[one.length][two[0].length];
		
		double sum;
		for (int i = 0; i < res.length; i++) { // iterates on one's first index
			for (int j = 0; j < res[i].length; j++) { // iterates on two's second index
				sum = 0;
				for (int k = 0; k < one[i].length; k++) {
					sum += (one[i][k] * two[k][j]);
				}
				res[i][j] = sum;
			}
		}
		return res;
	}
	
	static double[][] divideandconquer(double[][] a, double [][] b) {
		if (a.length != a[0].length) return eye(a.length);
		if (b.length != b[0].length) return eye(b.length);
		if (a.length != b.length) return eye(Math.min(a.length, b.length));
	//	if (!powerOfTwo(a.length)) return eye(a.length);
		if (a.length == 1) return cell(a[0][0] * b[0][0]);
		boolean even = (a.length % 2 == 0);
		if (!even) {
			a = augment(a);
			b = augment(b);
		}
		int n = a.length;
		
		// partitions use O(n^2) time
		double[][] a11 = sub(a, 0, 0, n / 2, n / 2);
		double[][] a12 = sub(a, 0, n / 2, n / 2, n);
		double[][] a21 = sub(a, n / 2, 0, n, n / 2);
		double[][] a22 = sub(a, n / 2, n / 2, n, n);
		double[][] b11 = sub(b, 0, 0, n / 2, n / 2);
		double[][] b12 = sub(b, 0, n / 2, n / 2, n);
		double[][] b21 = sub(b, n / 2, 0, n, n / 2);
		double[][] b22 = sub(b, n / 2, n / 2, n, n);
		
		// RECURSIVE STEP : 8x per iteration with size n/2
		double[][] c11 = add(divideandconquer(a11, b11), divideandconquer(a12, b21));
		double[][] c12 = add(divideandconquer(a11, b12), divideandconquer(a12, b22));
		double[][] c21 = add(divideandconquer(a21, b11), divideandconquer(a22, b21));
		double[][] c22 = add(divideandconquer(a21, b12), divideandconquer(a22, b22));
		
		// O(n^2)
		double[][] res = new double[n][n];
		for (int i = 0; i < n / 2; i++) {
			for (int j = 0; j < n / 2; j++) {
				res[i][j] = c11[i][j];
				res[i + n / 2][j] = c21[i][j];
				res[i][j + n / 2] = c12[i][j];
				res[i + n / 2][j + n / 2] = c22[i][j];
			}
		}
		
		if (!even) return reduce(res);
		return res;
	}
	
	static double[][] augment(double[][] a) {
		int n = a.length;
		int m = a[0].length;
		double[][] res = new double[n + 1][m + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) res[i][j] = a[i][j];
		}
		return res;
	}
	
	static double[][] reduce(double[][] a) {
		int n = a.length;
		int m = a[0].length;
		double[][] res = new double[n - 1][m - 1];
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < m - 1; j++) res[i][j] = a[i][j];
		}
		return res;
	}
	
	static double[][] strassen(double[][] a, double [][] b) {
		if (a.length != a[0].length) return eye(a.length);
		if (b.length != b[0].length) return eye(b.length);
		if (a.length != b.length) return eye(Math.min(a.length, b.length));
	//	if (!powerOfTwo(a.length)) return eye(a.length);
		if (a.length == 1) return cell(a[0][0] * b[0][0]);
		boolean even = (a.length % 2 == 0);
		
		// augment the odd-sized matrices
		if (!even) {
			a = augment(a);
			b = augment(b);
		}
		int n = a.length;
		
		// partitions use O(n^2) time
		double[][] a11 = sub(a, 0, 0, n / 2, n / 2);
		double[][] a12 = sub(a, 0, n / 2, n / 2, n);
		double[][] a21 = sub(a, n / 2, 0, n, n / 2);
		double[][] a22 = sub(a, n / 2, n / 2, n, n);
		double[][] b11 = sub(b, 0, 0, n / 2, n / 2);
		double[][] b12 = sub(b, 0, n / 2, n / 2, n);
		double[][] b21 = sub(b, n / 2, 0, n, n / 2);
		double[][] b22 = sub(b, n / 2, n / 2, n, n);
		double[][] s1 = subtract(b12, b22);
		double[][] s2 = add(a11, a12);
		double[][] s3 = add(a21, a22);
		double[][] s4 = subtract(b21, b11);
		double[][] s5 = add(a11, a22);
		double[][] s6 = add(b11, b22);
		double[][] s7 = subtract(a12, a22);
		double[][] s8 = add(b21, b22);
		double[][] s9 = subtract(a11, a21);
		double[][] s0 = add(b11, b12);
		
		// RECURSIVE STEP : This time there are only 7 RECURSIVE CALLS!!! This brings the runtime down to O(n^k) where k = log_2 (7) < 3
		double[][] m1 = strassen(a11, s1);
		double[][] m2 = strassen(s2, b22);
		double[][] m3 = strassen(s3, b11);
		double[][] m4 = strassen(a22, s4);
		double[][] m5 = strassen(s5, s6);
		double[][] m6 = strassen(s7, s8);
		double[][] m7 = strassen(s9, s0);
		
		double[][] c11 = add(subtract(add(m4, m5), m2), m6);
		double[][] c12 = add(m1, m2);
		double[][] c21 = add(m3, m4);
		double[][] c22 = subtract(add(m1, m5), add(m3, m7));
		
		// O(n^2) - MERGE
		double[][] res = new double[n][n];
		for (int i = 0; i < n / 2; i++) {
			for (int j = 0; j < n / 2; j++) {
				res[i][j] = c11[i][j];
				res[i + n / 2][j] = c21[i][j];
				res[i][j + n / 2] = c12[i][j];
				res[i + n / 2][j + n / 2] = c22[i][j];
			}
		}
		// return the matrices to the original size
		if (!even) return reduce(res);
		return res;
		
		// Total runtime = O(N^2.807) + O(N^2) = O(N^2.807) (log_2(7) = 2.807)
	}
	
	static double[][] random(int a, int b, double bound) {
		double[][] res = new double[a][b];
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) res[i][j] = (int)(Math.random() * bound);
		}
		return res;
	}
	
	public static void main(String[] args) {
		double[][] mat = {{1, 2, 3, 4, 5}, {2, 3, 4, 5, 6}, {3, 4, 5, 6, 7}, {5, 6, 7, 8, 9}, {8, 9, 10, 11, 12}};
		int n = 200;
		int m = 86;
		mat = random(n, n, m);
		double[][] mat2 = random(n, n, m);
		
		long start = System.nanoTime();
		double[][] standard = standard(mat, mat2);
		System.out.println("ELAPSED " + ((System.nanoTime() - start)));
		
		start = System.nanoTime();
		double[][] div = divideandconquer(mat, mat2);
		System.out.println("ELAPSED " + ((System.nanoTime() - start)));
		
		start = System.nanoTime();
		double[][] faster = strassen(mat, mat2);
		System.out.println("ELAPSED " + (System.nanoTime() - start));
		
		System.out.println(Arrays.deepEquals(standard, faster));
		System.out.println(Arrays.deepEquals(standard, div));
		// https://www.symbolab.com/solver/step-by-step/%5Cbegin%7Bpmatrix%7D1%262%263%264%265%5C%5C%20%202%263%264%265%266%5C%5C%20%203%264%265%266%267%5C%5C%20%205%266%267%268%269%5C%5C%20%208%269%2610%2611%2612%5Cend%7Bpmatrix%7D%5Cbegin%7Bpmatrix%7D1%262%263%264%265%5C%5C%20%20%202%263%264%265%266%5C%5C%20%20%203%264%265%266%267%5C%5C%20%20%205%266%267%268%269%5C%5C%20%20%208%269%2610%2611%2612%5Cend%7Bpmatrix%7D
	}
}
