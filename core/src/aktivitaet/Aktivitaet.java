package aktivitaet;

import de.ur.ai.Renderer;
import main.KochAssistentObject;

import java.util.List;

abstract public class Aktivitaet {
    public Aktivitaet() {

    }

    abstract public boolean isPossible(List<Renderer> objects);

    abstract public void perform(List<Renderer> objects);
}
