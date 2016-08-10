package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The implementation of the algorithm must be in-place! 
 */
public class GnomeSort<T extends Comparable<T>> extends AbstractSorting<T>{
	public void sort(T[] array,int leftIndex, int rightIndex){
		// tratamento
		if(array.length == 0 || array.equals(null) || leftIndex > rightIndex 
				|| rightIndex >= array.length || leftIndex < 0) return;

		
		int pos = leftIndex;

		while(pos < rightIndex) {
			// se nao trocar assume que esta ordenado em relacao ao pos+1
			if(array[pos].compareTo(array[pos+1]) <= 0) {
				pos++;
			} else {
				Util.swap(array, pos, pos+1);
				if(pos > 0) pos--; // verifica se pos tem anterior
				else pos++; // se nao incrementa para poder comparar novamente pos-1 é pos+1 depois de ordenado
			}
		}
	}
}
