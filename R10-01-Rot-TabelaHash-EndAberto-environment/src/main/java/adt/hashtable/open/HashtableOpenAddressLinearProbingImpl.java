package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends
AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size,
			HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		if(isFull()) throw new HashtableOverflowException();

		if(element != null) {
			int probe = 0;
			
			while (probe < capacity()) {
				int hash = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);
				if(table[hash] == null || table[hash].equals(deletedElement)) {
					table[hash] = element;
					elements++;
					break; // quando inserir parar
				} else {
					probe++;
					COLLISIONS++;
				}
			}
		}
		
	}

	@Override
	public void remove(T element) {
		
		if(element != null || !isEmpty()) {
			int probe = 0;
			
			while (probe < capacity()) {
				int hash = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);
				
				 // elemento nao existe então não precisa calcular
				// diferente se element for deleted
				if(table[hash] == null) break;
				// verificar se elemento existe e remover, se não recalcula hash e tenta procurar
				if(table[hash].equals(element)) {
					table[hash] = deletedElement;
					elements--;
					break; // quando remover parar
				} else {
					probe++;
				}
			}

		}
	}

	@Override
	public T search(T element) {
		// Na quadratic probing fiz utilizando o metodo indexOf
		// chamando indexOf e verifica se e -1
		
		T result = null;

		if(element != null || !isEmpty()) {
			int probe = 0;
			
			while (probe < capacity()) {
				int hash = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);
				
				if(table[hash] == null) return result; // se no primeiro Ã© null e por que elemento nao existe
				
				if(table[hash].equals(element)) {
					return element;
				} else probe++;
			}
			
		}

		return result;
	}

	@Override
	public int indexOf(T element) {
		
		if(element != null || !isEmpty()) {
			int probe = 0;
			
			while (probe < capacity()) {
				int hash = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);
				
				if(table[hash] == null) break; 
				
				if(table[hash].equals(element)) {
					// se elemento existe retorna a posicao
					return hash;
				} else probe++;
			}
			
		}
		// caso elemento nao exista retorne -1 ou se element for null ou pilha vazia
		return -1;
	}

}
