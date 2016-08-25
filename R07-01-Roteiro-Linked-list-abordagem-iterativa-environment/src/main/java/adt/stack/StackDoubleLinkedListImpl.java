package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class StackDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> list;
	protected int size;

	public StackDoubleLinkedListImpl(int size) {
		this.size = size;
		this.list = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		// TODO Auto-generated method stub
		if(isFull()) {
			throw new StackOverflowException();			
		} else {
			if(element != null) list.insert(element);
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if(isEmpty()) {
			throw new StackUnderflowException();
		} else {
			T result = top();
			list.removeLast();
			return result;
		}
		
	}

	@Override
	public T top() {
		return list.toArray()[list.size()-1];
	}

	@Override
	public boolean isEmpty() {
		return (list.size() == 0);
	}

	@Override
	public boolean isFull() {
		return (list.size() == size);
	}

}
