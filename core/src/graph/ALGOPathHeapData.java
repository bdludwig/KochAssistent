package graph;

public class ALGOPathHeapData extends ALGOHeapData {
	private ALGOPath id;

	public ALGOPathHeapData(ALGOPath s, double p) {
		super(p);
		id = s;
	}

	public ALGOPath getData() {
		return id;
	}

	public ALGOPathHeapData clone() {
		return new ALGOPathHeapData(id.clone(), this.priority());
	}
}
