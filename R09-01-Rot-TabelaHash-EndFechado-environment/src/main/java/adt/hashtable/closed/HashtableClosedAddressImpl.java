package adt.hashtable.closed;

import util.Util;

import java.util.ArrayList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;

public class HashtableClosedAddressImpl<T> extends
AbstractHashtableClosedAddress<T> {

	/**
	 * A hash table with closed address works with a hash function with closed
	 * address. Such a function can follow one of these methods: DIVISION or
	 * MULTIPLICATION. In the DIVISION method, it is useful to change the size
	 * of the table to an integer that is prime. This can be achieved by
	 * producing such a prime number that is bigger and close to the desired
	 * size.
	 * 
	 * For doing that, you have auxiliary methods: Util.isPrime and
	 * getPrimeAbove as documented bellow.
	 * 
	 * The length of the internal table must be the immediate prime number
	 * greater than the given size. For example, if size=10 then the length must
	 * be 11. If size=20, the length must be 23. You must implement this idea in
	 * the auxiliary method getPrimeAbove(int size) and use it.
	 * 
	 * @param desiredSize
	 * @param method
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashtableClosedAddressImpl(int desiredSize,
			HashFunctionClosedAddressMethod method) {
		int realSize = desiredSize;

		if (method == HashFunctionClosedAddressMethod.DIVISION) {
			realSize = this.getPrimeAbove(desiredSize); // real size must the
			// the immediate prime
			// above
		}
		initiateInternalTable(realSize);
		HashFunction function = HashFunctionFactory.createHashFunction(method,
				realSize);
		this.hashFunction = function;
	}

	// AUXILIARY
	/**
	 * It returns the prime number that is closest (and greater) to the given
	 * number. You can use the method Util.isPrime to check if a number is
	 * prime.
	 */
	int getPrimeAbove(int number) {
		while(!Util.isPrime(number)) {
			number++;
		}

		return number;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(T element) {
		// verificar se elemento e null ou se ele ja existe
		// hash nao permite inserir elemento repetido
		if((element != null) && (search(element) == null)) {
			int posHash = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);

			// verifica se posicao e vazia
			if(table[posHash] == null) {
				table[posHash] = new ArrayList<T>();
			}

			// verificar se existe elemento para colisao
			if(((ArrayList<T>) table[posHash]).size() > 0) {
				COLLISIONS++;
			}

			// colocar o elemento na sua posicao
			((ArrayList<T>) table[posHash]).add(element);
			elements++;
		}
	}

	@Override
	public void remove(T element) {
		
		if(element != null) {
			int posHash = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
			
			// so remove caso elemento exista, lembrar que remove retorna boolean
			if(((ArrayList<T>) table[posHash]).remove(element)) {
				elements--;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T search(T element) {
		if(element != null) {

			int posHash = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);

			// pode ocorrer de procurar elemento que nao existe na posicao do array e nao pode utilizar contains
			// verificar se elemento existe na posicao
			if((table[posHash] != null) && ((ArrayList<T>) table[posHash]).contains(element)) {
				return element;
			}
		}

		return null;
	}

	@Override
	public int indexOf(T element) {
		// TODO Auto-generated method stub
		// verificar se elemento existe em table e diferente de null
		if(element != null && search(element) != null){
			int posHash = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
			return posHash;
		} else {
			return -1;
		}
	}

}
