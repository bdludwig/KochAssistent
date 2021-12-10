package aktivitaet;

import com.badlogic.gdx.ai.btree.Task;
import de.ur.ai.Renderer;
import moebel.Schrank;
import prolog.ParameterSet;

import java.util.List;

public class Schliessen extends Aktivitaet {
    public Schliessen() {
        super();
    }

    @Override
    public boolean isPossible(List<Renderer> objects, ParameterSet current_sit) {
        Renderer my_object = objects.get(0);

        if (my_object.getObject() instanceof Schrank) {
            if (((Schrank)my_object.getObject()).isOpen()) return true;
            else return false;
        }
        else {
            System.out.println("not a Schrank");
            return false;
        }
    }

    @Override
    public Task.Status perform(List<Renderer> objects) {
        Renderer my_object = objects.get(0);

        if (my_object.getObject() instanceof Schrank) {
            ((Schrank)my_object.getObject()).close();

            return Task.Status.SUCCEEDED;
        }
        else {
            System.out.println("not a Schrank");
            return Task.Status.FAILED;
        }
    }
}
