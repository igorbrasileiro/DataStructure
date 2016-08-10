package sorting.variationsOfBubblesort;

import java.util.Arrays;

import sorting.AbstractSorting;
import util.Util;

/**
 * This algorithm simulates a logical partitioning of the input array by considering 
 * different indexing, that is, the first sub-array is indexed by even elements and
 * the second sub-array is indexed by odd elements. Then, it applies a complete bubblesort
 * in the first sub-array considering neighbours (even). After that, 
 * it applies a complete bubblesort in the second sub-array considering
 * neighbours (odd).  After that, the algorithm performs a merge between elements indexed
 * by even and odd numbers.
 */
public class OddEvenBubbleSort<T extends Comparable<T>> extends AbstractSorting<T>{
	@Override
	public void sort(T[] array,int leftIndex, int rightIndex){
		
		if(array == null || leftIndex >= rightIndex || rightIndex >= array.length || leftIndex < 0
				|| rightIndex - leftIndex < 2) {
			return;
		}
		
		int indice = leftIndex;
		boolean troca = true;
		
		while(troca == true && indice <rightIndex) {
			troca = false;
			for(int i = leftIndex; i < rightIndex -1; i+=2){
				if(array[i].compareTo(array[i+2]) > 0) {
					Util.swap(array, i, i+2);
					troca = true;
				}
			}
			
			for(int j = leftIndex+1; j < rightIndex -1; j+=2) {
				if(array[j].compareTo(array[j+2]) > 0) {
					Util.swap(array, j, j+2);
					troca = true;
				}
			}
			indice++;
		}
		
		merge(array, leftIndex, rightIndex);
	}

	private void merge(T[] array, int leftIndex, int rightIndex) {
		T[] arrayTemp = Arrays.copyOf(array, array.length);
		int indicePar = leftIndex;
		int indiceImpar = leftIndex +1;
		int indice = leftIndex;

		while(indicePar <= rightIndex && indiceImpar <= rightIndex){
			if(arrayTemp[indicePar].compareTo(arrayTemp[indiceImpar]) <= 0) {
				array[indice] = arrayTemp[indicePar];
				indicePar +=2;
			} else {
				array[indice] = arrayTemp[indiceImpar];
				indiceImpar += 2;
			}
			indice++;
		}
		
		while(indicePar <=  rightIndex) {
			array[indice] = arrayTemp[indicePar];
			indicePar += 2;
			indice++;
		}
		
		while(indiceImpar <= rightIndex) {
			array[indice] = arrayTemp[indiceImpar];
			indiceImpar+=2;
			indice++;
		}
	}
}
