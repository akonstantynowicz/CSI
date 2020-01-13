package pl.edu.ug.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Gauss {
    static int N;

    public Gauss() {
    }

    public Gauss(int n) {
        N = n;
    }

    private static void redukujMacierz(double[][] A, double[] B, int i, int j) {
        double wspolczynnik;
        for (int k = i + 1; k < N; k++) {
            wspolczynnik = A[k][j]/(A[i][j]);
            for (int l = j; l < N; l++) {
                double tmp = A[k][l];
                A[k][l] = tmp-(wspolczynnik*(A[i][l]));
            }
            B[k] = B[k]-(wspolczynnik*(B[i]));
        }
    }

    public static List<Double> PG(double[][] A, double[] B) {
        double max;
        int p;
        int j;
        for (int i = 0; i < N; i++) {
            j = i;
            max = Math.abs(A[i][j]);
            p = i;
            for (int k = i + 1; k < N; k++) {
                if (Math.abs(A[k][j]) > max) {
                    max = Math.abs(A[k][j]);
                    p = k;
                }
            }
            if (A[i][j] != max) {
                for (int l = 0; l < N; l++) {
                    double tmp = A[i][l];
                    A[i][l] = A[p][l];
                    A[p][l] = tmp;
                }
                double tmp = B[i];
                B[i] = B[p];
                B[p] = tmp;
            }
            redukujMacierz(A, B, i, j);
        }
        return dajWynik(A,B);
    }

    static List<Double> dajWynik(double[][] A, double[] B) {
        double[] wynik = new double[N];
        double[] tmp = new double[N - 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = N - 1; j > i; j--) {
                tmp[j - 1] = A[i][j] * wynik[j];
                B[i] = B[i] - tmp[j - 1];
            }
            wynik[i] = B[i] / A[i][i];
        }
        List<Double> result = new ArrayList<>();
        for(int i=0; i<N;i++){
            result.add(wynik[i]);
        }
        return result;
    }
}
