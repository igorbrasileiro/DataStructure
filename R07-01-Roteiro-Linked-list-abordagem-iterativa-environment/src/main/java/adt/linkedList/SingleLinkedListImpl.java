package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;
	protected int size;
 
	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<>(null, new SingleLinkedListNode<T>());
		this.size = 0;
	}

	@Override
	public boolean isEmpty() {
		if(size() == 0){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> node = getHead();

		while(!node.isNIL()){
			if(node.getData().equals(element)){
				return node.getData();
			}
			node = node.getNext();
		}

		return null;
	}

	@Override
	public void insert(T element) {

		SingleLinkedListNode<T> node = getHead();

		while(!node.isNIL()){
			node = node.getNext();
		}

		node.setData(element);
		node.setNext(new SingleLinkedListNode<T>());
		size++;
	}

	@Override
	public void remove(T element) {
		SingleLinkedListNode<T> node = getHead();
		SingleLinkedListNode<T> previous = getHead();

		if(!head.isNIL() && head.getData().equals(element)){
			head = head.getNext();
			size--;
			return;
		}

		while(!node.isNIL()){

			if(node.getData().equals(element)){
				previous.setNext(node.getNext());
				size--;
				return;
			}

			previous = node;
			node = node.getNext();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray(){

		SingleLinkedListNode<T> node = getHead();
		int sizeList = size();
		T[] array = (T[]) new Object[sizeList];
		int arrayIndex = 0;

		if(isEmpty()){
			return array;
		}

		while(!node.isNIL()){
			array[arrayIndex++] = node.getData();
			node = node.getNext();
		}

		return array;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
