package aktivitaet;

import de.ur.ai.Renderer;
import main.KochAssistentObject;
import prolog.ParameterSet;

import java.util.List;

abstract public class Aktivitaet {
    public Aktivitaet() {

    }

    abstract public boolean isPossible(List<Renderer> objects, ParameterSet current_sit);

    abstract public void perform(List<Renderer> objects);
}
