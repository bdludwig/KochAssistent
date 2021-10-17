package graph;

import java.util.Hashtable;
import java.util.LinkedList;

public class MyQueue extends LinkedList<ALGONode> {
	private Hashtable<ALGONode,Double> delta;

	public MyQueue(Hashtable<ALGONode,Double> delta) {
		super();
		
		this.delta = delta;
	}
	
	public void addAscending(ALGONode n, double costs) {
		int index = 0;
		for (ALGONode p : this) {
			if (delta.get(p).doubleValue() > costs) break;
			index ++;
		}
		
		this.add(index, n);
	}
}
