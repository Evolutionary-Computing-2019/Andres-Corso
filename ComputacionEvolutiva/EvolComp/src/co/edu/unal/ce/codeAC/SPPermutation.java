package co.edu.unal.ce.codeAC;

import javax.swing.*;

public class SPPermutation implements Space<Integer[]> {
    private int n;

    public SPPermutation(int n){
        this.n = n;
    }
    @Override
    public Integer[] get() {
        return Shuffle.apply(n);
    }

    @Override
    public Integer[] repair(Integer[] x) {
        return x;
    }
}
