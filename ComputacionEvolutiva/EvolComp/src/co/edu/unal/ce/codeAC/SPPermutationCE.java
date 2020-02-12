package co.edu.unal.ce.codeAC;

public class SPPermutationCE implements Space<TTP2Ind> {

    private int n;
    private int npop;

    public SPPermutationCE(int nC, int npop){
        this.npop = npop;
        this.n = nC;
    }
    @Override
    public TTP2Ind get() {
        Integer friend = (int)(Math.random() * npop);
        Integer[] gene = Shuffle.apply(n);
        return new TTP2Ind(friend, gene);
    }

    @Override
    public TTP2Ind repair(TTP2Ind x) {
        return x;
    }
}
