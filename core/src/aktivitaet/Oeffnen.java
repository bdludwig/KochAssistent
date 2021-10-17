package aktivitaet;

import de.ur.ai.Renderer;
import main.KochAssistentObject;
import moebel.Schrank;

import java.util.List;

public class Oeffnen extends Aktivitaet {
    public Oeffnen() {
        super();
    }

    @Override
    public boolean isPossible(List<Renderer> objects) {
        Renderer my_object = objects.get(0);

        if (my_object.getObject() instanceof Schrank) {
            if (!((Schrank)my_object.getObject()).isOpen()) return true;
            else return false;
        }
        else {
            System.out.println("not a Schrank");
            return false;
        }
    }

    @Override
    public void perform(List<Renderer> objects) {
        Renderer my_object = objects.get(0);

        if (my_object.getObject() instanceof Schrank) {
            ((Schrank)my_object.getObject()).open();
        }
        else System.out.println("not a Schrank");
    }
}
