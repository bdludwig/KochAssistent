package zubereitung;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import prolog.ParameterSet;
import prolog.Substitution;
import rezept.Rezept;
import status.StateDescription;
import zutat.Zutat;

public class Servieren extends Zubereitung {
    @TaskAttribute public String typ;
    @TaskAttribute public String name;
    private int count = 0;

    public boolean preconditionsSatisfied(StateDescription current) {

        if (typ == null) {
            return false;
        }
        else if (name == null) {
            return false;
        }
        else {
            Substitution s;

            s = current.entails(new ParameterSet(typ.toLowerCase() + "(" + name + ")"));
            return (s != null);
        }
    }

    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();
        return new StateDescription(p.add("serviert(" + name + ")"));
    }

    public Status execute() {
        System.out.println("Ich serviere das fertige Gericht.");
        return Status.SUCCEEDED;
    }

    protected Task copyTo(Task task) {
        return null;
    }
}
