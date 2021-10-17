package graph;

import java.util.Collection;
import java.util.Vector;

public class ALGOPath extends Vector<ALGOEdge> {
	private static final long serialVersionUID = 1L;

	public ALGOPath() {
	}
	
	public ALGOPath(Collection<ALGOEdge> c) {
		super(c);
	}
	
	public ALGOPath(ALGOEdge e) {
		add(e);
	}
	
	public ALGOEdge lastElement() {
		return this.get(this.size()-1);
	}

	public String toString() {
		return get(0).toString();
	}
	
	public int pathLength() {
		return this.size();
	}
	
	public ALGOEdge elementAt(int pos) {
		return get(pos);
	}
	
	public ALGOPath clone() {
		ALGOPath r = new ALGOPath();
		
		for (int i = 0; i < pathLength(); i++) r.add(elementAt(i));
		return r;
	}
	
	public double getCosts() {
		double c = 0;
		
		for (int i = 0; i < pathLength(); i++) c += get(i).getCosts();
		
		return c;
	}
}
