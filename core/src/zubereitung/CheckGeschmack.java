package zubereitung;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import rezept.Rezept;
import zutat.Zutat;

import java.io.InputStreamReader;
import java.util.Scanner;

public class CheckGeschmack extends Zubereitung {
    @TaskAttribute public String masse;

    public Status execute() {
        Rezept recipe = (Rezept)getObject();
        Zutat z;

        z = recipe.getIngredient(masse);

        if (z == null) {
            System.out.println(z + " kommt im Rezept nicht vor.");
            return Status.FAILED;
        }
        else {
            Scanner s = new Scanner(System.in);

            System.out.print("Willst du noch abschmecken: (j/n): ");
            String input = s.nextLine();
            if (input.startsWith("j")) return Status.FAILED;
            else return Status.SUCCEEDED;
        }
    }

    protected Task copyTo(Task task) {
        return null;
    }
}
