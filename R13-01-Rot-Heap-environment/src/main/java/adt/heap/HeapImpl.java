package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Essa comparação não é feita diretamente com os elementos armazenados,
 * mas sim usando um comparator. Dessa forma, dependendo do comparator, a heap
 * pode funcionar como uma max-heap ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é
	 * mudar apenas o comparator e mandar reordenar a heap usando esse
	 * comparator. Assim os metodos da heap não precisam saber se vai funcionar
	 * como max-heap ou min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma
	 * min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}
	

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**if
	 * Deve retornar o indice que representa o filho a esquerda do elemento
	 * indexado pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento
	 * indexado pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = (T[]) new Comparable[(index + 1)];
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode
	 * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		if(!isEmpty() || index > -1){
			int left = left(position); // verificar se esse valor pode, caso seja os elementos do final
			int right = right(position);
			int largest = position;
			
			if(left <= index && heap[left] != null 	&& comparator.compare(heap[left], heap[largest]) > 0) {
				largest = left;
			}
			
			if(right <= index && heap[right] != null && comparator.compare(heap[right], heap[largest]) > 0) {
				largest = right;
			}
			
			if(largest != position) {
				Util.swap(heap, largest, position);
				heapify(largest);
			}
		}
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
		// /////////////////////////////////////////////////////////////////
		// TODO Implemente a insercao na heap aqui.
		
		if (element != null) {
			index++; // incrementa posicao elemento
			
			// verificar se e maior do que o pai e subir
			int parent = parent(index);
			int auxPos = index; // posicao para onde o pai deve ir
			
			heap[index] = element;
			
			while(auxPos > 0 && comparator.compare(element, heap[parent]) > 0) {
				heap[auxPos] = heap[parent];
				auxPos = parent;
				parent = parent(parent);
			}
			
			heap[auxPos] = element;
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buildHeap(T[] array) {
		// TODO Auto-generated method stub
		if(array != null && array.length > 0) {
			heap = array; // recriando array
			index = array.length-1; // zerando as posicoes
			for(int i = (array.length/2 ); i >= 0; i--) {
				heapify(i);;
			}
		}
	}

	@Override
	public T extractRootElement() {
		if(isEmpty()) {
			return null;
		} else {
			T result = heap[0]; // pegar a raiz
			heap[0] = heap[index]; // substituir raiz pelo ultimo elemento
			index--; // decrementa posicao
			
			heapify(0);
			
			return result;
		}
	}

	@Override
	public T rootElement() {
		// TODO Auto-generated method stub
		return heap[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] heapsort(T[] array) {
		if(array != null && array.length > 0) { // declaracao do metodo nao e clara se e para excluir a heap que tem ou criar outra
			// copiar caracteristicas da heap existente
			T[] heapOriginal = heap; // copiar para caso comentario acima, de manter heap
			int indexOriginal = index; // mesma coisa de cima
			Comparator<T> comparatorOriginal = comparator;
			
			// criar uma mim heap e remover os elementos
			// criando comparador para mim heap
			comparator = new Comparator<T>() {

				@Override
				public int compare(T o1, T o2) {
					return o2.compareTo(o1); // o "-" e para ficar decrescente
				}
			};
			T[] result = (T[]) new Comparable[array.length];
			
			buildHeap(array);
			for(int i = 0; i < array.length; i++) {
				result[i] = extractRootElement(); // remove ja heapify
			}
			
			// recriar a heap antes do sort
			heap = heapOriginal; // passar a heap que ja existia caso queira manter
			index = indexOriginal; // passar indice antes
			comparator = comparatorOriginal; // passar comparador antigo

			return result;
		} else return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return index+1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}

}
