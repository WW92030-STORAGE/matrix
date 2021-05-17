public class QR {
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
	
	static double[][] trans(double[][] arr) {
		double[][] res = new double[arr[0].length][arr.length];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) res[j][i] = arr[i][j];
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
	
	// VECTORS
	
	public static double[][] vecmat(double[] v) {
		double[][] mat = new double[v.length][1];
		for (int i = 0; i < v.length; i++) mat[i][0] = v[i];
		return mat;
	}
	
	public static double[] matvec(double[][] mat) {
		double[]v = new double[mat.length];
		for (int i = 0; i < mat.length; i++) v[i] = mat[i][0];
		return v;
	}
	
	static double[] add(double[] a, double[] b) {
		int n = Math.min(a.length, b.length);
		double[] res = new double[n];
		for (int i = 0; i < n; i++) res[i] = a[i] + b[i];
		return res;
	}
	
	static double[] multiply(double[] v, double a) {
		int n = v.length;
		double[] res = new double[n];
		for (int i = 0; i < n; i++) res[i] = a * v[i];
		return res;
	}
	
	static double[] opp(double[] a) {
		return multiply(a, -1.0);
	}
	
	static double[] subtract(double[] a, double[] b) {
		return add(a, opp(b));
	}
	
	static double length(double[] a) {
		double res = 0;
		for (double d : a) res += d * d;
		return Math.sqrt(res);
	}
	
	static double[] unit(double[] v) {
		return multiply(v, 1.0 / length(v));
	}
	
	static double dot(double[] u, double[] v) {
		if (u.length != v.length) return 0;
		double res = 0;
		for (int i = 0; i < u.length; i++) res += u[i] * v[i];
		return res;
	}
	
	// PROJECTION
	
	static double[] proj(double[] u, double[] v) {
		return multiply(u, dot(u, v) / dot(u, u));
	}
	
	// GRAM SCHMIDT
	
	static double[][] implant(double[][] res, double[] v, int col) {
		if (res.length != v.length) return res;
		for (int i = 0; i < res.length; i++) res[i][col] = v[i];
		return res;
	}
	
	static double[] col(double[][] mat, int i) {
		double[] res = new double[mat.length];
		for (int x = 0; x < mat.length; x++) res[x] = mat[x][i];
		return res;
	}
	
	static double[][] gramschmidt(double[][] mat) {
		int n = mat.length;
		int k = mat[0].length;
		double[][] res = new double[n][k];
		for (int i = 0; i < k; i++) {
			double[] crazyprojection = new double[n];
			double[] column = col(mat, i);
			for (int j = 0; j < i; j++) {
				crazyprojection = add(crazyprojection, proj(col(res, j), column));
			}
			double[] incoming = subtract(column, crazyprojection);
			implant(res, unit(incoming), i);
		}
		return res;
	}
	
	// QR ALGORITHM FOR SQUARES ONLY (sorry rectangles you have to go)
	
	static double[][] qr(double[][] mat) {
		if (mat.length != mat[0].length) return mat;
		int n = mat.length;
		double[][] q = gramschmidt(mat);
		double[][] res = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) res[i][j] = dot(col(q, i), col(mat, j));
		}
		return res;
	}
	
	static double[][] r(double[][] q, double[][] mat) {
		if (mat.length != mat[0].length) return mat;
		int n = mat.length;
		double[][] res = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) res[i][j] = dot(col(q, i), col(mat, j));
		}
		return res;
	}
	
	public static void main(String[] args) {
		double[][] mat = {{1, 2, 2}, {-1, 0, 2}, {0, 0, 1}};
		mat = trans(mat);
		print(mat);
		double[][] q = gramschmidt(mat);
		double[][] r = r(q, mat);
		print(q);
		print(r);
		print(multiply(q, r));
	}
}
