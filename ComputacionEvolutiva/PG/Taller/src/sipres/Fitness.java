package sipres;

import sipres.interpreter.*;
import sipres.language.LexicalException;
import sipres.language.Parser;
import sipres.language.SyntacticalException;
import sipres.language.Term;

public class Fitness {

    public double eval(Evaluator e, Program p, Input in) throws GoalException, ProgramException, SyntacticalException, LexicalException, ExampleException {

        double cov = 0;
        double tot=0;

        for (Term a: Parser.parsing(in.getExamples())){

            Term sal = e.evalue(p, a.getChild(0));

            if (sal.equals(a.getChild(1))) {
                cov++;
            }
            tot++;



        }
        return -1 * cov / tot;
    }
}
