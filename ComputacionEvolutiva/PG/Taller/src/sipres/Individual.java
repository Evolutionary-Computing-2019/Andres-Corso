package sipres;

import sipres.interpreter.Program;

public class Individual {
    private Program x;
    private double f;


    public Individual(Program x, double f) {
        this.x = x;
        this.f = f;

    }

    public Program x() {
        return x;
    }

    public double f() {
        return f;
    }

}