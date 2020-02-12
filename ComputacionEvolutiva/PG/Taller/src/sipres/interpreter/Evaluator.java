package sipres.interpreter;

import sipres.language.LexicalException;
import sipres.language.SyntacticalException;
import sipres.language.Term;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import sipres.language.SipresConstants;

/**
 *
 * @author Camiku
 */
public class Evaluator implements SipresConstants {

    public static final int INITIAL_MAX_STEP = 500;
    public static final int INITIAL_MAX_REDEX = 20;
    public static final int WITHOUT_LIMIT_REDEX = -1;
    public static final int GOAL_EVALUATED = 1000;
    public static final int GOAL_NOT_EVALUATED = 1001;
    public static final int INFINITY_STEPS = 1002;
    public static final int NON_TRACE = 2000;
    public static final int TRACE_WITH_SYNTACTICAL_SUGAR = 2001;
    public static final int TRACE_WITHOUT_SYNTACTICAL_SUGAR = 2002;
    private static LinkedList<Boolean> equaEvaluated = new LinkedList<Boolean>();
    private static int status = Evaluator.GOAL_NOT_EVALUATED;
    private static final LinkedList<Term> LISTGOALS = new LinkedList();
    private static Equation equation, equafiltre;
    private static Term lhs, rhs, redex, termfiltre, goal, parent, newTerm;
    private static int maxNumberStep = INITIAL_MAX_STEP;
    private static int numberSteps;
    private static int maxNumberRedex = INITIAL_MAX_REDEX;
    private static int i_filtre;
    private static int iter_list_equa;
    private static int numberRedex;
    private static boolean hadComputation;
    private static Replacement sigma;

    public Evaluator() {
    }

    /**
     *
     * @param source
     * @param goal
     * @return
     * @throws LexicalException
     * @throws SyntacticalException
     * @throws funico.interpreter.ProgramException
     * @throws funico.interpreter.GoalException
     */
    public String evalue(String source, String goal)
            throws LexicalException, SyntacticalException, ProgramException, GoalException {
        return evalue(source, goal, Evaluator.INITIAL_MAX_STEP);
    }

    /**
     *
     * @param source
     * @param goal
     * @param maxStep
     * @return
     * @throws LexicalException
     * @throws SyntacticalException
     * @throws funico.interpreter.ProgramException
     * @throws funico.interpreter.GoalException
     */
    public String evalue(String source, String goal, int maxStep)
            throws LexicalException, SyntacticalException, ProgramException, GoalException {
        Program p = new Program(source);
        Term g = new Goal(goal).getListGoals().getFirst();
        return evalue(p, g, maxStep, Evaluator.WITHOUT_LIMIT_REDEX).toString();
    }

    /**
     *
     * @param source
     * @param goal
     * @param maxStep
     * @param maxRedex
     * @return
     * @throws LexicalException
     * @throws SyntacticalException
     * @throws funico.interpreter.ProgramException
     * @throws funico.interpreter.GoalException
     */
    public String evalue(String source, String goal, int maxStep, int maxRedex)
            throws LexicalException, SyntacticalException, ProgramException, GoalException {
        Program p = new Program(source);
        Term g = new Goal(goal).getListGoals().getFirst();
        return evalue(p, g, maxStep, maxRedex).toString();
    }

    public Term evalue(Program program, String goal) throws ProgramException, GoalException, LexicalException, SyntacticalException {
        Term g = new Goal(goal).getListGoals().getFirst();
        return evalue(program, g, Evaluator.INITIAL_MAX_STEP);
    }
    public Term evalue(Program program, Term goal) throws ProgramException, GoalException {
        return evalue(program, goal, Evaluator.INITIAL_MAX_STEP);
    }

    /**
     *
     * @param program
     * @param goal
     * @param maxStep
     * @return
     * @throws funico.interpreter.ProgramException
     * @throws funico.interpreter.GoalException
     */
    public Term evalue(Program program, Term goal, int maxStep) throws ProgramException, GoalException {
        return evalue(program, goal, maxStep, Evaluator.WITHOUT_LIMIT_REDEX, Evaluator.NON_TRACE, null, null);
    }

    /**
     *
     * @param program
     * @param goal
     * @param maxStep
     * @param maxRedex
     * @return
     * @throws funico.interpreter.ProgramException
     * @throws funico.interpreter.GoalException
     */
    public Term evalue(Program program, Term goal, int maxStep, int maxRedex) throws ProgramException, GoalException {
        return evalue(program, goal, maxStep, maxRedex, Evaluator.NON_TRACE, null, null);
    }

    public Term evalue(Program program, Term goal, int maxStep,
            int traceState, JTextArea jTextArea, JLabel statusBar) throws ProgramException, GoalException {
        return evalue(program, goal, maxStep, Evaluator.WITHOUT_LIMIT_REDEX, traceState, jTextArea, statusBar);
    }

    public Term evalue(Program program, Term goal, int maxStep, int maxRedex,
            int traceState, JTextArea jTextArea, JLabel statusBar) throws ProgramException, GoalException {
        if (!program.isValid()) {
            throw new ProgramException(program.toString());
        }
        if (!goal.isGoalTerm()) {
            throw new GoalException(goal.toString());
        }
        setMaxNumberStep(maxStep);
        setMaxNumberRedex(maxRedex);
        LISTGOALS.clear();
        LISTGOALS.add((Term) goal.clone());
        fillFalseList(program);
        numberSteps = -1;
        Evaluator.setStatus(Evaluator.GOAL_NOT_EVALUATED);
        if (traceState != Evaluator.NON_TRACE && jTextArea != null) {
            printHeadLine(jTextArea);
        }
        do {
            hadComputation = rewriteStep(program.getListEquations(), LISTGOALS, traceState, jTextArea);
            if (hadComputation) {
                Evaluator.setStatus(Evaluator.GOAL_EVALUATED);
            }
            numberSteps++;
            if (statusBar != null) {
                setReport(statusBar, LISTGOALS.getFirst());
            }
        } while (hadComputation && numberSteps < getMaxNumberStep());
        if (traceState != Evaluator.NON_TRACE && jTextArea != null) {
            printTrace(LISTGOALS.getFirst(), jTextArea, traceState);
        }
        return LISTGOALS.getFirst();
    }

    private boolean rewriteStep(LinkedList<Equation> listEquation,
            LinkedList<Term> listGoals, int traceState, JTextArea jTextArea) {
        goal = listGoals.pop();
        RedexIterator.reset(goal);
        do {
            redex = RedexIterator.next();
            if (redex != null) {
                for (iter_list_equa = 0; iter_list_equa < listEquation.size(); iter_list_equa++) {
                    equation = listEquation.get(iter_list_equa);
                    lhs = equation.getLhs();
                    rhs = equation.getRhs();
                    sigma = Unifier.getGroundUnifier(lhs, redex);
                    if (sigma != null) {
                        if (traceState != Evaluator.NON_TRACE && jTextArea != null) {
                            printReduction(goal, redex, equation, sigma, jTextArea, traceState);
                        }
                        goal = replaceTerm(goal, redex, rhs, sigma);
                        listGoals.push(goal);
                        Evaluator.equaEvaluated.set(iter_list_equa, true);
                        return true;
                    }
                }
            }
        } while (RedexIterator.hasNext() && redex != null);
        listGoals.push(goal);
        return false;
    }

    public static Term replaceTerm(Term goal, Term redex, Term rhs, Replacement sigma) {
        newTerm = sigma.apply(rhs);
        if (goal == redex) {
            redex.setParent(null);
            return newTerm;
        } else {
            parent = redex.getParent();
            numberRedex = parent.getListChildren().indexOf(redex);
            parent.getListChildren().set(numberRedex, newTerm);
            newTerm.setParent(parent);
            redex.setParent(null);
            return goal;
        }
    }

    public static LinkedList<Boolean> getEquaEvaluated() {
        return equaEvaluated;
    }

    public static void setEquaEvaluated(LinkedList<Boolean> equaEvaluated) {
        Evaluator.equaEvaluated = equaEvaluated;
    }

    public static int getStatus() {
        return status;
    }

    public static void setStatus(int status) {
        Evaluator.status = status;
    }

    public static void stopComputing() {
        Evaluator.numberSteps = Integer.MAX_VALUE;
    }

    public Program filtrateProgram(Program p, Example positive) throws ProgramException, GoalException {
        boolean[] array = new boolean[p.getListEquations().size()];
        for (Iterator<Equation> it = positive.getListEquations().iterator(); it.hasNext();) {
            equafiltre = it.next();
            termfiltre = evalue(p, equafiltre.getLhs(), Evaluator.getMaxNumberStep(), Evaluator.getMaxNumberRedex());
            if (Evaluator.getStatus() == Evaluator.GOAL_EVALUATED && termfiltre.equals(equafiltre.getRhs())) {
                for (i_filtre = 0; i_filtre < array.length; i_filtre++) {
                    array[i_filtre] = array[i_filtre] || Evaluator.getEquaEvaluated().get(i_filtre);
                }
            }
        }
        for (i_filtre = array.length - 1; i_filtre >= 0 && p.getListEquations().size() > 1; i_filtre--) {
            if (!array[i_filtre]) {
                p.removeEquation(i_filtre);
            }
        }
        return p;
    }

    private static void fillFalseList(Program program) {
        Evaluator.status = Evaluator.GOAL_NOT_EVALUATED;
        if (Evaluator.equaEvaluated.size() > program.getListEquations().size()) {
            for (i_filtre = Evaluator.equaEvaluated.size(); i_filtre > program.getListEquations().size(); i_filtre--) {
                Evaluator.equaEvaluated.removeLast();
            }
        } else if (Evaluator.equaEvaluated.size() < program.getListEquations().size()) {
            for (i_filtre = Evaluator.equaEvaluated.size(); i_filtre < program.getListEquations().size(); i_filtre++) {
                Evaluator.equaEvaluated.add(false);
            }
        }
        for (i_filtre = 0; i_filtre < Evaluator.equaEvaluated.size(); i_filtre++) {
            Evaluator.equaEvaluated.set(i_filtre, false);
        }
    }

    public static int getMaxNumberStep() {
        return maxNumberStep;
    }

    public static void setMaxNumberStep(int maxNumberStep) {
        Evaluator.maxNumberStep = maxNumberStep;
    }

    public static int getNumberSteps() {
        return numberSteps;
    }

    public static void setNumberSteps(int numberSteps) {
        Evaluator.numberSteps = numberSteps;
    }

    public static Term getGoal() {
        return goal;
    }

    public static void setGoal(Term goal) {
        Evaluator.goal = goal;
    }

    public static int getMaxNumberRedex() {
        return maxNumberRedex;
    }

    public static void setMaxNumberRedex(int maxNumberRedex) {
        Evaluator.maxNumberRedex = maxNumberRedex;
    }

    protected void setReport(JLabel statusBar, Term goal) {

    }

    protected void printTrace(Term goal, JTextArea jTextArea, int traceState) {

    }

    protected void printHeadLine(JTextArea jTextArea) {
        
    }

    protected void printReduction(Term goal, Term redex, Equation equation, Replacement sigma, JTextArea jTextArea, int traceState) {

    }

}
