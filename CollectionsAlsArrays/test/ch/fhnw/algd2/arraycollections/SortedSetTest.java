package ch.fhnw.algd2.arraycollections;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import ch.fhnw.algd2.arraycollections.general.AbstractSetTest;
import ch.fhnw.algd2.arraycollections.general.ElementExistenceChecker;
import ch.fhnw.algd2.arraycollections.general.MemoryLeakChecker;

public class SortedSetTest extends AbstractSetTest {
	@Override
	protected AbstractArrayCollection<Integer> createIntegerCollection(int size) {
		return new SortedSet<Integer>(size);
	}

	@Override
	protected Integer[] getExpectedOrderFor(Integer[] values) {
		LinkedHashSet<Integer> set = new LinkedHashSet<Integer>(
				Arrays.asList(values));
		List<Integer> list = new LinkedList<Integer>(set);
		Collections.sort(list);
		Integer[] elements = new Integer[list.size()];
		int index = 0;
		for (Integer element : list) {
			elements[index++] = element;
		}
		return elements;
	}

	@Test
	public void removeHasNoMemoryLeak() {
		MemoryLeakChecker.removeHasNoMemoryLeak(new SortedSet<MemoryLeakChecker.TestObj>(20));
	}

	@Override
	protected void checkElementExistence(Comparable<Integer>[] numbers) {
		ElementExistenceChecker.inGivenOrder(Arrays.copyOf(numbers, numbers.length), bag);
	}
}
