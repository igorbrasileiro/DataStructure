package adt.linkedList.ordered;

import java.util.Comparator;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListNode;
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
public class OrderedDoubleLinkedListImpl<T> extends OrderedSingleLinkedListImpl<T> implements
OrderedLinkedList<T>,DoubleLinkedList<T> {

	private DoubleLinkedListNode<T> previous;

	public OrderedDoubleLinkedListImpl() {
		head = new DoubleLinkedListNode<>();
		previous = new DoubleLinkedListNode<>();
	}

	public OrderedDoubleLinkedListImpl(Comparator<T> comparator) {
		super(comparator);
		head = new DoubleLinkedListNode<>();
		previous = new DoubleLinkedListNode<>();
	}

	/**
	 * Este método faz sentido apenas se o elemento a ser inserido pode 
	 * realmente ficar na primeira posição (devido a ordem)
	 */
	@Override
	public void insertFirst(T element) {
		if(element != null) {
			if(isEmpty()) { // nil - head/previous - nil
				DoubleLinkedListNode<T> novoNode = new DoubleLinkedListNode<T>(element, previous, (DoubleLinkedListNode<T>) head);
				head = novoNode;
				previous = novoNode;
			} else { // nil - novo elemento - head 
				DoubleLinkedListNode<T> novoNode = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) head, ((DoubleLinkedListNode<T>) head).getPrevious());
				((DoubleLinkedListNode<T>) head).setPrevious(novoNode); // novo elemento <- head
				head = novoNode; // nova head = novo elemento
			}
		}
	}

	@Override
	public void insert(T element) {
		if(element != null) {
			if(isEmpty()) { 
				insertFirst(element);
			} else if(getComparator().compare(element, head.getData()) < 0) { // menor do que head
				insertFirst(element);
			} else { // maior do que o primeiro
				DoubleLinkedListNode<T> auxNode = (DoubleLinkedListNode<T>) head; // pega depois de cabeca

				while(!auxNode.getNext().isNIL()) {
					if(getComparator().compare(element, auxNode.getData()) < 0) {
						break;
					}
					auxNode = (DoubleLinkedListNode<T>) auxNode.getNext();	
				}

				if(getComparator().compare(element, auxNode.getData()) > 0) { // add o ultimo (node - novo elemento - nil)
					DoubleLinkedListNode<T> novoNode = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) auxNode.getNext(), auxNode);
					auxNode.setNext(novoNode);
					previous = novoNode;
				} else { // add posicao menor que ultimo ( previous auxnode - novo node - auxnode ) 
					DoubleLinkedListNode<T> novoNode = new DoubleLinkedListNode<T>(element, auxNode, auxNode.getPrevious());
					auxNode.getPrevious().setNext(novoNode); // previous auxnode -> novo node
					auxNode.setPrevious(novoNode); // novo node <- auxnode
				}


			}
		}
	}

	@Override
	public void removeFirst() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public void removeLast() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

}
