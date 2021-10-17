package graph;

abstract public class ALGONode {
    protected int nodeID;
    protected double nodeCost;
    protected boolean isHighlighted; 
    protected boolean isSelected;
    protected boolean onClosedList;
	protected boolean onOpenList;
	protected boolean onOptPath;
    /**
     * default constructor 
     */

    public ALGONode() {
    	nodeID = 0;
    	nodeCost = 0;

    	isHighlighted = false;
    	isSelected = false;

        onClosedList = false;
        onOpenList = false;
        onOptPath = false;
    }

    /**
     * constructor
     *
     * @param id nodeID for this <code>ALGONode</code>
     */
    
    public ALGONode(int id) {
    	nodeID = id;
    	nodeCost = 0;

    	isHighlighted = false;
    	isSelected = false;

        onClosedList = false;
        onOpenList = false;
        onOptPath = false;
    }

    /**
     * constructor
     *
     * @param id nodeID for this <code>ALGONode</code>
     * @param cost costs assigned to this <code>ALGONode</code>;
     */
    
    public ALGONode(int id, double cost) {
    	nodeID = id;
    	nodeCost = cost;

    	isHighlighted = false;
    	isSelected = false;

        onClosedList = false;
        onOpenList = false;
        onOptPath = false;
    }

    /**
     * marks this <code>ALGONode</code> as selected
     */

    public void select() {
    	isSelected = true;
    }

    /**
     * marks this <code>ALGONode</code> as not selected.
     */

    public void unselect() {
    	isSelected = false;
    }
    
    /**
     * marks this <code>ALGONode</code> as highlighted
     */

    public void highlight() {
    	isHighlighted = true;
    }

    /**
     * marks this <code>ALGONode</code> as normal - that means not highlighted.
     */

    public void unhighlight() {
    	isHighlighted = false;
    }
    
    /**
     * tests whether this <code>ALGONode</code> is marked as highlighted
     * 
     * @return true if highlighted, false otherwise
     */
    
    public boolean highlighted() {
    	return isHighlighted;
    }

    /**
     * returns the id of this <code>ALGONode</code>
     * 
     * @return <code>int</code> containing the requested id
     */
    
    public int getNodeID() {
    	return nodeID;
    }
    
    /**
     * returns costs assigned to this <code>ALGONode</code>
     * 
     * @return <code>double</code> containing the costs
     */
    
    public double getNodeCosts() {
    	return nodeCost;
    }

    /**
     * sets costs for this <code>ALGONode</code> to given value
     * 
     * @param c new costs for this <code>ALGONode</code>
     */

    public void setNodeCosts( double c ) {
    	nodeCost = c;
    }

    /**
     * creates symbolic representation for this <code>ALGONode</code>
     * 
     * @return <code>String</code> 
     */
    
    abstract public String toString();

    /**
     * creates symbolic representation of this <code>ALGONode</code>
     * in the GraphViz format used for visualizing <code>ALGOGraph</code>s
     * 
     * @return <code>String</code>
     */
    
    abstract public String toGraphViz();

    /**
     * compares two instances of <code>ALGONode>
     * 
     * @return <code>true</code> if they are identical,
     * <code>false</code> otherwise
     */
    
    abstract public boolean equals(Object o);
    
    public void putOnOpenList(ALGOHeap heap) {
		onOpenList = true;
	}

    public boolean isClosed() {
        return onClosedList;
    }

    public void putOnClosedList() {
        onClosedList = true;
    }

    public void removeFromClosedList() {
        onClosedList = false;
    }

    public boolean isOpen() {
        return onOpenList;
    }

    public boolean isOnPath() {
        return onOptPath;
    }

    public void addToBestPath() {
        onOptPath = true;
    }

    public void removeFromBestPath() {
        onOptPath = false;
    }

    public void removeFromOpenList() {
        onOpenList = false;
    }

    abstract public double costs(ALGONode targetNode);

    public String nodeName() {
        return Integer.toString(nodeID);
    }
}
