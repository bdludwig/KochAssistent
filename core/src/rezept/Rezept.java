package rezept;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import kuechengeraet.Kuechengeraet;
import main.Main;
import prolog.ParameterSet;
import status.StateDescription;
import zutat.Zutat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Rezept {
    private HashMap<String, Kuechengeraet> geraete;
    private HashMap<String, Zutat> zutaten;

    private String name;
    private String tree_file_name;
    private BehaviorTree<Rezept> instructions;

    public Rezept(String n, String f) {
        tree_file_name = f;
        name = n;
    }

    public void init() throws FileNotFoundException {
        Reader reader = new FileReader(tree_file_name);
        BehaviorTreeParser<Rezept> parser = new BehaviorTreeParser<Rezept>(BehaviorTreeParser.DEBUG_HIGH);
        instructions = parser.parse(reader, this);

        zutaten = new HashMap<String, Zutat>();
        geraete = new HashMap<String, Kuechengeraet>();
    }

    public BehaviorTree<Rezept> getTree() {
        return instructions;
    }

    public void addIngredient(Zutat zutat) {
        zutaten.put(zutat.id(), zutat);
    }

    public Zutat getIngredient(String ingredient) {
        return zutaten.get(ingredient);
    }

    public Kuechengeraet getTool(String tool) {
        return geraete.get(tool);
    }

    public void addTool(Kuechengeraet kuechengeraet) {
        geraete.put(kuechengeraet.bezeichner(), kuechengeraet);
    }

    public StateDescription statusProlog() {
        // Zunächst wird eine neue ParameterSet Instanz angelegt
        ParameterSet allObjects = new ParameterSet();

        // Unsere HashMap Zutaten holen wir uns als Entryset
        Set<Map.Entry<String, Zutat>> ingredients = zutaten.entrySet();
        //für jede Zutat rufen wir die Methode factsToProlog der Klasse Zutat auf
        for (Map.Entry<String, Zutat> ingredient : ingredients) {
            allObjects = ingredient.getValue().factsToProlog(allObjects);
        }

        // Analog zu Zutaten
        Set<Map.Entry<String, Kuechengeraet>> tools = geraete.entrySet();
        for (Map.Entry<String, Kuechengeraet> tool : tools) {
            allObjects = tool.getValue().factsToProlog(allObjects);
        }

        // wir geben die StateDescription mit unserem ParameterSet zurück
        return new StateDescription(allObjects);
    }

    public String name() {
        return name;
    }
}

