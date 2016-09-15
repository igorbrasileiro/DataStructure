
package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;
	protected final DoubleLinkedListNode<T> nil = new DoubleLinkedListNode<T>();

	public DoubleLinkedListImpl(){
		super();
		setHead(nil);
		setLast(nil);
	}

	@Override
	public void insertFirst(T element) {

		if(element == null){
			return;
		}


		if(isEmpty()) {
			DoubleLinkedListNode<T> newNode = new DoubleLinkedListNode<>(element, nil, nil);
			setHead(newNode);
			setLast(newNode);
			super.size++;
			return;
		}

		DoubleLinkedListNode<T> oldHead = (DoubleLinkedListNode<T>) this.head;
		DoubleLinkedListNode<T> newHead = new DoubleLinkedListNode<>(element, oldHead, nil);

		oldHead.setPrevious(newHead);
		setHead(newHead);

		super.size++;
	}

	@Override
	public void removeFirst() {
		if(!isEmpty()){
			DoubleLinkedListNode<T> node = (DoubleLinkedListNode<T>) head.getNext();
			node.setPrevious(new DoubleLinkedListNode<T>());
			head = node;
			super.size--;
		}
		if(size() == 0){
			last = (DoubleLinkedListNode<T>) head;
		}
	}

	@Override
	public void removeLast() {
		if(!isEmpty()){
			DoubleLinkedListNode<T> node = last.getPrevious();
			node.setNext(new DoubleLinkedListNode<T>());
			last = node;
			super.size--;
		}
		if(size() == 0){
			head = last;
		}
	}

	@Override
	public void insert(T element){
		if(element == null){
			return;
		}

		DoubleLinkedListNode<T> newLast = new DoubleLinkedListNode<>(element, nil, last);
		last.setNext(newLast);
		setLast(newLast);

		if(isEmpty()){
			setHead(newLast);
		}

		super.size++;
	}

	@Override
	public void remove(T element){
		DoubleLinkedListNode<T> node = (DoubleLinkedListNode<T>) getHead();

		while(!node.isNIL()){
			if(node.getData().equals(element)){
				if(node == head) removeFirst();
				else if(node == last) removeLast();
				else{
					DoubleLinkedListNode<T> nodePrevious = node.getPrevious();
					DoubleLinkedListNode<T> nodeNext = (DoubleLinkedListNode<T>) node.getNext();

					nodeNext.setPrevious(nodePrevious);
					nodePrevious.setNext(nodeNext);
					super.size--;
				}

				return;
			}

			node = (DoubleLinkedListNode<T>) node.getNext();
		}

	}


	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}