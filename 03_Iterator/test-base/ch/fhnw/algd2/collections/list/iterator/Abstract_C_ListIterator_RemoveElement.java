package ch.fhnw.algd2.collections.list.iterator;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Test;

public abstract class Abstract_C_ListIterator_RemoveElement extends
		AbstractListIteratorTest {

	private Integer one = Integer.valueOf(1);
	private Integer two = Integer.valueOf(2);
	private Integer three = Integer.valueOf(3);
	private Integer four = Integer.valueOf(4);
	private Integer five = Integer.valueOf(5);

	private Integer[] allNumbers = new Integer[] { one, two, three, four, five };

	@Test
	public void removeFirst_OneToFiveInList_TwoToFiveRemaining() {
		addElementsToList();
		removeElementAtPositionWithIterator(0, one);
		assertRemainingElements(new Integer[] { two, three, four, five });
	}

	private void assertRemainingElements(Integer[] integers) {
		Iterator<Integer> it = getIterator();
		for (Integer i : integers) {
			assertTrue(it.hasNext());
			assertSame(i, it.next());
		}
		assertFalse(it.hasNext());
	}

	@Test
	public void removeSecond_OneToFiveInList_RestRemaining() {
		addElementsToList();
		removeElementAtPositionWithIterator(1, two);
		assertRemainingElements(new Integer[] { one, three, four, five });
	}

	@Test
	public void removeThrid_OneToFiveInList_RestRemaining() {
		addElementsToList();
		removeElementAtPositionWithIterator(2, three);
		assertRemainingElements(new Integer[] { one, two, four, five });
	}

	@Test
	public void removeFourth_OneToFiveInList_RestRemaining() {
		addElementsToList();
		removeElementAtPositionWithIterator(3, four);
		assertRemainingElements(new Integer[] { one, two, three, five });
	}

	@Test
	public void removeLast_OneToFiveInList_OneToFourRemaining() {
		addElementsToList();
		removeElementAtPositionWithIterator(4, five);
		assertRemainingElements(new Integer[] { one, two, three, four });
		assertSame(list.get(list.size() - 1), four);
	}

	@Test(expected = IllegalStateException.class)
	public void remove_BeforeFirstNextCall_IllegalStateException() {
		addElementsToList();
		Iterator<Integer> it = getIterator();
		it.remove();
		fail("Remove call should fail");
	}

	@Test
	public void removeTwice_NoNextCallInBetween_IllegalStateException() {
		addElementsToList();
		Iterator<Integer> it = getIterator();
		assertSame(one, it.next());
		assertSame(two, it.next());
		assertSame(three, it.next());
		// First call must pass
		it.remove();
		// Second call must fail
		try {
			it.remove();
			fail("Second remove call must fail");
		} catch (IllegalStateException ex) {
			// OK
		}
	}

	@Test
	public void removeTwice_NextCallInBetween_ElementsRemoved() {
		addElementsToList();
		Iterator<Integer> it = getIterator();
		assertSame(one, it.next());
		assertSame(two, it.next());
		assertSame(three, it.next());
		it.remove();
		assertSame(four, it.next());
		it.remove();
		assertSame(five, it.next());
		assertFalse(it.hasNext());
		assertRemainingElements(new Integer[] { one, two, five });
	}

	@Test
	public void remove_OneElementInList_NoElementsRemaining() {
		list.add(one);
		Iterator<Integer> it = getIterator();
		it.next();
		it.remove();
		assertTrue(list.isEmpty());
	}

	@Test(expected = ConcurrentModificationException.class)
	public void next_OtherIteratorHasRemovedElement_ConcurrentModificationException() {
		addNumbersFromOneToFiveToList();
		Iterator<Integer> first = getIterator();
		Iterator<Integer> second = getIterator();
		first.next();
		first.next();
		// Changes mod value
		first.remove();
		second.hasNext();
		second.next();
	}
	
	@Test
	public void remove_onListWithOneElementAndAddOneAgain_validElement(){
		list.add(one);
		Iterator<Integer> it = list.iterator();
		assertTrue(it.hasNext());
		assertEquals(one, it.next());
		it.remove();
		list.add(two);
		assertRemainingElements(new Integer[]{two});
		assertEquals(two, list.get(0));
	}

	@Test
	public void add_afterLastElementRemoved_validList() {
		addNumbersFromOneToFiveToList();
		Iterator<Integer> it = getIterator();
		// go to the end
		while (it.hasNext()) {
			it.next();
		}
		assertEquals(5, list.size());
		it.remove();
		assertEquals(4, list.size());
		Integer six = Integer.valueOf(6);
		list.add(six);
		assertEquals(5, list.size());
		Integer[] expected = new Integer[] { one, two, three, four, six };
		assertRemainingElements(expected);
	}

	@Test
	public void remove_SecondElementWhenDuplicatesExists_SecondElementRemoved() {
		list.add(one);
		list.add(two);
		list.add(three);
		list.add(one);
		list.add(four);
		Integer last = null;
		Iterator<Integer> it = list.iterator();
		for (int i = 0; i < 4; i++) {
			assertTrue(it.hasNext());
			last = it.next();
		}
		assertEquals(one, last);
		it.remove();
		assertRemainingElements(new Integer[] { one, two, three, four });
	}

	private void removeElementAtPositionWithIterator(int indexToRemove,
			Integer expectToBeRemoved) {
		Iterator<Integer> it = getIterator();
		Integer willBeRemoved = null;
		int i = 0;
		for (; i <= indexToRemove; i++) {
			assertTrue(it.hasNext());
			willBeRemoved = it.next();
		}
		//assertTrue(it.hasNext());
		//willBeRemoved = it.next();
		assertSame(expectToBeRemoved, willBeRemoved);
		it.remove();
		for (i = i + 1; i < allNumbers.length; i++) {
			assertTrue(it.hasNext());
			it.next();
		}
		assertFalse(it.hasNext());
	}

	private void addElementsToList() {
		for (Integer i : allNumbers) {
			assertTrue(list.add(i));
		}
	}

}
