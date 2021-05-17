
public class characteristic {
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
	
	public static double[][] clone(double[][] mat) {
		double[][] grid = new double[mat.length][mat[0].length];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) grid[i][j] = mat[i][j];
		}
		return grid;
	}
	
	static double trace(double[][] arr) {
		double res = 0;
		for (int i = 0; i < Math.min(arr.length, arr[0].length); i++) res += arr[i][i];
		return res;
	}
	
	// OPERATIONS
	
	public static double[][] add(double[][] one, double[][] two) {
		int n = Math.min(one.length, two.length);
		int m = Math.min(one[0].length, two[0].length);
		double[][] res = new double[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) res[i][j] = one[i][j] + two[i][j];
		}
		return res;
	}
	
	public static double[][] multiply(double[][] one, double[][] two) {
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
	
	public static double[][] scale(double[][] mat, double c) {
		double[][] res = new double[mat.length][mat[0].length];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) res[i][j] = c * mat[i][j];
		}
		return res;
	}
	
	// FADDEEV-LEVERRIER
	
	public static double[] chara(double[][] arr) {
		if (arr.length != arr[0].length) return new double[arr.length];
		int n = arr.length;
		double[][] I = eye(n);
		double[] res = new double[n + 1];
		res[n] = 1; // first value
		double[][][] m = new double[n + 1][n][n]; // auxilliary matrix
		for (int k = 1; k <= n; k++) {
			double[][] first = multiply(arr, m[k - 1]);
			double[][] second = scale(I, res[n - k + 1]);
			m[k] = add(first, second);
			res[n - k] = -1.0 * (trace(multiply(arr, m[k]))) / k;
		}
		/*
		for (int i = 0; i <= n; i++) {
			System.out.println(res[n - i]);
			print(m[i]);
		}
		*/
		double[] cp = new double[n + 1];
		for (int i = 0; i <= n; i++) cp[i] = res[n - i];
		return cp;
	}
	
	public static void main(String[] args) {
		double[][] mat = {{3, 1, 5}, {3, 3, 1}, {4, 6, 4}};
		print(chara(mat));
	}
}
