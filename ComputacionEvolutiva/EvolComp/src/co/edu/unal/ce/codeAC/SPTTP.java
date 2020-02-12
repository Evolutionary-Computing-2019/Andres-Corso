package co.edu.unal.ce.codeAC;

import javax.swing.*;

public class SPTTP implements Space<TTPInd> {


    private final Double[] weights;
    private int nC;
    private int nI;
    private double maxW;
    private Boolean[][] av;



    public SPTTP(Function<TTPInd> f, int ttp)
    {
        this.nC = f.getSize();
        if (ttp == 1) {
            FUTTP1 t = (FUTTP1) f;
            this.nI = t.nItems;
            this.av = t.getAvailability();
            this.maxW = t.maxW;
            this.weights = t.weights;
        }else{
            FUTTP2_F t = (FUTTP2_F) f;
            this.nI = t.nItems;
            this.av = t.getAvailability();
            this.maxW = t.maxW;
            this.weights = t.weights;

        }

    }

    @Override
    public TTPInd get() {
        Integer[] newKP = new Integer[nI];
        for (int i=0; i < this.nI; i++){
            if (Math.random() < 1.0/nI){
                int j;
                do{
                    j = (int)(nC*Math.random());
                    newKP[i] = j;
                }while (!av[i][j]);

            }else {
                newKP[i] = -1;
            }
        }


        return  repair(new TTPInd(Shuffle.apply(nC),newKP));


    }

    @Override
    public TTPInd repair(TTPInd x) {
        Integer[] kp = x.getKP().clone();
        Integer[] tsp = x.getTSP().clone();
        for (int i=0; i < nI; i++){
            if(kp[i] >= 0 ){
                while (!av[i][kp[i]]) {
                    kp[i] = (int)(nC*Math.random());
                }
            }
        }
        while(getW(kp) >= maxW){
            int j = (int)(nI*Math.random());
            while (kp[j] >= 0){
                kp[j] = -1;
                j = (int)(nI*Math.random());
            }
        }


        return new TTPInd(tsp, kp);
    }

    private double getW(Integer[] kp){

        double w = 0.0;
        for (int i=0; i < nI; i++){
            if (kp[i] >= 0){
                w += weights[i];
            }
        }
        return w;
    }
}
