package graph;

import com.badlogic.gdx.ai.btree.BranchTask;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.branch.Parallel;
import com.badlogic.gdx.ai.btree.branch.RandomSequence;
import com.badlogic.gdx.ai.btree.branch.Selector;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.ai.btree.decorator.AlwaysSucceed;
import prolog.ParameterSet;
import rezept.Rezept;
import status.StateDescription;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class ALGOGraphFactory {
    private SearchableGraph myGraph;
    private HashMap<Task,ALGONode> taskNodeMap;
    private HashMap<ALGONode, ALGONode> edges;
    private Task root;
    private int node_id;

    public static PrintStream log_stream;

    static {
        try {
            log_stream = new PrintStream(new FileOutputStream("search_graph.dot"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ALGOGraphFactory(Rezept r) {
        root = r.getTree();
        myGraph = new DijkstraGraph();
        taskNodeMap = new HashMap<Task, ALGONode>();
        edges = new HashMap<ALGONode, ALGONode>();

        node_id = 0;
    }

    public Task getRoot() {
        return root;
    }

    public void addTaskToGraph(Task t) {
        if (!taskNodeMap.containsKey(t)) {
            ALGONode n;

            if (t instanceof LeafTask) n = new BTLeafTaskNode(node_id, t);
            else if (t instanceof Parallel) n = new BTParallelNode(node_id, t);
            else if (t instanceof RandomSequence) n = new BTRandomSequenceNode(node_id, t);
            else if (t instanceof Sequence) n = new BTSequenceNode(node_id, t);
            else if (t instanceof Selector) n = new BTSelectorNode(node_id, t);
            else if (t instanceof AlwaysSucceed) n = new BTAlwaysSucceedNode(node_id, t);
            else {
                System.out.println("unknown type: " + t.getClass().getSimpleName());
                n = new BTTreeNode(node_id, t);
            }

            if (taskNodeMap.size() == 0) root = t;
            taskNodeMap.put(t, n);
            myGraph.addNode(n);
            node_id ++;
        }
    }

    public void addEdge(Task t, Task parent) {
        ALGONode source = taskNodeMap.get(parent);
        ALGONode sink = taskNodeMap.get(t);

        if ((source != null) && (sink != null)) {
            if (!(edges.get(source) == sink)) {
                edges.put(source, sink);
                myGraph.addEdge(new ALGOCookingEdge(source.getNodeID(), sink.getNodeID()));
            }
        }
    }

    private void generate(Task t) {
        for (int i = 0; i < t.getChildCount(); i++) {
            Task child = t.getChild(i);

            addTaskToGraph(child);
            addEdge(child, t);
            if (child instanceof BranchTask) addEdge(getRoot(), child);
            generate(child);
        }
    }
    public void generateGraph() {
        addTaskToGraph(getRoot());
        ((SearchableGraph)myGraph).setRootNode(taskNodeMap.get(getRoot()));
        generate(getRoot());
    }

    public void printGraph() {
        myGraph.printGraph();
    }

    public void plan(ParameterSet p) {
        myGraph.initSearch(new StateDescription(new ParameterSet("kitchentest(k)")),
                new StateDescription(p));

        do {
            myGraph.nextStep();
        }
        while (!myGraph.noSolution());

        System.out.println("solution: " + myGraph.solutionFound());
        System.out.println("more test items: " + myGraph.moreTestItems());
    }

    public void bestPath() {
        myGraph.computeBestPath();
    }
}
