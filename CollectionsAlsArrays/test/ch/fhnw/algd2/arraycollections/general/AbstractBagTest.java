package ch.fhnw.algd2.arraycollections.general;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public abstract class AbstractBagTest extends AbstractCollectionTest {

	@Override
	protected void addNumbersToBag(Integer[] numbers) {
		for (Integer number : numbers) {
			assertTrue(bag.add(number));
		}
	}

	@Test
	public void removesOnlyOneInstance(){
		Integer[] numbers = new Integer[3];
		numbers[0] = new Integer(1);
		numbers[1] = new Integer(1);
		numbers[2] = new Integer(1);
		addNumbersToBag(numbers);
		assertTrue(bag.remove(new Integer(1)));
		assertEquals(bag.size(),2);
		assertTrue(bag.contains(new Integer(1)));
	}

}
