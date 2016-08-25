package sorting.divideAndConquer.quicksort3;

import java.util.Comparator;

import sorting.AbstractSorting;
import util.Util;

/**
 * A classe QuickSortMedianOfThree representa uma variação do QuickSort que funciona 
 * de forma ligeiramente diferente. Relembre que quando o pivô escolhido divide o 
 * array aproximadamente na metade, o QuickSort tem um desempenho perto do ótimo. 
 * Para aproximar a entrada do caso ótimo, diversas abordagens podem ser utilizadas. 
 * Uma delas é usar a mediana de 3 para achar o pivô. Essa técnica consiste no seguinte:
 * 1.	Comparar o elemento mais a esquerda, o central e o mais a direita do intervalo.
 * 2.	Ordenar os elemento, tal que: A[left] < A[center] < A[right].
 * 3.	Adotar o A[center] como pivô.
 * 4.	Colocar o pivô na penúltima posição A[right-1].
 * 5.	Aplicar o particionamento considerando o vetor menor, de A[left+1] até A[right-1].
 * 6.	Aplicar o algoritmo na metade a esquerda e na metade a direita do pivô.
 */
public class QuickSortMedianOfThree<T extends Comparable<T>> extends AbstractSorting<T>{

	public void sort(T[] array, int leftIndex, int rightIndex){
		//tratamento
		if(array.length == 0 || array.equals(null) || leftIndex > rightIndex 
				|| rightIndex >= array.length || leftIndex < 0) return;


		if( leftIndex < rightIndex ) {		
			int pivot = leftIndex + (rightIndex - leftIndex) /2;
			//ordenar left mid e right
			encontraMediana(array, leftIndex, rightIndex, pivot);
			// troca mid por right-1
			Util.swap(array, pivot, rightIndex-1);

			pivot = partition(array, leftIndex, rightIndex);
			sort(array,leftIndex, pivot-1);
			sort(array, pivot+1, rightIndex);
		}

	}

	private void encontraMediana(T[] array, int leftIndex, int rightIndex, int centro) {		
		// inicio < meio > fim
		if (array[leftIndex].compareTo(array[centro]) == -1 && array[centro].compareTo(array[rightIndex]) == 1) {
			Util.swap(array, centro, rightIndex);
			// inicio > meio and incio < fim
		} else if (array[leftIndex].compareTo(array[centro]) == 1 && array[leftIndex].compareTo(array[rightIndex]) == -1) {
			Util.swap(array, leftIndex, centro);
			// inicio < meio and inicio > fim
		} else if (array[leftIndex].compareTo(array[centro]) == -1 && array[leftIndex].compareTo(array[rightIndex]) == 1) {
			Util.swap(array, leftIndex, centro);
			Util.swap(array, leftIndex, rightIndex);
			// inicio > meio and incio > fim and meio < fim
		} else if (array[leftIndex].compareTo(array[centro]) == 1 && array[leftIndex].compareTo(array[rightIndex]) == 1
				&& array[centro].compareTo(array[rightIndex]) == -1) {
			Util.swap(array, leftIndex, centro);
			Util.swap(array, centro, rightIndex);
			// inicio > meio and incio > fim and meio > fim
		} else if (array[leftIndex].compareTo(array[centro]) == 1 && array[leftIndex].compareTo(array[rightIndex]) == 1
				&& array[centro].compareTo(array[rightIndex]) == 1) {
			Util.swap(array, leftIndex, centro);
			Util.swap(array, centro, rightIndex);
		}
	}

	private int partition(T[] array, int leftIndex, int rightIndex) {
		int i = leftIndex +1;
		int j = rightIndex;
		T pivot = array[leftIndex];

		while(i <= j) {
			if(array[i].compareTo(pivot) <= 0) {
				i++;
			} else if(array[j].compareTo(pivot) > 0){
				j--;
			} else {
				Util.swap(array, i, j);
				i++;
				j--;
			}

		}

		Util.swap(array, leftIndex, j);

		return j;


	}

}
