/*
 * Created on 23.02.2016
 */
package ch.fhnw.algd2.arraycollections.general;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Wolfgang Weck
 */
public class MemoryLeakChecker {
	/**
	 * Checks whether Collection c keeps references to elements that have been
	 * removed.
	 * 
	 * @param c
	 *          the Collection to investigate
	 */
	public static class TestObj implements Comparable<TestObj> {
		private int val;

		private TestObj(int val) {
			this.val = val;
		}

		@Override
		public int compareTo(TestObj o) {
			return val - o.val;
		}
	}

	private static WeakReference<TestObj> add(Collection<TestObj> c, int val) {
		TestObj o = new TestObj(val);
		assertTrue("could not add element, check add first", c.add(o));
		return new WeakReference<MemoryLeakChecker.TestObj>(o);
	}

	private static void remove(Collection<TestObj> c, WeakReference<TestObj> w) {
		assertTrue("element to remove not found", c.remove(w.get()));
		System.gc();
		assertTrue("removed element should not be referenced anymore",
				w.get() == null);
	}

	public static void removeHasNoMemoryLeak(Collection<TestObj> c) {
		int s = c.size();
		List<WeakReference<TestObj>> refs = new ArrayList<>();
		int i = 0;
		try {
			while (true) {
				refs.add(add(c, i));
				i++;
			}
		}
		catch (IllegalStateException e) {}
		assertEquals("size incorect after inserting " + i + " elements", s + i,
				c.size());
		assertTrue(
				"bad test configuration: this tests needs a collection with minimum capacity of 5",
				i >= 5);
		remove(c, refs.get(i - 1));
		remove(c, refs.get(0));
		remove(c, refs.get(2));
		remove(c, refs.get(i - 3));
		remove(c, refs.get(i - 2));
		refs.forEach(r -> {
			if (r.get() != null) {
				remove(c, r);
			}
		});
		assertEquals("size incorect after inserting and removing " + i
				+ " elements", s, c.size());
	}
}
