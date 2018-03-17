package ch.fhnw.algd2.treeeditor.binarysearchtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import ch.fhnw.algd2.treeeditor.base.Tree;

/**
 * @author Michael Henninger
 */
public abstract class BinarySearchTreeTest_Remove {
	private static final Integer FIFTEEN = Integer.valueOf(15);
	private static final Integer TEN = Integer.valueOf(10);
	private static final Integer FIFTY = Integer.valueOf(50);
	private static final Integer TWENTY = Integer.valueOf(20);
	private static final Integer FORTY = Integer.valueOf(40);
	private static final Integer SIXTY = Integer.valueOf(60);
	private static final Integer FIFTY_FIVE = Integer.valueOf(55);
	private static final Integer THIRTY = Integer.valueOf(30);
	private static final Integer TWELVE = Integer.valueOf(12);
	private static final Integer THIRTY_FIVE = Integer.valueOf(35);
	private Tree<Integer, Object> tree;
	private int initTreeSize;

	protected abstract <K extends Comparable<? super K>, V> Tree<K, V> newTree();

	@Before
	public void init() {
		tree = newTree();
		assertEquals(0, tree.size());
		tree.insert(TWENTY, TWENTY);
		tree.insert(TEN, TEN);
		tree.insert(FIFTEEN, FIFTEEN);
		tree.insert(TWELVE, TWELVE);
		tree.insert(FIFTY, FIFTY);
		tree.insert(SIXTY, SIXTY);
		tree.insert(FORTY, FORTY);
		tree.insert(THIRTY, THIRTY);
		tree.insert(THIRTY_FIVE, THIRTY_FIVE);
		tree.insert(FIFTY_FIVE, FIFTY_FIVE);
		assertEquals(10, tree.size());
		initTreeSize = tree.size();
	}

	@Test
	public void removeLeafNode() {
		assertEquals(TWELVE, tree.search(TWELVE));
		assertEquals(TWELVE, tree.getRoot().getLeft().getRight().getLeft().getKey());
		tree.remove(TWELVE);
		assertEquals(initTreeSize - 1, tree.size());
		// Check search
		assertNull(tree.search(TWELVE));
		// Check structure
		assertNull(tree.getRoot().getLeft().getRight().getLeft());
	}

	@Test
	public void removeNodeOnlyHavingRightChild() {
		assertEquals(TEN, tree.search(TEN));
		assertEquals(TEN, tree.getRoot().getLeft().getKey());
		tree.remove(TEN);
		assertEquals(initTreeSize - 1, tree.size());
		assertNull(tree.search(TEN));
		assertEquals(FIFTEEN, tree.getRoot().getLeft().getKey());
		assertEquals(TWELVE, tree.getRoot().getLeft().getLeft().getKey());
	}

	@Test
	public void removeNodeOnlyHavingLeftChild() {
		assertEquals(FORTY, tree.search(FORTY));
		assertEquals(FORTY, tree.getRoot().getRight().getLeft().getKey());
		tree.remove(FORTY);
		assertEquals(initTreeSize - 1, tree.size());
		assertNull(tree.search(FORTY));
		assertEquals(THIRTY, tree.getRoot().getRight().getLeft().getKey());
		assertEquals(THIRTY_FIVE, tree.getRoot().getRight().getLeft().getRight()
				.getKey());
	}

	@Test
	public void removeNodeWithTwoChildren() {
		assertEquals(FIFTY, tree.search(FIFTY));
		assertEquals(FIFTY, tree.getRoot().getRight().getKey());
		tree.remove(FIFTY);
		assertEquals(initTreeSize - 1, tree.size());
		assertNull(tree.search(FIFTY));
		Tree.Node<Integer, Object> fiftyFiveNode = tree.getRoot().getRight();
		assertEquals(FIFTY_FIVE, fiftyFiveNode.getKey());
		assertEquals(FORTY, fiftyFiveNode.getLeft().getKey());
		assertEquals(SIXTY, fiftyFiveNode.getRight().getKey());
		// Check if fifty-five node is not referenced anymore by sixty
		assertNull(fiftyFiveNode.getRight().getLeft());
	}

	@Test
	public void removeRootNodeWithTwoChildren() {
		assertEquals(TWENTY, tree.search(TWENTY));
		assertEquals(TWENTY, tree.getRoot().getKey());
		tree.remove(TWENTY);
		assertEquals(initTreeSize - 1, tree.size());
		assertNull(tree.search(TWENTY));
		Tree.Node<Integer, Object> thirtyNode = tree.getRoot();
		assertEquals(THIRTY, thirtyNode.getKey());
		assertEquals(TEN, thirtyNode.getLeft().getKey());
		assertEquals(FIFTY, thirtyNode.getRight().getKey());
		// Check if fifty-five node is not referenced anymore by sixty
		assertEquals(THIRTY_FIVE, thirtyNode.getRight().getLeft().getLeft()
				.getKey());
	}
}
