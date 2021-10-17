package aktivitaet;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import prolog.ParameterSet;
import prolog.Substitution;
import rezept.Rezept;
import status.StateDescription;
import zubereitung.Zubereitung;
import zutat.Zutat;

public class Herausnehmen extends Zubereitung {
    // Diesen Roesten Task nutzen wir in unserer .tree Datei!
    @TaskAttribute public String zutat;
    @TaskAttribute public String name;
    @TaskAttribute public String geraet;
    @TaskAttribute public String geraet_name;

    public boolean preconditionsSatisfied(StateDescription current) {
        if (geraet == null) {
            // System.out.println("Gerät" + mixer.bezeichner() + " ist nicht verfügbar.");
            return false;
        }
        else if (zutat == null) {
            return false;
        }
        else {
            Substitution s;

            s = current.entails(new ParameterSet("in(" + name + "," + geraet_name + ")"));
            return (s != null);
        }
    }

    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();

        if (zutat != null) {
            p.add(geraet.toLowerCase() + "(" + geraet_name + ")");
            p.remove("in(" + name + "," + geraet_name + ")");
            p.add("leer(" + geraet_name + ")");
        }

        return new StateDescription(p);
    }

    public Status execute() {
        Rezept recipe = (Rezept) getObject();
        Zutat z = recipe.getIngredient(zutat);

        System.out.println("ich nehme " + z + " aus " + recipe.getTool(geraet));

        return Status.SUCCEEDED;
    }

    protected Task copyTo(Task task) {
        return null;
    }
}