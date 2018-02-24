package ch.fhnw.algd2.arraycollections;

import java.util.Arrays;

public class SortedBag<E extends Comparable<? super E>> extends
		AbstractArrayCollection<E> {
	public static final int DEFAULT_CAPACITY = 100;
	private Object[] data;

	private int capacity;
	private int size = 0;

	public SortedBag() {
		this(DEFAULT_CAPACITY);
	}

	public SortedBag(int capacity) {
		data = new Object[capacity];
		this.capacity = capacity;
	}

	@Override
	public boolean add(E e) {
		if (e == null) throw new NullPointerException("null not supported.");
		if (size < capacity){
			data[size++] = e;
			return true;
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public boolean remove(Object o) {
		System.out.println(Arrays.toString(data) + " before");

		if (contains(o)){
			int i = 0;
			//Lücke finden
			while (i < size && !data[i].equals(o)){
				i++;
			}
			//nachrücken:
			for (int j = i + 1; j < size; j++){
				System.out.println("length: " + data.length + " size:  " + size);
				data[j - 1] = data[j]; //adv. over data[j] = data[j + 1] -> no IndexOutOfBoundExc.
				//Semantisch: j = i + 1 -> Element, welches nachgerückt werden soll; j - 1 -> Lücke.
			}
			data[size - 1] = null;	//remove
			size--;
			System.out.println(Arrays.toString(data) + " after");

			return true;
		}
		return false;
	}

	@Override
	public boolean contains(Object o) {
		for (int i = 0; i < size; i++){
			if (data[i].equals(o)) return true;
		}
		return false;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(data, size());
	}

	@Override
	public int size() {
		System.out.println("length: " + data.length + " size:  " + size);
		return size;
	}

	public static void main(String[] args) {
		SortedBag<Integer> bag = new SortedBag<Integer>();
		bag.add(2);
		bag.add(1);
		System.out.println(Arrays.toString(bag.toArray()));
	}
}
