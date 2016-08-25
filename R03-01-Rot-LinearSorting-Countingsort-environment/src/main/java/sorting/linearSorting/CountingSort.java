package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure evitar desperdicio de 
 * memoria alocando o array de contadores com o tamanho sendo o máximo inteiro presente no array 
 * a ser ordenado.  
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array,int leftIndex, int rightIndex) {
		if(array.length == 0 || array.equals(null) || leftIndex > rightIndex 
				|| rightIndex >= array.length || leftIndex < 0) return;

		// array temporario dos valores de array
		Integer[] arrayTemp = Arrays.copyOf(array, array.length);
		
		int valorMaxArray =  encontraValorMax(array, leftIndex, rightIndex);
		
		// criar um array, do tamanho do valor maximo e substituir os valores por 0
		Integer[] arrayPos =  new Integer[valorMaxArray];
		for(int i = 0; i < arrayPos.length; i++) {
			arrayPos[i] = 0;
		}
		
		// contando os elementos
		for(int i = leftIndex; i <= rightIndex; i++) {
			//pegar a posicao referente ao valor valor 3 = pos 3 do array
			arrayPos[arrayTemp[i]]++;
		}
		
		// soma da pos i com i-1 OBS VALOR MAX de array TEMPorario
		for(int i = 1; i < valorMaxArray; i++) {
			arrayPos[i] += arrayPos[i-1];
		}
		
		//variavel auxiliar para pegar valor de i
		//int auxTemp;
		for(int i = rightIndex; i >= 0; i--) {
			// auxTemp = valor da posicao de i do array copia
			// a posicao referente ao valor auxTemp decrementado do arrayTemp é a posicao no array que precisa ordenar = valor da posicao
			arrayPos[arrayTemp[i]]--;
			array[arrayPos[arrayTemp[i]]] = arrayTemp[i]; 
			
			
		}
		
		
	}

	private int encontraValorMax(Integer[] array, int leftIndex, int rightIndex) {
		int max = 0;
		
		for(int i = leftIndex; i <= rightIndex; i++) {
			if(array[i] > max) {
				max = array[i];
			}
		}
		// incluir o 0
		return max+1;
	}

}
