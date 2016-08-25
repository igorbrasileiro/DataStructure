package adt.linkedList.ordered;

import java.util.Comparator;

import adt.linkedList.SingleLinkedListImpl;
import adt.linkedList.SingleLinkedListNode;

/**
 * Para testar essa classe voce deve implementar seu comparador. Primeiro
 * implemente todos os métodos requeridos. Depois implemente dois comparadores
 * (com idéias opostas) e teste sua classe com eles. Dependendo do comparador
 * que você utilizar a lista funcionar como ascendente ou descendente, mas a
 * implemntação dos métodos é a mesma.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class OrderedSingleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
OrderedLinkedList<T> {

	private Comparator<T> comparator;

	public OrderedSingleLinkedListImpl() {
		// fazer o que?
		this.comparator = new Comparator<T>() {

			@Override
			public int compare(T o1, T o2) {
				return ((Comparable<T>) o1).compareTo(o2);
			}
		};
		this.head = new SingleLinkedListNode<T>();
	}

	public OrderedSingleLinkedListImpl(Comparator<T> comparator) {
		this.comparator = comparator;
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public T minimum() {
		if(head.isNIL()) {
			return null;
		} 

		return head.getData();
	}

	@Override
	public T maximum() {
		if(head.isNIL()){
			return null;
		} else {
			SingleLinkedListNode<T> auxNode = head;
			while(!auxNode.getNext().isNIL()) {
				auxNode = auxNode.getNext();
			}

			return auxNode.getData();
		}
	}

	@Override
	public void insert(T element) {
		if(element != null) {
			if(isEmpty()) { // head sempre iniciado nil
				head = new SingleLinkedListNode<T>(element, head);
			} else if(comparador(element, head.getData())) {//insere no comeco
				// menor do que cabeca
					SingleLinkedListNode<T> newNode = new SingleLinkedListNode<T>(element,head);
					head = newNode;						
			} else { // mais de 1 elemento
				SingleLinkedListNode<T> auxNodePrev = head;
				SingleLinkedListNode<T> auxNode = head.getNext();
				while(!auxNode.isNIL()) {
					if(comparador(element, auxNode.getData())) { // maior que node e menor que o proximo de node
						break;
					} else {
						auxNodePrev = auxNode;
						auxNode = auxNode.getNext();
					}
				}
				// novo elemento com pega o proximo do node
				SingleLinkedListNode<T> newNode = new SingleLinkedListNode<T>(element, auxNode);
				auxNodePrev.setNext(newNode);
			}
		}
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	private boolean comparador(T element, T auxNode) {
		return getComparator().compare(element, auxNode) < 0;
	}
}
