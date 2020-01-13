package pl.edu.ug.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    public static List<List<Double>> generatePoints(int size){
        List<Double> X = new ArrayList<>();
        List<Double> Y = new ArrayList<>();
        List<List<Double>> points = new ArrayList<>();
        Random gen = new Random();
        for (int i=0;i<size;i++){
            X.add(Double.valueOf(i));
            Y.add(gen.nextDouble()*10);
        }
        points.add(X);
        points.add(Y);
        return points;
    }
}
