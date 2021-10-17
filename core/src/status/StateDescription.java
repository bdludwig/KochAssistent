package status;

import org.jpl7.Term;
import plan.AgendaItem;
import prolog.ParameterSet;
import prolog.Substitution;

import java.util.ArrayList;
import java.util.Iterator;

public class StateDescription {
    ParameterSet parameterSetState;

    public StateDescription(ParameterSet p) {
        parameterSetState = p;
        parameterSetState.assertRule("hatInhalt(X) :- in(_,X)");
    }

    public Substitution entails(ParameterSet p) {
        Substitution res;
        // assertState wird für ParameterSet state aufgerufen (wurde im Konstruktor mit p initialisiert) -> in unserem Fall das
        // ParameterSet aus der StatusProlog Methode in der RezeptKlasse
        assertState();
        //Wir rufen also jetzt die entails Methode aus der Klasse ParameterSet auf
        res = parameterSetState.entails(p);
        retractState();

        return res;
    }

    public Substitution applicable(ActionTerm a) {
        Substitution res;

        assertState();
        res = parameterSetState.entails(a.getPrecond());
        retractState();

        return res;
    }

    public void assertState() {
        parameterSetState.assertState();
    }

    public void retractState() {
        parameterSetState.retractState();
    }

    public double distanceToGoal(StateDescription goal) {
        double res;

        this.assertState();
        res = parameterSetState.distanceToGoal(goal.getFacts());
        this.retractState();

        return res;
    }

    public boolean isGoalState(StateDescription goal) {
        boolean res;

        this.assertState();
        res = (getFacts().entails(goal.getFacts()) != null);
        this.retractState();

        return res;
    }

    public ParameterSet getFacts() {
        return parameterSetState;
    }

    public String toString() {
        Iterator<Term> ps = parameterSetState.getFacts().iterator();
        Term t;
        String res = "";

        while (ps.hasNext()) {
            t = ps.next();
            res += ("  " + t + "\n");
        }

        return res;
    }
}
