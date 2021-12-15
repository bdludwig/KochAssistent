package aktivitaet;

import com.badlogic.gdx.ai.btree.Task;
import de.ur.ai.Renderer;
import main.KochAssistentObject;
import moebel.Arbeitsplatte;
import moebel.Moebel;
import prolog.ParameterSet;
import prolog.Substitution;
import status.StateDescription;
import zutat.Zutat;

import java.util.List;

public class Abstellen extends Aktivitaet {
    public Abstellen() {
        super();
    }

    public boolean isPossible(ParameterSet current_sit) {
        KochAssistentObject obj, source;
        StateDescription s = new StateDescription(current_sit);

        obj = args.get(0);
        source = args.get(1);

        Substitution subs = s.entails(new ParameterSet("poss(abstellen(" + obj.id() + "," + source.id() + "),s0)"));

        return (subs != null);
    }

    @Override
    public Task.Status perform() {
        KochAssistentObject obj, source;

        obj = args.get(0);
        source = args.get(1);

        if (source instanceof Arbeitsplatte) {
            ((Arbeitsplatte) source).addContainedObject(obj);
            ((Zutat)obj).setInHand(false);
            ((Zutat) obj).setContainer(source);

            return Task.Status.SUCCEEDED;
        }
        else return Task.Status.FAILED;
    }

    @Override
    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();
        KochAssistentObject obj = args.get(0);
        KochAssistentObject source = args.get(1);

        System.out.println("EFFECT now true: " + "contains(" + source.id() + "," + obj.id() + ",s0)");
        System.out.println("EFFECT now false: " + "in_hand(" + obj.id() + ",s0)");

        p.add("contains(" + source.id() + "," + obj.id() + ",s0)");
        p.remove("in_hand(" + obj.id() + ",s0)");

        return new StateDescription(p);
    }

    @Override
    public String toString() {
        return "abstellen(" + args.get(0).id() + "," + args.get(1).id() + ")";
    }
}
