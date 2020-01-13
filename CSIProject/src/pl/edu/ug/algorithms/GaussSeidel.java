package pl.edu.ug.algorithms;

import pl.edu.ug.structures.Key;
import pl.edu.ug.structures.Matrix;
import pl.edu.ug.structures.SparseMatrix;

import java.util.ArrayList;
import java.util.List;

public class GaussSeidel {
    int size;

    public GaussSeidel(int size) {
        this.size = size;
    }

    public List<Double> gaussSeidelMethod(Matrix M, int iterationNumber) {
        List<Double> X = initList(M.N,0);
        for (int i=0; i<iterationNumber; i++){
            for (int j=0; j<X.size(); j++){
                X.set(j,M.vector[j]);
                for (int k=0; k<X.size(); k++){
                    if (j!=k)
                        X.set(j, X.get(j)-X.get(k)*M.matrix[j][k]);
                }
                X.set(j,X.get(j)/M.matrix[j][j]);
            }
        }
        return X;
    }

    public List<Double> gaussSeidelMethod(SparseMatrix M, int iterationNumber) {
        List<Double> X = initList(this.size,0);
        for (int i=0; i<iterationNumber; i++){
            for (int j=0;j<X.size();j++){
                X.set(j,M.vector.get(j));
                for (int k=j-1;k<j+2;k++){
                    if(k!=j && k>=0 && k<X.size()){
                        Key key = new Key(j,k);
                        X.set(j, X.get(j)-X.get(k)*M.matrix.get(key));
                    }
                }
                Key key = new Key(j,j);
                X.set(j,X.get(j)/M.matrix.get(key));
            }
        }
        return X;
    }

    public List<Double> initList(int size, double t) {
        List<Double> list = new ArrayList<>();
        while (list.size() < size) list.add(t);
        return list;
    }

    public List<Double> copyList(List<Double> toCopy){
        List<Double> copied = new ArrayList<>();
        for(double value : toCopy){
            copied.add(value);
        }
        return copied;
    }
}
