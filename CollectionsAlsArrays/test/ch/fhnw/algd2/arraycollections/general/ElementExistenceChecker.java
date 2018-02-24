package ch.fhnw.algd2.arraycollections.general;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ch.fhnw.algd2.arraycollections.AbstractArrayCollection;

public class ElementExistenceChecker {

	public static void inGivenOrder(Comparable<Integer>[] numbers,
			AbstractArrayCollection<Integer> bag) {
		Object[] bagContent = bag.toArray();
		assertEquals(numbers.length, bagContent.length);
		for (int i = 0; i < numbers.length; i++) {
			assertEquals(numbers[i], bagContent[i]);
		}
	}

	public static void inRandomOrder(Comparable<Integer>[] numbers,
			AbstractArrayCollection<Integer> bag) {
		Object[] bagContent = bag.toArray();
		assertEquals(numbers.length, bagContent.length);
		checkNoNullValues(bagContent);
		// Assume that all values can be set to null (all exist)
		for (int i = 0; i < numbers.length; i++) {
			assertTrue(setSingleValueToNullInArray(numbers[i], bagContent));
		}

		// Check that complete array was set to null
		assertAllValuesNull(bagContent);
	}

	private static boolean setSingleValueToNullInArray(Comparable<Integer> key,
			Object[] bagContent) {
		int i = 0;
		// call method on key because bagContent is Nulled
		while (i < bagContent.length && !key.equals(bagContent[i]))
			i++;
		if (i < bagContent.length) {
			bagContent[i] = null;
			return true;
		} else
			return false;
	}

	private static void assertAllValuesNull(Object[] bagContent) {
		for (int i = 0; i < bagContent.length; i++) {
			assertTrue(bagContent[i] == null);
		}
	}

	private static void checkNoNullValues(Object[] bagContent) {
		for (int i = 0; i < bagContent.length; i++) {
			assertTrue(bagContent[i] != null);
		}
	}

}
