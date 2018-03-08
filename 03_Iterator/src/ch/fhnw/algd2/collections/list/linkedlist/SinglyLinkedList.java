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
	private int generationCounter = 0;

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
		++generationCounter;
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
		++generationCounter;
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
			++generationCounter;
			return;
		}
		else if (index == size) {			//case: adding as last currentElement
			last.next = new Node<>(element);
			last = last.next;
			++size;
			++generationCounter;
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
		++generationCounter;
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
			++generationCounter;
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
		++generationCounter;
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
		private int generationNumber = generationCounter;

		//Access to data structure (the list)
		private Node<E> next = first; //Note: MyIterator sees members of outer class.
		E currentElement;
		private boolean mayRemove = false;
		private boolean alreadyMoved = false;

		@Override
		public boolean hasNext() {
			return next != null;		//Frage: next oder next.next?
		}

		@Override
		public E next() {
			//Todo: trying to fix jumping issue after using .remove()
			/*if (alreadyMoved){
				alreadyMoved = false;
				return currentElement;
			}*/
			if(next == null) throw new NoSuchElementException("No next currentElement available");
			if(generationNumber != generationCounter) throw new ConcurrentModificationException("Iterator outdated");
			currentElement = next.elem;
			next = next.next;
			mayRemove = true;
			return currentElement;
		}

		@Override
		public void remove() {
			if(generationNumber != generationCounter) throw new ConcurrentModificationException("Iterator outdated");
			if (currentElement == null) throw new IllegalStateException("Before First Next Call not allowed");
			if (!mayRemove) throw new IllegalStateException("removed has already been called once without .next() in-between.");
			SinglyLinkedList.this.remove(currentElement);
			if (next != null){		//prevents NullPointerExc at last element.
				currentElement = next.elem;
				next = next.next;
				//alreadyMoved = true;
			}
			mayRemove = false;
			++generationNumber;
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
