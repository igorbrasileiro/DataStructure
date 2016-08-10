package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T> implements Queue<T> {

	
	protected DoubleLinkedList<T> list;
	protected int size;
	
	public QueueDoubleLinkedListImpl(int size) {
		this.size = size;
		this.list = new DoubleLinkedListImpl<T>();
	}
	
	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if(isFull()) {
			throw new QueueOverflowException();
		} else {
			if(element != null)	list.insert(element);
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		
		if(isEmpty()) {
			throw new QueueUnderflowException();
		} else {
			T result = head();
			return result;
		}
	}

	@Override
	public T head() {
		T result = list.toArray()[0];
		return result;
	}
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (list.size() == 0);
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return (list.size() == size);
	}

}
