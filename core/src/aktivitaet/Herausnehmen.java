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
    public Herausnehmen() {
        super();
    }

    public boolean isPossible(ParameterSet current_sit) {
        KochAssistentObject obj, source;
        StateDescription s = new StateDescription(current_sit);

        obj = args.get(0);
        source = args.get(1);

        Substitution subs = s.entails(new ParameterSet("poss(herausnehmen(" + obj.id() + "," + source.id() + "),s0)"));

        return (subs != null);
    }

    @Override
    public Task.Status perform() {
        KochAssistentObject obj, source;

        obj = args.get(0);
        source = args.get(1);

        if (source instanceof Moebel) {
            ((Moebel)source).removeContainedObject(obj);
            ((Zutat)obj).setInHand(true);
            ((Zutat) obj).setContainer(null);

            return Task.Status.SUCCEEDED;
        }
        else return Task.Status.FAILED;
    }

    @Override
    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();
        KochAssistentObject obj = args.get(0);
        KochAssistentObject source = args.get(1);

        System.out.println("EFFECT now false: contains(" + source.id() + "," + obj.id() + ",s0)");
        System.out.println("EFFECT now true: in_hand(" + obj.id() + ",s0)");

        p.add("in_hand(" + obj.id() + ",s0)");
        p.remove("contains(" + source.id() + "," + obj.id() + ",s0)");

        return new StateDescription(p);
    }

    @Override
    public String toString() {
        return "herausnehmen(" + args.get(0).id() + "," + args.get(1).id() + ")";
    }
}