package graph;

import status.StateDescription;

import java.util.LinkedList;

public class AStarGraph extends SearchableGraph {
	 /**
     * implementation of the AStar algorithm for computing routes in the
     * bus network
     */
    
	protected ALGOHeap agenda;

	private ALGONode currentNode;
	private ALGONode targetNode;
	private double [] bestCostSoFarInNode;
	public static double gw = 1, 
						 hw = 1,
						 c = 0;
	
	public AStarGraph() {
		super();
		
    	agenda = new ALGOHeap(2000);
	}

	public void resetSearch() {
		agenda = new ALGOHeap(2000);
		currentSolution = null;

		for (int i = 0; i < nodes.size(); i++) {
			ALGONode p = nodes.get(i);

			p.removeFromOpenList();
			p.removeFromClosedList();
			bestCostSoFarInNode[i] = Double.MAX_VALUE;
		}

		resetEdges();
	}

	public boolean moreTestItems() { return !agenda.isEmpty();}

	public void initSearch(StateDescription startNode, StateDescription targetNode) {
		/*
		agenda = new ALGOHeap(nodes.size());
		bestCostSoFarInNode = new double[nodes.size()];
		ALGOEdge [] succ = this.getEdgesForNode(startNode.getNodeID());
		
		for (int i = 0; i < nodes.size(); i++) {
    		ALGONode p = nodes.get(i);
    		
    		p.removeFromOpenList();
    		p.removeFromClosedList();
    		bestCostSoFarInNode[i] = Double.MAX_VALUE;
    	}
		
		for (int i = 0; i < succ.length; i++) {
			bestCostSoFarInNode[succ[i].getSink()] =
				gw * succ[i].getCosts() +
				hw * nodes.get(succ[i].getSink()).costs(targetNode);

			agenda.insert(new AStarHeapData(new ALGOPath(succ[i]),
					                        bestCostSoFarInNode[succ[i].getSink()],
					                        succ[i].getCosts()));
			nodes.get(succ[i].getSink()).putOnOpenList(agenda);
		}
		
		for (int i = 0; i < nodes.size(); i++) {
    		LinkedList<ALGOEdge> l = edges.get(new Integer(i));
    		if (l != null) {
    			for(int j = 0; j < l.size(); j++) {
    				ALGOEdge e = l.get(j);
    				e.setOptimal(false);
    			}
    		}
		}
		
		
		currentNode = startNode;
		this.targetNode = targetNode;
		 */
	}
	
	public boolean solutionFound() {
		return (currentNode.getNodeID() == targetNode.getNodeID());
	}

	public boolean noSolution() {
		return agenda.isEmpty();
	}
	
	public void nextStep() {	
	 	AStarHeapData hd;
    	ALGONode min;
    	double currentG;
    	double currentF;
    	
    	hd = (AStarHeapData) agenda.retrieve();
    	agenda.deleteHighest();
    	
    	min = getNode(hd.getData().lastElement().getSink());
    		
    	if (min.getNodeID() == targetNode.getNodeID()) {
    		currentNode = min;
    		currentSolution = hd.getData();
    		System.out.println("Ziel gefunden");
    		return;
    	}

    	min.putOnClosedList();
    	ALGOEdge [] minEdges = this.getEdgesForNode(min.getNodeID());   		
    	for (int i = 0; i < minEdges.length; i++) {
    		ALGOEdge e = minEdges[i];
    		ALGONode succ = nodes.get(e.getSink());

    		currentG = hd.getActualCosts() + e.getCosts();
    		currentF = gw * currentG + 
    				   hw * succ.costs(targetNode) +
    				   c;

    		if (!succ.isOpen() && !succ.isClosed()) {
    			// noch nicht besucht und auch bisher noch kein Kandidat dafür
    			// succ wird Kandidat für weitere Suche
    			
    			succ.putOnOpenList(agenda);
    			ALGOPath p1 = hd.getData().clone();
    			p1.add(minEdges[i]);
    			agenda.insert(new AStarHeapData(p1, currentF, currentG));
    			
    			bestCostSoFarInNode[succ.getNodeID()] = currentF;
    		}
    		else if (currentF < bestCostSoFarInNode[succ.getNodeID()]) {
    			// succ schon besucht oder bereits Kandidat und besserer Weg zu succ als bisher
    			// dann speichere diesen Pfad

    			bestCostSoFarInNode[succ.getNodeID()] = currentF;
    			e.highlight();

    			if (succ.isClosed()) {
    				System.out.println("remove from closed: " + succ.toString());
    				// succ sogar schon besucht, nicht nur Kandidat
    				// dann prüfe noch mal alles ab succ (backtracking)
    				
    				ALGOPath p1 = hd.getData().clone();
        			p1.add(minEdges[i]);
        			agenda.insert(new AStarHeapData(p1, currentF, currentG));
    				succ.removeFromClosedList();
    				succ.putOnOpenList(agenda);
    			}
    		}
    	}
    }
	
	public Instructions computeBestPath() {
		return null;
	}
}
