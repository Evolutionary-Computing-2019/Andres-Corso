package co.edu.unal.ce.codeAC;

public class SESharedF implements Selection<TTPInd> {

    private double sigma;

    public SESharedF(double sigma){
        this.sigma = sigma;
    }

    private Individual<TTPInd> selBestOfFour(Individual<TTPInd>[] pop){
        Individual<TTPInd> best = pop[(int)(pop.length*Math.random())];
        double shFbest = sharedFitness(best, pop);
        for(int i=1; i<=3; i++ ){
            Individual<TTPInd> comp = pop[(int)(pop.length*Math.random())];
            double shFcomp = sharedFitness(comp, pop);
            if( shFcomp < shFbest ){
                best = comp;
                shFbest = shFcomp;
            }
        }
        return best;
    }
    private double sharedFitness(Individual<TTPInd> x, Individual<TTPInd>[] pop){
        Integer[] tsp = x.x().getTSP();
        Integer[] kp = x.x().getKP();

        double sh = 0.0;
        for (Individual<TTPInd> ttpIndIndividual : pop) {
            Integer[] tspComp = ttpIndIndividual.x().getTSP();
            Integer[] kpComp = ttpIndIndividual.x().getKP();

            double dist = KendallTau(tsp, tspComp) * CosineSim(kp, kpComp);


            if (dist < sigma) {
                sh += 1 - (dist / sigma);
            }
        }

        //System.out.println(sh);
        return x.f()/sh;
    }
    @Override
    public Individual<TTPInd>[] get(Individual<TTPInd>[] pop, int n) {
        @SuppressWarnings("unchecked")
        Individual<TTPInd>[] s = new Individual[n];
        for( int i=0; i<n; i++ ){
            s[i] = selBestOfFour(pop);
        }
        return s;
    }

    private double CosineSim (Integer[] a, Integer[] b) {
        double num = 0;
        double den = 0;
        for(int i=0;i< a.length; i++){
            if(!(a[i] == -1 || b[i] == -1)){
                num++;
            }
            if(!(a[i] == -1 && b[i] == -1)){
                den++;
            }
        }
        return 1-num/den;
    }

    private double KendallTau(Integer[] a, Integer[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array dimensions disagree");
        }
        int n = a.length;

        int[] ainv = new int[n];
        for (int i = 0; i < n; i++)
            ainv[a[i]] = i;

        Integer[] bnew = new Integer[n];
        for (int i = 0; i < n; i++)
            bnew[i] = ainv[b[i]];

        return Inversions.count(bnew);
    }

}
