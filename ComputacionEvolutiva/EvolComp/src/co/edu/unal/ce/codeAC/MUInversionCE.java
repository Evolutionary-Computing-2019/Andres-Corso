package co.edu.unal.ce.codeAC;

import co.edu.unal.ce.TTP2;

public class MUInversionCE implements Mutation<TTP2Ind> {
    private double prob = 0.1;
    private int nFriends;
    public MUInversionCE(int nF){
        this.nFriends = nF;
    }
    @Override
    public TTP2Ind apply(TTP2Ind child) {
        if (Math.random() < prob) {

            int i = (int) (Math.random() * child.getGene().length - 1);
            int l = (int) (Math.random() * (child.getGene().length - i));
            //System.out.println(i+"-"+l);
            for (int k = 0; k <= l / 2; k++) {
                Integer tmp = child.getGene()[i + k];
                child.setGenei(i + k, child.getGene()[i + l - k]);
                child.setGenei(i + l - k, tmp);
            }
        }
        if (Math.random() < prob) {
            child.setFriend((int)(Math.random()*nFriends));
        }
        return child;
    }
}