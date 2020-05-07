package Main;

public class MatCusto {
    //Matriz  para a filial 1 e 2.
    double[][] mCusto0;
    // Matriz Custo para a filial 1 associada a tranferir 1,2 ou 3 .
    double[][] mCusto1_f1;
    double[][] mCusto2_f1;
    double[][] mCusto3_f1;

    // Matriz Custo para a filial 2, associada a tranferir 1,2 ou 3.

    double[][] mCusto1_f2;
    double[][] mCusto2_f2;
    double[][] mCusto3_f2;

    public MatCusto(double[][] m0, double[][] m11, double[][] m12, double[][] m13, double[][] m21, double[][] m22, double[][] m23){
        this.mCusto0=m0;

        this.mCusto1_f1=m11;
        this.mCusto2_f1=m12;
        this.mCusto3_f1=m13;

        this.mCusto1_f2=m21;
        this.mCusto2_f2=m22;
        this.mCusto3_f2=m23;
    }

    public MatCusto(int tam){
        this.mCusto0=Matrix.init(tam,0);
        this.mCusto1_f1=Matrix.init(tam,0);;
        this.mCusto2_f1=Matrix.init(tam,0);;
        this.mCusto3_f1=Matrix.init(tam,0);;
        this.mCusto1_f2=Matrix.init(tam,0);;
        this.mCusto2_f2=Matrix.init(tam,0);;
        this.mCusto3_f2=Matrix.init(tam,0);;
    }


    public void setmCusto0(double[][] mCusto0) {
        this.mCusto0 = mCusto0;
    }

    public void setmCusto1_f1(double[][] mCusto1_f1) {
        this.mCusto1_f1 = mCusto1_f1;
    }

    public void setmCusto2_f1(double[][] mCusto2_f1) {
        this.mCusto2_f1 = mCusto2_f1;
    }

    public void setmCusto3_f1(double[][] mCusto3_f1) {
        this.mCusto3_f1 = mCusto3_f1;
    }

    public void setmCusto1_f2(double[][] mCusto1_f2) {
        this.mCusto1_f2 = mCusto1_f2;
    }

    public void setmCusto2_f2(double[][] mCusto2_f2) {
        this.mCusto2_f2 = mCusto2_f2;
    }

    public void setmCusto3_f2(double[][] mCusto3_f2) {
        this.mCusto3_f2 = mCusto3_f2;
    }

    public double[][] getmCusto0() {
        return mCusto0;
    }

    public double[][] getmCusto1_f1() {
        return mCusto1_f1;
    }

    public double[][] getmCusto2_f1() {
        return mCusto2_f1;
    }

    public double[][] getmCusto3_f1() {
        return mCusto3_f1;
    }

    public double[][] getmCusto1_f2() {
        return mCusto1_f2;
    }

    public double[][] getmCusto2_f2() {
        return mCusto2_f2;
    }

    public double[][] getmCusto3_f2() {
        return mCusto3_f2;
    }


    //Incrementa o valor na matriz de custo 0, na posicao indicada.
    public boolean inc0(double val,int i ,int j) {
        this.mCusto0[i][j]+= val;
        return true;
    }
    //Put na matriz mCust
    public boolean inc11(double val,int i ,int j) {
        this.mCusto1_f1[i][j]+=val;
        return true;
    }
    //Put na matriz 12
    public boolean inc12(double val,int i ,int j) {
        this.mCusto2_f1[i][j]+=val;
        return true;
    }
    public boolean inc13(double val,int i ,int j) {
        this.mCusto3_f1[i][j]+=val;
        return true;
    }
    public boolean inc21(double val,int i ,int j) {
        this.mCusto1_f2[i][j]+=val;
        return true;
    }
    public boolean inc22(double val,int i ,int j) {
        this.mCusto2_f2[i][j]+=val;
        return true;
    }
    public boolean inc23(double val,int i ,int j) {
        this.mCusto3_f2[i][j]+=val;
        return true;
    }




    public double get_M0_elem(int i ,int j) {
        return  this.mCusto0[i][j];
    }
    public double get_M11_elem(int i ,int j) {
       return this.mCusto1_f1[i][j];
    }
    public double get_M12_elem(int i ,int j) {
        return this.mCusto2_f1[i][j];
    }
    public double get_M13_elem(int i ,int j) {
        return this.mCusto3_f1[i][j];
    }
    public double get_M21_elem(int i ,int j) {
        return this.mCusto1_f2[i][j];
    }public double get_M22_elem(int i ,int j) {
        return this.mCusto2_f2[i][j];
    }
    public double get_M23_elem(int i ,int j) {
        return this.mCusto3_f2[i][j];
    }


    //Put na matriz

    /*public boolean put0(int m,double val,int i ,int j){
        if (m==0){
            this.mCusto0[i][j]=val;
            return true;
        }else if(m==11){
            this.mCusto1_f1[i][j]=val;
            return true;
        }
        else if(m==12){
            this.mCusto2_f1[i][j]=val;
            return true;
        }
        else if(m==13){
            this.mCusto3_f1[i][j]=val;
            return true;
        }
        else if(m==21){
            this.mCusto1_f2[i][j]=val;
            return true;
        }
        else if(m==22){
            this.mCusto2_f2[i][j]=val;
            return true;
        }else if(m==23){
            this.mCusto3_f2[i][j]=val;
            return true;
        }else {
            return false;
        }
    }*/
}
