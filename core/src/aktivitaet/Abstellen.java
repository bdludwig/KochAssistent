package aktivitaet;

import com.badlogic.gdx.ai.btree.Task;
import de.ur.ai.Renderer;
import moebel.Arbeitsplatte;
import moebel.Moebel;
import prolog.ParameterSet;
import prolog.Substitution;
import status.StateDescription;
import zutat.Zutat;

import java.util.List;

public class Abstellen extends Aktivitaet{
    public boolean isPossible(List<Renderer> objects, ParameterSet current_sit) {
        Renderer obj, source;
        StateDescription s = new StateDescription(current_sit);

        obj = objects.get(0);
        source = objects.get(1);

        System.out.println(s.toString());
        System.out.println("poss(abstellen(" + obj.getObject().id() + "," + source.getObject().id() + "),s0)");
        Substitution subs = s.entails(new ParameterSet("poss(abstellen(" + obj.getObject().id() + "," + source.getObject().id() + "),s0)"));

        return (subs != null);
    }

    @Override
    public Task.Status perform(List<Renderer> objects) {
        Renderer obj, source;

        obj = objects.get(0);
        source = objects.get(1);

        if (source.getObject() instanceof Arbeitsplatte) {
            ((Arbeitsplatte) source.getObject()).addContainedObject(obj.getObject());
            ((Zutat)obj.getObject()).setInHand(false);

            return Task.Status.SUCCEEDED;
        }
        else return Task.Status.FAILED;
    }
}
