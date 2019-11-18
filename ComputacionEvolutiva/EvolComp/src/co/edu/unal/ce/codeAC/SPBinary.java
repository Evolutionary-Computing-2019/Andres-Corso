package co.edu.unal.ce.codeAC;

import javax.swing.*;

public class SPBinary implements Space<Boolean[]> {
    private double prob;
    private int n;
    public SPBinary(double prob, int n){
        this.n = n;
        this.prob = prob;
    }

    @Override
    public Boolean[] get() {
        Boolean[] newS = new Boolean[n];
        for (int i=0; i < this.n; i++){
            newS[i] = Math.random() < prob;
        }
        return newS;
    }

    @Override
    public Boolean[] repair(Boolean[] x) {
        return x;
    }
}
