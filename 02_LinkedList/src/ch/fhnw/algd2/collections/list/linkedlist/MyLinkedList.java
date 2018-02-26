package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.Arrays;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class MyLinkedList<E> extends MyAbstractList<E> {
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
			last.next = n;
		}
		last = n;
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
		Node<E> current = first;
		Node<E> previous = null;
		while (current != null && !(current.elem.equals(o))){
			previous = current;
			current = current.next;
		}
		if (first == current) {				//special case: first element has to be removed
			first = current.next;
		} else if(last == current) {		//special case: last element has to be removed
			previous.next = null;
			last = previous;
		} else {
			previous.next = current.next;	//redirect reference
		}

		--size;
		return true;
	}

	@Override
	public E get(int index) {
		// TODO implement this operation (part D)
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E element) {
		// TODO implement this operation (part D)
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		// TODO implement this operation (part D)
		throw new UnsupportedOperationException();
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
		private Node<E> next;
		//Lesson learned: private fields of static inner classes are visible within outer class.

		private Node(E elem) {
			this.elem = elem;
		}

		private Node(E elem, Node<E> next) {
			this.elem = elem;
			this.next = next;
		}
	}

	public static void main(String[] args) {
		MyLinkedList<Integer> list = new MyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(Arrays.toString(list.toArray()));
	}
}
