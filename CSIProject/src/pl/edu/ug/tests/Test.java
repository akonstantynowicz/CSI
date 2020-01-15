package pl.edu.ug.tests;

import pl.edu.ug.algorithms.GaussSeidel;
import pl.edu.ug.algorithms.Jacobi;
import pl.edu.ug.csi.CSI;
import pl.edu.ug.structures.Matrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class Test {
    
    public void jacobiCheck(){
        Matrix test = new Matrix(3,3);
        test.matrix[0][0]=5;
        test.matrix[0][1]=-1;
        test.matrix[0][2]=2;
        test.matrix[1][0]=3;
        test.matrix[1][1]=8;
        test.matrix[1][2]=-2;
        test.matrix[2][0]=1;
        test.matrix[2][1]=1;
        test.matrix[2][2]=4;
        test.vector[0]=12;
        test.vector[1]=-25;
        test.vector[2]=6;
        test.print();
        Jacobi jacobi = new Jacobi(3);
        System.out.println("Wynik Jacobi: " + jacobi.jacobiMethod(test,2));
    }
    public void gaussSeidelCheck(){
        Matrix test = new Matrix(3,3);
        test.matrix[0][0]=5;
        test.matrix[0][1]=-1;
        test.matrix[0][2]=2;
        test.matrix[1][0]=3;
        test.matrix[1][1]=8;
        test.matrix[1][2]=-2;
        test.matrix[2][0]=1;
        test.matrix[2][1]=1;
        test.matrix[2][2]=4;
        test.vector[0]=12;
        test.vector[1]=-25;
        test.vector[2]=6;
        test.print();
        GaussSeidel gaussSeidel = new GaussSeidel(3);
        System.out.println("Wynik Gauss-Seidel: " + gaussSeidel.gaussSeidelMethod(test,2));
    }

    public static void mistakeTest() throws Exception {
        CSI csi = new CSI("pomorskie",500,10,false);
        csi.fillMatrix("regular", "gauss",0);
        System.out.println(csi.checkResultDifferencesInHiddenValues());
        CSI csi1 = new CSI("warminskie",500,10,false);
        csi1.fillMatrix("regular", "gauss",0);
        System.out.println(csi1.checkResultDifferencesInHiddenValues());
        CSI csi2 = new CSI("mazowieckie",500,10,false);
        csi2.fillMatrix("regular", "gauss",0);
        System.out.println(csi2.checkResultDifferencesInHiddenValues());
        CSI csi3 = new CSI("hills",500,10,false);
        csi3.fillMatrix("regular", "gauss",0);
        System.out.println(csi3.checkResultDifferencesInHiddenValues());
        CSI csi4 = new CSI("xtremehills",500,10,false);
        csi4.fillMatrix("regular", "gauss",0);
        System.out.println(csi4.checkResultDifferencesInHiddenValues());
    }

    public static void interationAlgorithmsMistakeTest() throws Exception {
        CSI csi = new CSI("pomorskie",500,10,false);
        csi.fillMatrix("regular", "jacobi",60);
        System.out.println(csi.checkResultDifferencesInHiddenValues());
        csi.fillMatrix("regular", "gauss-seidel",60);
        System.out.println(csi.checkResultDifferencesInHiddenValues());
        csi.fillMatrix("regular", "gauss",0);
        System.out.println(csi.checkResultDifferencesInHiddenValues());

    }

    public static void algorithmsDifferenceTest() throws Exception {
        CSI csi = new CSI("pomorskie100",100,0,false);
        csi.fillMatrix("regular", "jacobi",60);
        List<Double> result = csi.M;
        csi.fillMatrix("regular", "gauss-seidel",60);
        List<Double> result1 = csi.M;
        csi.fillMatrix("regular", "gauss",0);
        List<Double> result2 = csi.M;
        csi.fillMatrix("sparse", "jacobi",60);
        List<Double> result3 = csi.M;
        csi.fillMatrix("sparse", "gauss-seidel",60);
        List<Double> result4 = csi.M;

        System.out.println("Różnica między Jacobi a Gauss-Seidel: " + compareResults(result,result1));
        System.out.println("Różnica między Gauss a Gauss-Seidel: " + compareResults(result1,result2));
        System.out.println("Różnica między Jacobi a Gauss: " + compareResults(result,result2));
        System.out.println("Różnica między Jacobi a Jacobi sparse: " + compareResults(result,result3));
        System.out.println("Różnica między Gauss-Seidel a Gauss-Seidel sparse: " + compareResults(result1,result4));
        System.out.println("Różnica między Jacobi a Gauss-Seidel sparse: " + compareResults(result,result4));
        System.out.println("Różnica między Gauss-Seidel a Jacobi sparse: " + compareResults(result1,result3));
        System.out.println("Różnica między Gauss a Gauss-Seidel sparse: " + compareResults(result2,result4));
        System.out.println("Różnica między Gauss a Jacobi sparse: " + compareResults(result2,result3));
    }

    public static double compareResults(List<Double> result1, List<Double> result2){
        double sum=0;
        for (int i=0;i<result1.size();i++){
            sum += Math.abs(result1.get(i)-result2.get(i));
        }
        return sum;
    }

    public static void speedTest(int numberOfPoints) throws Exception {
        double sum1=0,sum2=0,sum3=0,sum4=0,sum5=0;
        CSI csi = new CSI("pomorskie", numberOfPoints, 0,false);
        System.out.println("Number of points:" + numberOfPoints);
        for(int i=0;i<50;i++) {
            long var1 = System.nanoTime();
            csi.fillMatrix("regular","gauss",0);
            long var2 = System.nanoTime() - var1;
            sum1 += (double) var2 / 1.0E9D;

            var1 = System.nanoTime();
            csi.fillMatrix("regular","jacobi",60);
            var2 = System.nanoTime() - var1;
            sum2 += (double) var2 / 1.0E9D;

            var1 = System.nanoTime();
            csi.fillMatrix("regular","gauss-seidel",60);
            var2 = System.nanoTime() - var1;
            sum3 += (double) var2 / 1.0E9D;

            var1 = System.nanoTime();
            csi.fillMatrix("sparse","jacobi",60);
            var2 = System.nanoTime() - var1;
            sum4 += (double) var2 / 1.0E9D;

            var1 = System.nanoTime();
            csi.fillMatrix("sparse","gauss-seidel",60);
            var2 = System.nanoTime() - var1;
            sum5 += (double) var2 / 1.0E9D;
        }
        System.out.println("Gauss: " + sum1/50);
        System.out.println("Jacobi: " + sum2/50);
        System.out.println("Gauss-Seidel: " + sum3/50);
        System.out.println("Sparse Jacobi: " + sum4/50);
        System.out.println("Sparse Gauss-Seidel: " + sum5/50);
    }

   public static void GaussVsJacobiSparse() throws Exception {
       CSI csi = new CSI("ultimate", 2000, 10, false);
       long var1 = System.nanoTime();
       csi.fillMatrix("regular", "gauss", 0);
       long var2 = System.nanoTime() - var1;
       System.out.println((double) var2 / 1.0E9D);
       System.out.println(csi.checkResultDifferencesInHiddenValues());

       var1 = System.nanoTime();
       csi.fillMatrix("sparse", "jacobi", 60);
       var2 = System.nanoTime() - var1;
       System.out.println((double) var2 / 1.0E9D);
       System.out.println(csi.checkResultDifferencesInHiddenValues());
   }

   public static void iterationMethodTest() throws Exception {
        CSI csi = new CSI("pomorskie100", 100,0,false);
       csi.fillMatrix("regular","gauss",0);
        List<Double> resultGauss = csi.M;
        for(int i=0;i<500;i++) {
            csi.fillMatrix("regular","jacobi",i);
            List<Double> resultJacobi = csi.M;
            System.out.println(i + " " + compareResults(resultGauss,resultJacobi));
        }
    }

    public static void dependenciesTest() throws Exception {
        CSI csi = new CSI("ultimate",100,10,false);
        csi.fillMatrix("regular","gauss",0);
        List<Double> resultGauss = csi.M;
        double sum;
        BufferedWriter var0 = new BufferedWriter(new FileWriter("zaleznosci.txt"));
        for(int i=0;i<100;i++) {
            sum=0;
            for (int j=0;j<50;j++) {
                long var1 = System.nanoTime();
                csi.fillMatrix("sparse", "gauss-seidel", i);
                long var2 = System.nanoTime() - var1;
                sum += (double) var2 / 1.0E9D;
            }
            List<Double> resultJacobi = csi.M;
            var0.write(i + ";" + csi.checkResultDifferencesInHiddenValues() + ";" + sum/50 + "\n");

        }
        var0.close();
    }
}
