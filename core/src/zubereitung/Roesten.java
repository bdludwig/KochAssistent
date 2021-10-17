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

public class Roesten extends Zubereitung {
    // Diesen Roesten Task nutzen wir in unserer .tree Datei!
    @TaskAttribute public String zutat;
    @TaskAttribute public String name;
    @TaskAttribute public String geraet;
    @TaskAttribute public String geraet_name;
    @TaskAttribute public Integer time;

    public boolean preconditionsSatisfied(StateDescription current) {
        if (geraet == null) {
            return false;
        }
        else if (zutat == null) {
            return false;
        }
        else {
            Substitution s;
            Substitution sGeraet;

            s = current.entails(new ParameterSet("bereit(" + name + ")"));
            sGeraet = current.entails(new ParameterSet("bereit(" + geraet_name + ")"));
            return (s != null && sGeraet != null);
        }
    }

    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();

        if (zutat != null) {
            p.add(zutat.toLowerCase() + "(" + name + ")");
            p.add("geroestet(" + name + ")");
            p.add("warm(" + name + ")");
        }
        else if (geraet != null) {
            p.add(geraet.toLowerCase() + "(" + geraet_name + ")");
            p.add("abgeschaltet(" + geraet_name + ")");
        }
        else return current;

        p.add("in(" + name + "," + geraet_name + ")");

        return new StateDescription(p);
    }

    public Status execute() {
        Rezept recipe = (Rezept)getObject();
        Zutat z = recipe.getIngredient(zutat);

        if (z != null) {
            if (getStatus() == Status.FRESH) {
                z.addProperty("blockiert(" + z.id() + ")");
            }

            if (time > 0) {
                System.out.println("ich röste " + z + " in " + recipe.getTool(geraet));
                time--;
                return Status.RUNNING;
            }
            else {
                z.addProperty("verbraucht(" + z.id() + ")");
                z.addProperty("geroestet("+ z.id() + ")");
                z.removeProperty("blockiert(" + z.id() + ")");

                return Status.SUCCEEDED;
            }
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
