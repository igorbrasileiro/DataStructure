package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> list;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		list = new RecursiveDoubleLinkedListImpl<>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if(!isFull()) {
			list.insert(element);
		} else {
			throw new StackOverflowException();
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if(!isEmpty()) {
			T retorno = top();
			list.removeLast();
			return retorno;
			
		} else {
			throw new StackUnderflowException();
		}
		
	}

	@Override
	public T top() {
		T retorno = null;
		if(list.size() > 0) {
			retorno = list.toArray()[list.size()-1];
		} 
		
		return retorno;
	}

	@Override
	public boolean isEmpty() {
		// TODO Implement the method
		return (list.size() == 0);
	}

	@Override
	public boolean isFull() {
		// TODO Implement the method
		return (list.size() == size);
		
	}

}
