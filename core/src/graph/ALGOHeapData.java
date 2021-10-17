package graph;

abstract public class ALGOHeapData {
	private double prio;

	public ALGOHeapData(double p) {
		prio = p;
	}

	public double priority() {
		return prio;
	}

	public String toString() {
		return "current cost: " + prio;
	}
	
	public abstract ALGOHeapData clone();
}
