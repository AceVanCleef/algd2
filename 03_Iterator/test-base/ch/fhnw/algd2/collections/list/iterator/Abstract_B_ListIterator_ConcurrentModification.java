package ch.fhnw.algd2.collections.list.iterator;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Test;

public abstract class Abstract_B_ListIterator_ConcurrentModification extends
		AbstractListIteratorTest {

	@Test(expected = ConcurrentModificationException.class)
	public void next_ElementAddedAfterIteratorCreation_ConcurrentModificationException() {
		addNumbersFromOneToFiveToList();
		Iterator<Integer> it = getIterator();
		list.add(5);
		assertTrue(it.hasNext());
		it.next();
		fail("Receiving element should throw an ConcurrentModificationException");
	}

	@Test(expected = ConcurrentModificationException.class)
	public void next_ElementReomvedAfterIteratorCreation_ConcurrentModificationException() {
		addNumbersFromOneToFiveToList();
		Iterator<Integer> it = getIterator();
		list.remove(Integer.valueOf(2));
		assertTrue(it.hasNext());
		it.next();
		fail("Receiving element should throw an ConcurrentModificationException");
	}

	@Test(expected = ConcurrentModificationException.class)
	public void next_ElementRemovedByIndexAfterIteratorCreation_ConcurrentModificationException() {
		addNumbersFromOneToFiveToList();
		Iterator<Integer> it = getIterator();
		list.remove(1);
		assertTrue(it.hasNext());
		it.next();
		fail("Receiving element should throw an ConcurrentModificationException");
	}


	@Test(expected = ConcurrentModificationException.class)
	public void next_ElementAddedAtHeadAfterIteratorCreation_ConcurrentModificationException() {
		addNumbersFromOneToFiveToList();
		Iterator<Integer> it = getIterator();
		list.add(0, new Integer(3));
		it.next();
	}

	@Test(expected = ConcurrentModificationException.class)
	public void next_ElementAddedAtTailAfterIteratorCreation_ConcurrentModificationException() {
		addNumbersFromOneToFiveToList();
		Iterator<Integer> it = getIterator();
		list.add(list.size(), new Integer(3));
		it.next();
	}

	@Test(expected = ConcurrentModificationException.class)
	public void next_ElementAddedInBetweenAfterIteratorCreation_ConcurrentModificationException() {
		addNumbersFromOneToFiveToList();
		Iterator<Integer> it = getIterator();
		list.add(2, new Integer(3));
		it.next();
	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void next_ElementRemovedAfterIteratorCreation_ConcurrentModificationException() {
		addNumbersFromOneToFiveToList();
		Iterator<Integer> it = getIterator();
		list.remove(Integer.valueOf(3));
		it.next();
	}
	
	@Test
	public void next_IterateThroughAllElements_NoException(){
		Integer[] expected = new Integer[]{1,2,3,4,5};
		addNumbersFromOneToFiveToList();
		Iterator<Integer> it = getIterator();
		for (int i=0; i < expected.length; i++){
			assertTrue(it.hasNext());
			assertEquals(expected[i].intValue(), it.next().intValue());
		}
		assertFalse(it.hasNext());
	}
	
}
