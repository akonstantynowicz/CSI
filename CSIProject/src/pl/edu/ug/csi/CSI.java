package pl.edu.ug.csi;

import pl.edu.ug.algorithms.Gauss;
import pl.edu.ug.algorithms.GaussSeidel;
import pl.edu.ug.algorithms.Jacobi;
import pl.edu.ug.data.FileParser;
import pl.edu.ug.structures.Key;
import pl.edu.ug.structures.Matrix;
import pl.edu.ug.structures.SparseMatrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSI {
    public static List<Double> M;
    public static List<Double> X;
    public static List<Double> Y;
    public static List<Double> hiddenX;
    public static List<Double> hiddenY;
    public static int N;

    public CSI(String fileName,int numberOfPoints,int toHide,boolean randomize) throws Exception {
        FileParser fp = new FileParser();
        List<List<Double>> points = fp.pointsGenerator(fileName,toHide,numberOfPoints,randomize);
        X = points.get(0);
        Y = points.get(1);
        hiddenX = points.get(2);
        hiddenY = points.get(3);
        N = X.size();
        M = new ArrayList<>();
    }


    public static void fillMatrix(String matrixType, String algorithm,int iterationNumber) {
        double h0 = 0;
        double h1 = X.get(1) - (X.get(0));

        if (matrixType.equals("regular")) {
            Matrix m = new Matrix(N, N);
            m.fillWithZeros();
            for (int i = 0; i < N; i++) {
                if (i == 0) {
                    m.matrix[i][i + 1] = 1;
                    m.vector[i] = 6 / h1 * ((Y.get(i + 1) - Y.get(i)) / h1);
                } else if (i == N - 1) {
                    m.matrix[i][i - 1] = 1;
                    m.vector[i] = 6 / h1 * (0 - ((Y.get(i) - Y.get(i - 1)) / h1));
                } else {
                    h0 = h1;
                    h1 = X.get(i + 1) - (X.get(i));
                    m.vector[i] = (6 / (h0 + h1)) * (((Y.get(i + 1) - Y.get(i)) / h1) - ((Y.get(i) - Y.get(i - 1)) / h0));
                }
                for (int j = 0; j < N; j++) {
                    if (i == j) {
                        m.matrix[i][j] = 2;
                    } else if (i == j - 1 && i != 0) {
                        m.matrix[i][j] = h1 / (h0 + h1);
                    } else if (i == j + 1 && i != N - 1) {
                        m.matrix[i][j] = h1 / (h0 + h1);
                    }
                }
            }

            switch (algorithm) {
                case "gauss":
                    Gauss gauss = new Gauss(N);
                    M = gauss.PG(m.matrix, m.vector);
                    break;
                case "jacobi":
                    Jacobi jacobi = new Jacobi(N);
                    M = jacobi.jacobiMethod(m, iterationNumber);
                    break;
                case "gauss-seidel":
                    GaussSeidel gaussSeidel = new GaussSeidel(N);
                    M = gaussSeidel.gaussSeidelMethod(m, iterationNumber);
                    break;
                default:
                    System.out.println("Taka metoda nie istnieje dla tej macierzy");
            }
        }

        if (matrixType.equals("sparse")) {
            SparseMatrix sm = new SparseMatrix();
            for (int i = 0; i < N; i++) {
                Key keyv = new Key(i, i);
                sm.matrix.put(keyv, 2d);
                if (i == 0) {
                    Key key = new Key(i, i + 1);
                    sm.matrix.put(key, 1d);
                    sm.vector.add(6 / h1 * ((Y.get(i + 1) - Y.get(i)) / h1));
                } else if (i == N - 1) {
                    Key key = new Key(i, i - 1);
                    sm.matrix.put(key, 1d);
                    sm.vector.add(6 / h1 * (0 - ((Y.get(i) - Y.get(i - 1)) / h1)));
                } else {
                    h0 = h1;
                    h1 = X.get(i + 1) - (X.get(i));
                    sm.vector.add((6 / (h0 + h1)) * (((Y.get(i + 1) - Y.get(i)) / h1) - ((Y.get(i) - Y.get(i - 1)) / h0)));
                    Key key = new Key(i, i - 1);
                    Key key1 = new Key(i, i + 1);
                    sm.matrix.put(key, h1 / (h0 + h1));
                    sm.matrix.put(key1, h1 / (h0 + h1));
                }
            }

            switch (algorithm) {
                case "jacobi":
                    Jacobi jacobi = new Jacobi(N);
                    M = jacobi.jacobiMethod(sm, iterationNumber);
                    break;
                case "gauss-seidel":
                    GaussSeidel gaussSeidel = new GaussSeidel(N);
                    M = gaussSeidel.gaussSeidelMethod(sm, iterationNumber);
                    break;
                default:
                    System.out.println("Taka metoda nie istnieje dla tej macierzy");
            }
        }
    }

    public static Double interpolate(double x){
        if (X.get(0)>=x){
            return Y.get(0);
        }
        if (x>=X.get(N-1)){
            return Y.get(N-1);
        }
        double a,b,c,d,h1,t;
        int i=0;
        while(x>=X.get(i+1)){
            i++;
        }
        h1 = X.get(i+1)-X.get(i);
        a = Y.get(i);
        b = ((Y.get(i+1)-Y.get(i))/h1)-(((2*M.get(i)+M.get(i+1))/6)*(h1));
        c = M.get(i)/2;
        d = (M.get(i+1)-M.get(i))/(6*h1);
        t = x-X.get(i);
        Double result = a+(b*t)+(c*t*t)+(d*t*t*t);
        //System.out.println(result);
        return result;
    }

    public void saveInterpolated() throws IOException {
        BufferedWriter var0 = new BufferedWriter(new FileWriter("interpolated.txt"));
        for(int i=1;i<N;i++){
            double h=X.get(i)-X.get(i-1);
            var0.write(X.get(i)+h/2 + ";" +interpolate(X.get(i)+h/2) +"\n");
        }
        var0.close();
    }

    public static SparseMatrix transformToSparse(Matrix m){
        SparseMatrix sparse = new SparseMatrix();
        for (int i=0;i<m.M;i++){
            for (int j=0; j<m.N;j++){
                if(m.matrix[i][j]!=0){
                    Key key = new Key(i,j);
                    double v = m.matrix[i][j];
                    sparse.matrix.put(key,v);
                }
            }
            sparse.vector.add(m.vector[i]);
        }
        return sparse;
    }

    public static double checkResultDifferencesInHiddenValues(){
        double sum=0;
        for(int i=0;i<hiddenX.size();i++){
            double countedResult = interpolate(hiddenX.get(i));
            double realValue = hiddenY.get(i);
            sum += Math.abs(realValue-countedResult);
        }
        //System.out.println("Średni błąd: " + sum/hiddenX.size());
        return sum/hiddenX.size();
    }

    public static void checkResultDifferencesInKnownValues(){
        double sum=0;
        for(int i=0;i<N;i++){
            double countedResult = interpolate(X.get(i));
            double realValue = Y.get(i);
            sum += Math.abs(realValue-countedResult);
        }
        System.out.println("Średni błąd: " + sum/X.size());
    }
}
