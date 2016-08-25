package adt.queue;

public class QueueImpl<T> implements Queue<T> {

	private T[] array;
	private int tail;
	
		
	
	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[])new Object[size];
		tail = -1;
	}

	@Override
	public T head() {
    		
		if(isEmpty()) return null;
		
		return array[0];
	}

	@Override
	public boolean isEmpty() {

		if(tail == -1) return true;
		
		return false;
	}

	@Override
	public boolean isFull() {
		// se quantidade de elementos for menor do que o tamanho
		if(tail < array.length - 1) return false;
		
		return true;
	}
	
	private void shiftLeft(){
		// varre o array ate a penultima posicao 
		for(int i = 0; i < array.length - 1; i++) {
			array[i] = array[i+1];
		}
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if(isFull()) throw new QueueOverflowException();
		
		if(element != null) {
			tail++;
			array[tail] = element;
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		
		if(isEmpty()) throw new QueueUnderflowException();
		
		T saida = array[0];
		// passar os elemetos para esquerda
		shiftLeft();
		
		return saida;
	}


}
