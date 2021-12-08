package aktivitaet;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import de.ur.ai.Renderer;
import moebel.Moebel;
import prolog.ParameterSet;
import prolog.Substitution;
import rezept.Rezept;
import status.StateDescription;
import zubereitung.Zubereitung;
import zutat.Zutat;

import java.util.List;

public class Herausnehmen extends Aktivitaet {
    public boolean isPossible(List<Renderer> objects, ParameterSet current_sit) {
        Renderer obj, source;
        StateDescription s = new StateDescription(current_sit);

        obj = objects.get(0);
        source = objects.get(1);

        System.out.println(s.toString());
        System.out.println("poss(entnehmen(" + obj.getObject().id() + "," + source.getObject().id() + "),s0)");
        Substitution subs = s.entails(new ParameterSet("poss(entnehmen(" + obj.getObject().id() + "," + source.getObject().id() + "),s0)"));

        return (subs != null);
    }

    @Override
    public void perform(List<Renderer> objects) {
        Renderer obj, source;

        obj = objects.get(0);
        source = objects.get(1);

        if (source.getObject() instanceof Moebel) {
            ((Moebel)source.getObject()).removeContainedObject(obj.getObject());
            ((Zutat)obj.getObject()).setInHand(true);
        }
    }

    /*
    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();

        if (zutat != null) {
            p.add(geraet.toLowerCase() + "(" + geraet_name + ")");
            p.remove("in(" + name + "," + geraet_name + ")");
            p.add("leer(" + geraet_name + ")");
        }

        return new StateDescription(p);
    }
*/
    /*
    public Status execute() {
        Rezept recipe = (Rezept) getObject();
        Zutat z = recipe.getIngredient(zutat);

        System.out.println("ich nehme " + z + " aus " + recipe.getTool(geraet));

        return Status.SUCCEEDED;
    }

    protected Task copyTo(Task task) {
        return null;
    }
     */
}