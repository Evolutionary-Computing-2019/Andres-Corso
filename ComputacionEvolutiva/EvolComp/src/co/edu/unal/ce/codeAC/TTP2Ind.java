package co.edu.unal.ce.codeAC;

public class TTP2Ind {
    private Integer friend;
    private Integer[] gene;
    private Integer[] geneFriend;

    public TTP2Ind(Integer friend, Integer[] info){
        this.friend = friend;
        this.gene = info;
    }

    public TTP2Ind(Integer friend, Integer[] info, Integer[] gFriend){
        this.friend = friend;
        this.gene = info;
        this.geneFriend = gFriend;
    }

    public void setGeneFriend(Integer[] geneFriend) {
        this.geneFriend = geneFriend;
    }

    public Integer getFriend() {
        return friend;
    }

    public Integer[] getGeneFriend() {
        return geneFriend;
    }

    public Integer[] getGene() {
        return gene;
    }

    public void setFriend(Integer friend) {
        this.friend = friend;
    }

    public void setGene(Integer[] gene) {
        this.gene = gene;
    }

    public void setGenei(Integer i, Integer val){
        this.gene[i] = val;
    }

}
