package pl.edu.ug;

import pl.edu.ug.csi.CSI;

public class Main {

    public static void main(String[] args) throws Exception {
        //Test.mistakeTest();
        //Test.speedTest();
        CSI csi = new CSI("pomorze100","regular","gauss");
        csi.saveInterpolated();
    }
}
