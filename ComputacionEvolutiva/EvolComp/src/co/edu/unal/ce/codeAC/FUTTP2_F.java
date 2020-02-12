package co.edu.unal.ce.codeAC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.DoublePredicate;

public class FUTTP2_F implements Function<TTPInd>  {
    protected int nCities;
    protected int nItems;
    protected Double maxW;
    protected Double maxV;
    protected Double minV;
    protected Double C;
    protected Double Dr;
    protected Double[][] distances;
    protected Double[] weights;
    protected Double[] values;
    protected Boolean[][] availability;

    protected Double[] partialT;

    public FUTTP2_F(String file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            nCities = Integer.parseInt(br.readLine());
            nItems = Integer.parseInt(br.readLine());

            maxW = Double.parseDouble(br.readLine());
            maxV = Double.parseDouble(br.readLine());
            minV = Double.parseDouble(br.readLine());

            C = Double.parseDouble(br.readLine());
            Dr = Double.parseDouble(br.readLine());

            distances = new Double[nCities][nCities];
            for (int i = 0; i < nCities; i++){
                line = br.readLine();
                String[] d = line.split(" ");
                for (int j = 0; j < nCities; j++){
                    distances[i][j] = Double.valueOf(d[j]);
                }
            }

            weights = new Double[nItems];
            line = br.readLine();
            String[] d = line.split(" ");
            for (int j = 0; j < nItems; j++){
                weights[j] = Double.valueOf(d[j]);
            }

            values = new Double[nItems];
            line = br.readLine();
            d = line.split(" ");
            for (int j = 0; j < nItems; j++){
                values[j] = Double.valueOf(d[j]);
            }

            availability = new Boolean[nItems][nCities];
            for (int i = 0; i < nItems; i++){
                line = br.readLine();
                d = line.split(" ");
                for (int j = 0; j < nCities; j++){
                    availability[i][j] = d[j].equals("1");
                }
            }

            partialT = new  Double[nCities];


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double eval(TTPInd x) {
        Integer[] tsp = x.getTSP();
        Integer[] kp  = x.getKP();
        Double[] totW = iW(kp);

        double time = distances[tsp[nCities-1]][tsp[0]] / vel(totW[nCities-1]);
        double fitness = time;
        partialT[nCities-1] = time;
        for(int i=0; i<nCities-1; i++){
            time = distances[tsp[i]][tsp[i + 1]] / vel(totW[i]);
            partialT[i] = time;
            fitness += time;
        }

        return fitness;
    }


    public double eval_KP(TTPInd x) {

        Integer[] kp  = x.getKP();

        double fitness = 0.0;
        for(int i=0; i < nItems; i++){
            Double t = 0.0;
            if (kp[i] != 0){
                for (int j = kp[i]-1 ; j < nCities; j++){
                    t += partialT[j];
                }
                fitness += values[i] * Math.pow(Dr,t/C);
            }
        }
        return -1*fitness;
    }

    @Override
    public int getSize() {
        return nCities;
    }



    private double vel(Double w){
        return maxV - w*(maxV-minV)/maxW;
    }

    private Double[] iW(Integer[] kp){
        /*Peso de la mochila durante el recorrido*/
        Double[] currentW = new Double[nCities];
        for(int i=0; i < nCities; i++){
            currentW[i] = 0.0;
        }
        for(int i=0; i < nItems; i++){
            if (kp[i] >= 0){
                for (int j = kp[i] ; j < nCities; j++) {
                    currentW[j] += weights[i];
                }
            }
        }
        return currentW;
    }


    public Boolean[][] getAvailability() {
        return availability;
    }
}
