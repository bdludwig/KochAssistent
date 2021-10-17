package zubereitung;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import rezept.Rezept;
import prolog.ParameterSet;
import status.StateDescription;
import prolog.Substitution;
import zutat.Zutat;

public class CheckGeroestet extends Zubereitung {
    @TaskAttribute public String zutat;
    @TaskAttribute public String name;

    public Task.Status execute() {
        Rezept recipe = (Rezept)getObject();
        Zutat z;

        z = recipe.getIngredient(zutat);

        System.out.println("execute: "+ this.getClass().getSimpleName());

        if (z != null) {
            StateDescription geroestet = recipe.statusProlog();
            Substitution s;

            s = geroestet.entails(new ParameterSet("geroestet(" + z.id() + ")"));

            if (s != null) {
                System.out.println("Antwort von Prolog: " + s);
                return Task.Status.SUCCEEDED;
            } else return Task.Status.FAILED;
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
