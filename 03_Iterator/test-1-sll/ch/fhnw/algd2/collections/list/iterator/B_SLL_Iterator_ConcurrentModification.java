package ch.fhnw.algd2.collections.list.iterator;

import ch.fhnw.algd2.collections.list.MyAbstractList;
import ch.fhnw.algd2.collections.list.linkedlist.SinglyLinkedList;

public class B_SLL_Iterator_ConcurrentModification extends
		Abstract_B_ListIterator_ConcurrentModification {

	@Override
	protected MyAbstractList<Integer> createInstance() {
		return new SinglyLinkedList<Integer>();
	}

}
