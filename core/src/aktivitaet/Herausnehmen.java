package aktivitaet;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import de.ur.ai.Renderer;
import main.KochAssistentObject;
import moebel.Moebel;
import prolog.ParameterSet;
import prolog.Substitution;
import rezept.Rezept;
import status.StateDescription;
import zubereitung.Zubereitung;
import zutat.Zutat;

import java.util.List;

public class Herausnehmen extends Aktivitaet {
    public boolean isPossible(ParameterSet current_sit) {
        KochAssistentObject obj, source;
        StateDescription s = new StateDescription(current_sit);

        obj = args.get(0);
        source = args.get(1);

        System.out.println(s.toString());
        Substitution subs = s.entails(new ParameterSet("poss(herausnehmen(" + obj.id() + "," + source.id() + "),s0)"));

        return (subs != null);
    }

    @Override
    public Substitution effects_satisfied(ParameterSet current_sit) {
        ParameterSet p = new ParameterSet();

        p.add("in_hand(" + args.get(0).id() + ",s0)");

        return new StateDescription(current_sit).entails(p);
    }

    @Override
    public Task.Status perform() {
        KochAssistentObject obj, source;

        obj = args.get(0);
        source = args.get(1);

        if (source instanceof Moebel) {
            ((Moebel)source).removeContainedObject(obj);
            ((Zutat)obj).setInHand(true);

            return Task.Status.SUCCEEDED;
        }
        else return Task.Status.FAILED;
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