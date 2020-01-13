package pl.edu.ug.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SparseMatrix {
    public Map<Key,Double> matrix;
    public List<Double> vector;

    public SparseMatrix() {
        matrix = new TreeMap<>();
        vector = new ArrayList<>();
    }

    public void print() {
        for (Map.Entry<Key,Double> value : matrix.entrySet()){
            Key key = value.getKey();
            double number = value.getValue();
            System.out.println("Key: " + key + ": " + number);
        }
    }


}
