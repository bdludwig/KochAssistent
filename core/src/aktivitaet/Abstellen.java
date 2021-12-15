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
    public boolean isPossible(ParameterSet current_sit) {
        KochAssistentObject obj, source;
        StateDescription s = new StateDescription(current_sit);

        obj = args.get(0);
        source = args.get(1);

        Substitution subs = s.entails(new ParameterSet("poss(abstellen(" + obj.id() + "," + source.id() + "),s0)"));

        return (subs != null);
    }

    @Override
    public Substitution effects_satisfied(ParameterSet current_sit) {
        ParameterSet p = new ParameterSet();

        p.add("contains(" + args.get(1).id() + "," + args.get(0).id() + ",s0)");

        return new StateDescription(current_sit).entails(p);
    }

    @Override
    public Task.Status perform() {
        KochAssistentObject obj, source;

        obj = args.get(0);
        source = args.get(1);

        if (source instanceof Arbeitsplatte) {
            ((Arbeitsplatte) source).addContainedObject(obj);
            ((Zutat)obj).setInHand(false);

            return Task.Status.SUCCEEDED;
        }
        else return Task.Status.FAILED;
    }
}
