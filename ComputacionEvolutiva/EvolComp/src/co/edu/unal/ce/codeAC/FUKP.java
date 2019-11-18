package co.edu.unal.ce.codeAC;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FUKP implements Function<Boolean[]> {
    //Knapsack Problem
    private int nItems = 0;
    private int maxW = 0;
    private ArrayList<Integer> weights;
    private ArrayList<Integer> values;

    public FUKP(String file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] line = br.readLine().split(" ");
            nItems = Integer.parseInt(line[0]);
            maxW = Integer.parseInt(line[1]);

            weights = new ArrayList<Integer>(nItems);
            values = new ArrayList<Integer>(nItems);

            for (int i = 0; i < nItems; i++){
                line = br.readLine().split(" ");
                values.add(Integer.parseInt(line[0]));
                weights.add(Integer.parseInt(line[1]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double eval(Boolean[] x) {
        double fitness = 0;
        int weight = 0;
        for (int i = 0; i < x.length ; i++) {
            if (x[i]) {
                fitness += values.get(i);
                weight += weights.get(i);
                if (weight > maxW){
                    //fitness = 0;
                    //fitness = -100 * maxW;
                    return 0;
                }
            }
        }
        return -1*fitness;
    }

    @Override
    public int getSize() {
        return nItems;
    }
}
