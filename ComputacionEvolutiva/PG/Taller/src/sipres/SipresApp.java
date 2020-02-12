package sipres;

/*
 * FunicoApp.java
 * -Xms512m -Xmx1024m
 * -Xms32m -Xmx32m
 */
import sipres.interpreter.Evaluator;
import sipres.interpreter.ExampleException;
import sipres.interpreter.Extractor;
import sipres.interpreter.GoalException;
import sipres.interpreter.ProgramException;
import sipres.language.LexicalException;
import sipres.language.SyntacticalException;

/**
 * The main class of the application.
 */
public class SipresApp {

    public SipresApp() {
    }

    /**
     * Main method launching the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        try {
            Extractor ext = new Extractor(
                    "geq(0,1) = false; geq(0,0) = true; geq(1,0) = true; geq(1,1) = true; geq(1,2) = false; geq(2,1) = true; geq(2,5) = false; geq(5,2) = true; geq(3,3) = true");
            System.out.println(ext.getTableMainFunctors());
            System.out.println(ext.getTableFunctors());
            System.out.println(ext.getTableTerminals());
            System.out.println(ext.getTableVariables());
            System.out.println();
            System.out.println(evaluator.evalue(
                    "mod3(0) = 0; mod3(1) = 1; mod3(2) = 2; mod3(s(s(s(X)))) = mod3(X)",
                    "mod3(5)"));
            System.out.println(evaluator.evalue(
                    "even(0) = true; even(1) = false; even(s(s(X))) = even(X)",
                    "even(5)"));
            System.out.println(evaluator.evalue(
                    "sum(0,X) = X; sum(s(X),Y) = s(sum(X,Y))",
                    "sum(5,3)"));
        } catch (ExampleException | ProgramException | GoalException | LexicalException | SyntacticalException ex) {
            System.out.println(ex);
        }
        try {
            System.out.println();
            System.out.println(evaluator.evalue(
                    "sum(0,X) = X; sum(s(X),Y) = s(sum(X,Y))",
                    "sum(5,3)=0"));
        } catch (ProgramException | GoalException | LexicalException | SyntacticalException ex) {
            System.out.println(ex);
        }
        try {
            System.out.println();
            System.out.println(evaluator.evalue(
                    "sum(0,X) =| X; sum(s(X),Y) = s(sum(X,Y))",
                    "sum(5,3)"));
        } catch (ProgramException | GoalException | LexicalException | SyntacticalException ex) {
            System.out.println(ex);
        }
        try {
            System.out.println();
            System.out.println(evaluator.evalue(
                    "sum(0,X) = Y; sum(s(X),Y) = s(sum(X,Y))",
                    "sum(5,3)"));
        } catch (ProgramException | GoalException | LexicalException | SyntacticalException ex) {
            System.out.println(ex);
        }
    }
}
