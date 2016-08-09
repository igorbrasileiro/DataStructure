package adt.linkedList;

import javax.swing.text.AbstractDocument.LeafElement;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;
	private DoubleLinkedListNode<T> nillNode = new DoubleLinkedListNode<T>();
	int size = 0;
	
	@Override
	public void insertFirst(T element) {
		
		if (isEmpty()) {
			insert(element);
		} else {
			DoubleLinkedListNode<T> newNode = new DoubleLinkedListNode<>(element, nillNode, nillNode);
			newNode.next = head;
			head = newNode;
		}
	}
	
	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			head = head.getNext();
		}
	}
	
	@Override
	public void removeLast() {
		if (!isEmpty()) {
			last = last.getPrevious();
			
			
			last.setNext(nillNode);
		}
	}

	@Override
	public T search(T element) {

		if (!isEmpty()) {

			SingleLinkedListNode<T> auxLeft = super.head;
			DoubleLinkedListNode<T> auxRight = last;

			while (!acabouLista(auxLeft, auxRight)) {
				if (auxLeft.getData().equals(element)) {
					return auxLeft.getData();
				}
				if (auxRight.getData().equals(element)) {
					return auxRight.getData();
				}
				auxLeft = auxLeft.next;
				auxRight = auxRight.previous;
			}
		}

		return null;
	}

	@Override
	public void insert(T element) {

		DoubleLinkedListNode<T> insertion = new DoubleLinkedListNode<T>(element, nillNode, nillNode);

		if (isEmpty()) {
			super.head = insertion;
			last = insertion;
		} else {
			last.next = insertion;
			last = insertion;
		}

		size += 1;
	}

	@Override
	public void remove(T element) {

		if (!isEmpty()) {

			if (head.getData().equals(element)) {

				head = head.next;
				size -= 1;

			} else {

				SingleLinkedListNode<T> auxLeft = head;
				DoubleLinkedListNode<T> auxRight = last;

				while (!acabouLista(auxLeft, auxRight) && !achouElemento(element, auxLeft, auxRight)) {
					auxLeft = auxLeft.next;
					auxRight = auxRight.previous;
				}

				if (!acabouLista(auxLeft, auxRight)) {
					if (auxLeft.getData().equals(element)) {
						auxRight = (DoubleLinkedListNode<T>) auxLeft;
					}

					auxRight.previous.next = auxRight.next;
					((DoubleLinkedListNode<T>) auxRight.getNext()).previous = auxRight.previous;

					size -= 1;
				}
			}
		}
	}

	private boolean acabouLista(SingleLinkedListNode<T> auxLeft, DoubleLinkedListNode<T> auxRight) {
		return auxRight.next.equals(auxLeft);
	}
	
	private boolean achouElemento(T element, SingleLinkedListNode<T> auxLeft, DoubleLinkedListNode<T> auxRight) {
		return auxLeft.getData().equals(element) || auxRight.getData().equals(element);
	}

}