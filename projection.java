public class projection {
	static class operation implements Comparable<operation>{
		private double x;
		private int y;
		private int z;
		public operation(double a, int b, int c) {
			x = a;
			y = b;
			z = c;
		}
		
		public void set(double a, int b, int c) {
			x = a;
			y = b;
			z = c;
		}
		
		public int compareTo(operation other) {
			Double px = this.x;
			int py = this.y;
			int pz = this.z;
			Double ox = other.x;
			int oy = other.y;
			int oz = other.z;
			if (px != ox) return px.compareTo(ox);
			if (py != oy) return py - oy;
			if (pz != oz) return pz - oz;
			return 0;
		}
		
		public boolean equals(operation other) {
			return this.compareTo(other) == 0;
		}
		
		public String toString() {
			return "[" + x + " " + y + " " + z + "]";
		}
	}
	
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
	
	// DETERMINANT
	
	static double det(double[][] grid) {
		int n = grid.length;
		if (n != grid[0].length) return 0;
		if (n == 2) return grid[0][0] * grid[1][1] - grid[1][0] * grid[0][1];
		
		double sum = 0;
		double[][] minor = new double[n - 1][n - 1];
		int coeff = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < n; j++) {
				int count = 0;
				for (int k = 0; k < n; k++) {
					if (k == i) continue;
					minor[j - 1][count] = grid[j][k];
					count++;
				}
			}
			
			double rec = det(minor);
			sum += grid[0][i] * rec * coeff;
			coeff *= -1;
		}
		return sum;
	}
	
	static double[][] inv(double[][] grid) {
		int n = grid.length;
		double[][] res = eye(n);
		if (det(grid) == 0) return res;
		
		// upper triangle
		for (int col = 0; col < n - 1; col++) {
			double diag = grid[col][col];
			if (diag == 0) {
				boolean found = false;
				int row2 = n - 1;
				for (row2 = n - 1; row2 > col; row2--) {
					double test = grid[row2][col];
					if (test != 0) {
						found = true;
						break;
					}
				}
				if (!found) continue;
				// add row2 to the diagonal row to make it not 0
				for (int c = 0; c < n; c++) {
					grid[col][c] = grid[col][c] + grid[row2][c];
					res[col][c] = res[col][c] + res[row2][c];
				}
			}
			diag = grid[col][col];
			for (int row = col + 1; row < n; row++) {
				double piece = grid[row][col];
				// subtract (piece / diagonal) times the row of the diagonal element
				double ratio = piece / diag;
			//	System.out.println(row + " " + col + " " + diag + " " + piece + " " + ratio);
				for (int c = 0; c < n; c++) {
					grid[row][c] -= ratio * grid[col][c];
					res[row][c] -= ratio * res[col][c];
				}
			//	print(grid);
			}
		}
		
		// lower triangle
		for (int col = 1; col < n; col++) {
			double diag = grid[col][col];
			if (diag == 0) {
				boolean found = false;
				int row2 = 0;
				for (row2 = 0; row2 < col; row2++) {
					double test = grid[row2][col];
					if (test != 0) {
						found = true;
						break;
					}
				}
				if (!found) continue;
				// add row2 to the diagonal row to make it not 0
				for (int c = 0; c < n; c++) {
					grid[col][c] = grid[col][c] + grid[row2][c];
					res[col][c] = res[col][c] + res[row2][c];
				}
			}
			diag = grid[col][col];
			for (int row = 0; row < col; row++) {
				double piece = grid[row][col];
				// subtract (piece / diagonal) times the row of the diagonal element
				double ratio = piece / diag;
				for (int c = 0; c < n; c++) {
					grid[row][c] -= ratio * grid[col][c];
					res[row][c] -= ratio * res[col][c];
				//	print(grid);
				}
			}
		}
		
		// reduce to 1
		for (int row = 0; row < n; row++) {
			double diag = grid[row][row];
			for (int col = 0; col < n; col++) {
				grid[row][col] = grid[row][col] / diag;
				res[row][col] = res[row][col] / diag;
			}
		}
		return res;
	}
	
	// MULTIPLICATION
	
	public static double[][] multiply(double[][] one, double[][] two) {
		if (one[0].length != two.length) return eye(1);
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
	
	static double[] opp(double[] v) {
		int n = v.length;
		double[] res = new double[n];
		for (int i = 0; i < n; i++) res[i] = -1 * v[i];
		return res;
	}
	
	static double[] subtract(double[] a, double[] b) {
		return add(a, opp(b));
	}
	
	static double length(double[] a) {
		double res = 0;
		for (double d : a) res += d * d;
		return Math.sqrt(res);
	}
	
	// PROJECTION
	
	static double[][] promat(double[][] mat) {
		double[][] inner = inv(multiply(trans(mat), mat));
	//	print(inner);
		return multiply(multiply(mat, inner), trans(mat));
	}
	
	static double[] proj(double[] v, double[][] mat) {
		return matvec(multiply(promat(mat), vecmat(v)));
	}
	
	static double[] error(double[] v, double[][] mat) {
		return subtract(v, proj(v, mat));
	}
	
	public static void main(String[] args) {
		double[] v = {3, -1, 5};
		double[][] m = {{1, 2}, {-1, 4}, {1, 2}};
		print(promat(m));
		print(proj(v, m));
		print(error(v, m));
	}
}
