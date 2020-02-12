package sipres;

import sipres.interpreter.Program;

import java.util.Random;

public class Crossover {
    private double probBinary = 0.9;
    private double probUnary = 0.3;


    public Program[] apply(Program[] parents) {
        Program[] ch;
        double doC = Math.random();
        if ( doC < probBinary){
            ch =  globalXOver(parents);
        }else{
            ch = parents.clone();
        }

        doC = Math.random();
        if(doC < probUnary){
            ch = unaryOprs(ch);

        }
        return ch;
    }

    private Program[] unaryOprs(Program[] ch) {
        Random random = new Random();
        if(random.nextBoolean()) {
            return internalSwap(ch);
        }
        return globalSwap(ch);
    }

    private Program[] globalSwap(Program[] ch) {
        Program[] newC = new Program[2];
        for (int i =0 ; i < ch.length; i++){
            Program child = ch[i];
            int i0 = (int)(child.getListEquations().size()*Math.random());
            int i1 = (int)(child.getListEquations().size()*Math.random());
            Program nc = new Program();
            for (int j = 0; j < child.getListEquations().size(); j++){
                if (j == i0){
                    nc.addEquationClone(child.getListEquations().get(i1));
                }else if(j==i1){
                    nc.addEquationClone(child.getListEquations().get(i0));
                }else{
                    nc.addEquationClone(child.getListEquations().get(j));
                }
            }
            newC[i] = nc;
        }
        return newC;
    }
    private Program[] internalSwap(Program[] ch) {
        return ch;
    }


    private Program[] globalXOver(Program[] parents) {
        int i0 = (int)(parents[0].getListEquations().size()*Math.random());
        int i1 = (int)(parents[0].getListEquations().size()*Math.random());
        Program[] ch = new Program[2];
        ch[0] = new Program();
        ch[1] = new Program();
        ch[0].addEquationItself(parents[1].getListEquations().get(i1));
        ch[1].addEquationItself(parents[0].getListEquations().get(i0));

        for (int i = 0; i < parents[0].getListEquations().size(); i++){
            if (i != i0){
                ch[0].addEquationItself(parents[0].getListEquations().get(i));
            }
            if (i != i1){
                ch[1].addEquationItself(parents[1].getListEquations().get(i));
            }

        }

        return ch;
    }



}

