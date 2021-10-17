package graph;


import plan.AgendaItem;
import status.StateDescription;

public abstract class SearchableGraph extends ALGOGraph {
	protected ALGONode root_node;
	protected AgendaItem current_agenda_item;
	protected StateDescription targetState;
	
	public abstract void nextStep();

	public abstract boolean noSolution();

	public abstract boolean solutionFound();
	
	public void init() {};
	
	public abstract void initSearch(StateDescription start, StateDescription end);
	
	public abstract Instructions computeBestPath();
	
	public void printQueue() {
	}

	abstract public boolean moreTestItems();

	public void setRootNode(ALGONode n) {
		root_node = n;
	}
}
