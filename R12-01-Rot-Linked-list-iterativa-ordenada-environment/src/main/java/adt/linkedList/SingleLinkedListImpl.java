package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;
	
	
	
	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if(head.isNIL()) return true;
		
		return false;
	}

	@Override
	public int size() {
		int result = 0;
		
		if(isEmpty()) return result;
		
		SingleLinkedListNode<T> aux = head;
		
		while(!aux.isNIL()) {
			result++;
			aux = aux.getNext();
		}
		
		return result;
	}

	@Override
	public T search(T element) {
		T result = null;
		
		if(isEmpty()) return result;
		
		SingleLinkedListNode<T> auxHead = head;
		// enquanto proximo elemento seja diferente de null
		while(!auxHead.isNIL()) {
			if(auxHead.getData().equals(element)) {
				result = auxHead.getData(); 
				break;
			}
			auxHead = auxHead.getNext();
		}
		
		return result;
	}

	@Override
	public void insert(T element) {
		// TODO Auto-generated method stub
		
		if(element != null) return;
		SingleLinkedListNode<T> auxHead = head;
		// se cabeca e null, cria nova cabeca com element, passando o nil e seta nova cabeca com cabeca;
		if(head.isNIL()) {
			SingleLinkedListNode<T> newHead = new SingleLinkedListNode<T>(element, head);
			head = newHead;
		} else {
			// procura ate achar o ultimo elemento que anteceda nil
			while(!auxHead.getNext().isNIL()) {
				auxHead = auxHead.getNext();
			}
			// crio o elemento nov e passo o next do aux, que e nill
			SingleLinkedListNode<T> newHead = new SingleLinkedListNode<>(element, auxHead.getNext());
			// subistituo o proximo de auxhead pelo novo elemento criado
			auxHead.setNext(newHead);
		}
	}

	@Override
	public void remove(T element) {
		
		if(head.getData().equals(element)) head = head.getNext();
		
		else {
			SingleLinkedListNode<T> auxNext = head;
			SingleLinkedListNode<T> auxPrevious = null;
			// procura até nó seja antes do nill e se valor diferente do elemento recebido, caso ache para
			while(!auxNext.isNIL() && !auxNext.getData().equals(element)) {
				auxPrevious = auxNext;
				auxNext = auxNext.getNext();
			}
			// seta o next do no anterior como proximo valor, o do meio e removido, ja que nao tem apontador
			if(!auxNext.isNIL()) {
				// o anterior ao escolhido, que é previous, recebe o auxNext.next, por que next e o elemento a remover
				auxPrevious.setNext(auxNext.getNext());
			}
		}
	}
	@Override
	public T[] toArray(){
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[size()];
		
		SingleLinkedListNode<T> aux = head;
		int index = 0;
		while(!aux.isNIL()) {
			result[index] = aux.getData();
			aux = aux.getNext();
			index++;
		}
		
		return result;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

	
}
