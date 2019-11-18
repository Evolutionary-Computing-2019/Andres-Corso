package co.edu.unal.ce;

import co.edu.unal.ce.codeAC.*;

public class Example {
    public static void local(){
        Function<Double> f = new FUQuadratic();
        //Mutation<Double> m = new MUUniform();
        Mutation<Double> m = new MUGaussian();
        Space<Double> e = new SPInterval(-10,10);
        Replace<Double> r = new REHIllClimbing<>();
        ALGLocal<Double> ac = new ALGLocal<Double>(r, e, f, m);
        Individual<Double> x = ac.apply(1000);
        System.out.println("Local gets:"+x+" "+x.f());
    }

    public static void popBased(){
        Function<Double> f = new FUQuadratic();
        //Mutation<Double> m = new MUUniform();
        Mutation<Double> m = new MUGaussian();
        Crossover<Double> c = new CRLinear();
        Space<Double> e = new SPInterval(-10,10);
        Replace<Double> r = new REGenerational<Double>();
        Selection<Double> s = new SEBestOfFour<Double>();
        ALGPopulationBased<Double> ac = new ALGPopulationBased<Double>(r, e, f, s, c, m);
        Individual<Double> x = ac.apply(100, 1000);
        System.out.println("PopBased gets:"+x+" "+x.f());
    }


    public static void main(String[] args) {
	// write your code here
        local();
        //popBased();
    }
}
