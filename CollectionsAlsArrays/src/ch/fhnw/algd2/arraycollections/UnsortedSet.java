package ch.fhnw.algd2.arraycollections;

import java.util.Arrays;
import java.util.Set;

public class UnsortedSet<E> extends AbstractArrayCollection<E> implements
		Set<E> {
	public static final int DEFAULT_CAPACITY = 100;
	private Object[] data;

	private int capacity;
	private int size = 0;

	public UnsortedSet() {
		this(DEFAULT_CAPACITY);
	}

	public UnsortedSet(int capacity) {
		data = new Object[capacity];
		this.capacity = capacity;
	}

	@Override
	public boolean add(E e) {
		//Bag: O(1). Set: O(n).
		if (e == null) throw new NullPointerException("null not supported.");
		//#Difference-to-bag:
		if (contains(e)) return false;	// O(n)!
		if (size < capacity){
			data[size++] = e;			// O(1).
			return true;
		} else {
			throw new IllegalStateException("Collection is full");
		}
	}

	@Override
	public boolean remove(Object o) {
		int i = indexOf(o);				// search: O(n).
		//contains:
		if (i < 0) return false;
		else {
			data[i] = data[size - 1];	//[][][i][][][last]: last goes to [i]. remove: O(1)!
			data[size - 1] = null;
			size--;
			return true;
		}
		/*System.out.println(Arrays.toString(data) + " before");

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
		return false;*/
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) >= 0;	// linear search: O(n).
	}

	private int indexOf(Object o) {
		if (o == null) throw new NullPointerException();
		int i = size - 1;
		//backwards linear search: O(n).
		while (i >= 0 && !data[i].equals(o)) {
			i--;
		}
		return i;
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
		UnsortedSet<Integer> bag = new UnsortedSet<Integer>();
		bag.add(2);
		bag.add(2);
		bag.add(1);
		System.out.println(Arrays.toString(bag.toArray()));
	}
}
