package co.edu.unal.ce.codeAC;

public class MUInv_CH implements Mutation<TTPInd> {
    private Mutation<Integer[]> mTSP;
    private Mutation<Integer[]> mKP;
    public MUInv_CH(int n){
        this.mTSP = new MUInversion();
        this.mKP = new MUBinaryCH(n);
    }

    @Override
    public TTPInd apply(TTPInd child) {
        Integer[] mutsp = mTSP.apply(child.getTSP().clone());
        Integer[] mukp = mKP.apply(child.getKP().clone());
        TTPInd mutation = new TTPInd(mutsp, mukp);
        return mutation;
    }
}