package Main;


public class Matrix {

    public static double[][] init(int n,int val){
        int i,j;
        double[][] a = new double  [n][n];
        for (i = 0; i < n; i++)
            for ( j = 0;j < n; j++){
                a[i][j]=val;
            }
        return a;
    }


    public static double[] sumRows(double[][] m,int r,int c){
        double[] aux_sum = new double[r];
        double val;
        int i,j;
        for (i = 0; i < r; i++){
            val=0;
            for(j = 0; j < c; j++){
                val +=m[i][j];
            }
            aux_sum[i]=val;
        }
        return aux_sum;

    }

    public static double[] sumColuns(double[][] m,int r,int c){
        double[] aux_sum=new double[r];
        double val;
        int i,j;
        for (i = 0; i < r; i++){
            val=0;
            for(j = 0; j < c; j++){
                val +=m[j][i];
            }
            aux_sum[i]=val;
        }
        return aux_sum;
    }


    // return n-by-n identity matrix I
    public static double[][] identity(int n) {
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++)
            a[i][i] = 1;
        return a;
    }

    // return x^T y
    public static double dot(double[] x, double[] y) {
        if (x.length != y.length) throw new RuntimeException("Illegal vector dimensions.");
        double sum = 0.0;
        for (int i = 0; i < x.length; i++)
            sum += x[i] * y[i];
        return sum;
    }

    // return B = A^T
    public static double[][] transpose(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        double[][] b = new double[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                b[j][i] = a[i][j];
        return b;
    }

    // return c = a + b
    public static double[][] add(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        double[][] c = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] + b[i][j];

        return c;
    }

    // return c = a - b
    public static double[][] subtract(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        double[][] c = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] - b[i][j];
        return c;
    }

    // return c = a * b
    public static double[][] multiply(double[][] a, double[][] b) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] c = new double[m1][n2];
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    // matrix-vector multiplication (y = A * x)
    public static double[] multiply(double[][] a, double[] x) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += a[i][j] * x[j];
        return y;
    }


    // vector-matrix multiplication (y = x^T A)
    public static double[] multiply(double[] x, double[][] a) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != m) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[n];
        for (int j = 0; j < n; j++)
            for (int i = 0; i < m; i++)
                y[j] += a[i][j] * x[i];
        return y;
    }

    public static double[] multiply_by_rows(double[][] a,double[][] b){
        int m = a.length;
        int n = a[0].length;
        if (b.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] res = new double[n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                res[i] += a[i][j] * b[i][j];
        return res;
    }

    public static double[][][] createProbBig(double[][][] f1, double[][][] f2) {
        double [][][]result;
        int a = f1.length; // Num transferências
        int b = f1[0].length; // Linhas
        int c = f1[0][0].length; // Colunas
        int d = f2[0].length; // Linhas
        int e = f2[0][0].length; // Colunas

        result = new double[a * a][b * d][c * e];

        for (int t = 0; t < a; t++) {
            for (int i = 0; i < b; i++) {
                for (int j = 0; j < c; j++) {
                    for (int k = 0; k < d; k++) {
                        for (int l = 0; l < e; l++) {
                            result[t][i * 13 + k][j * 13 + l] = f1[t][i][j] * f2[a - 1 - t][k][l];
                        }
                    }
                }
            }
        }

        return result;
    }

    public static double[][][] createBigCustos(double[][][] l1, double[][][] l2, double[][][] p1, double[][][] p2) {
        double [][][]result;
        int a = l1.length; // Num transferências
        int b = l1[0].length; // Linhas
        int c = l1[0][0].length; // Colunas
        int d = l2[0].length; // Linhas
        int e = l2[0][0].length; // Colunas

        result = new double[a * a][b * d][c * e];

        for (int t = 0; t < a; t++) {
            for (int i = 0; i < b; i++) {
                for (int j = 0; j < c; j++) {
                    for (int k = 0; k < d; k++) {
                        for (int l = 0; l < e; l++) {
                            if (p1[t][i][j] != 0 && p2[a - t - 1][k][l] != 0) {
                                result[t][i * 13 + k][j * 13 + l] = (l1[t][i][j] + l2[a - t - 1][k][l]);
                            } else {
                                result[t][i * 13 + k][j * 13 + l] = -1000;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }


    public static void printM(double[][] m) {
        StringBuilder sb = new StringBuilder();

        int N = m.length;//linhas
        int M=m[0].length;//colunas
        sb.append("    |");
        for (int i = 0; i < M; i++) {
            sb.append(String.format(" %10d |", i));
        }
        sb.append("\n");

        sb.append("____|");
        for (int i = 0; i < M; i++) {
            sb.append("____________|");
        }
        sb.append("\n");

        for (int i = 0; i < N; i++) {
            sb.append(String.format("%3d |", i));
            for (int j = 0; j < M; j++) {
                sb.append(String.format(" %10.4f |",m[i][j]));
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}