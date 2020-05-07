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


    public static void printM(double[][] m) {
        StringBuilder sb = new StringBuilder();

        int N = m.length;

        sb.append("    |");
        for (int i = 0; i < N; i++) {
            sb.append(String.format(" %9d |", i));
        }
        sb.append("\n");

        sb.append("____|");
        for (int i = 0; i < N; i++) {
            sb.append("___________|");
        }
        sb.append("\n");

        for (int i = 0; i < N; i++) {
            sb.append(String.format("%3d |", i));
            for (int j = 0; j < N; j++) {
                sb.append(String.format(" %9.4f |",m[i][j]));
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}