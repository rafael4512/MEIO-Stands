package Main;

import javafx.util.Pair;

import javax.sql.rowset.spi.SyncResolver;
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
       // Matrix.printM(p1[2]);
       // Matrix.printM(l1[2]);


        /*for (int i = -3; i < 4; i++) {
            System.out.println("Transferência " + i);
            Matrix.printM(p1[i + 3]);
            Matrix.printM(l1[i + 3]);
            int indice = 0;
            for (double[] lista : p1[i + 3]) {
                System.out.println("Linha " + indice + " " + Arrays.stream(lista).sum());
                indice++;
            }
            System.out.println();
        }*/

        double[][][] bigProbMatrix;
        bigProbMatrix = Matrix.createProbBig(p1, p2);
        //for(int i=0;i<7;i++)

        /*for (int i = 0; i < 7; i++) {
            System.out.println("Estado " + (i - 3));
            Matrix.printM(bigProbMatrix[i]);

            for (int j = 0; j < 169; j++) {
                double sum = Arrays.stream(bigProbMatrix[3][i]).sum();
                System.out.println("Linha " + j + " = " + sum);
            }
        }*/

        double[][][] bigCustosMatrix;
        bigCustosMatrix = Matrix.createBigCustos(l1, l2, p1, p2);
        //printCSV(bigCustosMatrix[6]);

        /*for (int i = 0; i < 7; i++) {
            System.out.println("Estado " + (i - 3));
            Matrix.printM(bigCustosMatrix[i]);
       }
        Matrix.printM(bigCustosMatrix[0]);
        */
        //Par ( (Decisões,Fn) , Dn )  ;
        Par<Par <double[],double[]>, double[][]> sol =resolve_N_iteracao(bigProbMatrix,bigCustosMatrix,30);

        for(int i=0;i<30;i++){
            System.out.println("Iteracao:"+i);
            printCSV(sol.getSecond()[i]);
        }


    };

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

                        double prob2 = f2p[p] * f2e[e];
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
    //Calcula a Matriz Vn.
    public double[] calcula_Vn(double[]qn,double[] pn_fn_1){
        if(qn==null ||pn_fn_1==null )
            throw new RuntimeException("Null Vector ");
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

    //Retorna o maior elemento de um vetor
    public Par <Integer,Double> escolhe_Maior(double[] vn){
        if (vn==null)
            throw new RuntimeException("NULL Vector on escolhe_Maior");
        double maior=vn[0];
        int descisao=-3;
        for (int i=1;i<vn.length;i++){
            if (vn[i]>maior){
                maior=vn[i];
                descisao=i-3;
            }
        }
        return new Par(descisao,maior);
    }

    //Retorna o vetor final de forma a maximizar a função objetivo.
    public Par<int[],double[]> solução(double[][] vn){
        int i,j;
        if (vn ==null)
            throw new RuntimeException("Vectors with different sizes!!");
        int t1=vn.length;
        double casos[]=new double[7];
        double res[]=new double[169];
        int decisao[]=new int[169];

        for(j=0;j<169;j++){
            for(i=0;i<7;i++) //para todos os estados:
                casos[i] = vn[i][j];
            Par<Integer, Double> melhorCaso = escolhe_Maior(casos);
            res[j] = melhorCaso.getSecond();
            decisao[j] = melhorCaso.getFirst();
        }
        return new Par (decisao,res);
    }

    //Calcula a solução do problema.
    public Par<Par <double[],double[]>, double[][]>resolve_N_iteracao(double[][][] pn,double[][][] rn,int iteracoes){
        int transf,i,j;
        Par<int [],double []>  fn=new Par(new double[169], new double[169]);// Fn inical
        Par<int [],double []>  fn_ant=new Par(new double[169], new double[169]);// Fn anterior.
        double [][] dn =new double[iteracoes][169];//Vetor Dn para todas as Iterações.

        //Vetor Qn
        double [][] q=new double[7][169];
        for(transf=-3;transf<4;transf++){
            q[transf+3] = calcula_Q( pn[transf+3],rn[transf+3]);
        }

        //faz as n iteraçoes
        for(j=0;j<iteracoes;j++) {
            //Vetor Vn
            double vn[][] = new double[7][169];

            for (i = 0; i < 7; i++) {// Para todos as decisões alternativas, de uma iteração
                double[] pn_fn = Matrix.multiply(pn[i], fn.getSecond());
                vn[i] = calcula_Vn(q[i], pn_fn);
            }
            fn_ant=fn;
            fn = solução(vn);

            //Calculo do Dn
            for(i=0;i<169;i++){
                dn[j][i] = fn.getSecond()[i] - fn_ant.getSecond()[i];
            }
        }
        return new Par(fn,dn);
    }

    //Print de Matrizes para csv de doubles.
    public void printCSV(double [][] m){
        StringBuilder sb = new StringBuilder();
        sb.append("_;");
        for (int j = 0; j < m[0].length; j++) {
            if (j == m[0].length - 1)
                sb.append(j);
            else
                sb.append(j+";");
        }
        sb.append("\n");
        for (int i=0;i<m.length;i++) {
            sb.append(i+";");
            for (int j = 0; j < m[0].length; j++) {
                if (j == m[0].length - 1)
                    sb.append(String.format("%10.4f", m[i][j]));
                else
                    sb.append(String.format("%10.4f;", m[i][j]));
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    //Print de um Vetor para csv.
    public void printCSV(double [] m){
        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < m.length; j++) {
                sb.append(String.format("%10.2f\n", m[j]));
        }
        System.out.println(sb.toString());
    }

    //Tranforma um vetore de 169 em uma matriz
    public double [][] tranformVectorToMat(double v[]){
        if (v==null)
            throw new RuntimeException("Null Vector!");
        double [][] mat=new double[13][13];
        int i,j;

        for(i=0;i<13;i++) {
            for (j = 0; j < 13; j++) {
                mat[i][j] = v[i * 13 + j];
            }
        }
        return mat;
    }

    public static void main(String[] args) {
        new Main();
    }
}






