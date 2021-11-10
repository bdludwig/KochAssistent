package prolog;

import java.util.ArrayList;
import org.jpl7.*;

public class ParameterSet {
    private ArrayList<Term> facts;

    public ParameterSet() {
        facts = new ArrayList<Term>();
    }

    public ParameterSet(String s) {
        facts = new ArrayList<Term>();
        facts.add(Util.textToTerm(s));
    }

    public ParameterSet add(String s) {
        facts.add(Util.textToTerm(s));
        return this;
    }

    public ParameterSet add(Term t) {
        facts.add(t);
        return this;
    }

    public ParameterSet add(ParameterSet p) {
        ArrayList<Term> fact_list = p.getFacts();

        for (int i = 0; i < fact_list.size(); i++) {
            if (!facts.contains(fact_list.get(i))) facts.add(fact_list.get(i));
        }

        return this;
    }

    public ParameterSet remove(String s) {
        facts.remove(Util.textToTerm(s));
        return this;
    }

    public ParameterSet remove(ParameterSet p) {
        ArrayList<Term> fact_list = p.getFacts();

        for (int i = 0; i < fact_list.size(); i++) {
            if (facts.contains(fact_list.get(i))) facts.remove(fact_list.get(i));
        }

        return this;
    }

    public ArrayList<Term> getFacts() {
        return facts;
    }

    public void clear() {
        facts.clear();
    }

    public void assertState() {
        Query q;

        for (int i = 0; i < facts.size(); i++) {
            Term t = facts.get(i);

            q = new Query(new Compound("assertz", new Term [] {t}));
            q.hasSolution();
            q.close();
        }
    }

    public void assertRule(String formula) {
        Query q = new Query(new Compound("assertz", new Term[] {Util.textToTerm(formula)}));
        q.hasSolution();
        q.close();
    }

    public void retractState() {
        Query q;

        for (int i = 0; i < facts.size(); i++) {
            Term t = facts.get(i);

            q = new Query(new Compound("retractall", new Term [] {t}));
            q.hasSolution();
            q.close();
        }
    }

    public Substitution entails(ParameterSet p) {
        Query q;
        Substitution res;
        String query_string = "";

        Term query_term = p.getFacts().get(0);

        for (int i = 1; i < p.getFacts().size(); i++) {
            Term t = p.getFacts().get(i);

            for (int k = 0; k < t.args().length; k++)
                query_term = new Compound(",", new Term[] {query_term, t});
        }

        try {
            q = new Query(query_term);

            if (q.hasSolution()) {
                res = new Substitution(q.allSolutions());
                q.close();
                return res;
            }
            else {
                q.close();
                return null;
            }
        }
        catch (PrologException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ParameterSet) {
            return (this.entails((ParameterSet)o) != null) &&
                    (((ParameterSet)o).entails(this) != null);
        }
        else return false;
    }

    public boolean isGoalState(ParameterSet goal) {
        return (goal.entails(this ) != null);
    }

    public ParameterSet clone()  {
        ArrayList<Term> all_keys = this.getFacts();
        ParameterSet ps = new ParameterSet();

        for (int i = 0; i < all_keys.size(); i++) {
            Term key = all_keys.get(i);
            ps.add(copyTerm(key));
        }

        return ps;
    }

    public double distanceToGoal(ParameterSet goal) {
        double distance = 0;
        ArrayList<Term> all_keys = goal.getFacts();

        for (int i = 0; i < all_keys.size(); i++) {
            Term key = all_keys.get(i);
            Query q = new Query(key);
            if (!q.hasSolution()) distance ++;
            q.close();
        }

        return distance;
    }

    private Term copyTerm(Term t) {
        if (t.isCompound()) {
            Term [] args = new Term[t.args().length];

            for (int i = 0; i < t.args().length; i++) {
                args[i] = copyTerm(t.args()[i]);
            }
            return new Compound(t.name(), args);
        }
        else if (t.isAtom()) {
            return new Atom(((Atom)t).name());
        }
        else if (t.isVariable()) {
            return new Variable(((Variable)t).name());
        }
        else {
            System.out.println("copy term: " + t.getClass());
            return null;
        }
    }

    public String toString() {
        String res = facts.get(0).toString();

        for (int i = 1; i < facts.size(); i++) {
            res += ", " + facts.get(i).toString();
        }

        return res;
    }
}
