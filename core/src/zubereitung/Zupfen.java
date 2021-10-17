package zubereitung;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import prolog.ParameterSet;
import prolog.Substitution;
import rezept.Rezept;
import status.StateDescription;
import zutat.Zutat;

public class Zupfen extends Zubereitung {
    @TaskAttribute public String zutat;
    @TaskAttribute public String name;

    public boolean preconditionsSatisfied(StateDescription current) {
        if (zutat == null) {
            return false;
        }
        else {
            Substitution s;
            ParameterSet preconditions = new ParameterSet();
            preconditions.add("getrocknet(" + name + ")");
            preconditions.add("gewaschen(" + name + ")");

            s = current.entails(preconditions);
            return (s != null);
        }
    }

    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();

        if (zutat != null) {
            p.add("gezupft(" + name + ")");
        }

        return new StateDescription(p);
    }

    public Status execute() {
        Rezept recipe = (Rezept)getObject();
        Zutat z;

        z = recipe.getIngredient(name);

        if (z != null) {
            System.out.println("Ich zupfe " + z);
            return Status.SUCCEEDED;
        }
        else {
            System.out.println(z + " kommt im Rezept nicht vor.");
            return Status.FAILED;
        }
    }

    protected Task copyTo(Task task) {
        return null;
    }
}
