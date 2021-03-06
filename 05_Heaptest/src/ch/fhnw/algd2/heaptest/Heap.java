package ch.fhnw.algd2.heaptest;
/* Heap als Implementierung einer Priority Queue */
@SuppressWarnings("LossyEncoding")
class Heap<K> implements PriorityQueue<K> {
	private HeapNode<K>[] heap; // Array to store the heap elements. 0-indiziert.
	private int size; // position of last insertion into the heap

	/**
	 * Construct the binary heap.
	 * 
	 * @param size
	 *          how many items the heap can store
	 */
	@SuppressWarnings("unchecked")
	Heap(int capacity) {
		// TODO allocate array of proper size and assign to heap
		heap = new HeapNode[capacity];
		size = 0;
	}

	/**
	 * Returns the number of elements in this priority queue.
	 * 
	 * @return the number of elements in this priority queue.
	 */
	@Override
	public int size() {
		// TODO return number of elements currently contained in the heap
		return size;
	}

	/**
	 * Check whether the heap is empty.
	 * 
	 * @return true if there are no items in the heap.
	 */
	@Override
	public boolean isEmpty() {
		// TODO return true if no element is in the heap
		return size == 0;
	}

	/**
	 * Check whether the heap is full.
	 * 
	 * @return true if no further elements can be inserted into the heap.
	 */
	@Override
	public boolean isFull() {
		// TODO return true if no further element can be inserted to the heap
		return size == heap.length;
	}

	/**
	 * Make the heap (logically) empty.
	 */
	@Override
	public void clear() {
		// TODO clear the heap from all elements
		heap = new HeapNode[ heap.length ];
		size = 0;
	}

	/**
	 * Add to the priority queue, maintaining heap order. Duplicates and null
	 * values are allowed. Small values of the argument priority means high
	 * priority, Large values means low priority.
	 * 
	 * @param element
	 *          the item to insert
	 * @param priority
	 *          the priority to be assigned to the item element
	 * @exception QueueFullException
	 *              if the heap is full
	 */
	@Override
	public void add(K element, long priority) throws QueueFullException {
		// TODO add the item element with the priority priority to the heap
		//Note: "Duplicates and null values are allowed" means
		// I don't have to do anything about it. No exception has to be thrown.
		if (isFull()) throw new QueueFullException();
		//add element.
		int index = size;
		heap[index] = new HeapNode<>(element, priority);
		++size;
		// check: has the newly added node to move up? Note, that siftUp does half of the check.
		if ( size > 1)
			siftUp(index);
	}

	/**
	 * Removes and returns the item with highest priority (smallest priority
	 * value) from the queue, maintaining heap order.
	 * 
	 * @return the item with highest priority (smallest priority value)
	 * @throws QueueEmptyException
	 *           if the queue is empty
	 */
	@Override
	public K removeMin() throws QueueEmptyException {
		// TODO return the element from the heap's root and remove the element from
		// the heap
		HeapNode<K> root = heap[0];
		//delete root and replace it with last element.
		if (size > 1)
			heap[0] = heap[ size - 1 ];
		heap[ size - 1] = null;
		--size;
		siftDown(0);	//all checks are held within siftDown().
		return root.element;
	}

	/**
	 * Internal method to let an element sift down in the heap.
	 * 
	 * @param start
	 *          the index at which the percolate begins
	 */
	private void siftDown(int start) {
		// TODO implement sift down for element at start

		//TODO make sure that this implementation also works when the tree has three generations.
		int leftChild = indexOfLeftChild(start);
		int rightChild = indexOfRightChild(start);
		int parent = start;
		HeapNode<K> swapCache;
		while (heap[ leftChild ] != null && heap[ parent ].priority > heap[ leftChild ].priority ||
				heap[ rightChild ] != null && heap[ parent ].priority > heap[ rightChild ].priority) {
			//decide to swap with which child. Note: Remember that
			// sometimes, there might be less than 2 children.
			int swapWith;
			if (heap[ leftChild ] != null && heap[ rightChild ] != null) {
				if (heap[ leftChild ].priority < heap[ rightChild ].priority) {
					swapWith = leftChild;
				} else {
					swapWith = rightChild;
				}
			} else if (heap[ leftChild ] != null) {
				swapWith = leftChild;
			} else if (heap[ rightChild ] != null) {
				swapWith = rightChild;
			} else {
				System.out.println("went to break.");
				break;
			}

			//swap.
			swapCache = heap[ swapWith ];
			heap[ swapWith ] = heap[ parent ];
			heap[ parent ] = swapCache;
			//update indices.
			parent = swapWith;
			leftChild = indexOfLeftChild(start);
			rightChild = indexOfRightChild(start);
		}
	}

	/**
	 * Internal method to let an element sift up in the heap.
	 * 
	 * @param start
	 *          the index at which the percolate begins
	 */
	private void siftUp(int start) {
		// TODO implement sift up for element at start
		int predecessor = indexOfParent(start);
		int child = start;
		HeapNode<K> swapCache;
		while (heap[ child ].priority < heap[ predecessor ].priority) {
			//swap nodes.
			swapCache = heap[ predecessor ];
			heap[ predecessor ] = heap[ child ];
			heap[ child ] = swapCache;
			//update indices.
			child = predecessor;
			predecessor = indexOfParent(child);
		}
	}

	/**
	 * returns index of the parent node's left child.
	 * @param i index of parent node.
	 * @return position of left child node within 0-indexed array.
	 */
	private int indexOfLeftChild(int i) {
		return 2*i + 1; //note: 4,5 becomes 4 due to integer math.
	}

	/**
	 * returns index of the parent node's right child.
	 * @param i index of parent node.
	 * @return position of right child node within 0-indexed array.
	 */
	private int indexOfRightChild(int i) {
		return 2*i + 2; //note: 4,5 becomes 4 due to integer math.
	}

	/**
	 * returns index of the parent node.
	 * @param i index of child node.
	 * @return position of parent node within 0-indexed array.
	 */
	private int indexOfParent(int i) {
		return (i - 1) / 2; //note: 4,5 becomes 4 due to integer math.
	}

	/**
	 * Erzeugt ein neues long[] Array und kopiert die Werte der Priorit�ten aus
	 * dem Heaparray dort hinein. Die Gr�sse des zur�ckgegebenen Arrays soll der
	 * Anzahl Elemente in der Queue entsprechen (= size()). An der Position 0 soll
	 * die kleinste Priorit�t (= Priorit�t des Wurzelelementes) stehen.
	 * 
	 * @return Array mit allen Priorit�ten
	 */
	@Override
	public long[] toLongArray() {
		// TODO return array with all the priorities currently in the heap. Use
		// order of storage. Put root element at position 0.
		long[] prioArray = new long[size];
		for (int i = 0; i < prioArray.length; ++i) {
			prioArray[i] = heap[i].priority;
		}
		return prioArray;
	}

	private static class HeapNode<K> {
		private final K element;
		private final long priority;

		HeapNode(K element, long priority) {
			this.element = element;
			this.priority = priority;
		}
	}
}
