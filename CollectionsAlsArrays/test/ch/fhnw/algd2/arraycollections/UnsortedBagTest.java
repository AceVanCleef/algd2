package ch.fhnw.algd2.arraycollections;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import ch.fhnw.algd2.arraycollections.general.AbstractBagTest;
import ch.fhnw.algd2.arraycollections.general.ElementExistenceChecker;
import ch.fhnw.algd2.arraycollections.general.MemoryLeakChecker;

public class UnsortedBagTest extends AbstractBagTest {
	@Override
	protected AbstractArrayCollection<Integer> createIntegerCollection(int size) {
		return new UnsortedBag<Integer>(size);
	}

	@Override
	protected Integer[] getExpectedOrderFor(Integer[] values) {
		return values;
	}

	@Test
	public void testLinearSearch(){
		Integer[] numbers = new Integer[]{6, 10, 8, 5, 1};
		addNumbersToBag(numbers);
		assertTrue(bag.contains(1));
	}
	
	@Test
	public void removeHasNoMemoryLeak() {
		MemoryLeakChecker.removeHasNoMemoryLeak(new UnsortedBag<MemoryLeakChecker.TestObj>(20));
	}

	@Override
	protected void checkElementExistence(Comparable<Integer>[] numbers) {
		ElementExistenceChecker.inRandomOrder(Arrays.copyOf(numbers, numbers.length), bag);
	}
}
