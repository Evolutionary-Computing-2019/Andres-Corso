/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sipres.interpreter;

import sipres.SipresSetTheory;
import sipres.language.LexicalException;
import sipres.language.Parser;
import sipres.language.SyntacticalException;
import sipres.language.Term;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Camiku
 */
public final class Equation implements sipres.language.SipresConstants {

    private Term root;
    private int maxLength = 0;

    public Equation(Term term) {
        this.root = term;
        maxLength = toString().length() - 2;
    }

    public Equation(Equation e) {
        this((Term) e.getRoot().clone());
    }

    public Equation(Term lhs, Term rhs) {
        this.root = new Term("equal", EQUAL, null);
        this.root.addChild(lhs);
        this.root.addChild(rhs);
        maxLength = toString().length() - 2;
    }

    public Equation(String text) throws LexicalException, SyntacticalException, ExampleException {
        this(Parser.parsing(text).getFirst());
    }

    public Term getRoot() {
        return root;
    }

    public void setRoot(Term root) {
        this.root = root;
    }

    /**
     * Get the value of lhs
     *
     * @return the value of lhs
     */
    public Term getLhs() {
        return this.getRoot().getChild(0);
    }

    /**
     * Set the value of lhs
     *
     * @param lhs new value of lhs
     */
    public void setLhs(Term lhs) {
        this.getRoot().setChild(0, lhs);
    }

    /**
     * Get the value of rhs
     *
     * @return the value of rhs
     */
    public Term getRhs() {
        return this.getRoot().getChild(1);
    }

    /**
     * Set the value of rhs
     *
     * @param rhs new value of rhs
     */
    public void setRhs(Term rhs) {
        this.getRoot().setChild(1, rhs);
    }

    @Override
    public Object clone() {
        return new Equation(this);
    }

    @Override
    public String toString() {
        return getRoot().printSyntacticSugar();
    }

    public String print() {
        return getLhs().print() + " = " + getRhs().print();
    }

    public String printWithOutSyntacticalSugar() {
        return getRoot().print();
    }

    public Equation getVariant() {
        return new Equation(this.getRoot().getVariant());
    }

    private int calculateMaxLength() {
        maxLength = getRoot().toString().length() - 2;
        return maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public Equation renameVar() {
        this.getRoot().renameVar();
        calculateMaxLength();
        return this;
    }

    public int getNumberNodes() {
        return this.getRoot().getNumberNodes();
    }

    public boolean isBasicEquation() {
        return isBasicEquation(getRhs());
    }

    private boolean isBasicEquation(Term term) {
        switch (term.getType()) {
            case FUNCTOR:
                return false;
            case SUCCESSOR:
                return isBasicEquation(term.getChild(0));
            case LIST:
                return isBasicEquation(term.getChild(0)) && isBasicEquation(term.getChild(1));
            case VARIABLE:
            case NULL:
            case NATURAL:
            case TRUE:
            case FALSE:
            case CONSTANT:
            case UNDEFINED:
                return true;
            default:
                System.out.println("Equation neither simple nor composed");
                return true;
        }
    }

    public Term getBasicFunctor() {
        return (Term) this.getLhs().clone();
    }

    public boolean isPrimitiveFunctor() {
        for (Iterator<Term> iterator = this.getLhs().getListChildren().iterator(); iterator.hasNext();) {
            if (iterator.next().getType() != VARIABLE) {
                return false;
            }
        }
        return true;
    }

    public Term getPrimitiveFunctor() {
        Term functor = new Term(this.getLhs().getValue(), FUNCTOR);
        for (int i = 0; i < this.getLhs().getArity(); i++) {
            functor.addChild(new Term(Term.getFreshVariable(), VARIABLE));
        }
        return functor;
    }

    public boolean isValid() {
        return this.getRoot().isEquationProgramTerm();
    }

    public LinkedList<Term> getListMappings() {
        return getListMappings(this);
    }

    public static LinkedList<Term> getListMappings(Equation equa) {
        return getListMappings(equa.getRoot(), equa.getLhs(), null);
    }

    private static LinkedList<Term> getListMappings(Term term, Term mainFunctor, LinkedList<Term> list) {
        switch (term.getType()) {
            case VARIABLE:
            case NULL:
            case TRUE:
            case FALSE:
            case NATURAL:
            case CONSTANT:
            case UNDEFINED:
                return list;
            case SUCCESSOR:
            case EQUAL_SYMBOL:
            case EQUAL:
            case LIST:
            case FUNCTOR:
                if (term.getType() == FUNCTOR && term != mainFunctor) {
                    if (list == null) {
                        list = new LinkedList<Term>();
                    }
                    list.add(term);
                }
                for (Iterator<Term> it = term.getListChildren().iterator(); it.hasNext();) {
                    list = getListMappings(it.next(), mainFunctor, list);
                }
                return list;
            default:
                System.out.println("Term type no found");
                return null;
        }
    }

    public ArrayList<Term> getListFunctorNArity() {
        return getListFunctorNArity(this.getRoot(), null);
    }

    public static ArrayList<Term> getListFunctorNArity(Equation equa) {
        return getListFunctorNArity(equa.getRoot(), null);
    }

    private static ArrayList<Term> getListFunctorNArity(Term term, ArrayList<Term> listF) {
        if (term.getArity() > 1 && term.getType() != EQUAL && term.getType() != EQUAL_SYMBOL) {
            if (listF == null) {
                listF = new ArrayList<Term>();
            }
            listF.add(term);
        }
        if (term.getListChildren() != null) {
            for (Iterator<Term> it = term.getListChildren().iterator(); it.hasNext();) {
                listF = getListFunctorNArity(it.next(), listF);
            }
        }
        return listF;
    }

    public LinkedList<Term> getListSubTermsWithoutEqualAndLhs() {
        LinkedList<Term> list = getLhs().getListSubTerms();
        getRhs().getListSubTerms(list);
        list.remove(getLhs());
        return list;
    }

    public static ArrayList<Term> getListMappingArityOne(Equation equa) {
        return getListMappingArityOne(equa.getRoot(), null);
    }

    private static ArrayList<Term> getListMappingArityOne(Term term, ArrayList<Term> list) {
        switch (term.getType()) {
            case VARIABLE:
            case NULL:
            case TRUE:
            case FALSE:
            case NATURAL:
            case CONSTANT:
            case UNDEFINED:
                return list;
            case EQUAL_SYMBOL:
            case EQUAL:
            case LIST:
            case SUCCESSOR:
            case FUNCTOR:
                if (term.getArity() == 1 && term.getParent() != null
                        && term.getParent().getArity() > 1
                        && term.getParent().getType() != EQUAL_SYMBOL
                        && term.getParent().getType() != EQUAL) {
                    if (list == null) {
                        list = new ArrayList<Term>();
                    }
                    list.add(term);
                }
                for (Iterator<Term> it = term.getListChildren().iterator(); it.hasNext();) {
                    list = getListMappingArityOne(it.next(), list);
                }
                return list;
            default:
                System.out.println("Term type no found");
                return null;
        }
    }

    public Equation repair() {
        while (!getLhs().hasVar() && getRhs().hasVar()) {
            getLhs().changeVarConsBySetVar(getRhs().getSetVars());
        }
        repairRhsVarWithLhsVar();
        return this;
    }

    public Equation repairRhsVarWithLhsVar() {
        HashSet<String> setLhs = getLhs().getSetVars();
        HashSet<String> setRhs = getRhs().getSetVars();
        if (!setRhs.isEmpty()) {
            if (setLhs.isEmpty()) {
                for (int i = 0; i < setRhs.size(); i++) {
                    getLhs().changeConstantsBySameSetVar();
                }
                setLhs = getLhs().getSetVars();
            }
            if (!SipresSetTheory.isSubSet(setRhs, setLhs)) {
                getRhs().changeVarByVar(setLhs);
            }
        }
        return this;
    }

    public Equation repairRhsVarWithLhsFunctors() {
        Term functor = new Term(getLhs().getValue(), getLhs().getType());
        int i;
        for (i = 0; i < getLhs().getArity(); i++) {
            functor.addChild(new Term(Term.getFreshVariable(), VARIABLE));
        }
        Random random = new Random();
        LinkedList<Term> setTermVars = getRhs().getSubTermsVars();
        Term subTerm = setTermVars.get(random.nextInt(setTermVars.size()));
        Term parent = subTerm.getParent();
        i = parent.getListChildren().indexOf(subTerm);
        functor.setParent(parent);
        parent.setChild(i, functor);
        repairRhsVarWithLhsVar();
        return this;
    }

    public int calculateFUNICO(HashSet<String> hashCommutativeOperators) {
        return getRoot().calculateFUNICO(hashCommutativeOperators);
    }

}
