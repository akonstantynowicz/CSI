package pl.edu.ug.tests;

import pl.edu.ug.algorithms.GaussSeidel;
import pl.edu.ug.algorithms.Jacobi;
import pl.edu.ug.csi.CSI;
import pl.edu.ug.structures.Matrix;

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
