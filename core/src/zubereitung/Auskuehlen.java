package zubereitung;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import prolog.ParameterSet;
import prolog.Substitution;
import quantitaet.Quantitaet;
import rezept.Rezept;
import status.StateDescription;
import zutat.Zutat;

public class Auskuehlen extends Zubereitung {
    @TaskAttribute public String zutat;
    @TaskAttribute public String name;

    public boolean preconditionsSatisfied(StateDescription current) {
        if (zutat == null) {
            return false;
        }
        else {
            Substitution s;

            s = current.entails(new ParameterSet("warm(" + name + ")"));
            return (s != null);
        }
    }

    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();

        if (zutat != null) {
            p.add(zutat.toLowerCase() + "(" + name + ")");
            p.remove("warm(" + name + ")");
            p.add("ausgekuehlt(" + name + ")");
        }

        return new StateDescription(p);
    }

    public Task.Status execute() {
        Rezept recipe = (Rezept)getObject();
        Zutat z;

        z = recipe.getIngredient(zutat);

        if (z != null) {
            System.out.println("Ich lasse " + z + " auskühlen");
            return Task.Status.SUCCEEDED;
        }
        else {
            System.out.println(z + " kommt im Rezept nicht vor.");
            return Task.Status.FAILED;
        }
    }

    protected Task copyTo(Task task) {
        return null;
    }
}
