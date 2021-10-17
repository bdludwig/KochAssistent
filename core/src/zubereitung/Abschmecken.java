package zubereitung;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import rezept.Rezept;
import zutat.Zutat;

public class Abschmecken extends Zubereitung {
    @TaskAttribute public String masse;
    @TaskAttribute public String zutat;

    public Task.Status execute() {
        Rezept recipe = (Rezept) getObject();
        Zutat m, z;

        m = recipe.getIngredient(masse);
        z = recipe.getIngredient(zutat);

        if (m == null) {
            System.out.println(m + " kommt im Rezept nicht vor.");
            return Task.Status.FAILED;
        }
        else if (z == null) {
            System.out.println(z + " kommt im Rezept nicht vor.");
            return Task.Status.FAILED;
        } else {
            System.out.println("Ich schmecke " + m + " mit " + z + " ab.");
            z.verbrauchen();
            return Status.SUCCEEDED;
        }
    }

    protected Task copyTo(Task task) {
        return null;
    }
}