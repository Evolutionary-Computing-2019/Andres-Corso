package co.edu.unal.ce.codeAC;

public class CRMOC_CUT implements Crossover<TTPInd> {
    private int n;
    private Crossover<Integer[]> cTSP;
    private Crossover<Integer[]> cKP;
    public CRMOC_CUT(int n){
        this.n = n;
        //this.cTSP = new CRMOC(4);
        this.cTSP = new CROX1();
        this.cKP = new CRCut();
    }

    @Override
    public TTPInd[] apply(Individual<TTPInd>[] parents) {
        @SuppressWarnings("unchecked")
        Individual<Integer[]>[] tsp = new Individual[2];
        @SuppressWarnings("unchecked")
        Individual<Integer[]>[] kp = new Individual[2];
        tsp[0] = new Individual<Integer[]>(parents[0].x().getTSP(),0);
        tsp[1] = new Individual<Integer[]>(parents[1].x().getTSP(),0);

        kp[0] = new Individual<Integer[]>(parents[0].x().getKP(),0);
        kp[1] = new Individual<Integer[]>(parents[1].x().getKP(),0);
        Integer[][] crTSP = cTSP.apply(tsp);
        Integer[][] crKP  = cKP.apply(kp);
        @SuppressWarnings("unchecked")
        TTPInd[] cross = new TTPInd[2];
        cross[0] = new TTPInd(crTSP[0], crKP[0]);
        cross[1] = new TTPInd(crTSP[1], crKP[1]);

        return cross;
    }
}
