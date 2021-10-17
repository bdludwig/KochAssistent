package graph;

/**
 * implementation of a class for edges in ALGOGraphs
 */

public class ALGOEdge {
    protected double costs;
    protected int sourceID, sinkID;
    protected boolean isHighlighted;
    protected boolean isSelected;
    protected boolean onOptPath;

    /*
     * default constructor
     */

    public ALGOEdge() {
    	isHighlighted = false;
    	isSelected = false;
    	onOptPath = false;
    	
    	costs = 0.0;
    }

    /**
     * constructor
     *
     * @param soid source node id
     * @param siid sink nodee id
     */

    public ALGOEdge(int soid, int siid) {
    	sourceID = soid;
    	sinkID = siid;
    	
    	isHighlighted = false;
    	isSelected = false;
    	onOptPath = false;
    	
    	costs = 0.0;
    }

    /**costs
     * constructor
     *
     * @param soid source node id
     * @param siid sink nodee id
     * @param c costs for this edge
     */

    public ALGOEdge(int soid, int siid, double c) {
    	sourceID = soid;
    	sinkID = siid;
    	
    	isHighlighted = false;
    	isSelected = false;
    	onOptPath = false;
    	
    	costs = c;
    }

    /**
     * @return source of this ALGOEdge
     */

    public int getSource() {
    	return sourceID;
    }

    /**
     * @return sink of this ALGOEdge
     */

    public int getSink() {
    	return sinkID;
    }

    /**
     * marks this ALGOEdge as selected
     */

    public void select() {
    	isSelected = true;
    }

    /**
     * marks this ALGOEdge as not selected.
     */

    public void unselect() {
    	isSelected = false;
    }
    
    /**
     * marks this ALGOEdge as highlighted
     */

    public void highlight() {
    	isHighlighted = true;
    }

    /**
     * marks this ALGOEdge as normal - that means not highlighted.
     */

    public void unhighlight() {
    	isHighlighted = false;
    }

    /**
     * creates string representation
     *
     * @return string for this ALGOEdge: (<source>,<sink>)
     */

    public boolean isHighlighted() {
    	return isHighlighted;
    }
    
    public boolean isSelected() {
    	return isSelected;
    }
    
    public boolean isOptimal() {
    	return onOptPath;
    }
    
    public void setOptimal(boolean b) {
    	onOptPath = b;
    }
    
    public String toString() {
    	return "(" + sourceID + "," + sinkID + "," + costs + ")";
    }

    public String toGraphViz() {
    	return sourceID + " -> " + sinkID + " [color = " +
		( isHighlighted ? "red" : "black" ) + " label = \"cost: " +
		costs + "\"];";
    }
    
    public double getCosts() {
    	return costs;
    }
    
    public boolean equals(Object o) {
    	if (o == null) return false;
    	else return false;
    }

}