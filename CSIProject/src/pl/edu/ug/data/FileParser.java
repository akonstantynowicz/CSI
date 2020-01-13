package pl.edu.ug.data;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
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

        JSONObject obj = new JSONObject(str);
        if (! obj.getString("status").equals("OK"))
            return null;

        int N = obj.getJSONArray("results").length();
        BufferedWriter var0 = new BufferedWriter(new FileWriter("points.txt"));
        double x,y,x1=0,y1=0;
        for (int i=0; i<N; i++){
            JSONObject res = obj.getJSONArray("results").getJSONObject(i);
            Y.add(res.getDouble("elevation"));
            JSONObject loc = res.getJSONObject("location");
                x=loc.getDouble("lat");
                y=loc.getDouble("lng");
                    double distance = Math.sqrt(Math.pow((x-x1),2)+Math.pow((y-y1),2));
                    if(i==0){
                        X.add(distance);
                    }else{
                        X.add(X.get(i-1)+distance);
                    }
                x1 = x;
                y1 = y;
            var0.write( X.get(i)+ ";" + res.getDouble("elevation")+"\n");
        }
        var0.close();
        int numberToHide = (int) (percentage*0.01*N);

        //random numbers hidden
        /*Random gen = new Random();
        int i=0;
        while(i<numberToHide){
            int toHide = gen.nextInt(N-i-1);
            hiddenX.add(X.get(toHide));
            X.remove(toHide);
            hiddenY.add(Y.get(toHide));
            Y.remove(toHide);
            i++;
        }*/

        //chosen numbers hidden
        int i=0;
        for(i=1;i<X.size();i=i+numberToHide){
            hiddenX.add(X.get(i));
            X.remove(i);
            hiddenY.add(Y.get(i));
            Y.remove(i);
        }

        points.add(X);
        points.add(Y);
        points.add(hiddenX);
        points.add(hiddenY);
        return points;
    }
}
