package pl.edu.ug.tests;

import pl.edu.ug.algorithms.GaussSeidel;
import pl.edu.ug.algorithms.Jacobi;
import pl.edu.ug.csi.CSI;
import pl.edu.ug.structures.Matrix;

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
        CSI csi = new CSI("pomorskie","regular","gauss");
        csi.checkResultDifferencesInHiddenValues();
        CSI csi1 = new CSI("warminskie","regular","gauss");
        csi1.checkResultDifferencesInHiddenValues();
        CSI csi2 = new CSI("mazowieckie","regular","gauss");
        csi2.checkResultDifferencesInHiddenValues();
        CSI csi3 = new CSI("hills","regular","gauss");
        csi3.checkResultDifferencesInHiddenValues();
        CSI csi4 = new CSI("xtremehills","regular","gauss");
        csi4.checkResultDifferencesInHiddenValues();
    }

    public static void interationAlgorithmsMistakeTest() throws Exception {
        CSI csi = new CSI("pomorskie","regular","jacobi");
        csi.checkResultDifferencesInHiddenValues();
        CSI csi1 = new CSI("pomorskie","regular","gauss-seidel");
        csi1.checkResultDifferencesInHiddenValues();
        CSI csi2 = new CSI("pomorskie","regular","gauss");
        csi2.checkResultDifferencesInHiddenValues();

    }

    public static void interationAlgorithmsDifferenceTest() throws Exception {
        CSI csi = new CSI("pomorskie100","regular","jacobi");
        List<Double> result = csi.M;
        CSI csi1 = new CSI("pomorskie100","regular","gauss-seidel");
        List<Double> result1 = csi1.M;
        CSI csi2 = new CSI("pomorskie100","regular","gauss");
        List<Double> result2 = csi2.M;
        CSI csi3 = new CSI("pomorskie100","sparse","jacobi");
        List<Double> result3 = csi3.M;
        CSI csi4 = new CSI("pomorskie100","sparse","gauss-seidel");
        List<Double> result4 = csi4.M;

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

    public static void speedTest() throws Exception {
        double sum1=0,sum2=0,sum3=0;
        for(int i=0;i<50;i++) {
            long var1 = System.nanoTime();
            CSI csi = new CSI("pomorskie", "regular", "gauss");
            long var2 = System.nanoTime() - var1;
            sum1 += (double) var2 / 1.0E9D;

            var1 = System.nanoTime();
            CSI csi1 = new CSI("pomorskie", "regular", "jacobi");
            var2 = System.nanoTime() - var1;
            sum2 += (double) var2 / 1.0E9D;

            var1 = System.nanoTime();
            CSI csi2 = new CSI("pomorskie", "regular", "gauss-seidel");
            var2 = System.nanoTime() - var1;
            sum3 += (double) var2 / 1.0E9D;
        }
        System.out.println("Gauss: " + sum1/50);
        System.out.println("Jacobi: " + sum2/50);
        System.out.println("Gauss-Seidel: " + sum3/50);
    }
}
