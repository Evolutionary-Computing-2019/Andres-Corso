package co.edu.unal.ce.codeAC;

public class SPKPTTP2 implements Space<TTP2Ind> {

    private final Double[] weights;
    private int nC;
    private int nI;
    private double maxW;
    private Boolean[][] av;
    private int nP;

    public SPKPTTP2(Function<TTP2Ind> f, int nPop)
    {
        this.nC = f.getSize();

        FUTTP2_F_CE t = (FUTTP2_F_CE) f;
        this.nI = t.nItems;
        this.av = t.getAvailability();
        this.maxW = t.maxW;
        this.weights = t.weights;
        this.nP = nPop;

    }

    @Override
    public TTP2Ind get() {
        Integer friend = (int)(Math.random()*nP);
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
        return  repair(new TTP2Ind(friend, newKP, new Integer[nI]));

    }

    @Override
    public TTP2Ind repair(TTP2Ind x) {
        Integer[] kp = x.getGene().clone();

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


        return new TTP2Ind(x.getFriend(), kp);
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
