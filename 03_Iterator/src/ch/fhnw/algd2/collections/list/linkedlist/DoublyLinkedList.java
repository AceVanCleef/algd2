package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class DoublyLinkedList<E> extends MyAbstractList<E> {
	// variable int modCount already declared by AbstractList<E>
	private int size = 0;
	private Node<E> first;

	private Node<E> last;

	@Override
	public boolean add(E e) {
		if (e == null) throw new NullPointerException("null not allowed");
		Node<E> n = new Node<>(e);
		if (size == 0){
			first = n;
		} else if (size > 0) {
			n.prev = last;  //[last]<-[n]
			last.next = n;	//[last]<->[n]
		}
		last = n;			//[n-1]<->[n, last]
		++size;
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
		/*if(last.elem.equals(o)) {		//special case: last currentElement has to be removed
			//reduces asympt. complexity to O(1) instead of O(n)
			last = last.prev;
			last.next = null;
			--size;
			return true;
		}*/
		Node<E> current = first;
		Node<E> previous = null;
		while (current != null && !(current.elem.equals(o))){
			previous = current;
			current = current.next;
		}
		if (first == current) {				//special case: first currentElement has to be removed
			first = current.next;
		} else if(last == current) {        //special case: last currentElement has to be removed
			//Note: can't put this section before the linear search.
			// Reason: In [1,2,3,4,5,2], delete(new Integer(2)) would delete the 2 at index 5.
			//			What should happen is that the 2 at index 2 gets deleted first acc. to unit tests.
			//#Question: Why?

			last = last.prev;
			last.next = null;
		} else {
			previous.next = current.next;	//redirect next reference ([a]->[b]->[c] to [a]->[c]: Überbrückt B.)
			current.next.prev = current.prev; //redirect prev reference [a[<-[b]<-[c] to [a]<-[c]
		}
		--size;
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
		while (i != index) { //No guard such as 'i < size' or 'current!= null' needed. Valid index range already tested.
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
			return;
		}
		else if (index == size) {			//case: adding as last currentElement
			last.next = new Node<>(last, element, null); 	//[last]<->[n]-|
			last = last.next;	//[n-1]<->[n, last]-|
			++size;
			return;
		}
		//moving through the list
		Node<E> current = first;
		Node<E> previous = null;
		int i = 0;
		while (i != index) {	//No guard such as 'current != null' needed. Valid index range already tested.
			previous = current;
			current = current.next;
			++i;
		}
		Node<E> n = new Node<>(previous, element, current);		//[previous]<-[n]->[current]
		if (i == 0){					//case: not empty list, but adding as first currentElement
			first = n;
		} else {						//case: adding in-between
			previous.next = n;	//[previous]<->[n]->[current]
			current.prev = n;		//[previous]<->[n]<->[current]
		}
		if (i == size - 1){
			last = current;
		}
		++size;
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
			return oldFirst.elem;
		}
		if (index == size - 1){
			//reduces asympt. complexity to O(1) instead of O(n)
			Node<E> oldLast = last;
			last = last.prev;
			last.next = null;
			--size;
			return oldLast.elem;
		}
		//moving through the list
		Node<E> current = first;
		Node<E> previous = null;
		int i = 0;
		while (i != index) {	//No guard such as 'current != null' or 'i < size' needed. Valid index range already tested.
			previous = current;
			current = current.next;
			++i;
		}
		//remove currentElement at index
		previous.next = current.next; //redirect next reference ([a]->[b]->[c] to [a]->[c]: Überbrückt B.)
		current.next.prev = previous; //redirect prev reference [a[<-[b]<-[c] to [a]<-[c]
		--size;
		return current.elem;
	}

	@Override
	public Object[] toArray() {
		return arrayForDoublyLinkedList();
		// return arrayForCyclicDoublyLinkedList();
	}

	private Object[] arrayForDoublyLinkedList() {
		Object[] array = new Object[size];
		int index = 0;
		Node<E> current = first;
		while (current != null) {
			array[index++] = current.elem;
			current = current.next;
		}
		return array;
	}

	private Object[] arrayForCyclicDoublyLinkedList() {
		Object[] array = new Object[size];
		int index = 0;
		Node<E> current = first.next;
		while (current != first) {
			array[index++] = current.elem;
			current = current.next;
		}
		return array;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return new MyListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException("Implement later");
	}

	private static class Node<E> {
		private E elem;
		private Node<E> prev, next;

		private Node(E elem) {
			this.elem = elem;
		}

		private Node(Node<E> prev, E elem, Node<E> next) {
			this.prev = prev;
			this.elem = elem;
			this.next = next;
		}
	}

	private class MyListIterator implements Iterator<E> {
		@Override
		public boolean hasNext() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E next() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(Arrays.toString(list.toArray()));
	}
}
