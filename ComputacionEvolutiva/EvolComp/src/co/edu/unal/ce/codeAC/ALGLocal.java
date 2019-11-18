package co.edu.unal.ce.codeAC;

public class ALGLocal<T> {
    protected Function<T> f;
    protected Mutation<T> m;
    protected Space<T> e;
    protected Replace<T> r;

    public ALGLocal(Replace<T> r, Space<T> e, Function<T> f, Mutation<T> m ){
        this.r = r;
        this.e = e;
        this.f = f;
        this.m = m;
    }

    public Individual<T> apply( int ITERS ){
        T x = e.get();
        double vx = f.eval(x);
        @SuppressWarnings("unchecked")
        Individual<T>[] ind = new Individual[1];
        ind [0] = new Individual<T>(x, vx);
        System.out.println("0 "+x+":"+vx);
        for( int i=1; i<=ITERS; i++){
            T y = e.repair(m.apply(x));
            double vy = f.eval(y);
            @SuppressWarnings("unchecked")
            Individual<T>[] ind2 = new Individual[1];
            ind2[0] = new Individual<T>(y, vy);
            ind  = r.apply(ind,ind2);
            System.out.println(i+" "+ind[0].x()+":"+ind[0].f());
        }
        return ind[0];
    }
}
