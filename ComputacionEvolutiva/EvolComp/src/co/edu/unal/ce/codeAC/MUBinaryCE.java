package co.edu.unal.ce.codeAC;

public class MUBinaryCE implements Mutation<TTP2Ind> {
    private int nFriends;
    private int n;
    private double prob = 0.1;

    public MUBinaryCE(int nC, int nF) {
        this.n = nC;
        this.nFriends = nF;
    }

    @Override
    public TTP2Ind apply(TTP2Ind child) {
        double p = 1.0 / child.getGene().length;
        TTP2Ind newChild = new TTP2Ind(child.getFriend(), child.getGene().clone());
        for (int i = 0; i < child.getGene().length; i++) {
            if (Math.random() < p) {
                if (child.getGene()[i] >= 0) {
                    newChild.setGenei(i, -1);
                } else {
                    newChild.setGenei(i, (int) (n * Math.random()));
                }
            }
        }
        if (Math.random() < prob) {
            child.setFriend((int)(Math.random()*nFriends));
        }
        return newChild;
    }
}
