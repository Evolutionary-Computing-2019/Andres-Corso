package sipres;

import sipres.interpreter.Program;

public class Replace {
    public Program[] apply(Program[] pop, Program[] children) {
        return REBestPop1vsCh1(pop, children);
    }

    private Program[] REBestPop1vsCh1(Program[] pop, Program[] children) {
        Program[] bests = new Program[pop.length];
        Integer[] orderP =  Shuffle.apply(pop.length);
        Integer[] orderC =  Shuffle.apply(pop.length);
        for(int i = 0; i < pop.length; i++){
            if( children[orderC[i]].getCovering() <= pop[orderP[i]].getCovering() ){
                bests[i] = children[orderC[i]];
            }else{
                bests[i] = pop[orderP[i]];
            }

        }
        return bests;
    }

}
