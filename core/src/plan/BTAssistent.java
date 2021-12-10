package plan;

import aktivitaet.Aktivitaet;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import prolog.ParameterSet;
import prolog.Substitution;
import raum.Kueche;
import rezept.Rezept;
import status.StateDescription;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class BTAssistent {
    private BehaviorTree<BTAssistent> myBT;
    private Kueche myK;
    private Aktivitaet last_action;
    private String my_message;

    public BTAssistent(Kueche k) {
        myK = k;
        my_message = null;
    }

    public void init() {
        try {
            Reader reader = new FileReader("./zutat_bereitstellen.tree");
            BehaviorTreeParser<BTAssistent> parser = new BehaviorTreeParser<BTAssistent>(BehaviorTreeParser.DEBUG_NONE);
            StateDescription s = new StateDescription(myK.factsToProlog());
            Substitution subs;

            myBT = parser.parse(reader, this);
            my_message = null;
            subs = s.entails(new ParameterSet("do(oeffnen(o_9):herausnehmen(o_10,o_9):(abstellen(o_10,o_24):schliessen(o_9)#schliessen(o_9):abstellen(o_10,o_24)),s0,S)"));

            if (subs != null) step();
            else System.out.println("cannot solve this task.");
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void step() {
        System.out.println("next step in BT with state: " + myBT.getStatus());
        myBT.step();
        if (myBT.getStatus() == Task.Status.SUCCEEDED) this.setCurrentMessage("Ok - fertig!");
        this.setLastAction(null);
    }


    public void setCurrentMessage(String s) {
        my_message = s;
    }

    public String getCurrentMessage() {
        return my_message;
    }

    public void setLastAction(Aktivitaet a) {
        last_action = a;
    }

    public Aktivitaet getLastAction() {
        return last_action;
    }
}
