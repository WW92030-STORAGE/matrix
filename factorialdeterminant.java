
public class factorialdeterminant {
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
	
	public static void main(String[] args) {
		double[][] test = {{1, 2, 3, 4, 5}, {6, 8, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 31, 20}, {21, 69, 420, 1000, 9000}};
		// https://www.symbolab.com/solver/step-by-step/det%5Cbegin%7Bpmatrix%7D1%262%263%264%265%5C%5C%20%20%20%206%268%268%269%2610%5C%5C%20%20%20%2011%2612%2613%2614%2615%5C%5C%20%20%20%2016%2617%2618%2631%2620%5C%5C%20%20%20%2021%2669%26420%261000%269000%5Cend%7Bpmatrix%7D
	//	print(test);
	//	double x = det(test);
	//	System.out.println(x);
		
		double[][] test2 = {{1, 3, 2}, {5, 6, 4}, {9, 8, 7}};
		double[][] test3 = {{6, 9, 6}, {5, 6, 4}, {9, 8, 7}};
		double[][] test4 = {{47, 110, 111}, {116, 97, 115}, {109, 114, 47}};
		double[][] test5 = {{83, 65, 73}, {78, 84, 80}, {65, 85, 76}};
		double[][] test6 = {{115, 97, 105}, {110, 116, 112}, {97, 117, 108}};
		System.out.println(det(test5));
	}
}
