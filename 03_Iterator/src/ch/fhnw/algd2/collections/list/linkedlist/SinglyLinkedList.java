package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class SinglyLinkedList<E> extends MyAbstractList<E> {
	// variable int modCount already declared by AbstractList<E>

	private int size = 0;
	private Node<E> first;

	private Node<E> last;

	//#Concurrent Modification (Iterator)
	private int modCount = 0;

	@Override
	public boolean add(E e) {
		if (e == null) throw new NullPointerException("null not allowed");
		Node<E> n = new Node<>(e);
		if (size == 0){
			first = n;
		} else if (size > 0) {
			last.next = n;
		}
		last = n;
		++size;
		++modCount;
		return true;
	}

	@Override
	public boolean contains(Object o) {
		if (o == null) throw new NullPointerException("null not allowed");
		Node<E> current = first;
		while (current != null && !(current.elem.equals(o))){
			current = current.next;
		}
		return current != null;
	}

	@Override
	public boolean remove(Object o) {
		if (o == null) throw new NullPointerException("null not allowed");
		if (!contains(o)) return false;
		Node<E> current = first;
		Node<E> previous = null;
		while (current != null && !(current.elem.equals(o))){
			previous = current;
			current = current.next;
		}
		if (first == current) {				//special case: first currentElement has to be removed
			first = current.next;
		} else if(last == current) {		//special case: last currentElement has to be removed
			previous.next = null;
			last = previous;
		} else {
			previous.next = current.next;	//redirect reference ([a]->[b]->[c] zu [a]->[c]: Überbrückt B.)
		}

		--size;
		++modCount;
		return true;
	}

	@Override
	public E get(int index) {
		if (index >= size || index < 0) throw new IndexOutOfBoundsException("invalid index: too low or high");
		if (size == 0)	throw new IndexOutOfBoundsException("empty list");			//case: empty list
		//Note: 'size !=  0' is guaranteed from here on.
		if (index == 0){ 			//case: get first currentElement.
			return first.elem;
		} else if (index == size - 1) {			//case: get last currentElement
			return last.elem;
		}
		//moving through the list
		Node<E> current = first;
		int i = 0;
		while (i != index) { //No guard such as 'i < size' or 'next!= null' needed. Valid index range already tested.
			current = current.next;
			++i;
		}
		return current.elem;
	}

	@Override
	public void add(int index, E element) {
		if (element == null) throw new NullPointerException("null not allowed");
		if (index > size || index < 0) throw new IndexOutOfBoundsException("Index too large or small");
		if (size == 0){						//case: empty list
			first = new Node<>(element);
			last = first;
			++size;
			++modCount;
			return;
		}
		else if (index == size) {			//case: adding as last currentElement
			last.next = new Node<>(element);
			last = last.next;
			++size;
			++modCount;
			return;
		}
		//moving through the list
		Node<E> current = first;
		Node<E> previous = null;
		int i = 0;
		while (i != index) {	//No guard such as 'next != null' needed. Valid index range already tested.
			previous = current;
			current = current.next;
			++i;
		}
		Node<E> n = new Node<>(element, current);
		if (i == 0){					//case: not empty list, but adding as first currentElement
			first = n;
		} else {						//case: adding in-between
			previous.next = n;
		}
		if (i == size - 1){
			last = current;
		}
		++size;
		++modCount;
	}

	//deletion: [A]->[B]->[C]-| ; [A]->[C]
	@Override
	public E remove(int index) {
		if (index >= size || index < 0) throw new IndexOutOfBoundsException("invalid index: too low or high");
		if (size == 0)	throw new IndexOutOfBoundsException("empty list");			//case: empty list
		//Note: 'size !=  0' is guaranteed from here on.
		if (index == 0){ 				//case: get first currentElement
			Node<E> oldFirst = first;
			first = first.next;
			--size;
			++modCount;
			return oldFirst.elem;
		}
		//moving through the list
		Node<E> current = first;
		Node<E> previous = null;
		int i = 0;
		while (i != index) {	//No guard such as 'next != null' or 'i < size' needed. Valid index range already tested.
			previous = current;
			current = current.next;
			++i;
		}
		previous.next = current.next; //removes currentElement at index
		if (i == size - 1) {			//case: get last currentElement
			//Note: previous.next will be automatically equal null.
			// 	Reason: 'previous = next' && 'previous.next = next.next', next is last elem. and 'next.next == null'
			last = previous; //last.next will be null because of 'previous.next == null';
		}
		--size;
		++modCount;
		return current.elem;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int index = 0;
		Node<E> current = first;
		while (current != null) {
			array[index++] = current.elem;
			current = current.next;
		}
		return array;
	}

	@Override
	public int size() {
		return size;
	}


	private static class Node<E> {
		private final E elem;
		private Node<E> next = null;

		private Node(E elem) {
			this.elem = elem;
		}

		private Node(E elem, Node<E> next) {
			this.elem = elem;
			this.next = next;
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new MyIterator();
	}

	private class MyIterator implements Iterator<E> {

		//#Concurrent Modification
		private int expectedModCount = modCount;

		//Access to data structure (the list)
		private Node<E> next = first; //Note: MyIterator sees members of outer class.
		private Node<E> previous = null, previousPrevious = null;

		@Override
		public boolean hasNext() {	return next != null;	}

		@Override
		public E next() {
			//Ensure has next
			if (!hasNext())	throw new NoSuchElementException("next == null");

			//Check Modification
			if(expectedModCount != modCount) throw new ConcurrentModificationException("Iterator outdated");

			//element removed
			if (previous != null){
				previousPrevious = previous;
			}
			previous = next;
			next = next.next;
			return previous.elem;
		}

		@Override
		public void remove() {
			// can not remove
			if (previous == null) 	throw new IllegalStateException("Can't remove yet. next() needed.");
			previous.next = null;

			//remove first element
			if (previousPrevious == null) {
				first = next;
			} else {
				previousPrevious.next = next; //umbiegen auf next.
			}

			// check if tail is removed
			if (previous == last ) last = previousPrevious;

			previous = null;	//zu löschendes Element
			++modCount;
			--size;
			++expectedModCount;
		}
	}

	public static void main(String[] args) {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(Arrays.toString(list.toArray()));
	}
}
