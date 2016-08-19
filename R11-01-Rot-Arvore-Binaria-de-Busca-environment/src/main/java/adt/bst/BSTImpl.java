package adt.bst;

import java.util.ArrayList;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

   protected BSTNode<T> root;

   public BSTImpl() {
      root = new BSTNode<T>();
   }

   public BSTNode<T> getRoot() {
      return this.root;
   }

   @Override
   public boolean isEmpty() {
      return root.isEmpty();
   }

   @Override
   public int height() {
      return height(root);
   }

   private int height(BSTNode<T> node) {
      if (node.isEmpty()) {
         return -1; // caso seja vazio
      } else {
         return 1 + Math.max(height((BSTNode<T>) node.getLeft()), height((BSTNode<T>) node.getRight()));
      }
   }

   @Override
   public BSTNode<T> search(T element) {
      if (element != null)
         return search(element, root);
      else
         return null;
   }

   private BSTNode<T> search(T element, BSTNode<T> node) {

      if (!node.isEmpty()) {
         // caso base
         if (element.equals(node.getData()))
            return node;
         else {
            if (element.compareTo(node.getData()) < 0) {
               return search(element, (BSTNode<T>) node.getLeft());
            } else if (element.compareTo(node.getData()) > 0) {
               return search(element, (BSTNode<T>) node.getRight());
            }
         }
      } else
         return node; // retorna node NIL

      // questao de compilacao else if, porem professor nao insere elemento igual
      // nunca chegara aqui pelo que professor disse
      return new BSTNode<T>();

   }

   @Override
   public void insert(T element) {
      if (element != null) {
         insert(element, root);
      }
   }

   /**
    * insere elemento na arvore. 
    * parent da raiz e null
    * @param element
    * @param node
    */
   private void insert(T element, BSTNode<T> node) {
      if (node.isEmpty()) {
         node.setData(element);

         node.setLeft(new BSTNode<T>());
         node.getLeft().setParent(node);

         node.setRight(new BSTNode<T>());
         node.getRight().setParent(node);
      } else {
         if (element.compareTo(node.getData()) < 0) {
            insert(element, (BSTNode<T>) node.getLeft());
         } else if (element.compareTo(node.getData()) > 0) {
            insert(element, (BSTNode<T>) node.getRight());
         }
      }
   }

   @Override
   public BSTNode<T> maximum() {
      return maximum(root);
   }

   private BSTNode<T> maximum(BSTNode<T> node) {
      if (node.getData() == null)
         return null; // no caso da tree vazia, root = null
      if (node.isLeaf())
         return node;

      if (!node.getRight().isEmpty())
         return maximum((BSTNode<T>) node.getRight());
      else
         return node;
   }

   @Override
   public BSTNode<T> minimum() {
      return minimum(root);
   }

   private BSTNode<T> minimum(BSTNode<T> node) {
      if (node.getData() == null)
         return null; // no caso da tree vazia, root = null
      if (node.isLeaf())
         return node;

      if (!node.getLeft().isEmpty())
         return minimum((BSTNode<T>) node.getLeft());
      else
         return node;
   }

   @Override
   public BSTNode<T> sucessor(T element) {
      if (element != null) {

         BSTNode<T> auxNode = search(element);

         //fazer verificar se element é o ultimo
         if (auxNode.equals(maximum()))
            return null;

         // verifica se auxNode e diferente NIL
         if (!auxNode.isEmpty()) {
            //verifica se direita existe direita
            if (!auxNode.getRight().isEmpty()) {
               return minimum((BSTNode<T>) auxNode.getRight());
            } else { // segunda parte do algoritmo
               return sucessor((BSTNode<T>) auxNode.getParent(), auxNode); // passando pai e noded
            }

         } else
            return null; // se node for nil

      }
      return null; // caso elemento seja null
   }

   private BSTNode<T> sucessor(BSTNode<T> parent, BSTNode<T> node) {
      //ficando aqui o passo recursivo
      //sobe ate enquanto filho for diferente do elemento da esquerda do pai
      if ((parent != null) && ((!node.equals(parent.getLeft())))) {
         return sucessor((BSTNode<T>) parent.getParent(), (BSTNode<T>) parent); // passando pai do pai e pai

      } else {
         return parent;
      }
   }

   @Override
   public BSTNode<T> predecessor(T element) {
      if (element != null) {
         BSTNode<T> auxNode = search(element);
         //verificar se e o menor elemento
         if (auxNode.equals(minimum()))
            return null;

         //verifica se auxNode e diferente nil
         if (!auxNode.isEmpty()) {
            // verifica se esquerda existe
            if (!auxNode.getLeft().isEmpty()) {
               return maximum((BSTNode<T>) auxNode.getLeft()); //pegando sempre o menor dos maiores.
            } else { //segunda parte algoritmo
               // procura pelo pai
               return predecessor((BSTNode<T>) auxNode.getParent(), auxNode); // passa pai e node;
            }
         } else
            return null; // se node for nil

      } else
         return null; //caso element seja null

   }

   private BSTNode<T> predecessor(BSTNode<T> parent, BSTNode<T> auxNode) {
      //vai subindo
      // sobe ate enquanto filho for diferente do elemento da direita do pai
      if ((parent != null) && ((!auxNode.equals(parent.getRight())))) {
         return predecessor((BSTNode<T>) parent.getParent(), parent); //passando o pai do pai e pai;

      } else {
         return parent;
      }
   }

   @Override
   public void remove(T element) {
      // ideia ir empurrando elemento para o final ate chegar ao caso base
      BSTNode<T> node = search(element);
      if ((!node.isEmpty()) && (element != null)) {
         removeRecursivo(node);
      }
   }

   public void removeRecursivo(BSTNode<T> node) {
      if (node != null && !node.isEmpty()) {
         // caso 1 (base) no nao tem filhos, so remove
         if (node.isLeaf()) {
            node.setData(null);
            node.setLeft(null);
            node.setRight(null);
         } else if (node.getRight().isEmpty()) { // so tem esquerda, pega sub tree do elemento a esquerda
            node.setData(node.getLeft().getData()); // pega elemento a esquerda
            node.setRight(node.getLeft().getRight()); // pega esquerda da esquerda(escolhida passo anterior)
            node.setLeft(node.getLeft().getLeft()); // pega direita da esquerda(escolhida passo anterior)
            node.getRight().setParent(node); //seta o pai dos novos filhos para ele
            node.getLeft().setParent(node);
         } else if (node.getLeft().isEmpty()) {// so tem direita, pega sub tree do elemento a direita
            node.setData(node.getRight().getData()); // mesma ideia do esquerda, so que para direita
            node.setLeft(node.getRight().getLeft());
            node.setRight(node.getRight().getRight());
            node.getRight().setParent(node);
            node.getLeft().setParent(node);
         } else { // tem dois filhos
            BSTNode<T> sucessor = sucessor(node.getData());
            if (sucessor != null) {
               node.setData(sucessor.getData());
               removeRecursivo(sucessor);
            }
         }
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public T[] preOrder() {
      // R,E,D
      @SuppressWarnings("unchecked")
      ArrayList<T> aux = new ArrayList<>();

      preOrder(aux, root);

      return makeArray(aux);
   }

   private void preOrder(ArrayList<T> result, BSTNode<T> node) {
      //R,E,D
      if (!node.isEmpty()) {
         result.add(node.getData());
         preOrder(result, (BSTNode<T>) node.getLeft());
         preOrder(result, (BSTNode<T>) node.getRight());
      }
   }

   @Override
   public T[] order() {
      //E,R,D
      ArrayList<T> aux = new ArrayList<>();

      order(aux, root);

      return makeArray(aux);
   }

   private void order(ArrayList<T> result, BSTNode<T> node) {

      if (!node.isEmpty()) {
         order(result, (BSTNode<T>) node.getLeft());
         result.add(node.getData());
         order(result, (BSTNode<T>) node.getRight());
      }
   }

   @Override
   public T[] postOrder() {
      // D,E,R
      ArrayList<T> aux = new ArrayList<>();

      postOrder(aux, root);

      return makeArray(aux);
   }

   private void postOrder(ArrayList<T> result, BSTNode<T> node) {
      if (!node.isEmpty()) {
         postOrder(result, (BSTNode<T>) node.getLeft());
         result.add(node.getData());
         postOrder(result, (BSTNode<T>) node.getRight());
      }
   }

   /**
    * This method is already implemented using recursion. You must understand
    * how it work and use similar idea with the other methods.
    */
   @Override
   public int size() {
      return size(root);
   }

   private int size(BSTNode<T> node) {
      int result = 0;
      // base case means doing nothing (return 0)
      if (!node.isEmpty()) { // indusctive case
         result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
      }
      return result;
   }

   private T[] makeArray(ArrayList<T> aux) {
      @SuppressWarnings("unchecked")
      T[] result = (T[]) new Comparable[aux.size()];
      for (int i = 0; i < aux.size(); i++) {
         result[i] = aux.get(i);
      }

      return result;
   }

}
