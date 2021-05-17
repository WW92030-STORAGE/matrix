import java.util.Arrays;

public class eigenvalues { // FIMALLY
	static class pair implements Comparable<pair>{
		private double x;
		private double y;
		public pair(double a, double b) {
			x = a;
			y = b;
		}
		
		public void set(double a, double b) {
			x = a;
			y = b;
		}
		
		public void set(pair p) {
			x = p.x;
			y = p.y;
		}
		
		public pair clone() {
			return new pair(this.x, this.y);
		}
		
		public int compareTo(pair other) {
			double epsilon = Math.pow(10, -12);
			Double px = this.x;
			Double py = this.y;
			Double ox = other.x;
			Double oy = other.y;
			if (!px.equals(ox)) {
				if (Math.abs(px - ox) < epsilon) return 0;
				else return px.compareTo(ox);
			}
			if (!py.equals(oy)) {
				if (Math.abs(py - oy) < epsilon) return 0;
				else return py.compareTo(oy);
			}
			return 0;
		}
		
		public boolean equals(pair other) {
			return this.compareTo(other) == 0;
		}
		
		public pair add(pair other) {
			return new pair(this.x + other.x, this.y + other.y);
		}
		
		public pair subtract(pair other) {
			return new pair(this.x - other.x, this.y - other.y);
		}
		
		public pair multiply(pair other) {
			return new pair(this.x * other.x - this.y * other.y, this.x * other.y + this.y * other.x);
		}
		
		public pair inv() {
			double rsq = this.x * this.x + this.y * this.y;
			if (rsq == 0) return this.clone();
			return new pair(this.x / rsq, -1.0 * this.y / rsq);
		}
		
		public pair divide(pair other) {
			return this.multiply(other.inv());
		}
		
		public String toString() {
			return "[" + x + " " + y + "]";
		}
	}
	
	// MATRICES
	
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
	
	// SOLVER
	
	static pair eval(pair[] p, pair x) {
		pair res = new pair(0, 0);
		int n = p.length;
		for (int i = 0; i < n; i++) {
			res = res.multiply(x);
			res = res.add(p[i]);
		}
		return res;
	}
	
	static pair deriv(pair[] p, pair x) {
		double e = Math.pow(10, -12);
		pair epsilon = new pair(e, e);
		pair dy = (eval(p, x.add(epsilon)).subtract(eval(p, x)));
		return dy.divide(epsilon);
	}
	
	static pair[] convert(double[] p) {
		pair[] res = new pair[p.length];
		for (int i = 0; i < p.length; i++) res[i] = new pair(p[i], 0);
		return res;
	}
	
	static pair[] solve(pair[] p) {
		int n = p.length - 1;
		pair[] res = new pair[n];
		pair[] offset = new pair[n];
		pair one = new pair(1, 0);
		double x = -1.0 * p[1].x / n;
		double y = -1.0 * p[1].y / n;
		for (int i = 0; i < n; i++) res[i] = new pair(Math.random() * 2 + x - 1.0, Math.random() * 2 + y - 1.0);
		for (int sksk = 0; sksk < 1000000; sksk++) {
			for (int i = 0; i < n; i++) {
				pair sum = new pair(0, 0);
				for (int j = 0; j < n; j++) {
					if (j == i) continue;
					sum = sum.add((res[i].subtract(res[j])).inv());
				}
				pair val = eval(p, res[i]);
				pair dev = deriv(p, res[i]);
				pair quo = val.divide(dev);
				pair denom = one.subtract(quo.multiply(sum));
				offset[i] = quo.divide(denom);
			}
			for (int i = 0; i < n; i++) res[i] = res[i].subtract(offset[i]);
		}
		Arrays.sort(res);
		return res;
	}
	
	// EIGENVALUES
	
	public static pair[] eig(double[][] mat) {
		if (mat[0].length != mat.length) return new pair[mat.length];
		int n = mat.length;
		pair[] p = convert(chara(mat));
		return solve(p);
	}
	
	public static void main(String[] args) {
		long start = System.nanoTime();
		
		double[][] mat = {{3, 1, 5}, {3, 3, 1}, {4, 6, 4}};
		double[][] mat2 = {{34, 31, 25}, {30, 83, 17}, {43, 66, 44}};
		System.out.println(Arrays.toString(eig(mat)));
		// https://www.symbolab.com/solver/step-by-step/eigenvalues%5Cbegin%7Bpmatrix%7D3%261%265%5C%5C%203%263%261%5C%5C%204%266%264%5Cend%7Bpmatrix%7D
	
		System.out.println("ELAPSED " + (System.nanoTime() - start) / 1000000);
	}
}
