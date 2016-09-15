package adt.queue;

public class CircularQueue<T> implements Queue<T> {

	private T[] array;
	private int tail;
	private int head;
	private int elements;

	public CircularQueue(int size) {
		array = (T[]) new Object[size];
		head = -1;
		tail = -1;
		elements = 0;
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if(isFull()) throw new QueueOverflowException();
		
		if(element != null) {
			elements++;
			tail++;
			// seta o valor de tail, sempre zerando quando for = size
			tail = tail % array.length;
			array[tail] = element;
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if(isEmpty()) throw new QueueUnderflowException();
		
		// incrementa o head e remove o da posicao, comeca com -1 / decrementa quantidade de elementos
		head++; // passa a ser pos do elemento a remover
		head = head % array.length;
		elements--;
		return array[head]; // elemento removido
	}

	@Override
	public T head() {
		if(isEmpty()) return null;
		
		// head +1 por que head comeca de -1, e so incremento quando removo
		return array[head+1];
	}

	@Override
	public boolean isEmpty() {
		// se quantidade de elemetnos for 0 esta vazia
		return (elements == 0);
	}

	@Override
	public boolean isFull() {
		// se quantidade de elementos for do tamamho da lista return full;		
		return elements == array.length;
	}

}
