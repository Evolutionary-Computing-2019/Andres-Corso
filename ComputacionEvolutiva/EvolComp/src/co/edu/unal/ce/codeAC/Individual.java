package co.edu.unal.ce.codeAC;

public class Individual<T> {
    private T x;
    private double f;

    public Individual(T x, double f ){
        this.x = x;
        this.f = f;
    }

    public T x(){ return x; }
    public double f(){ return f; }

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
                geneString += "-"+String.valueOf(a[i]);

            }
            return geneString;
        }else{
            return String.valueOf(x);
        }

    }
}
