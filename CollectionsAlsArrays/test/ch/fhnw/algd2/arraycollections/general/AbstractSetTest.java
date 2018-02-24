package ch.fhnw.algd2.arraycollections.general;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public abstract class AbstractSetTest extends AbstractCollectionTest {
	@Override
	protected void addNumbersToBag(Integer[] numbers) {
		Set<Integer> alreadyAdded = new HashSet<Integer>();
		for (Integer number : numbers) {
			if (alreadyAdded.contains(number)) {
				assertFalse(bag.add(number));
			} else {
				assertTrue(bag.add(number));
				alreadyAdded.add(number);
			}
		}
	}

	@Test
	public void addExistingElementToFullSet() {
		int i = 0;
		try {
			while (true)
				assertTrue(
						"Inserting a not yet contained element must not result in return value false",
						bag.add(i++));
		}
		catch (IllegalStateException e) {}
		assertTrue("bag capacity needs to exceeed 2", bag.size() > 2);
		assertFalse(
				"adding an already contained element to a set must always result in return value false",
				bag.add(2));
	}
}
