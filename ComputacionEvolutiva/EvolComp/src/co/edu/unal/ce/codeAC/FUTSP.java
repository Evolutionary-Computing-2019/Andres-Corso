package co.edu.unal.ce.codeAC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FUTSP implements Function<Integer[]>  {
    private int dimension;
    private Double[][] distances;
    private Integer[] bestS;
    private double fbestS;

    public FUTSP(String file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            do {
                line = br.readLine();
            }while (!line.startsWith("DIMENSION"));
            dimension =  Integer.parseInt(line.split(":")[1].replace(" ",""));

            distances = new Double[dimension][dimension];
            do {
                line = br.readLine();
            }while (!line.equals("NODE_COORD_SECTION"));
            Double[] x = new Double[dimension];
            Double[] y = new Double[dimension];
            for (int i = 0; i< dimension; i++){
                String[] coord = br.readLine().split(" ");
                x[i] = Double.valueOf(coord[1]);
                y[i] = Double.valueOf(coord[2]);
                distances[i][i] = 0.0;
            }
            for (int i = 0; i< dimension; i++){
                for (int j = i+1; j< dimension; j++){
                    distances[i][j] = calcDistance(x[i],y[i],x[j],y[j]);
                    distances[j][i] = distances[i][j];
                }
            }
            do {
                line = br.readLine();
            }while (!line.equals("TOUR_SECTION"));
            bestS = new Integer[dimension];
            for (int i=0; i<dimension; i++){
                line = br.readLine();
                bestS[i] = Integer.parseInt(line)-1;
            }
            fbestS = this.eval(bestS);
            System.out.println("Best path distance: "+fbestS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Double calcDistance(Double x1, Double y1, Double x2, Double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    @Override
    public double eval(Integer[] x) {
        double fitness = distances[x[dimension-1]][x[0]];
        for(int i=0; i<dimension-1; i++){
            fitness += distances[x[i]][x[i+1]];
        }
        return fitness;
    }

    @Override
    public int getSize() {
        return dimension;
    }
}
