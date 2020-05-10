package Main;

import java.util.Arrays;

public class Main {

    //FILIAL1- Probabilidade de entregas e pedidos
    private static double[] f1p = {0.0272, 0.0944, 0.1552, 0.2092, 0.1932, 0.1476, 0.0864, 0.0528, 0.0208, 0.0088, 0.0032, 0.0012, 0.0000};
    private static double[] f1e = {0.0404, 0.0676, 0.1024, 0.1392, 0.1396, 0.1236, 0.1012, 0.0896, 0.0740, 0.0572, 0.0388, 0.0220, 0.0044};

    //FILIAL2 - Probabilidade de entregas e pedidos
    private static double[] f2p = {0.0292, 0.0724, 0.1168, 0.1488, 0.1452, 0.1116, 0.0968, 0.0824, 0.0736, 0.0508, 0.0376, 0.0268, 0.0080};
    private static double[] f2e = {0.0280, 0.1024, 0.1848, 0.2216, 0.1964, 0.1324, 0.0740, 0.0360, 0.0144, 0.0064, 0.0016, 0.0008, 0.0012};

    public Main(){
        Par <Par<double[][][], double[][][]>,Par<double[][][], double[][][]>> mats;
        mats = criarMatriz();
        double[][][] p1 = mats.getFirst().getFirst();
        double[][][] p2 = mats.getFirst().getSecond();

        double[][][] l1 = mats.getSecond().getFirst();
        double[][][] l2 = mats.getSecond().getSecond();


        for (int i = -3; i < 4; i++) {
            System.out.println("Transferência " + i);
            Matrix.printM(p1[i + 3]);
            Matrix.printM(l1[i + 3]);
            int indice = 0;
            for (double[] lista : p1[i + 3]) {
                System.out.println("Linha " + indice + " " + Arrays.stream(lista).sum());
                indice++;
            }
            System.out.println();
        }

        double[][][] bigProbMatrix;
        bigProbMatrix = Matrix.createProbBig(p1, p2);

        for (int i = 0; i < 7; i++) {
            System.out.println("Estado " + (i - 3));
            Matrix.printM(bigProbMatrix[i]);

            for (int j = 0; j < 169; j++) {
                double sum = Arrays.stream(bigProbMatrix[3][i]).sum();
                System.out.println("Linha " + j + " = " + sum);
            }
        }

        double[][][] bigCustosMatrix;
        bigCustosMatrix = Matrix.createBigCustos(l1, l2, p1, p2);

        /*
        for (int i = 0; i < 7; i++) {
            System.out.println("Estado " + (i - 3));
            Matrix.printM(bigCustosMatrix[i]);
       }
        Matrix.printM(bigCustosMatrix[0]);
        */
    }

    final int MAX = 13;

    private int min(int a, int b) {
        return (a > b) ? b : a;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public Par<Par<double[][][], double[][][]>, Par<double[][][], double[][][]>> criarMatriz(){
        double[][][] probs1 = new double[7][MAX][MAX];
        double[][][] lucrs1 = new double[7][MAX][MAX];
        double[][][] probs2 = new double[7][MAX][MAX];
        double[][][] lucrs2 = new double[7][MAX][MAX];

        for (int t = -3; t < 4; t++) {
            for (int n_Inicial = 0; n_Inicial < MAX; n_Inicial++) {
                for (int e = 0; e < MAX; e++) {
                    for (int p = 0; p < MAX; p++) {

                        int pAtendidos = min(n_Inicial, p);
                        int fim = min(n_Inicial - pAtendidos + e, MAX - 1);

                        fim = max(fim + t, 0);
                        fim = min(fim, MAX - 1);

                        double prob1 = f1p[p] * f1e[e];
                        probs1[t + 3][n_Inicial][fim] += prob1;
                        lucrs1[t + 3][n_Inicial][fim] += calculaLucros(fim, pAtendidos, prob1, t);

                        double prob2 = f1p[p] * f1e[e];
                        probs2[t + 3][n_Inicial][fim] += prob2;
                        lucrs2[t + 3][n_Inicial][fim] += calculaLucros(fim, pAtendidos, prob2, t);
                    }
                }
            }
        }

        for (int t = -3; t < 4; t++) {
            for (int i = 0; i < MAX; i++) {
                for (int j = 0; j < MAX; j++) {
                    lucrs1[t + 3][i][j] /= probs1[t + 3][i][j];
                    if (Double.isNaN(lucrs1[t + 3][i][j])) {
                        lucrs1[t + 3][i][j] = -1000;
                    }

                    lucrs2[t + 3][i][j] /= probs2[t + 3][i][j];
                    if (Double.isNaN(lucrs2[t + 3][i][j])) {
                        lucrs2[t + 3][i][j] = -1000;
                    }
                }
            }
        }
        return new Par(new Par(probs1, probs2), new Par(lucrs1, lucrs2));
    }

    public double calculaLucros(int qtFim, int pedidosAtendidos, double prob, int transf) {
        double sum = 0.0;

        if (qtFim > 8) sum -= 10 * prob;
        if (transf < 0) sum += transf * 7 * prob;

        sum += pedidosAtendidos * 30 * prob;

        return sum;
    }

    //Serve para ca
    public double[] calcula_Q(double[][]pn,double[][] rn){
        return Matrix.multiply_by_rows(pn,rn);
    }

    public double[] calcula_Pn_Fantes(double[][]pn,double[] fn_1){
        return Matrix.multiply(pn,fn_1);

    }

    public double[] calcula_Vn(double[]qn,double[] pn_fn_1){
        int t1=qn.length;
        int t2=pn_fn_1.length;
        if(t1!=t2)
            throw new RuntimeException("Vectors with different sizes!!");
        double []res=new double[t1];
        for (int i=0;i<t1;i++){
            res[i]=qn[i]+pn_fn_1[i];
        }
        return res;
    }

    //Retorna o maior elemento.
    public double escolhe_Maior(double[] vn){
        if (vn==null)
            throw new RuntimeException("NULL Vector on escolhe_Maior");
        double maior=vn[0];
        for (int i=1;i<vn.length;i++){
            if (vn[i]>maior){
                maior=vn[i];
            }
        }
        return maior;
    }

    //Retorna o vetor final de forma a maximizar a função objetivo.
    public double[] solução(double[][] vn){
        int i,j;
        if (vn ==null)
            throw new RuntimeException("Vectors with different sizes!!");
        int t1=vn.length;
        double res[]=new double[t1];
        for(i=0;i<t1;i++){
             res[i]=escolhe_Maior(vn[i]);
        }
        return res;
    }

    public double[] add_vect(double[] a, double[] b){
        if (a==null || b==null)
            throw new RuntimeException("NULL Vector");
        int tam=a.length;
        if (tam!=b.length){
            throw new RuntimeException("Vectors with different sizes!!");
        }
        double []res=new double[tam];
        for (int i=0;i<tam;i++){
            res[i]=a[i]+b[i];
        }
        return res;
    }

    /*
    public double[] resolve_N_iteracao(double[][] pn1, double[][] pn2,MatCusto mc,int tam,int iteracoes){
        int i,j;
        double []fn=new double[13];// Fn inical
        for (i=0;i<7;i++)
            fn[i]=0;

        //Vetor Qn
        double [][] q=new double[7][tam];
        q[0] = add_vect(calcula_Q(pn1, mc.mCusto0),calcula_Q(pn2, mc.mCusto0) );
        q[1] = calcula_Q(pn1, mc.mCusto1_f1);
        q[2] = calcula_Q(pn1, mc.mCusto2_f1);
        q[3] = calcula_Q(pn1, mc.mCusto3_f1);
        q[4] = calcula_Q(pn2, mc.mCusto1_f2);
        q[5] = calcula_Q(pn2, mc.mCusto2_f2);
        q[6] = calcula_Q(pn2, mc.mCusto3_f2);


        for(j=0;j<iteracoes;j++) {
            //Vetor Vn
            double vn[][] = new double[13][tam];
            double []pn_fn_1=Matrix.multiply(pn2, fn);
            double []pn_fn_2=Matrix.multiply(pn1, fn);
            for (i = 0; i < 7; i++){
                if (i==0)
                    vn[i]=add_vect(calcula_Vn(q[i],pn_fn_1),calcula_Vn(q[i],pn_fn_2 ));
                else if (i<4)
                    vn[i]=calcula_Vn(q[i], pn_fn_1);
                else
                    vn[i]=calcula_Vn(q[i], pn_fn_2);
            }
            fn = solução(vn);
        }
        return fn;

    }
    */

    public double[][] calculaMatrizTransicao(double[][]f1,double [][]f2){
        double[][] matrizT=new double[169][169];
        int i,j,a,b;
        for(i=0;i<13;i++)
            for (j = 0; j < 13; j++)
                for (a = 0; a < 13; a++)
                    for (b = 0; b < 13; b++)
                        matrizT[i * 13 + a][j * 13 + b] = f1[i][j] * f2[a][b];


        return matrizT;

    }

    public static void main(String[] args) {
        new Main();
    }
}






