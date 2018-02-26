package ch.fhnw.algd2.collections.list.linkedlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.fhnw.algd2.collections.list.linkedlist.general.AbstractMyLinkedListTest;

public class C_MyLinkedListTest_Remove extends AbstractMyLinkedListTest {
	@Test
	public void remove1_Numbers1To5Added_ElementRemoved() {
		addNumbersFromOneToFiveToList();
		Integer number = Integer.valueOf(1);
		assertTrue(list.remove(number));
		assertFalse(list.contains(number));
		assertEquals(4, list.size());
		checkOrderIndependentOccurrence(new Integer[] { 2, 3, 4, 5 });
	}

	@Test
	public void remove5_Number1To5Added_ElementRemoved() {
		addNumbersFromOneToFiveToList();
		Integer number = Integer.valueOf(5);
		assertTrue(list.remove(number));
		assertFalse(list.contains(number));
		assertEquals(4, list.size());
		checkOrderIndependentOccurrence(new Integer[] { 1, 2, 3, 4 });
	}

	@Test
	public void remove2_Numbers1To5Added_ElementRemoved() {
		addNumbersFromOneToFiveToList();
		Integer number = Integer.valueOf(2);
		assertTrue(list.remove(number));
		assertFalse(list.contains(number));
		assertEquals(4, list.size());
		checkOrderIndependentOccurrence(new Integer[] { 1, 3, 4, 5 });
	}

	@Test
	public void remove4_Numbers1To5Added_ElementRemoved() {
		addNumbersFromOneToFiveToList();
		Integer number = Integer.valueOf(4);
		assertTrue(list.remove(number));
		assertFalse(list.contains(number));
		assertEquals(4, list.size());
		checkOrderIndependentOccurrence(new Integer[] { 1, 2, 3, 5 });
	}

	@Test
	public void remove3_Numbers1To5Added_ElementRemoved() {
		addNumbersFromOneToFiveToList();
		Integer number = Integer.valueOf(3);
		assertTrue(list.remove(number));
		assertFalse(list.contains(number));
		assertEquals(4, list.size());
		checkOrderIndependentOccurrence(new Integer[] { 1, 2, 4, 5 });
	}

	@Test
	public void remove10_Numbers1To5Added_NoCollectionChanges() {
		addNumbersFromOneToFiveToList();
		assertFalse(list.remove(Integer.valueOf(10)));
		assertEquals(5, list.size());
	}

	@Test
	public void remove10_EmptyList_NoCollectionChanges() {
		assertFalse(list.remove(Integer.valueOf(10)));
	}

	@Test
	public void remove_WhenDuplicatesExist_OnlyFirstOccurance() {
		addNumbersFromOneToFiveToList();
		Integer number = Integer.valueOf(2);
		list.add(number);
		assertTrue(list.remove(number));
		checkOrderDependentOccurrence(new Integer[] { 1, 3, 4, 5, 2 });
	}

	@Test
	public void remove_removeLastElement_CanAddToEndOfListAfterwards() {
		addNumbersFromOneToFiveToList();
		Integer number = Integer.valueOf(6);
		assertTrue(list.add(number));
		assertTrue(list.remove(number));
		assertTrue(list.add(Integer.valueOf(7)));
		checkOrderIndependentOccurrence(new Integer[] { 1, 2, 3, 4, 5, 7 });
	}

	@Test
	public void remove_removeOnlyElement_CanAddToEndOfListAfterwards() {
		Integer number = Integer.valueOf(6);
		assertTrue(list.add(number));
		assertTrue(list.remove(number));
		assertTrue(list.add(Integer.valueOf(7)));
		checkOrderDependentOccurrence(new Integer[] { 7 });
	}

	@Test
	public void remove_SeparateButEqualObjects_WillBeRemoved() {
		for (int i = 0; i < 4; i++)
			assertTrue(list.add(new Integer(i)));
		assertTrue("Different but equal object not identified as equal by remove",
				list.remove(new Integer(2)));
		assertTrue("Different but equal object not identified as equal by remove",
				list.remove(new Integer(0)));
		assertTrue("Different but equal object not identified as equal by remove",
				list.remove(new Integer(3)));
		assertTrue("Different but equal object not identified as equal by remove",
				list.remove(new Integer(1)));
	}
}
