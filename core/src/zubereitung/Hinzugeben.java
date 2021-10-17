package zubereitung;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import kuechengeraet.Kuechengeraet;
import rezept.Rezept;
import prolog.ParameterSet;
import status.StateDescription;
import prolog.Substitution;
import zutat.Zutat;

public class Hinzugeben extends Zubereitung {
    // Diesen Roesten Task nutzen wir in unserer .tree Datei!
    @TaskAttribute public String zutat;
    @TaskAttribute public String name;
    @TaskAttribute public String geraet;
    @TaskAttribute public String geraet_name;

    public boolean preconditionsSatisfied(StateDescription current) {
        if (zutat == null) {
            return false;
        }
        if (geraet == null){
            return false;
        }
        else {
            Substitution s;
            ParameterSet preconditions = new ParameterSet();
            preconditions.add("bereit(" + name + ")");
            preconditions.add("bereit(" + geraet_name + ")");

            s = current.entails(preconditions);
            return (s != null);
        }
    }

    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();

        if (zutat != null) {
            p.add("in(" + name + "," + geraet_name + ")");
            p.remove("leer(" + geraet_name + ")");
        }

        return new StateDescription(p);
    }

    public Status execute() {
        Rezept recipe = (Rezept) getObject();
        Zutat z = recipe.getIngredient(zutat);
        Kuechengeraet kuechengeraet = recipe.getTool(geraet);

        if (z != null) {
            StateDescription blockiert = recipe.statusProlog();
            Substitution s = blockiert.entails(new ParameterSet("blockiert(" + z.id() + ")"));

            if (s == null) {
                // diese Zutat ist nicht blockiert, die Aktion kann also ausgeführt werden.
                System.out.println("ich gebe " + z + " in " + recipe.getTool(geraet));

                z.verbrauchen();
                z.addProperty("in(" + z.id() + "," + kuechengeraet.bezeichner() + ")");

                return Status.SUCCEEDED;
            }
            else {
                System.out.println("Warten auf " + z.id());
                return Status.RUNNING;
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