package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends
RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data,
			RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, (RecursiveDoubleLinkedListImpl<T>) next);
		this.previous = previous;
	}

	@Override
	public void insertFirst(T element) {
		if(element != null) {
			// se lista vazia nil -> novoElemento <- nil
			if(isEmpty()) {
				data = element;	
				next = new RecursiveDoubleLinkedListImpl<T>(); // nill
				((RecursiveDoubleLinkedListImpl<T>) this.next).setPrevious(this); // ultimo seta o nil pra ele, quando for add proximo ja tem apontador pra previos
				if(previous == null) { // so ocorre na primeira vez
					previous = new RecursiveDoubleLinkedListImpl<>();
				}
			} else {
				RecursiveDoubleLinkedListImpl<T> novo = 
						new RecursiveDoubleLinkedListImpl<T>(data, this.next, this); //novo elemento copia dessa cabeca aponta next para next dele e previous aponta para ele mesmo, pra inserir data = element
				((RecursiveDoubleLinkedListImpl<T>) this.next).setPrevious(novo); // setei o previous do next para novo elemento, que é o atual cabeca, por enquanto
				// atribuir esse next a novo 
				this.next = novo;
				// pseudo novo elemento
				this.data = element;
			}
		}
	}

	@Override
	public void insert(T element) {
		if(element != null) {
			if(isEmpty()) {
				data = element;	
				next = new RecursiveDoubleLinkedListImpl<T>(); // nill
				((RecursiveDoubleLinkedListImpl<T>) this.next).setPrevious(this); // ultimo seta o nil pra ele, quando for add proximo ja tem apontador pra previos
				if(previous == null) { // so ocorre na primeira vez
					previous = new RecursiveDoubleLinkedListImpl<>();
				}
			} else {
				((RecursiveDoubleLinkedListImpl<T>) next).insert(element);
			}
		}
	}

	@Override
	public void removeFirst() {
		if(!isEmpty()) {
			// pega data do prox
			this.data = next.getData();
					
			if(!next.isEmpty()) {
				((RecursiveDoubleLinkedListImpl<T>) next).removeFirst();
			} else {
				// entao proximo. Logo esse sera novo NIL
				this.data = null;
				this.previous = null;
				this.next = null;
			}
		}
	}

	@Override
	public void removeLast() {
		if(!isEmpty()) {
			// se proximo nao for empty e por que nao e o ultimo
			if(!next.isEmpty()) {
				((RecursiveDoubleLinkedListImpl<T>) next).removeLast();
			} else {
				// pegar o previous e seta para o nil, que e o next dessa classe
				previous.setNext(this.next);
			}
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}
