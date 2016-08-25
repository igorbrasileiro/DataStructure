package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. 
 * Desta vez este algoritmo deve satisfazer os seguitnes requisitos:
 * - Alocar o tamanho minimo possivel para o array de contadores (C)
 * - Ser capaz de ordenar arrays contendo numeros negativos
 */


public class ExtendedCountingSort extends AbstractSorting<Integer> {
	private boolean temNegativo = false;
	private boolean temPositivo = false;
	@Override
	public void sort(Integer[] array,int leftIndex, int rightIndex) {
		if(array.length == 0 || array.equals(null) || leftIndex > rightIndex 
				|| rightIndex >= array.length || leftIndex < 0) return;

		
		// array temporario dos valores de array
		Integer[] arrayTemp = Arrays.copyOf(array, array.length);

		int j = leftIndex;
		while((!temNegativo || !temPositivo) && j <= rightIndex) {
			if(arrayTemp[j] < 0) temNegativo = true;

			if(arrayTemp[j] >=0) temPositivo = true;

			j++;
		}

		int valorMinNegativo = 0;
		int valorMaxNegativo = 0;
		Integer[] arrayPosNegativo = null;
		if(temNegativo) {
			valorMinNegativo =  encontraValorMinNegativo(array, leftIndex, rightIndex, -1);
			// procura maior do que o valor min negativo
			valorMaxNegativo = encontraValorMax(arrayTemp, leftIndex, rightIndex, valorMinNegativo);

			// cria um array do menor ate o maior do negativo
			if(Math.abs(valorMinNegativo) > 1) {
				arrayPosNegativo =  new Integer[Math.abs(valorMinNegativo) - valorMaxNegativo];
				for(int i = 0; i < Math.abs(valorMinNegativo) - valorMaxNegativo; i++) {
					arrayPosNegativo[i] = 0;
				}
			} else {
				// é por que no min ele e -1
				arrayPosNegativo =  new Integer[1];
				arrayPosNegativo[0] = -1;
			}
		}

		int valorMaxPositivo = 0;
		int valorMinPositivo = 0;
		Integer[] arrayPosPositivo = null;
		if(temPositivo) {
			valorMaxPositivo = encontraValorMax(arrayTemp, leftIndex, rightIndex, 0);
			// procura menor valor dos positivos
			valorMinPositivo = encontraValorMinNegativo(arrayTemp, leftIndex, rightIndex, valorMaxPositivo);

			if(valorMaxPositivo > 1){
				// +1 por que é inclusivo e conta o zero. se fosse maior - menor -> [1,2] =  3(pq é 2+1 no metodo) -1 = 2 que nao inclui o zero
				if(valorMaxPositivo - valorMinPositivo > 1) {
					arrayPosPositivo = new Integer[valorMaxPositivo - valorMinPositivo + 1];
				} else {
					arrayPosPositivo = new Integer[valorMaxPositivo];
				}
				for(int i = 0; i < arrayPosPositivo.length; i++) {
					arrayPosPositivo[i] = 0;
				}
			} else {
				// se o mair for 1 é por que pode ter 0 e 1;
				arrayPosPositivo = new Integer[2];
				arrayPosPositivo[0] = 0;
				arrayPosPositivo[1] = 0;
			}
		} 

		// contando os elementos negativos e positivos		

		

		// soma dos valores posicao array negativo
		if(temNegativo){
			for(int i = 1; i < arrayPosNegativo.length; i++) {
				arrayPosNegativo[i] += arrayPosNegativo[i-1];
			}
			arrayPosPositivo[0] += arrayPosNegativo[array.length-1];
		}
		
		for(int i = 1; i < arrayPosPositivo.length-1; i++) {
			arrayPosPositivo[array[i] - valorMinPositivo]++;
		}



		
		for(int i = rightIndex; i >= 0; i--) {

			// se for maior que zero
			//array valor - menor valor +1(pq inclui 0)
			if(arrayTemp[i] < 0){
				// somar no vetor positivo o ultimo valor do negativo
				arrayPosNegativo[arrayTemp[i] - valorMinNegativo]--;
				array[arrayPosNegativo[arrayTemp[i] - valorMinNegativo]] = arrayTemp[i];
			} else {
				arrayPosPositivo[arrayTemp[i] - valorMinPositivo +1]--;
				array[arrayPosPositivo[arrayTemp[i] - valorMinPositivo +1]] = arrayTemp[i];
			}
			
		}

	}

	

private int encontraValorMinNegativo(Integer[] array, int leftIndex, int rightIndex, int menor) {
	int min = menor;
	// procurar o menor maior que zero
	if(menor >= 0) {
		for(int i = leftIndex; i <= rightIndex; i++) {
			// verifica se a[i] é positivo e menor que min
			if(array[i] < min && array[i] >= 0) {
				min = array[i];
			}
		}
	} else {
		// procura o menor dos negativos
		for(int i = leftIndex; i <= rightIndex; i++){
			if(array[i] < min) {
				// procura menor de todos
				min = array[i];
			}
		}
	}
	return min;
}

private int encontraValorMax(Integer[] array, int leftIndex, int rightIndex, int maior) {
	int max = maior;

	if(maior < 0) {
		//procura menor dos negativos
		for(int i = leftIndex; i <= rightIndex; i++) {
			// verifica se e negativo e maior do que o menor
			if(array[i] < 0 && array[i] > max) {
				max = array[i];
			}
		}
	} else {
		// procura maior de todos
		for(int i = leftIndex; i <= rightIndex; i++) {
			if(array[i] > max) {
				max = array[i];
			}
		}
		max++; // para incluir o zero
	}
	// incluir o 0
	return max;
}
}
