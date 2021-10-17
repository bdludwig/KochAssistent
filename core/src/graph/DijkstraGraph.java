package graph;

import com.badlogic.gdx.ai.btree.Task;
import plan.AgendaItem;
import status.StateDescription;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;

public class DijkstraGraph extends SearchableGraph implements Comparator<AgendaItem> {
	 /**
     * implementation of the breadthfirst algorithm for computing routes in the
     * bus network
     */
	private Hashtable<AgendaItem,Double> delta;
	private Hashtable<AgendaItem,AgendaItem> pred;
	private Hashtable<Integer,AgendaItem> task_status;

	protected PriorityQueue<AgendaItem> agenda;
	private StateDescription current_state;

	public DijkstraGraph() {
		super();

		agenda = new PriorityQueue<AgendaItem>(2000,this);
		delta = new Hashtable<AgendaItem, Double>();
		pred = new Hashtable<AgendaItem, AgendaItem>();
		task_status = new Hashtable<Integer, AgendaItem>();
	}

	public int getAgendaId() {
		return task_status.size();
	}

	public void resetSearch() {
		agenda.clear();
		delta.clear();
		pred.clear();
	}

	public void updateTaskStatus(int id, AgendaItem a) {

		a.setID(getAgendaId());
		task_status.put(Integer.valueOf(id), a);
	}

	public AgendaItem getTaskStatus(int id) {
		return task_status.get(Integer.valueOf(id));
	}

	public void initSearch(StateDescription startState, StateDescription targetState) {
		AgendaItem startItem = new AgendaItem(root_node, startState, Task.Status.RUNNING, 0);

		resetSearch();
		delta.put(startItem, new Double(0.0));
		agenda.add(startItem);
		this.targetState = targetState;
		this.current_agenda_item = startItem;
	}

	public boolean solutionFound() {
		if (current_agenda_item.getState() == null) return false;
		else return (current_agenda_item.getState().isGoalState(targetState));
	}

	public boolean noSolution() {
		return (!solutionFound() && agenda.isEmpty());
	}

	public boolean moreTestItems() { return !agenda.isEmpty();}

	public void addToAgenda(AgendaItem a) {
		agenda.add(a);
	}

	public void nextStep() {
		if (solutionFound()) System.exit(0);
		if (!agenda.isEmpty()) {
			Iterator<AgendaItem> iter = agenda.iterator();
			int i = 0;
			AgendaItem a;
			while (iter.hasNext()) {
				a = iter.next();
				System.out.println("pos " + i + ": " + a.getNode().getNodeID() + " cost: " + a.f());
				i ++;
			}
			System.out.println("poll agenda element");
			current_agenda_item = agenda.poll();

				System.out.println("expanding node: " + current_agenda_item.getNode().getClass().getSimpleName() + " id: " + current_agenda_item.getNode().getNodeID());
				if (current_agenda_item.getState() == null) {
					System.out.println("operator state was not known. using current_state");
					current_agenda_item.setState(current_state);
				}
				((BTNode) current_agenda_item.getNode()).nextStep(current_agenda_item,
						this.getEdgesForNode(current_agenda_item.getNode().getNodeID()),
						this);
//				System.out.println("effects: " + current_agenda_item.getState());
				current_state = current_agenda_item.getState();

			System.out.println("# elements: " + agenda.size());
		}
		else {
			System.out.println("# elements: " + agenda.size() + " -- no expansion");
			System.out.println("no plan found.");
			System.exit(0);
		}
	}
	
    public Instructions computeBestPath() {
    	if (solutionFound()) {
			Instructions t = new Instructions();
    		AgendaItem n = this.getTaskStatus(current_agenda_item.getNode().getNodeID());
    		
    		while (n.getPred() != null) {
    			t.appendPart(((BTNode)n.getNode()).getMyTask());
				System.out.print(n.getNode().getNodeID() + " " + ((BTNode)n.getNode()).getMyTask().getClass().getSimpleName() + " ");
				if (n.getTaskState() == Task.Status.FRESH) System.out.print("FRESH ");
				else if(n.getTaskState() == Task.Status.RUNNING) System.out.print("RUNNING ");
				else if(n.getTaskState() == Task.Status.SUCCEEDED) System.out.print("SUCCEEDED ");
				else if(n.getTaskState() == Task.Status.FAILED) System.out.print("FAILED ");
				else if(n.getTaskState() == Task.Status.CANCELLED) System.out.print("CANCELLED ");
				System.out.println( "");

    			n = n.getPred();
    		}

			return t;
		}
    	else return null;
    }

	@Override
	public int compare(AgendaItem arg0, AgendaItem arg1) {
		if (arg0.f() < arg1.f()) return 1;
		else if (arg0.f() == arg1.f()) return 0;
		else return -1;
	}
}
