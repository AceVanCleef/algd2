/*
 * Created on 07.03.2016
 */
package ch.fhnw.algd2.collections.list.linkedlist;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.fhnw.algd2.collections.list.linkedlist.general.AbstractMyLinkedListTest;

/**
 * @author Wolfgang Weck
 */
public class A_MyLinkedListTest_Add_Efficiency extends AbstractMyLinkedListTest {
	private static final int SAMPLE_SIZE = 100, PREFIX_SIZE = 100;

	@Test(timeout = 1000)
	public void add_Efficiency_Constant() {
		for (int i = 0; i < PREFIX_SIZE; i++)
			list.add(i);
		long t0 = System.nanoTime();
		for (int i = PREFIX_SIZE; i < PREFIX_SIZE + SAMPLE_SIZE; i++)
			list.add(i);
		t0 = System.nanoTime() - t0;
		int n = (int)(100_000_000 / t0 * SAMPLE_SIZE);
		for (int i = PREFIX_SIZE + SAMPLE_SIZE; i < n; i++)
			list.add(i);
		long t1 = System.nanoTime();
		for (int i = n; i < n + SAMPLE_SIZE; i++)
			list.add(i);
		t1 = System.nanoTime() - t1;
		assertTrue(
				"not O(1): adding the 20'000th element takes substantially longer than adding the 100th",
				t0 == 0 || t1 < 8 * t0);
	}
}
