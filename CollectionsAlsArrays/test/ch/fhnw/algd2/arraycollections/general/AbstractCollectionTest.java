package ch.fhnw.algd2.arraycollections.general;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import ch.fhnw.algd2.arraycollections.AbstractArrayCollection;

/**
 * Abstract Testclass containing test for all Bags / Sets and sorted / unsorted
 * 
 * @author Michael Henninger
 * 
 * @param <E>
 */
public abstract class AbstractCollectionTest {
	private static final int DEFAULT_SIZE = 10;

	protected AbstractArrayCollection<Integer> bag;

  @Rule
  public Timeout globalTimeout= new Timeout(1000);	//#Debuggen: Auskommentieren!

  @Before
	public void init() {
		bag = createIntegerCollection(DEFAULT_SIZE);
	}

	/*
	 * Default tests
	 */

	@Test
	public void testEmptyCollection() {
		assertTrue(bag.isEmpty());
		assertEquals(0, bag.size());
	}

	/*
	 * Test Adding
	 */
	@Test
	public void addNumberSequence() {
		Integer[] numbers = new Integer[] { 1, 2, 3, 4, 5 };
		addNumbersToBag(numbers);
		checkElementExistence(getExpectedOrderFor(numbers));
	}

	@Test
	public void addUnsortedNumberSequence() {
		Integer[] numbers = new Integer[] { 5, 4, 3, 2, 1 };
		addNumbersToBag(numbers);
		checkElementExistence(getExpectedOrderFor(numbers));
	}

	@Test
	public void addDuplicates() {
		Integer[] numbers = new Integer[] { 1, 2, 3, 2, 5 };
		addNumbersToBag(numbers);
		checkElementExistence(getExpectedOrderFor(numbers));
	}

	protected abstract void checkElementExistence(Comparable<Integer>[] numbers);
	


	@Test(expected = IllegalStateException.class)
	public void addMoreThanCapacity() {
		Integer[] numbers = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		try {
			addNumbersToBag(numbers);
		} catch (UnsupportedOperationException e) {
			fail("Should allow adding ten elements!");
		}
		bag.add(20);
		fail("Should only allow adding ten elements!");
	}

	@Test
	public void notExistingElementInFullCollection() {
		Integer[] numbers = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		try {
			addNumbersToBag(numbers);
		} catch (UnsupportedOperationException e) {
			fail("Should allow adding ten elements!");
		}
		assertFalse(bag.contains(0));
	}

	@Test(expected = NullPointerException.class)
	public void addNull() {
		Integer[] numbers = new Integer[] { 1, null, 2, 3 };
		addNumbersToBag(numbers);
	}

	/*
	 * Check contains
	 */

	@Test
	public void containsNull() {
		Integer[] numbers = new Integer[] { 1, 2, 3 };
		addNumbersToBag(numbers);
		boolean found = true;
		try {
			found = bag.contains(null);
		} catch (NullPointerException e){
			found = false;
		}
		assertFalse(found);
	}

	/*
	 * Check remove (and contains)
	 */

	@Test
	public void removeElement() {
		Integer[] numbers = new Integer[] { 1, 2, 3, 4, 5 };
		addNumbersToBag(numbers);
		assertTrue(bag.contains(2));
		assertTrue(bag.contains(3));
		assertTrue(bag.remove(2));
		assertTrue(bag.remove(3));
		assertFalse(bag.contains(2));
		assertFalse(bag.contains(3));
		checkElementExistence(getExpectedOrderFor(new Integer[] { 1, 4, 5 }));
	}

	@Test
	public void removeNonExisting() {
		Integer[] numbers = new Integer[] { 1, 2, 3, 4, 5 };
		addNumbersToBag(numbers);
		assertFalse(bag.contains(22));
		assertFalse(bag.remove(22));
		assertEquals(numbers.length, bag.size());
	}

	@Test
	public void removeElementFromFullList() {
		Integer[] numbers = new Integer[10];
		for (int i = 0; i < 10; i++) {
			numbers[i] = i;
		}
		addNumbersToBag(numbers);
		assertTrue(bag.remove(5));
		assertEquals(numbers.length - 1, bag.size());
	}

	@Test
	public void containsWithEquals(){
		Integer[] numbers = new Integer[1];
		numbers[0] = new Integer(1);
		addNumbersToBag(numbers);
		assertTrue(bag.contains(new Integer(1)));
	}
	
	@Test
	public void containsLastElement() {
		Integer[] numbers = new Integer[] { 1, 2, 3, 4, 5, 6, 7,8,9,10 };
		addNumbersToBag(numbers);
		assertTrue(bag.contains(10));
	}
	
	@Test
	public void containsOtherObject() {
		Integer[] numbers = new Integer[] { 1, 2, 3 };
		addNumbersToBag(numbers);
		boolean found = true;
		try {
			found = bag.contains("Asdf");
		} catch (ClassCastException ex){
			found = false;
		}
		assertFalse(found);
	}
	
	@Test
	public void removeLastElement() {
		Integer[] numbers = new Integer[] { 1, 2, 3, 4, 5, 6, 7,8,9,10 };
		addNumbersToBag(numbers);
		assertTrue(bag.remove(10));
	}

	protected abstract AbstractArrayCollection<Integer> createIntegerCollection(
			int size);

	protected abstract Integer[] getExpectedOrderFor(Integer[] values);

	protected abstract void addNumbersToBag(Integer[] numbers);

}
