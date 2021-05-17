
public class matrixmultiplication {
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
	
	public static double mod(double a, double b) {
		double res = a % b;
		while (res < 0) res += 2 * b;
		return res % b;
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
	
	public static double[][] multiply(double[][] one, double[][] two, double m) {
		if (one[0].length != two.length) return eye(one.length);
		double[][] res = new double[one.length][two[0].length];
		
		double sum;
		for (int i = 0; i < res.length; i++) { // iterates on one's first index
			for (int j = 0; j < res[i].length; j++) { // iterates on two's second index
				sum = 0;
				for (int k = 0; k < one[i].length; k++) {
					sum += (one[i][k] * two[k][j]);
				}
				res[i][j] = mod(sum, m);
			}
		}
		
		return res;
	}
	
	static int n, m;
	static long k;
	
	public static double[][] exp(double[][] b, long e) {
		if (b.length != b[0].length) return eye(b.length);
		
		if (e == 1) return b;
		if (e == 0) return eye(b.length);
		
		double[][] half = exp(b, e / 2);
		if (e % 2 == 0) return multiply(half, half);
		return multiply(multiply(half, half), b);
	}
	
	static String pad(String s, int length) {
		while (s.length() < length) s = " " + s;
		return s;
	}
	
	static void print(long[][] arr) {
		int n = -69;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				String num = "" + arr[i][j];
				n = Math.max(n, num.length());
			}
		}
		
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				String num = "" + arr[i][j];
				num = pad(num, n + 1);
				System.out.print(num);
			}
			System.out.println();
		}
	}
	
	static double s(double n) {
		return Math.sqrt(n);
	}
	
	public static void main(String[] args) {
		
		double[][] N = {{47, 110, 111}, {116, 97, 115}, {109, 114, 47}};
	}
}
