package graph;

public class ALGOHeap {
	private ALGOHeapData [] array;
	private int maxSize;
	private int numElements;
	
	public ALGOHeap() {
	    array = new ALGOHeapData[10];
	    maxSize = 10;
	    numElements = 0;

	    for (int i = 0; i < maxSize; i++) array[i] = null;
	}
	
	public ALGOHeap(int size) {
	    array = new ALGOHeapData[size];
	    maxSize = size;
	    numElements = 0;

	    for (int i = 0; i < maxSize; i++) array[i] = null;
	}
	
	private void swap(int i, int j) {
		ALGOHeapData tmp = array[i];
		
		array[i] = array[j];
		array[j] = tmp;
	}
	
	private void reheap(int i, int j) {
		int posLarger;
		
		if (2*i <= j) {
			// array[i] has at least one child
			
			if (2*i == j) {
				// only one child
				
				posLarger = j;
			}
			else {
				if (array[2*i].priority() < array[2*i+1].priority()) {
					posLarger = 2*i;
				}
				else {
					posLarger = 2*i+1;
				}
			}
			
			if (array[i].priority() > array[posLarger].priority()) {
				swap(i, posLarger);
				if (2*posLarger <= j) reheap(posLarger,j);
			}
		}
	}
	
	public ALGOHeapData retrieve() {
	    if (numElements > 0) {
	    	return array[1];
	    }
	    else return null;
	}
	
	public void insert(ALGOHeapData d) {
		int r;
		if (numElements < maxSize) {
			numElements++;

			array[numElements] = d;
			r = numElements/2;
			while (r >= 1) {
				reheap(r, numElements);
				r = r/2;
			}
		}
		else {
			System.err.println("ALGOHeap overflow: " + maxSize);
			System.exit(0);
		}
	}
	
	public void deleteHighest() {
	    if (numElements > 0) {
	    	swap(1, numElements);
	    	numElements--;
	    	reheap(1, numElements);
	    }
	    else {
	    	System.err.println("heap already empty.");
	    	System.exit(0);
	    }
	}
	
	public String toString() {
	    String s = (array[1] == null) ? "X" : array[1].toString();
	    
	    for (int i=2; i<numElements; i++) 
		s += " " + ((array[i] == null) ? "X" : array[i].toString());
	    
	    return s;
	}
	
	public boolean isEmpty() {
		return numElements == 0;
	}
	
	public ALGOHeapData [] getHeapStorage() {
		return array;
	}
	
	public ALGOHeap clone() {
		ALGOHeap r = new ALGOHeap(maxSize);
		
		for (int i = 0; i < maxSize; i++) {
			 if (array[i] != null) r.insert(array[i].clone());
		}
		
		return r;
	}
}
