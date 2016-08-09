package adt.stack;

public class StackImpl<T> implements Stack<T> {

	private T[] array;
	private int top;

	@SuppressWarnings("unchecked")
	public StackImpl(int size) {
		array = (T[]) new Object[size];
		top = -1;
	}

	@Override
	public T top() {
		// retorna o ultimo
		T saida = null;

		if(!isEmpty()) saida = array[top];

		return saida;
	}

	@Override
	public boolean isEmpty() {

		if(top == -1) return true;
		return false;
	}

	@Override
	public boolean isFull() {
		// se top < array.length -1, para considerar o valor anterior
		if(top < array.length - 1) return false;
		return true;
	}

	@Override
	public void push(T element) throws StackOverflowException {
		// verifica se esta cheia, se sim, lanca OverFlow
		if(isFull()) throw new StackOverflowException();
		// so add se elemento for diferente de null
		if(element != null) {
			top++;
			array[top] = element;
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		// se lista estiver vazia lance excecao
		if(isEmpty()) throw new StackUnderflowException();
		// decrementa para dizer que array foi diminuido
		top--;
		// top +1 por que diminui
		return array[top+1];
	}

}
