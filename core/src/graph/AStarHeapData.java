package graph;

public class AStarHeapData extends ALGOPathHeapData {

	private double g;

	public AStarHeapData(ALGOPath s, double p, double g) {
		super(s, p);
		this.g = g;
	}

	public double getActualCosts() {
		return g;
	}

	public String toString() {
		return "total cost: " + priority() + " cost so far: " + g;
	}
	
	public AStarHeapData clone() {
		return new AStarHeapData(getData().clone(), priority(), g);
	}
}
