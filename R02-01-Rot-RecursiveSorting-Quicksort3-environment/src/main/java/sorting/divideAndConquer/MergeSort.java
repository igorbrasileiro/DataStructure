package sorting.divideAndConquer;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm.  
 * The algorithm consists of recursively dividing the unsorted list in the middle,
 * sorting each sublist, and then merging them into one single sorted list.
 * Notice that if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array,int leftIndex, int rightIndex) {
		// tratamento
		if(array.length == 0 || array.equals(null) || leftIndex > rightIndex 
				|| rightIndex >= array.length || leftIndex < 0) return;
		
		
		if(leftIndex < rightIndex) {
			int mid = leftIndex + (rightIndex - leftIndex) / 2;		
			sort(array,leftIndex,mid);
			sort(array,mid+1,rightIndex);
			merge(array, leftIndex, mid,rightIndex);
		}

	}

	public void merge(T[] array, int leftIndex, int mid, int rightIndex) {
		T[] arrayTemp = Arrays.copyOf(array, array.length);
		int left = leftIndex;
		int right = mid+1;
		int index = leftIndex;

		while(left <= mid && right <= rightIndex) {
			if(arrayTemp[left].compareTo(arrayTemp[right]) < 0) {
				array[index] = arrayTemp[left];
				left++;
				
			} else {
				array[index] = arrayTemp[right];
				right++;
	
			}
			
			index++;
		}

		while(left <= mid) {
			array[index] = arrayTemp[left]; 
			left++;
			index++;
		}

		while(right <= rightIndex) {
			array[index] = arrayTemp[right]; 
			right++;
			index++;
		}
	}
}
