public class matrixinverse {
	// MATRIX UTIL 
	
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
	
	static boolean equals(double a, double b) {
		return Math.abs(a - b) < 0.000000001;
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
	
	// INVERSE
	
	static double[][] inv(double[][] mat) {
		int n = mat.length;
		double[][] res = eye(n);
		if (det(mat) == 0) return res;
		
		double[][] grid = clone(mat);
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
	
	
	
	public static void main(String[] args) {
		
		// https://www.symbolab.com/solver/matrix-inverse-calculator/inverse%20%5Cbegin%7Bpmatrix%7D1%20%26%202%20%26%203%20%5C%5C4%20%26%205%20%26%206%20%5C%5C7%20%26%202%20%26%209%5Cend%7Bpmatrix%7D?or=ex
		double[][] arr2 = {{1, 2, 3}, {4, 5, 6}, {7, 2, 9}};
		print(arr2);
		print(inv(arr2));
		print(arr2);
	}
}
