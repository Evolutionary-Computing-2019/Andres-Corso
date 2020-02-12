package co.edu.unal.ce.codeAC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FUTTP2_G implements Function<TTPInd>  {
    private int nCities;
    private int nItems;
    private Double maxW;
    private Double maxV;
    private Double minV;
    private Double C;
    private Double Dr;
    private Double[][] distances;
    private Double[] weights;
    private Double[] values;
    private Boolean[][] availability;

    private Double[] partialT;
    private FUTTP2_F f;

    public FUTTP2_G(Function<TTPInd> g){
        f = (FUTTP2_F) g;
        nCities = f.nCities;
        nItems = f.nItems;
        maxW = f.maxW;
        maxV = f.maxV;
        minV = f.minV;
        C = f.C;
        Dr = f.Dr;
        distances = f.distances;
        weights = f.weights;
        values = f.values;
        availability = f.availability;

        partialT = f.partialT;
    }

    @Override
    public double eval(TTPInd x) {
        partialT = f.partialT;

        Integer[] kp  = x.getKP();

        double fitness = 0.0;
        for(int i=0; i < nItems; i++){
            Double t = 0.0;
            if (kp[i] >= 0){
                for (int j = kp[i] ; j < nCities; j++){
                    t += partialT[j];
                }
                fitness += values[i] * Math.pow(Dr,t/C);
            }
        }
        return -1*fitness;
    }

    @Override
    public int getSize() {
        return nItems;
    }


}
