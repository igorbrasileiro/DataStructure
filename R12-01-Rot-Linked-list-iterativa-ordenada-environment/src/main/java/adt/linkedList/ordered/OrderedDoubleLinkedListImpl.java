package adt.linkedList.ordered;

import java.util.Comparator;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListNode;

/**
 * Para testar essa classe voce deve implementar seu comparador. Primeiro
 * implemente todos os mÃ©todos requeridos. Depois implemente dois comparadores
 * (com idÃ©ias opostas) e teste sua classe com eles. Dependendo do comparador
 * que vocÃª utilizar a lista funcionar como ascendente ou descendente, mas a
 * implemntaÃ§Ã£o dos mÃ©todos Ã© a mesma.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class OrderedDoubleLinkedListImpl<T> extends OrderedSingleLinkedListImpl<T> implements OrderedLinkedList<T>,
      DoubleLinkedList<T> {

   private DoubleLinkedListNode<T> previous;

   public OrderedDoubleLinkedListImpl() {
      super(); // pegar comparator
      head = new DoubleLinkedListNode<>();
      previous = new DoubleLinkedListNode<>();
   }

   public OrderedDoubleLinkedListImpl(Comparator<T> comparator) {
      super(comparator);
      head = new DoubleLinkedListNode<>();
      previous = new DoubleLinkedListNode<>();
   }

   /**
    * Este mÃ©todo faz sentido apenas se o elemento a ser inserido pode 
    * realmente ficar na primeira posiÃ§Ã£o (devido a ordem)
    */
   @Override
   public void insertFirst(T element) {
      if (element != null) {
         if (isEmpty()) { // nil - head/previous - nil
            DoubleLinkedListNode<T> novoNode = new DoubleLinkedListNode<T>(element, previous,
                  (DoubleLinkedListNode<T>) head);
            head = novoNode;
            previous = novoNode;
         } else { // nil - novo elemento - head 
            DoubleLinkedListNode<T> novoNode = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) head,
                  ((DoubleLinkedListNode<T>) head).getPrevious());
            ((DoubleLinkedListNode<T>) head).setPrevious(novoNode); // novo elemento <- head
            head = novoNode; // nova head = novo elemento
         }
      }
   }

   @Override
   public void insert(T element) {
      if (element != null) {
         if (isEmpty()) {
            insertFirst(element);
         } else if (comparador(element, head.getData())) { // menor do que head
            insertFirst(element);
         } else { // maior do que o primeiro
            DoubleLinkedListNode<T> auxNode = (DoubleLinkedListNode<T>) head; // pega depois de cabeca

            while (!auxNode.getNext().isNIL()) {
               if (comparador(element, auxNode.getData())) {
                  break;
               }
               auxNode = (DoubleLinkedListNode<T>) auxNode.getNext();
            }
            // obs na exclamacao pq la retorna o1 < o2, porem quero o1 > o2
            if (!comparador(element, auxNode.getData())) { // add no ultimo (node - novo elemento - nil)
               DoubleLinkedListNode<T> novoNode = new DoubleLinkedListNode<T>(element,
                     (DoubleLinkedListNode<T>) auxNode.getNext(), auxNode);
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
   public void removeFirst() { // nil -  head - prox
	  if(!isEmpty()) {
		  ((DoubleLinkedListNode<T>) head.getNext()).setPrevious(((DoubleLinkedListNode<T>) head).getPrevious());
		   head = head.getNext();
	  }
   }

   @Override
   public void removeLast() {
	   if (!isEmpty()) {
			previous.getPrevious().setNext(previous.getNext());
			previous = previous.getPrevious();
		}
   }
   
   private boolean comparador(T element, T auxNode) {
		return getComparator().compare(element, auxNode) < 0;
	}

}
