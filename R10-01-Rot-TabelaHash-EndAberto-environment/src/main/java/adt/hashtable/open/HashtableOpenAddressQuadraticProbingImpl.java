package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

   public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
      super(size);
      hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
      this.initiateInternalTable(size);
   }

   @Override
   public void insert(T element) {
      if (isFull())
         throw new HashtableOverflowException();

      if (element != null) {
         int probe = 0;

         while (probe < capacity()) {
            int hash = ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, probe);
            if (table[hash] == null || table[hash].equals(deletedElement)) {
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
      if (element != null) {
         int probe = 0;

         while (probe < capacity()) {
            int hash = ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, probe);

            if (table[hash] == null)
               break; // elemento nao existe 
            // verificar se elemento existe e remover
            if (table[hash].equals(element)) {
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
      if (element != null || !isEmpty()) {

         int posElement = indexOf(element);

         if (posElement != -1)
            return (T) table[posElement];

      }

      return null;
   }

   @Override
   public int indexOf(T element) {

      if (element != null || !isEmpty()) {
         int probe = 0;

         while (probe < capacity()) {
            int hash = ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, probe);
            // se elemento for null entao nao existe nenhum elemento la dentro
            // se for deleted continua procurando
            if (table[hash] == null)
               break;

            if (table[hash].equals(element)) {
               // se elemento existe retorna a posicao
               return hash;
            } else
               probe++;
         }
      }
      // caso elemento nao exista retorne -1
      return -1;
   }

}
