package co.edu.unal.ce.codeAC;

import java.util.Random;

public class CRCut_CE implements Crossover<TTP2Ind> {
    private double prob = 0.7;

    @Override
    public TTP2Ind[] apply(Individual<TTP2Ind>[] parents) {

        Integer[][] children = new Integer[2][];
        children[0] = parents[0].x().getGene().clone();
        children[1] = parents[1].x().getGene().clone();

        if (Math.random() < prob) {
            int pc = (int) ((children[0].length - 1) * Math.random());
            for (int i = pc; i < children[0].length; i++) {
                Integer tmp = children[0][i];
                children[0][i] = children[1][i];
                children[1][i] = tmp;
            }
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
}
