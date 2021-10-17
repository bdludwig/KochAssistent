package zubereitung;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import rezept.Rezept;
import prolog.ParameterSet;
import status.StateDescription;
import prolog.Substitution;
import zutat.Zutat;

public class CheckVerbraucht extends Zubereitung {
    @TaskAttribute public String zutat;

    public Status execute() {
        Rezept recipe = (Rezept)getObject();
        Zutat z;

        z = recipe.getIngredient(zutat);

        if (z != null) {
            StateDescription verbraucht = recipe.statusProlog();
            Substitution s;

            s = verbraucht.entails(new ParameterSet().add("verbraucht(" + z.id() + ")"));
            if (s != null) {
                // Zutat z ist wirklich verbraucht
                return Status.SUCCEEDED;
            } else return Status.FAILED;
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
