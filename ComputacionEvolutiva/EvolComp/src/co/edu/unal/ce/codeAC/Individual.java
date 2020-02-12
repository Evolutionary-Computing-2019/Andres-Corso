package co.edu.unal.ce.codeAC;

public class Individual<T> {
    private T x;
    private double f;
    private double g;

    public Individual(T x, double f ){
        this.x = x;
        this.f = f;
        this.g = 0.0;
    }
    public Individual(T x, double f, double g ){
        this.x = x;
        this.f = f;
        this.g = g;
    }

    public void setF(double f) {
        this.f = f;
    }

    public T x(){ return x; }
    public double f(){ return f; }
    public double g(){ return g; }

    @Override
    public String toString() {
        if(x instanceof Boolean[]) {
            Boolean[] na = (Boolean[]) x;
            String geneString = "";
            for (Boolean aBoolean : na) {
                if (aBoolean) {
                    geneString += 1;
                } else {
                    geneString += 0;
                }
            }
            return geneString;
        }else if (x instanceof Integer[]){
            Integer[] a = (Integer[]) x;
            String geneString = String.valueOf(a[0]);
            for (int i = 1; i< a.length; i++ ){
                geneString += "/"+String.valueOf(a[i]);

            }
            return geneString;
        }else if (x instanceof Double[]){
            Double[] a = (Double[]) x;
            String geneString = String.format("%.3f", a[0]);
            for (int i = 1; i< a.length; i++ ){
                geneString += " / "+ String.format("%.3f", a[i]);

            }
            return geneString;
        }else if (x instanceof TTPInd){
            Integer[] tsp =  ((TTPInd) x).getTSP();
            String geneString = String.valueOf(tsp[0]);
            for (int i = 1; i< tsp.length; i++ ){
                geneString += "-"+String.valueOf(tsp[i]);
            }
            geneString += "\t\t";
            Integer[] kp =  ((TTPInd) x).getKP();
            geneString += String.valueOf(kp[0]);
            for (int i = 1; i< kp.length; i++ ){
                geneString += "/"+String.valueOf(kp[i]);
            }
            return geneString;

        }else if (x instanceof TTP2Ind) {
            Integer[] tsp = (Integer[])((TTP2Ind) x).getGene();
            String geneString = String.valueOf(tsp[0]);
            for (int i = 1; i < tsp.length; i++) {
                geneString += "-" + String.valueOf(tsp[i]);
            }
            geneString += "\t\t";
            Integer[] kp =  (Integer[])((TTP2Ind) x).getGeneFriend();
            geneString += String.valueOf(kp[0]);
            for (int i = 1; i< kp.length; i++ ){
                geneString += "/"+String.valueOf(kp[i]);
            }

            return geneString;
        }else{
            return String.valueOf(x);
        }

    }
}
