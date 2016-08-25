package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm. 
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {
	/* (non-Javadoc)
	 * @see sorting.AbstractSorting#sort(java.lang.Comparable[], int, int)
	 */
	@Override
	public void sort(T[] array,int leftIndex, int rightIndex) {
		// tratamento
		if(array.length == 0 || array.equals(null) || leftIndex > rightIndex 
				|| rightIndex >= array.length || leftIndex < 0) return;


		int gap;
		if(leftIndex != 0) gap = rightIndex-leftIndex+1; // se for 0 nao precisa add +1
		else gap = rightIndex-leftIndex;

		for(int i= leftIndex; i < rightIndex; i++) {
			gap = (int) (gap / 1.25); // 1.25 e o fator
			if(gap < 1) gap = 1;

			int j = leftIndex; // para nao incrementar I
			while(j+gap <= rightIndex-leftIndex) {
				// compara com a[i] com a[i+gap]
				if(array[j].compareTo(array[j+gap]) > 0) Util.swap(array, j, j+gap);
				j++;
			}
		}

	}
}
