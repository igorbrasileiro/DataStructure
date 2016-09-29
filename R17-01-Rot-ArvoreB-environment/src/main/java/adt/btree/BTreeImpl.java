package adt.btree;

import java.util.ArrayList;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

   protected BNode<T> root;
   protected int order;

   public BTreeImpl(int order) {
      this.order = order;
      this.root = new BNode<T>(order);
   }

   @Override
   public BNode<T> getRoot() {
      return this.root;
   }

   @Override
   public boolean isEmpty() {
      return this.root.isEmpty();
   }

   @Override
   public int height() {
      return height(this.root);
   }

   private int height(BNode<T> node) {
		if(node.isEmpty()) {
			return 0;
		} else {
            int heightNode = 1;
            if(node == root) {
               heightNode = 0;
            }
			ArrayList<Integer> heights = new ArrayList<>();
			for(int i = 0; i < node.getChildren().size(); i++) {
				heights.add(height(node.getChildren().get(i))); // pegar o filho da posicao i e calcula height
			}


			if(heights.isEmpty()) {
				return heightNode;
			} else {
				heights.sort((o1, o2) -> o1.compareTo(o2));
				return heightNode += heights.get(heights.size()-1); //adiciona a maior height dos filhos
			}
		}
	}

   @Override
   public BNode<T>[] depthLeftOrder() {
      ArrayList<BNode<T>> list = new ArrayList<>();
      depthLeftOrder(root,list);

      BNode<T>[] result = new BNode[list.size()];
      result = list.toArray(result);

      return result;
   }

   private void depthLeftOrder(BNode<T> node, ArrayList<BNode<T>> list) {
      list.add(node);
      int childrenSize = node.getChildren().size();
      if(childrenSize>0) {
         for(int i = 0; i < childrenSize; i++) {
            depthLeftOrder(node.getChildren().get(i),list);
         }
      }
   }

   @Override
   public int size() {
      return size(root);
   }

   private int size(BNode<T> node) {
      int result = node.size();
      if(node.getChildren().size()>0) {
         for(int i = 0; i < node.getChildren().size(); i++) {
            result += size(node.getChildren().get(i));
         }
      }

      return result;
   }

   @Override
   public BNodePosition<T> search(T element) {
      if (root.isEmpty()) {
         return new BNodePosition<T>();
      } else {
         return search(this.root, element);
      }
   }

   private BNodePosition<T> search(BNode<T> node, T element) {

      int i = 0;

      while (i <= node.getElements().size() && element.compareTo(node.getElements().get(i)) > 0) {
         i++;
      }

      if (i <= node.size() && element.equals(node.getElements().get(i))) {
         return new BNodePosition<T>(node, i);
      }

      if (node.isLeaf()) {
         return new BNodePosition<T>();
      }

      return search(node.getChildren().get(i), element);
   }

   @Override
   public void insert(T element) {
      if (element != null)
         insert(this.root, element);
   }

   private void insert(BNode<T> node, T element) {
      if (node.isLeaf()) {
         node.addElement(element);
      } else {
         int i = 0;

         while (i < node.getElements().size() && // <= size por que possui k-1 elementos
               element.compareTo(node.getElements().get(i)) > 0) {
            i++;
         }

         insert(node.getChildren().get(i), element);
      }

      if(node.size() > node.maxKeys) {
         split(node);
      }
   }

   private void split(BNode<T> node) {
      BNode<T> newRoot = new BNode<T>(node.getMaxChildren());
      int midPosition = node.getElements().size() / 2;
      T midElement = node.getElementAt(midPosition);

      BNode<T> left = new BNode<T>(node.getMaxChildren());
      BNode<T> right = new BNode<T>(node.getMaxChildren());


      if(node.isEmpty()) {

      }
      // add elementos do no, no novo no left ou right
      for (int i = 0; i < node.getElements().size(); i++) {
         if (i < midPosition)
            left.getElements().add(node.getElementAt(i));
         else if (i >= midPosition + 1)
            right.getElements().add(node.getElementAt(i));

      }

      if(!node.isLeaf()) {
         if (node.getChildren().size() > 0) {
            for (int i = 0; i <= midPosition; i++)
               left.addChild(i, node.getChildren().get(i));

            int index = 0;
            for (int i = midPosition + 1; i < node.getChildren().size(); i++) {
               right.addChild(index, node.getChildren().get(i));
               index += 1;
            }
         }
      }

      // verificar se no e vazio para criar o novo root
      if (getRoot().equals(node)) {
         newRoot.addElement(midElement);
         node.setParent(newRoot);
         root = newRoot;



         newRoot.addChild(0, left);
         newRoot.addChild(1, right);

         newRoot.getChildren().get(0).setParent(newRoot);
         newRoot.getChildren().get(1).setParent(newRoot);

      } else {
         node.addChild(0, left);
         node.addChild(1, right);
         promote(node);
      }

   }

   private void promote(BNode<T> node) {
      int midPosition = node.getElements().size() / 2;

      T midELement = node.getElementAt(midPosition);

      node.getElements().clear();
      node.addElement(midELement);

      BNode<T> parent = node.getParent();

      // se parent ja for null ele e o root
      if (parent != null) {

         node.getChildren().get(0).setParent(parent);
         node.getChildren().get(1).setParent(parent);

         parent.addElement(midELement);

         int index = parent.getChildren().indexOf(node);
         parent.addChild(index, node.getChildren().get(0));
         parent.addChild(index + 1, node.getChildren().get(1));

         node.getChildren().get(0).setParent(parent);
         node.getChildren().get(1).setParent(parent);

         parent.getChildren().remove(node);
      }

   }

   // NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
   @Override
   public BNode<T> maximum(BNode<T> node) {
      // NAO PRECISA IMPLEMENTAR
      throw new UnsupportedOperationException("Not Implemented yet!");
   }

   @Override
   public BNode<T> minimum(BNode<T> node) {
      // NAO PRECISA IMPLEMENTAR
      throw new UnsupportedOperationException("Not Implemented yet!");
   }

   @Override
   public void remove(T element) {
      // NAO PRECISA IMPLEMENTAR
      throw new UnsupportedOperationException("Not Implemented yet!");
   }

}
