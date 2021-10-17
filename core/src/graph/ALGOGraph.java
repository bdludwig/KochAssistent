/**
 * This class implements graphs using a <code>Hashtable</code> for nodes and
 * another <code>Hashtable</code> for edges.
 */

package graph;

/**
 * implementation of the ALGOGraph data type, an abstract data type for the
 * representation of graphs.
 *
 * The implementation uses a Hashtable (to store nodes) and LinkedList for
 * adjacency lists (to store edges).
 */

import status.StateDescription;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;
import java.util.Map.Entry;

public abstract class ALGOGraph {
	protected int numNodes;
	protected int acweight;
	protected int hfweight;
	protected Hashtable<Integer,ALGONode> nodes;
	protected Hashtable<Integer,LinkedList<ALGOEdge>> edges;
	protected ALGOPath currentSolution;
	protected ALGOEdge [] predecessor;
	
    /**
     * default constructor
     */

    public ALGOGraph() {
    	numNodes = 0;
    	nodes = new Hashtable<Integer,ALGONode>();
    	edges = new Hashtable<Integer,LinkedList<ALGOEdge>>();
    	
    	acweight = hfweight = 1;
    }

    public void addNode(ALGONode n) {
    	if(nodes.get(n.getNodeID()) == null) {
    		nodes.put(n.getNodeID(), n);
    	}
    	else System.out.println("node id already in use:" + n.getNodeID());
    }
    
    public void addEdge(ALGOEdge e) {
    	LinkedList<ALGOEdge> l;
    	if ((l = edges.get(e.getSource())) == null) {
    		l = new LinkedList<ALGOEdge>();
    		l.add(e);
    		edges.put(new Integer(e.getSource()), l);
    	}
    	else {
    		// does not test whether there is an edge to the same sink as
    		// e.getSink()
    		
    		l.add(e);
    	}
    }
    
    public void resetNodes() {
    	Enumeration<ALGONode> elements = nodes.elements();
    	
    	while (elements.hasMoreElements()) {
    		ALGONode p = elements.nextElement();
    		
    		p.removeFromClosedList();
    		p.removeFromOpenList();
    	}
    }
    
    public void resetEdges() {
    	Enumeration<LinkedList<ALGOEdge>> elements = edges.elements();
    	
    	while (elements.hasMoreElements()) {
    		LinkedList<ALGOEdge> l = elements.nextElement();
    		ListIterator<ALGOEdge> it = l.listIterator();

    		while (it.hasNext()) {
    			ALGOEdge e = it.next();

    			e.unhighlight();
    			e.unselect();
    			e.setOptimal(false);
    		}
    	}
    }

    public abstract void resetSearch();

    public ALGONode findNode(String s) {
    	Enumeration<ALGONode> elements = nodes.elements();
    	ALGONode p = null;
    	
    	while (elements.hasMoreElements()) {
    		p = elements.nextElement();
    		if (p.nodeName().equals(s)) break;
    	}
    	
    	if (elements.hasMoreElements()) return p;
    	else return null;
    }
    
    public Enumeration<ALGONode> getAllNodes() {
    	return nodes.elements();
    }
    
    public ALGONode getNode(int id) {
    	return nodes.get(new Integer(id));
    }
        	
    public int getACWeight() {
    	return acweight;	
    }
    
    public int getHFWeight() {
    	return hfweight;
    }
    
    public void setWeights(int ac, int hf) {
    	acweight = ac;
    	hfweight = hf;
    }
    
    public int numNodes() {
    	return nodes.size();
    }
    
    public ALGOEdge [] getEdgesForNode(Integer id) {
    	LinkedList<ALGOEdge> l = edges.get(id);
    	if (l != null) {
    		ALGOEdge [] ea = new ALGOEdge[l.size()];
    		return l.toArray(ea);
    	}
    	else return null;
    }

    abstract public boolean solutionFound();
	
	abstract public boolean noSolution();
	
	abstract public void nextStep();

    public void printGraph() {
    	Set<Entry<Integer, LinkedList<ALGOEdge>>> entries = edges.entrySet();
    	Set<Entry<Integer,ALGONode>> nodeSet = nodes.entrySet();

    	/*
    	try {
    	 */
    		//PrintStream ps = new PrintStream(new FileOutputStream(new File("./graph.txt")));
			PrintStream ps = System.out;

			ps.println("printing graph ...");

			for(Entry<Integer,ALGONode> n : nodeSet) {
				ALGONode node = n.getValue();
				ps.println(" node: " + node.toString());
			}

    		for (Entry<Integer, LinkedList<ALGOEdge>> entry : entries) {
    			LinkedList<ALGOEdge> edgelist = entry.getValue();

    			for (ALGOEdge algoEdge : edgelist) {
    				ALGONode source = nodes.get(algoEdge.getSource());
    				ALGONode sink = nodes.get(algoEdge.getSink());

    				ps.println(source.toString() + " -> " + sink.toString() + " [label=\"" + algoEdge.getCosts() + "\"];");
    			}
    		}
    		/*
    		    } catch (FileNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		 */
    }
}