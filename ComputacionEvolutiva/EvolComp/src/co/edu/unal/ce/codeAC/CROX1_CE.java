package co.edu.unal.ce.codeAC;

import java.util.Random;

public class CROX1_CE implements Crossover<TTP2Ind> {
    private double prob = 0.7;
    @Override
    public TTP2Ind[] apply(Individual<TTP2Ind>[] parents) {
        int n = parents[0].x().getGene().length;
        Integer[][] children = new Integer[2][n];

        if (Math.random() < prob) {
            int i = (int) (Math.random() * n - 1);
            int l = (int) (Math.random() * (n - i));
            //System.out.println("i: "+i+ " l: "+l);

            int j = 0;
            for (int k = 0; k < n; k++) {
                if (k < l) {
                    children[0][k + i] = parents[0].x().getGene()[k + i];
                    j = (i + k + 1) % n;
                } else {
                    while (inArray(children[0], parents[1].x().getGene()[j])) {
                        j = (j + 1) % n;
                    }
                    children[0][(k + i) % n] = parents[1].x().getGene()[j];
                }
            }
            j = (l + 1) % n;
            for (int k = 0; k < n; k++) {
                if (k < l) {
                    children[1][k + i] = parents[1].x().getGene()[k + i];
                    j = (i + k + 1) % n;
                } else {
                    while (inArray(children[1], parents[0].x().getGene()[j])) {
                        j = (j + 1) % n;
                    }
                    children[1][(k + i) % n] = parents[0].x().getGene()[j];
                }
            }
        }else{
            children[0] = parents[0].x().getGene().clone();
            children[1] = parents[1].x().getGene().clone();

        }
        TTP2Ind[] nCh = new TTP2Ind[2];
        Random random = new Random();
        if (random.nextBoolean()) {
            nCh[0] = new TTP2Ind(parents[0].x().getFriend(), children[0]);
            nCh[1] = new TTP2Ind(parents[1].x().getFriend(), children[1]);
        }else{
            nCh[0] = new TTP2Ind(parents[1].x().getFriend(), children[0]);
            nCh[1] = new TTP2Ind(parents[0].x().getFriend(), children[1]);
        }

        return nCh;
    }

    private boolean inArray(Integer[] x, Integer v){
        for (Integer i: x){
            if(v.equals(i)){
                return true;
            }
        }
        return false;
    }


}
