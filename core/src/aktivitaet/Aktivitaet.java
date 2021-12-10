package aktivitaet;

import com.badlogic.gdx.ai.btree.Task;
import de.ur.ai.Renderer;
import prolog.ParameterSet;

import java.util.List;

abstract public class Aktivitaet {
    private Task.Status myState;

    public Aktivitaet() {
        myState = Task.Status.FRESH;
    }

    public Task.Status getStatus() {
        return myState;
    }

    public void setStatus(Task.Status s) {
        myState = s;
    }

    abstract public boolean isPossible(List<Renderer> objects, ParameterSet current_sit);

    abstract public Task.Status perform(List<Renderer> objects);
}
