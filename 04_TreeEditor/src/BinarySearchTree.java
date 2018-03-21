import ch.fhnw.algd2.treeeditor.base.Tree;

/**
 * Implements an unbalanced binary search tree. Note that all "matching" is
 * based on the compareTo method.
 * 
 * @author Dominik Gruntz
 */
class BinarySearchTree<K extends Comparable<? super K>, E> implements
		Tree<K, E> {
	private Node<K, E> root = null;
	private int nodeCount = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see Tree#height()
	 */
	@Override
	public int height() {
		return height(root);
	}

	/**
	 * Return the height of node t, or 0, if null.
	 */
	private int height(Node<K, E> t) {
		if (t != null) {
			int hl = height(t.left), hr = height(t.right);
			return hl >= hr ? hl + 1 : hr + 1;
		} else return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Tree#getRoot()
	 */
	@Override
	public Tree.Node<K, E> getRoot() {
		return root;
	}

	/**
	 * Searches an item in the tree.
	 * 
	 * @param key
	 *          the key to search for.
	 * @return the matching item or null if not found.
	 */
	@Override
	public E search(K key) {
		return search(root, key);
	}

	private E search(Node<K, E> p, K key) {
		if (p == null) return null;
		else {
			int c = key.compareTo(p.key);
			if (c == 0) return p.element;
			else if (c < 0) return search(p.left, key);
			else return search(p.right, key);
		}
	}

	/**
	 * number of nodes in the tree
	 * 
	 * @return size of the tree.
	 */
	@Override
	public int size() {
		return nodeCount;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Insert a value into the tree; if an element is already stored under the
	 * given key the element is replaced by the new one.
	 * 
	 * @param key
	 *          key with which the specified element is to be associated.
	 * @param element
	 *          element to be inserted into the tree.
	 */
	@Override
	public void insert(K key, E element) {
		root = insert(root, key, element);
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param p
	 *          the node that roots the tree.
	 * @param key
	 *          the key of the element to insert.
	 * @param element
	 *          the element to insert.
	 * @return the new root.
	 */
	private Node<K, E> insert(Node<K, E> p, K key, E element) {
		if (p == null) {
			nodeCount++;
			return new Node<>(key, element);
		} else {
			int c = key.compareTo(p.key);	//-1, 0 or 1 as return values of compareTo.
			if (c < 0) p.left = insert(p.left, key, element);
			else if (c > 0) p.right = insert(p.right, key, element);
			else p.element = element;
			if (!isBalanced(p)) {
				System.out.println(isBalanced(p) + "\n ");
			}
			return p;
		}
	}

	/**
	 * Remove Node with key <code>key</code> from the tree. Nothing is done if x
	 * is not found.
	 * 
	 * @param key
	 *          the key of the item to remove.
	 */
	@Override
	public void remove(K key) {
		// Done implement method remove here
		remove(root, key);
	}

	/*
	* int c = key.compareTo(p.key);
			if (c == 0) return p.element;
			else if (c < 0) return search(p.left, key);
			else return search(p.right, key);
	* */
	/**
	 *  Case 1 (leaf): left == null && right == null
	 *  Case 2 (inner node, grade = 1):
	 *  		left == null ^ right == null
	 *  Case 3 (inner node, grade = 2):
	 *  		!( left == null && right == null)
	 *  		or
	 *  		left != null && right != null
	 * @param node
	 * @param key
	 * @return
	 */
	private Node<K, E> remove(Node<K, E> node, K key) {
		if (node != null) {
			int c = key.compareTo(node.key);
			if (c < 0) node.left = remove(node.left, key);
			else if (c > 0) node.right = remove(node.right, key);
			else if (node.getRight() == null) node = node.left;
			else {
				if (node.getLeft() == null) node = node.right;
				else {
					Node<K, E> n = node.right, p = null;
					while (n.getLeft() != null) {
						p = n;
						n = n.left;
					}
					if (p != null) {
						p.left = n.right;
						n.left = node.left;
						n.right = node.right;
					} else n.left = node.left;
					node = n;
				}
				remove(node, key);
			}
		}
		return node;
	}


	@Override
	public String toString() {
		// Done implement method toString here
		return toStringInOrder(root);
	}

	private String toStringInOrder(Node<K, E> node) {
		if (node == null) {	//prevents NullPointer for left == null AND right == null.
			return "";
		}
		String s = "[";
		//no navigational checks needed (key < getKey oder key >= getKey).
		s = s + toStringInOrder(node.left);
		s = s + node.key;
		s = s + toStringInOrder(node.right);
		s = s + "]";
		return s;
	}

	//#AVL - Trees
	private Node<K, E> rotateR(Node<K, E> n) {
		// 1. künftige Wurzel zwischenmerken
		Node<K,E> n1 = n.left;
		//2. Überläufer behandeln (auf n.left umhängen, altes n1.right = null setzen):
		n.left = n1.right;
		n1.right = null;	//kann auch weggelassen werden.
		//3. n zum Kind von n1 machen.
		n1.right = n;
		return n1;
	}

	//#AVL - Trees
	private Node<K, E> rotateL(Node<K, E> n) {
		// 1. künftige Wurzel zwischenmerken
		Node<K,E> n1 = n.right;
		//2. Überläufer behandeln (auf n.right umhängen, altes n1.left = null setzen):
		n.right = n1.left;
		n1.left = null;	//kann auch weggelassen werden.
		//3. n zum Kind von n1 machen.
		n1.left = n;
		return n1;
	}

	private Node<K, E> rotateRL(Node<K, E> n) {
		rotateR(n.left);
		Node<K, E> newRoot = rotateL(n);
		return newRoot;
	}

	private Node<K, E> rotateLR(Node<K, E> n) {
		rotateL(n.left);
		Node<K, E> newRoot = rotateR(n);
		return newRoot;
	}

	//#AVL - Trees

	/**
	 * returns whether the (sub) tree is balanced.
	 * @param n
	 * @return boolean whether the balance is within the interval of [-1,1]
	 */
	private boolean isBalanced(Node<K,E> n) {
		int balance = height(n.right) - height(n.left);
		System.out.println("Balance of " + n.key + " is: " + balance);
		return -2 < balance && balance < 2;	//Balance must be in intervall of [-1,1]
	}

	private static class Node<K extends Comparable<? super K>, E> implements
			Tree.Node<K, E> {
		final K key;
		E element;
		Node<K, E> left, right;

		@SuppressWarnings("unused")
		Node(K key) {
			this(key, null);
		}

		Node(K key, E element) {
			this.key = key;
			this.element = element;
		}

		@SuppressWarnings("unused")
		Node(K key, E element, Node<K, E> left, Node<K, E> right) {
			this(key, element);
			this.left = left;
			this.right = right;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Tree.Node#getKey()
		 */
		@Override
		public K getKey() {
			return key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Tree.Node#getLeft()
		 */
		@Override
		public Tree.Node<K, E> getLeft() {
			return left;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Tree.Node#getRight()
		 */
		@Override
		public Tree.Node<K, E> getRight() {
			return right;
		}

		//#remove()
		public boolean isLeafNode(){
			return left == null && right == null;
		}

		//#remove()
		public boolean isInnerNodeOfGrade1(){
			return left == null ^ right == null;
		}

		//#remove()
		public boolean isInnerNodeOfGrade2(){
			return left != null && right != null;
		}
	}
}