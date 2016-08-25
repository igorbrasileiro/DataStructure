package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * As the insertion sort algorithm iterates over the array, it makes the
 * assumption that the visited positions are already sorted in ascending order,
 * which means it only needs to find the right position for the current element
 * and insert it there.
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSorting<T> {


	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if((leftIndex < 0) || (leftIndex == rightIndex) || (rightIndex > array.length) 
				|| (array.equals(null))) throw new RuntimeException();

		int pos;

		for(int i = leftIndex + 1; i <= rightIndex; i++) {

			T key = array[i];
			pos = i -1;

			while((pos >= leftIndex) && (array[pos].compareTo(key) > 0)) {
				array[pos+1] = array[pos];
				pos--;
			}
			array[pos+1] = key;
		}
		
	}
}

