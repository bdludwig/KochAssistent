package status;

import prolog.ParameterSet;
import prolog.Substitution;

public class ActionTerm {
    private String name;
    private ParameterSet precond;
    private ParameterSet add_effects;
    private ParameterSet del_effects;

    public ActionTerm(String n, ParameterSet p, ParameterSet a, ParameterSet d) {
        name = n;
        precond = p;
        add_effects = a;
        del_effects = d;
    }

    public String getName() {
        return name;
    }

    public void apply(Substitution subs) {}

    public ParameterSet getPrecond() {
        return precond;
    }

    public ParameterSet deleteEffects() { return del_effects; }

    public ParameterSet addEffects() { return add_effects; }

    public String toString() {
        return name + "(" + precond.toString() + "," + add_effects.toString() + "," + del_effects.toString() + ")";
    }

    public double cost() {
        return 1.0;
    }
}
