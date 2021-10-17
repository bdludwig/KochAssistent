package main;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Task;
import graph.ALGOGraphFactory;
import rezept.Rezept;
import prolog.ParameterSet;
import status.StateDescription;

import java.io.FileNotFoundException;

public class Main {
    public static ALGOGraphFactory graphFactory;

    public static ALGOGraphFactory getGraphFactory() {
        return graphFactory;
    }

    public static void main(String [] args) throws FileNotFoundException {
        Rezept pesto;
        // Wir geben unserem Rezept einen Namen und definieren den Pfad zur Rezeptdatei
        pesto = new Rezept("Pesto", "data/PestoUebung.tree");

        BehaviorTree<Rezept> recipeSteps;

        // mit init lesen wir die .tree Datei ein und erstellen Hashmaps für Zutaten und Küchengeräte
        pesto.init();
        // wir holen uns den Behavior Tree aus der .tree Datei
        recipeSteps = pesto.getTree();

        graphFactory = new ALGOGraphFactory(pesto);
        graphFactory.generateGraph();

        ALGOGraphFactory.log_stream.println("digraph G {");

        graphFactory.plan(new ParameterSet("serviert(z1)"));
        graphFactory.bestPath();

        ALGOGraphFactory.log_stream.println("}");
        ALGOGraphFactory.log_stream.close();
    }
}