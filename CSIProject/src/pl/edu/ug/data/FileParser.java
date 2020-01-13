package pl.edu.ug.data;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FileParser {

    public static List<List<Double>> pointsGenerator(String fileName, int percentage) throws Exception
    {
        List<Double> X = new ArrayList<>();
        List<Double> Y = new ArrayList<>();
        List<Double> hiddenX = new ArrayList<>();
        List<Double> hiddenY = new ArrayList<>();
        List<List<Double>> points = new ArrayList<>();
        File text = new File(fileName);
        Scanner scan = new Scanner(text);
        String str = new String();
        while (scan.hasNext())
            str += scan.nextLine();
        scan.close();

        // build a JSON object
        JSONObject obj = new JSONObject(str);
        if (! obj.getString("status").equals("OK"))
            return null;
        // get the first result
        int N = obj.getJSONArray("results").length();
        BufferedWriter var0 = new BufferedWriter(new FileWriter("points.txt"));
        for (int i=0; i<N; i++){
            JSONObject res = obj.getJSONArray("results").getJSONObject(i);
            Y.add(res.getDouble("elevation"));
            JSONObject loc = res.getJSONObject("location");
            X.add(loc.getDouble("lng"));
            var0.write( loc.getDouble("lng")+ ";" + res.getDouble("elevation")+"\n");
        }
        var0.close();
        int numberToHide = (int) (percentage*0.01*N);
        Random gen = new Random();
        int i=0;
        while(i<numberToHide){
            int toHide = gen.nextInt(N-i-1);
            hiddenX.add(X.get(toHide));
            X.remove(toHide);
            hiddenY.add(Y.get(toHide));
            Y.remove(toHide);
            i++;
        }
        points.add(X);
        points.add(Y);
        points.add(hiddenX);
        points.add(hiddenY);
        return points;
    }
}
