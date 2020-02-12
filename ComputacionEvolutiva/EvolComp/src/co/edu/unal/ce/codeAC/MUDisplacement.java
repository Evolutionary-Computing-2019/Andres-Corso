package co.edu.unal.ce.codeAC;

public class MUDisplacement implements Mutation<Integer[]> {
    @Override
    public Integer[] apply(Integer[] child) {
        int i = (int)(Math.random() * child.length-1);
        int l = (int)(Math.random() * (child.length-i));
        int j = (int)(Math.random() * (l-i))+i;
        System.out.println(i+"-"+l+"-"+j);
        Integer[] mChild = new Integer[child.length];
        for (int k = 0; k < child.length; k++) {
            if ( k < i || k > i+l ){
                mChild[k] = child[k];
            }else if (k < i+l-j ){
                mChild[k] = child[k+j];
            }else{
                mChild[k] = child[k+i-j];
            }
        }
        return mChild;
    }
}
