package ch.fhnw.algd2.arraycollections;

import java.util.Arrays;

public class SortedBag<E extends Comparable<? super E>> extends
		AbstractArrayCollection<E> {
	public static final int DEFAULT_CAPACITY = 100;
	private Object[] data;

	public SortedBag() {
		this(DEFAULT_CAPACITY);
	}

	public SortedBag(int capacity) {
		data = new Object[capacity];
	}

	@Override
	public boolean add(E e) {
		// TODO implement unless collection shall be immutable
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		// TODO implement unless collection shall be immutable
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object o) {
		// TODO must be implemented
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(data, size());
	}

	@Override
	public int size() {
		// TODO must be implemented
		return 0;
	}

	public static void main(String[] args) {
		SortedBag<Integer> bag = new SortedBag<Integer>();
		bag.add(2);
		bag.add(1);
		System.out.println(Arrays.toString(bag.toArray()));
	}
}
