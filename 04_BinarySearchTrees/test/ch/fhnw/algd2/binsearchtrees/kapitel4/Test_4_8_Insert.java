/*
 * Created on 09.03.2017
 */
package ch.fhnw.algd2.binsearchtrees.kapitel4;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Wolfgang Weck
 */
public class Test_4_8_Insert {
	private int[] inorderTraversal(BinSearchTree root) {
		List<Integer> l = new ArrayList<>(20);
		inorderTraversal(root, l);
		int[] arr = new int[l.size()];
		int i = 0;
		for (int x : l)
			arr[i++] = x;
		return arr;
	}

	private void inorderTraversal(BinSearchTree root, List<Integer> l) {
		if (root != null) {
			inorderTraversal(root.getLeft(), l);
			l.add(root.getKey());
			inorderTraversal(root.getRight(), l);
		}
	}

	@Test
	public void insertIntoEmptyTree() {
		BinSearchTree root = new BinSearchTree(0).insert(null, 6);
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 6 }, inorderTraversal(root));
	}

	@Test
	public void insertSmallerElementToSingleNode() {
		BinSearchTree root = new BinSearchTree(6);
		root.insert(root, 5);
		assertEquals("Value in root changed unexpectedly", 6, root.getKey());
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 5, 6 }, inorderTraversal(root));
	}

	@Test
	public void insertLargerElementToSingleNode() {
		BinSearchTree root = new BinSearchTree(6);
		root.insert(root, 7);
		assertEquals("Value in root changed unexpectedly", 6, root.getKey());
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 6, 7 }, inorderTraversal(root));
	}

	@Test
	public void insertTwoElementsToSingleNode() {
		BinSearchTree root = new BinSearchTree(6);
		root.insert(root, 5);
		root.insert(root, 7);
		assertEquals("Value in root changed unexpectedly", 6, root.getKey());
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 5, 6, 7 }, inorderTraversal(root));
	}

	@Test
	public void insertAtThirdLevelLeft() {
		BinSearchTree root = new BinSearchTree(6);
		root.insert(root, 4);
		root.insert(root, 8);
		root.insert(root, 5);
		assertEquals("Value in root changed unexpectedly", 6, root.getKey());
		assertEquals("Left successor value changed unexpectedly", 4,
				root.getLeft().getKey());
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 4, 5, 6, 8 }, inorderTraversal(root));
	}

	@Test
	public void insertAtThirdLevelRight() {
		BinSearchTree root = new BinSearchTree(6);
		root.insert(root, 4);
		root.insert(root, 8);
		root.insert(root, 7);
		assertEquals("Value in root changed unexpectedly", 6, root.getKey());
		assertEquals("Right successor value changed unexpectedly", 8,
				root.getRight().getKey());
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 4, 6, 7, 8 }, inorderTraversal(root));
	}

	@Test
	public void insertAtThirdLevelMin() {
		BinSearchTree root = new BinSearchTree(6);
		root.insert(root, 4);
		root.insert(root, 8);
		root.insert(root, 3);
		assertEquals("Value in root changed unexpectedly", 6, root.getKey());
		assertEquals("Left successor value changed unexpectedly", 4,
				root.getLeft().getKey());
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 3, 4, 6, 8 }, inorderTraversal(root));
	}

	@Test
	public void insertAtThirdLevelMax() {
		BinSearchTree root = new BinSearchTree(6);
		root.insert(root, 4);
		root.insert(root, 8);
		root.insert(root, 9);
		assertEquals("Value in root changed unexpectedly", 6, root.getKey());
		assertEquals("Right successor value changed unexpectedly", 8,
				root.getRight().getKey());
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 4, 6, 8, 9 }, inorderTraversal(root));
	}

	@Test
	public void insertMinIntoLargerTree() {
		BinSearchTree root = sampleTree();
		root.insert(root, 1);
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 1, 2, 3, 4, 5, 7, 8, 10, 14, 18, 20 },
				inorderTraversal(root));
	}

	@Test
	public void insertMaxIntoLargerTree() {
		BinSearchTree root = sampleTree();
		root.insert(root, 21);
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 2, 3, 4, 5, 7, 8, 10, 14, 18, 20, 21 },
				inorderTraversal(root));
	}

	@Test
	public void insertInnerIntoLargerTree1() {
		BinSearchTree root = sampleTree();
		root.insert(root, 6);
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 2, 3, 4, 5, 6, 7, 8, 10, 14, 18, 20 },
				inorderTraversal(root));
	}

	@Test
	public void insertInnerIntoLargerTree2() {
		BinSearchTree root = sampleTree();
		root.insert(root, 19);
		assertArrayEquals("inorder traversal of generated tree is not correct",
				new int[] { 2, 3, 4, 5, 7, 8, 10, 14, 18, 19, 20 },
				inorderTraversal(root));
	}

	private BinSearchTree sampleTree() {
		BinSearchTree root = new BinSearchTree(5);
		BinSearchTree node1 = new BinSearchTree(3);
		BinSearchTree node2 = new BinSearchTree(2);
		BinSearchTree node3 = new BinSearchTree(4);
		BinSearchTree node4 = new BinSearchTree(7);
		BinSearchTree node5 = new BinSearchTree(10);
		BinSearchTree node6 = new BinSearchTree(8);
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
		return root;
	}
}
