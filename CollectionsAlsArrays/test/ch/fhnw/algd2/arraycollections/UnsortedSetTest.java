package ch.fhnw.algd2.arraycollections;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import ch.fhnw.algd2.arraycollections.general.AbstractSetTest;
import ch.fhnw.algd2.arraycollections.general.ElementExistenceChecker;
import ch.fhnw.algd2.arraycollections.general.MemoryLeakChecker;

public class UnsortedSetTest extends AbstractSetTest {
	@Override
	protected AbstractArrayCollection<Integer> createIntegerCollection(int size) {
		return new UnsortedSet<Integer>(size);
	}

	@Override
	protected Integer[] getExpectedOrderFor(Integer[] values) {
		LinkedHashSet<Integer> set = new LinkedHashSet<Integer>(
				Arrays.asList(values));
		List<Integer> list = new LinkedList<Integer>(set);
		Integer[] elements = new Integer[list.size()];
		int index = 0;
		for (Integer element : list) {
			elements[index++] = element;
		}
		return elements;
	}

	@Test
	public void testLinearSearch(){
		Integer[] numbers = new Integer[]{6, 10, 8, 5, 1};
		addNumbersToBag(numbers);
		assertTrue(bag.contains(1));
	}

	@Test
	public void removeHasNoMemoryLeak() {
		MemoryLeakChecker
				.removeHasNoMemoryLeak(new UnsortedSet<MemoryLeakChecker.TestObj>(20));
	}

	@Override
	protected void checkElementExistence(Comparable<Integer>[] numbers) {
		ElementExistenceChecker.inRandomOrder(Arrays.copyOf(numbers, numbers.length), bag);
	}
}
