package adt.linkedList;

import javax.swing.text.AbstractDocument.LeafElement;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;
	int size = 0;
	
	@Override
	public void insertFirst(T element) {
		
		if (isEmpty()) {
			head = (DoubleLinkedListNode<T>) new DoubleLinkedListNode<T>(element, new DoubleLinkedListNode<T>(), new DoubleLinkedListNode<T>());
			last = (DoubleLinkedListNode<T>) head;
			size++;
		} else {
			DoubleLinkedListNode<T> auxHead = (DoubleLinkedListNode<T>) head;
			head = new DoubleLinkedListNode<T>(element, auxHead, auxHead.getPrevious());
			
			auxHead.setPrevious((DoubleLinkedListNode<T>) head);
		}
	}
	
	@Override
	public void insert(T element) {
		// no final
		if(element == null) return;
		// se cabeca e null, cria nova cabeca com element, passando o nil e seta nova cabeca com cabeca;
		if(head.isNIL()) {
			DoubleLinkedListNode<T> newHead = new DoubleLinkedListNode<T>(element, new DoubleLinkedListNode<T>(), new DoubleLinkedListNode<T>());
			head = newHead;
			last = (DoubleLinkedListNode<T>) head;
		} else {
			DoubleLinkedListNode<T> auxLast = last;
			last = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) auxLast.getNext(), auxLast);
			auxLast.setNext(last);
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
			DoubleLinkedListNode<T> auxLast = last;
			last = last.getPrevious();
			last.setNext((DoubleLinkedListNode<T>) auxLast.getNext());
		}
	}

	@Override
	public T search(T element) {

		if (!isEmpty()) {

			DoubleLinkedListNode<T> auxLeft = (DoubleLinkedListNode<T>) head;
			DoubleLinkedListNode<T> auxRight = last;

			while (!(auxLeft.getPrevious().equals(auxRight) || auxLeft.equals(auxRight))) {
				if (auxLeft.getData().equals(element)) {
					return auxLeft.getData();
				}
				if (auxRight.getData().equals(element)) {
					return auxRight.getData();
				}
				auxLeft = (DoubleLinkedListNode<T>) auxLeft.next;
				auxRight = auxRight.previous;
			}
			
			// case aux left == aux right
			if(auxLeft.getData().equals(element)) return auxLeft.getData();
		}

		return null;
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