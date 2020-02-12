package co.edu.unal.ce.codeAC;

public class FUTTP2_G_CE implements Function<TTP2Ind>  {
    private int nCities;
    private int nItems;
    private Double maxW;
    private Double maxV;
    private Double minV;
    private Double C;
    private Double Dr;
    private Double[][] distances;
    private Double[] weights;
    private Double[] values;
    private Boolean[][] availability;

    private Double[] partialT;
    private FUTTP2_F_CE f;

    protected Integer[] tsp;

    public FUTTP2_G_CE(Function<TTP2Ind> g){
        f = (FUTTP2_F_CE) g;
        nCities = f.nCities;
        nItems = f.nItems;
        maxW = f.maxW;
        maxV = f.maxV;
        minV = f.minV;
        C = f.C;
        Dr = f.Dr;
        distances = f.distances;
        weights = f.weights;
        values = f.values;
        availability = f.availability;
        partialT = new  Double[nCities];


    }

    @Override
    public double eval(TTP2Ind x) {

        Integer[] kp = x.getGene();
        Integer[] tsp = x.getGeneFriend();

        Double[] totW = iW(kp);

        double time = distances[tsp[nCities-1]][tsp[0]] / vel(totW[nCities-1]);

        partialT[nCities-1] = time;
        for(int i=0; i<nCities-1; i++){
            time = distances[tsp[i]][tsp[i + 1]] / vel(totW[i]);
            partialT[i] = time;

        }

        double fitness = 0.0;
        for(int i=0; i < nItems; i++){
            Double t = 0.0;
            if (kp[i] >= 0){
                for (int j = kp[i] ; j < nCities; j++){
                    t += partialT[j];
                }
                fitness += values[i] * Math.pow(Dr,t/C);
            }
        }
        return -1*fitness;
    }

    @Override
    public int getSize() {
        return nItems;
    }


    private double vel(Double w){
        return maxV - w*(maxV-minV)/maxW;
    }

    private Double[] iW(Integer[] kp){
        /*Peso de la mochila durante el recorrido*/
        Double[] currentW = new Double[nCities];
        for(int i=0; i < nCities; i++){
            currentW[i] = 0.0;
        }
        for(int i=0; i < nItems; i++){
            if (kp[i] >= 0){
                for (int j = kp[i] ; j < nCities; j++) {
                    currentW[j] += weights[i];
                }
            }
        }
        return currentW;
    }


}
