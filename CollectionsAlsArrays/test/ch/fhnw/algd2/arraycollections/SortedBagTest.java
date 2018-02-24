package ch.fhnw.algd2.arraycollections;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import ch.fhnw.algd2.arraycollections.general.AbstractBagTest;
import ch.fhnw.algd2.arraycollections.general.ElementExistenceChecker;
import ch.fhnw.algd2.arraycollections.general.MemoryLeakChecker;

public class SortedBagTest extends AbstractBagTest {

	@Override
	protected AbstractArrayCollection<Integer> createIntegerCollection(int size) {
		return new SortedBag<Integer>(size);
	}

	@Override
	protected Integer[] getExpectedOrderFor(Integer[] values) {
		List<Integer> list = new LinkedList<Integer>(Arrays.asList(values));
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
		MemoryLeakChecker.removeHasNoMemoryLeak(new SortedBag<MemoryLeakChecker.TestObj>(20));
	}

	@Override
	protected void checkElementExistence(Comparable<Integer>[] numbers) {
		ElementExistenceChecker.inGivenOrder(Arrays.copyOf(numbers, numbers.length), bag);
	}
	
}
