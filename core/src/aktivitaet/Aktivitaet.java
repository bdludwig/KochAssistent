package aktivitaet;

import com.badlogic.gdx.ai.btree.Task;
import de.ur.ai.Renderer;
import main.KochAssistentObject;
import prolog.ParameterSet;
import prolog.Substitution;
import status.StateDescription;

import java.util.ArrayList;
import java.util.List;

abstract public class Aktivitaet {
    private Task.Status myState;
    protected ArrayList <KochAssistentObject> args;

    public Aktivitaet() {
        myState = Task.Status.FRESH;
        args = new ArrayList<KochAssistentObject>();
    }

    public void addArg(KochAssistentObject k) {
        args.add(k);
    }

    public Task.Status getStatus() {
        return myState;
    }

    public void setStatus(Task.Status s) {
        myState = s;
    }

    abstract public boolean isPossible(ParameterSet current_sit);

    abstract public Task.Status perform();

    public KochAssistentObject getArg(int pos) {
        return args.get(pos);
    }

    abstract public StateDescription effects(StateDescription current);
}
