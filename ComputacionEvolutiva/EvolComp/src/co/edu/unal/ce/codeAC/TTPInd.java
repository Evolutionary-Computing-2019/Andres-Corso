package co.edu.unal.ce.codeAC;

public class TTPInd {
    private Integer[] TSP;
    private Integer[] KP;

    public TTPInd(Integer[] TSP, Integer[] KP){
        this.TSP = TSP;
        this.KP = KP;
    }

    public void setKP(Integer[] KP) {
        this.KP = KP;
    }

    public void setTSP(Integer[] TSP) {
        this.TSP = TSP;
    }

    public void setTSPi(Integer i, Integer val){
        this.TSP[i] = val;
    }
    public void setKPi(Integer i, Integer val){
        this.KP[i] = val;
    }

    public Integer[] getKP() {
        return KP;
    }

    public Integer[] getTSP() {
        return TSP;
    }
}
