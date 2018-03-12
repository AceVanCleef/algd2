/*
 * Created on 09.03.2017
 */
package ch.fhnw.algd2.binsearchtrees.kapitel4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Wolfgang Weck
 */
public class Test_4_3_Search {
	/**
	 * @throws java.lang.Exception
	 */
	private BinSearchTree root;

	@Before
	public void setUp() throws Exception {
		root = new BinSearchTree(6);
		BinSearchTree node1 = new BinSearchTree(3);
		BinSearchTree node2 = new BinSearchTree(2);
		BinSearchTree node3 = new BinSearchTree(4);
		BinSearchTree node4 = new BinSearchTree(7);
		BinSearchTree node5 = new BinSearchTree(10);
		BinSearchTree node6 = new BinSearchTree(7);
		BinSearchTree node7 = new BinSearchTree(18);
		BinSearchTree node8 = new BinSearchTree(14);
		BinSearchTree node9 = new BinSearchTree(20);
		root.setLeft(node1);
		node1.setLeft(node2);
		node1.setRight(node3);
		root.setRight(node4);
		node4.setRight(node5);
		node5.setLeft(node6);
		node5.setRight(node7);
		node7.setLeft(node8);
		node7.setRight(node9);
	}

	@Test
	public void searchInEmptyTree() {
		assertFalse("Element in inner node is not found", root.search(null, 6));
	}

	@Test
	public void searchInSingleNodeTreeRootElement() {
		root = new BinSearchTree(6);
		assertTrue("Element in tree with single node not found",
				root.search(root, 6));
	}

	@Test
	public void searchInSingleNodeTreeSmallerElement() {
		root = new BinSearchTree(6);
		assertFalse("Found non-existing element in Tree with single node",
				root.search(root, 5));
	}

	@Test
	public void searchInSingleNodeTreeLargerElement() {
		root = new BinSearchTree(6);
		assertFalse("Found non-existing element in Tree with single node",
				root.search(root, 7));
	}

	@Test
	public void searchRootElement() {
		assertTrue("Element in root node is not found", root.search(root, 6));
	}

	@Test
	public void searchExistingInnerElement() {
		assertTrue("Element in inner node is not found", root.search(root, 18));
	}

	@Test
	public void searchMaxElement() {
		assertTrue("Max. Element is not found", root.search(root, 20));
	}

	@Test
	public void searchMinElement() {
		assertTrue("Min. Element is not found", root.search(root, 2));
	}

	@Test
	public void searchExistingLeafElement1() {
		assertTrue("Element in inner node is not found", root.search(root, 14));
	}

	@Test
	public void searchExistingLeafElement2() {
		assertTrue("Element in inner node is not found", root.search(root, 4));
	}

	@Test
	public void searchMultiplyExistingElement() {
		assertTrue("Element in inner node is not found", root.search(root, 7));
	}

	@Test
	public void searchNonexistingElement1() {
		assertFalse("Element in inner node is not found", root.search(root, 15));
	}

	@Test
	public void searchNonexistingElement2() {
		assertFalse("Element in inner node is not found", root.search(root, 8));
	}

	@Test
	public void searchTooSmallElement() {
		assertFalse("Element in inner node is not found", root.search(root, 1));
	}

	@Test
	public void searchTooLargeElement() {
		assertFalse("Element in inner node is not found", root.search(root, 21));
	}
}
