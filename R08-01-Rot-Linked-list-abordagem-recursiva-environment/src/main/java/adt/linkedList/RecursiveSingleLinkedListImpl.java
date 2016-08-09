package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

   protected T data;
   protected RecursiveSingleLinkedListImpl<T> next;

   public RecursiveSingleLinkedListImpl() {

   }

   public RecursiveSingleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next) {

      //se data diferente de null incrementa size
      this.data = data;
      this.next = next;
   }

   @Override
   public boolean isEmpty() {
      return (data == null);
   }

   @Override
   public int size() {
      if (!isEmpty()) {
         return 1 + next.size();
      }

      return 0;
   }

   @Override
   public T search(T element) {
      // TODO Auto-generated method stub
      if (!isEmpty()) {
         if (data.equals(element)) {
            return (T) data;
         } else {
            return next.search(element);
         }
      }

      return null;
   }

   @Override
   public void insert(T element) {
      if (element != null) {
         if (isEmpty()) {
            this.data = element;
            this.next = new RecursiveSingleLinkedListImpl<T>();
         } else {
            this.next.insert(element);
         }
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public void remove(T element) {
      if (!isEmpty()) {
         if (this.data.equals(element)) {
            this.data = next.data;
            this.next = this.next.next;
         } else {
            next.remove(element);
         }
      }
   }

   @Override
   public T[] toArray() {
      // TODO Auto-generated method stub
      T[] arrayTemp = (T[]) new Object[size()];
      if (!isEmpty()) {
         toArray(arrayTemp, this, 0);
         return arrayTemp;
      }

      arrayTemp = (T[]) new Object[0];
      return arrayTemp;

   }

   private void toArray(T[] arrayTemp, RecursiveSingleLinkedListImpl<T> node, int index) {

      if (node.getData() != null) {
         arrayTemp[index++] = node.getData();
         toArray(arrayTemp, node.getNext(), index);
      }
   }

   public T getData() {
      return data;
   }

   public void setData(T data) {
      this.data = data;
   }

   public RecursiveSingleLinkedListImpl<T> getNext() {
      return next;
   }

   public void setNext(RecursiveSingleLinkedListImpl<T> next) {
      this.next = next;
   }

}
