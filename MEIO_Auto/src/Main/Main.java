package Main;

import java.util.Arrays;

public class Main {

    //FILIAL1- Pobabilidade de entregas e pedidos
    private static double[] f1p = {0.0272, 0.0944, 0.1552, 0.2092, 0.1932, 0.1476, 0.0864, 0.0528, 0.0208, 0.0088, 0.0032, 0.0012, 0.0000};
    private static double[] f1e = {0.0404, 0.0676, 0.1024, 0.1392, 0.1396, 0.1236, 0.1012, 0.0896, 0.0740, 0.0572, 0.0388, 0.0220, 0.0044};

    //FILIAL2 -Pobabilidade de entregas e pedidos
    private static double[] f2p = {0.0292, 0.0724, 0.1168, 0.1488, 0.1452, 0.1116, 0.0968, 0.0824, 0.0736, 0.0508, 0.0376, 0.0268, 0.0080};
    private static double[] f2e = {0.0280, 0.1024, 0.1848, 0.2216, 0.1964, 0.1324, 0.0740, 0.0360, 0.0144, 0.0064, 0.0016, 0.0008, 0.0012};

    //Matrizes probabilidades
    Par<double[][], double[][]>  mProb_sat;
    Par<double[][], double[][]>  mProb_insat;

    MatCusto mCustos;


    public Main(int tam){
        // mCustos=new  MatCusto(tam);
        // mProb_sat=  buildMatrixSat(tam);
        // mProb_insat=  buildMatrixIns(tam);
    }

    public Par<double[][], double[][]> getmProb_sat (){
        return mProb_sat;
    }
    public Par<double[][], double[][]> getmProb_insat(){
        return mProb_insat;
    }

    public MatCusto getmCustos() {
        return mCustos;
    }

    //Lucro= Receitas - custos
   /* public double calculaLucroSat(int n_atual,int n_depois,double prob,int estado){
        double tot_acc=0;
        for (int i=n_atual;i>=0; i--){
            tot_acc+= (30 * i  *  prob );//calculo de  receitas
        }
        if(n_depois>8){
            tot_acc -= 10 * prob ;
        }
        tot_acc-= (estado * 7 * prob );
        return tot_acc;
    }*/
    public double calculaLucroSat(int n_ped,int n_depois,double prob,int estado){
        double tot_acc=0;

        tot_acc+= (30 * n_ped *  prob );//calculo de  receitas

        if(n_depois>8){
            tot_acc -= 10 * prob ;
        }

        if( estado > 0) {
            tot_acc-= (estado * 7 * prob );
        }

        return tot_acc;
    }


    /*public double calculaLucroInsat(int n_atual,int n_depois,double prob,int estado){
        double tot_acc=0;
        for (int i=12;i>n_atual; i--){
            tot_acc+= (30 * n_atual  *  prob );//calculo de  receitas
        }
        if(n_depois>8){
            tot_acc -= (10*prob) ;
        }
        tot_acc-= (estado*7*prob);
        return tot_acc;
    }*/
    public double calculaLucroInsat(int n_atual,int n_depois,double prob,int estado){
        double tot_acc=0;

        tot_acc+= (30 * n_atual  *  prob );//calculo de  receitas

        if(n_depois>8){
            tot_acc -= (10*prob) ;
        }
        tot_acc-= (estado*7*prob);
        return tot_acc;
    }



    //Calcula uma probabilidade de satizfação dado no numero de carros atual e o numero final.No casos de ficar com 12 carros no final(n_depois), temos de considerar os casos em que já temos 12 carros mas podemos receber E(0..12).
    //Retorna dois pares, onde o primeiro  tem o custo calculado para todas as matrizes   custo, No segundo par temos as 2 probalilidades calculadas.
   /* public  Par<Par<double[], double[]> ,Par<Double, Double>> calculaSat(int n_atual, int n_depois) {
        double f1_prob = 0;
        double f2_prob = 0;
        int estado=0;
        double []lucro_f1_acc=new double[4];
        double []lucro_f2_acc=new double[4];
        int j=0;
        if (n_depois==12) {
            while (n_atual >= 0) {
                j = 0;
                while (j <= n_atual) {
                    f1_prob += f1p[j] * f1e[n_depois];
                    f2_prob += f2p[j] * f2e[n_depois];
                    //Calcula o  lucro para todos os  estados
                    for (estado=0;estado<4;estado++) {
                        lucro_f1_acc[estado]+=calculaLucroSat(n_atual, n_depois, f1p[j] * f1e[n_depois], estado);
                        lucro_f2_acc[estado]+=calculaLucroSat(n_atual, n_depois, f2p[j] * f2e[n_depois], estado);
                    }
                    j++;
                }
                n_depois--;
                n_atual--;
            }
        }else {
            while (n_depois >= n_atual && n_atual >= 0) {
                //System.out.print("P("+n_atual+")"+"*"+"E("+n_depois+")+");
                f1_prob += f1p[n_atual] * f1e[n_depois];
                f2_prob += f2p[n_atual] * f2e[n_depois];
                //Calcula o  lucro para todos os  estados
                for (estado=0;estado<4;estado++) {
                    lucro_f1_acc[estado]+=calculaLucroSat(n_atual, n_depois, f1p[j] * f1e[n_depois], estado);
                    lucro_f2_acc[estado]+=calculaLucroSat(n_atual, n_depois, f2p[j] * f2e[n_depois], estado);
                }
                n_atual--;
                n_depois--;
            }
            //System.out.println();
            while (n_atual > n_depois && n_depois >= 0) {
                //System.out.print("P("+n_atual+")"+"*"+"E("+n_depois+")+");
                f1_prob += f1p[n_atual] * f1e[n_depois];
                f2_prob += f2p[n_atual] * f2e[n_depois];
                //Calcula o  lucro para todos os  estados
                for (estado=0;estado<4;estado++) {
                    lucro_f1_acc[estado]+=calculaLucroSat(n_atual, n_depois, f1p[j] * f1e[n_depois], estado);
                    lucro_f2_acc[estado]+=calculaLucroSat(n_atual, n_depois, f2p[j] * f2e[n_depois], estado);
                }
                n_atual--;
                n_depois--;
            }
        }
        //System.out.println();
        return new Par ( new Par(lucro_f1_acc,lucro_f2_acc),new Par(f1_prob, f2_prob));
    }*/
    public  Par<Par<double[], double[]> ,Par<double[], double[]>> calculaSat(int n_atual, int n_depois) {
        double []f1_prob = new double[7];
        double []f2_prob = new double[7];

        double []lucro_f1_acc = new double[7];
        double []lucro_f2_acc = new double[7];

        /*
        if (n_depois==12) {
            while (n_atual >= 0) {
                j = 0;
                while (j <= n_atual) {
                    f1_prob += f1p[j] * f1e[n_depois];
                    f2_prob += f2p[j] * f2e[n_depois];
                    //Calcula o  lucro para todos os  estados
                    for (estado=0;estado<4;estado++) {
                        lucro_f1_acc[estado]+=calculaLucroSat(j, n_depois, f1p[j] * f1e[n_depois], estado);
                        lucro_f2_acc[estado]+=calculaLucroSat(j, n_depois, f2p[j] * f2e[n_depois], estado);
                    }
                    j++;
                }
                n_depois--;
                n_atual--;
            }
        }

 */
        /*
            Estado 0
            Começo com 12 e acabo com 12
            i Entregas + j Pedidos == i >= j

            Estado 1 == recebe 1 carro
            Começo com 12 e acabo com 12
            i Entregas + j Pedidos == i >= j - 1
        */

        for (int estado = - 3; estado <= 3; estado++) {
            // estado == 3 -> 3 carros entre carros
            if (estado < 0 && n_depois > 12 + estado) {
                // Basicamente entregas 1 carro e tentas ficar com 12, entregas 2 carros e tentas ficar com 11 ou 12, ...
                continue;
            } else if (estado > 0 && n_depois < 0 + estado) {
                // Basicamente tentas ficar com 0, mas recebes pelo menos 1 carro
                continue;
            } else if (estado >= 0 && n_depois == 12) {
                for (int i = n_atual, f = n_depois; i >= 0 && f >= 0 ; i--, f--) { // Entregas
                    for (int j = 0; j - estado <= i && j < 13; j++) { // Pedidos
                        // Transferir estado carros => 12 + estado
                        f1_prob[estado + 3] += f1p[j] * f1e[f];
                        // Filial 2, será que está correto?
                        f2_prob[estado + 3] += f2p[j] * f2e[f];

                        //Calcula o  lucro para todos os  estados
                        lucro_f1_acc[estado + 3] += calculaLucroSat(j, f, f1p[j] * f1e[f], estado);
                        lucro_f2_acc[estado + 3] += calculaLucroSat(j, f, f2p[j] * f2e[f], estado);
                    }
                }
            } else if (estado >= 0 && n_depois >= n_atual) {
                /*
                    Tens mais do que tinhas e recebe carros
                    P(4 -> 5) => (E,P) (1,0) || (2,1) || (3,2) || (4,3) || (5,4)
                    Estado == 1 -> P(4 -> 5) => (E,P) (0,0) || (1,1) || (2,2) || (3,3) || (4,4)

                    P(3 -> 5) => (E,P) (2,0) || (3,1) || (4,2) || (5,3)
                    Estado == 1 -> P(3 -> 5) => (E,P) (1,0) || (2,1) || (3,2) || (4,3)
                */
                for (int i = n_atual, j = n_depois - estado; i >= 0 && j >= 0 ; i--, j--) { // i Pedidos, j Entregas
                    f1_prob[estado + 3] += f1p[i] * f1e[j];
                    f2_prob[estado + 3] += f2p[i] * f2e[j];

                    lucro_f1_acc[estado + 3] += calculaLucroSat(i, j, f1p[i] * f1e[j], estado);
                    lucro_f2_acc[estado + 3] += calculaLucroSat(i, j, f2p[i] * f2e[j], estado);
                }
            } else if (estado >= 0 && n_atual > n_depois) {
            /*
                P(5 -> 4) => (E,P) (0,1) || (1,2) || (2,3) || (3,4) || (4,5)
                Estado == 1 -> P(5 -> 4) => (E,P) (0,2) || (1,3) || (2,4) || (3,5)
            */
                for (int i = n_atual, j = n_depois - estado; j >= 0 ; i--, j--) { // i Pedidos, j Entregas
                    f1_prob[estado + 3] += f1p[i] * f1e[j];
                    f2_prob[estado + 3] += f2p[i] * f2e[j];

                    lucro_f1_acc[estado + 3] += calculaLucroSat(i, j, f1p[i] * f1e[j], estado);
                    lucro_f2_acc[estado + 3] += calculaLucroSat(i, j, f2p[i] * f2e[j], estado);
                }
            } else if (estado < 0 && n_depois == 12 + estado) {
                // Basicamente entregas carros e tinhas ficado 12 e depois "perdes" alguns dos 12 dos carros que tinhas
                /*
                    Tens mais do que tinhas e recebe carros
                    P(12 -> 11) => (E,P) (0,1) || (1,2) || (2,3) || ... || (11,12)

                    Estado == 1 -> P(12 -> 11) => (E,P) (0,0) || (1,1) || ... || (12,12)
                                                        (1,0) || (2,1) || ... || (12,11)
                                                        (2,0) || ...
                                                        ...
                                                        (11,0) || (11,1)
                                                        (12,0)
                    i = 3, f = 2 Entregas
                    j = 0, j <= 2 Pedidos
                    (E,P) = (2,0), (2,1), (2,2)

                    i = 0, f = 0;
                    j = 0, j + 1 <= 0
                    (E,P) = (1,0)

                */
                for (int i = n_atual, f = n_depois; i >= 0 && f >= 0 ; i--, f--) { // Entregas
                    for (int j = 0; j - estado <= i; j++) { // Pedidos
                        // Transferir estado carros => 12 + estado
                        f1_prob[estado + 3] += f1p[j] * f1e[f];
                        // Filial 2, será que está correto?
                        f2_prob[estado + 3] += f2p[j] * f2e[f];

                        lucro_f1_acc[estado + 3] += calculaLucroSat(j, f, f1p[j] * f1e[f], estado);
                        lucro_f2_acc[estado + 3] += calculaLucroSat(j, f, f2p[j] * f2e[f], estado);
                    }
                }
            } else if (estado < 0 && n_depois >= n_atual) {
            /*
                Transfere 1 carro para outra filial, aka perde um carro
                P(4 -> 5) => (E,P) (1,0) || (2,1) || (3,2) || (4,3) || (5,4)
                Estado == -1 -> P(4 -> 5) => (E,P) (2,0) || (3,1) || (4,2) || (5,3) || ?(6,4)?
            */
                for (int i = n_atual, j = n_depois - estado; i >= 0 && j >= 0 ; i--, j--) {
                    f1_prob[estado + 3] += f1p[i] * f1e[j];
                    f2_prob[estado + 3] += f2p[i] * f2e[j];

                    lucro_f1_acc[estado + 3] += calculaLucroSat(i, j, f1p[i] * f1e[j], estado);
                    lucro_f2_acc[estado + 3] += calculaLucroSat(i, j, f2p[i] * f2e[j], estado);
                }
            } else if (estado < 0 && n_atual > n_depois) {
            /*
                Transfere 1 carro para outra filial, aka perde um carro
                P(5 -> 4) => (E,P) (0,1) || (1,2) || (2,3) || (3,4) || (4,5)
                Estado == -1 -> P(5 -> 4) => (E,P) (0,0) || (1,1) || (2,2) || (3,3) || (4,4) || ?(5,5)?
            */
                for (int i = n_atual, j = n_depois - estado; i >= 0 && j >= 0 ; i--, j--) { // i Pedidos, j Entregas

                    f1_prob[estado + 3] += f1p[i] * f1e[j];
                    f2_prob[estado + 3] += f2p[i] * f2e[j];

                    lucro_f1_acc[estado + 3]+=calculaLucroSat(i, j, f1p[i] * f1e[j], estado);
                    lucro_f2_acc[estado + 3]+=calculaLucroSat(i, j, f2p[i] * f2e[j], estado);
                }
            }
        }
        //System.out.println();
        return new Par ( new Par(lucro_f1_acc,lucro_f2_acc),new Par(f1_prob, f2_prob));
    }

    //Constroi a matriz Satizfação.
    public  Par<double[][], double[][]> buildMatrixSat(int tam) {
        int i, j;
        double[][][] m1 = new double[7][tam][tam];
        double[][][] m2 = new double[7][tam][tam];

        //m1 = Matrix.init(tam, 0);
        //m2 = Matrix.init(tam, 0);


        //este par tem aqui as probabilidades
        Par<Par<double[], double[]>,Par<double[], double[]> > Custos_probs;

        for (i = 0; i < tam; i++) {
            for (j = 0; j < tam; j++) {
                Custos_probs = calculaSat(i, j);

                // Matriz
                double[] probs1 = Custos_probs.getSecond().getFirst();
                double[] probs2 = Custos_probs.getSecond().getSecond();

                for (int estado = -3; estado < 4; estado++) {
                    m1[estado + 3][i][j] = probs1[estado + 3];
                    m2[estado + 3][i][j] = probs2[estado + 3];
                }

                    /*
                    //Matiz de custos;
                    double[] valC_f1=Custos_probs.getFirst().getFirst();
                    double[] valC_f2=Custos_probs.getFirst().getSecond();
                    this.mCustos.inc0(valC_f1[0]+valC_f2[0],i,j);
                    this.mCustos.inc11(valC_f1[1],i,j);
                    this.mCustos.inc12(valC_f1[2],i,j);
                    this.mCustos.inc13(valC_f1[3],i,j);
                    this.mCustos.inc21(valC_f2[1],i,j);
                    this.mCustos.inc22(valC_f2[2],i,j);
                    this.mCustos.inc23(valC_f2[3],i,j);
                    */

            }
        }

        System.out.println(m1.length*m1[0].length * m1[0][0].length);
        for (int estado = -3; estado < 4; estado++) {
            System.out.println("Estado " + estado);
            Matrix.printM(m1[estado + 3]);
            int indice = 0;
            for (double[] lista : m1[estado + 3]) {
                System.out.println("Estado " + indice + " " + Arrays.stream(lista).sum());
                indice++;
            }
            System.out.println();
        }

        //return new Par(m1, m2);
        return null;
    }

    //Calcula uma probabilidade de insatizfação, dado no numero de carros atual e o numero final.
 /*   public   Par<Par<double[], double[]> ,Par<Double, Double>> calculaInsat(int n_atual, int n_depois) {
        double f1_prob = 0;
        double f2_prob = 0;
        int i,estado;
        double []lucro_f1_acc=new double[4];
        double []lucro_f2_acc=new double[4];
        i=n_atual+1;
        while ( i<13 ) {
            //System.out.print("P("+i+")"+"*"+"E("+n_depois+")+");
            f1_prob+=f1p[i]*f1e[n_depois];
            f2_prob+=f2p[i]*f2e[n_depois];
            for (estado=0;estado<4;estado++) {
                lucro_f1_acc[estado]+=calculaLucroInsat(n_atual, n_depois, f1p[i] * f1e[n_depois], estado);
                lucro_f2_acc[estado]+=calculaLucroInsat(n_atual, n_depois, f2p[i] * f2e[n_depois], estado);
            }
            i++;
        }
        //System.out.print("\t"+f1_prob);
        //System.out.println();

        return new Par ( new Par(lucro_f1_acc,lucro_f2_acc),new Par(f1_prob, f2_prob));
    }*/
    public   Par<Par<double[], double[]> ,Par<Double, Double>> calculaInsat(int n_atual, int n_depois) {
        double []f1_prob;
        double []f2_prob;
        int i,estado;
        double []lucro_f1_acc=new double[4];
        double []lucro_f2_acc=new double[4];
        i=n_atual+1;
        
        while ( i<13 ) {
            //System.out.print("P("+i+")"+"*"+"E("+n_depois+")+");
            f1_prob+=f1p[i]*f1e[n_depois];
            f2_prob+=f2p[i]*f2e[n_depois];
            for (estado=0;estado<4;estado++) {
                lucro_f1_acc[estado]+=calculaLucroInsat(n_atual, n_depois, f1p[i] * f1e[n_depois], estado);
                lucro_f2_acc[estado]+=calculaLucroInsat(n_atual, n_depois, f2p[i] * f2e[n_depois], estado);
            }
            i++;
        }
        //System.out.print("\t"+f1_prob);
        //System.out.println();

        return new Par ( new Par(lucro_f1_acc,lucro_f2_acc),new Par(f1_prob, f2_prob));
    }

    //Constroi a matriz Insatizfação.
    public  Par<double[][], double[][]> buildMatrixIns(int tam) {
        int i, j;
        double[][] m1 = new double[tam][tam];
        m1 = Matrix.init(tam, 0);
        double[][] m2 = new double[tam][tam];
        m2 = Matrix.init(tam, 0);
        Par<Par<double[], double[]>,Par<Double, Double> > Custos_probs;
        for (i = 0; i < tam; i++) {
            for (j = 0; j < tam; j++) {
                Custos_probs = calculaInsat(i, j);
                m1[i][j] =Custos_probs.getSecond().getFirst();
                m2[i][j] =Custos_probs.getSecond().getSecond();
                //Matiz de custos;
                double[] valC_f1=Custos_probs.getFirst().getFirst();
                double[] valC_f2=Custos_probs.getFirst().getSecond();
                this.mCustos.inc0(valC_f1[0]+valC_f2[0],i,j);
                this.mCustos.inc11(valC_f1[1],i,j);
                this.mCustos.inc12(valC_f1[2],i,j);
                this.mCustos.inc13(valC_f1[3],i,j);
                this.mCustos.inc21(valC_f2[1],i,j);
                this.mCustos.inc22(valC_f2[2],i,j);
                this.mCustos.inc23(valC_f2[3],i,j);
            }
        }
        return new Par(m1, m2);
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
        Main a =new Main(13);
        //     F1          F2
        /*
        Par<double[][], double[][]> m=a.getmProb_sat();
        Par<double[][], double[][]> m2=a.getmProb_insat();

        //Matrizes probabilidades finais
        double[][] P1_final=Matrix.add(m.getFirst(),m2.getFirst());
        double[][] P2_final=Matrix.add(m.getSecond(),m2.getSecond());

        //Matrix.printM(P1_final);
        //double []sol = a.resolve_N_iteracao(P1_final,P2_final,a.getmCustos(),13,20);
        //for (int i=0;i<sol.length;i++){
        //    System.out.println("sol["+i+"]="+sol[i]);
        //}
        */

        a.buildMatrixSat(13);
    }
}






