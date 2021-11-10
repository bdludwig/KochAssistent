package prolog;

import org.jpl7.Term;

import java.util.ArrayList;
import java.util.Map;

public class Substitution {
    public Map<String, Term> mySubsts[];

    public Substitution() {
    }

    public Substitution(Map<String, Term>[] s) {
        mySubsts = s;
    }

    public String toString() {
        String res = "";

        for (int i = 0; i < mySubsts.length; i++) res += mySubsts[i].toString();
        return res;
    }

    private void substitute(ParameterSet ps, Map<String, Term> subst) {
        ArrayList<Term> fact_list = ps.getFacts();

        for (int i = 0; i < fact_list.size(); i++) {
            Term[] ta = fact_list.get(i).args();

            for (int j = 0; j < ta.length; j++) {
                if (subst.containsKey(ta[j].toString())) {
                    ta[j] = subst.get(ta[j].toString());
                }
            }
        }
    }
}