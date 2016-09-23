package adt.rbtree;

import adt.bst.BSTImpl;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

import java.util.ArrayList;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

   public RBTreeImpl() {
      this.root = new RBNode<T>();
   }

   //

   protected boolean verifyProperties() {
      boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
            && verifyBlackHeight();

      return resp;
   }

   /**
    * The colour of each node of a RB tree is black or red. This is guaranteed
    * by the type Colour.
    */
   private boolean verifyNodesColour() {
      return true; // already implemented
   }

   /**
    * The colour of the root must be black.
    */
   private boolean verifyRootColour() {
      return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
      // implemented
   }

   /**
    * This is guaranteed by the constructor.
    */
   private boolean verifyNILNodeColour() {
      return true; // already implemented
   }

   /**
    * Verifies the property for all RED nodes: the children of a red node must
    * be BLACK.
    */
   private boolean verifyChildrenOfRedNodes() {
      if (root != null && !root.isEmpty())
         return verifyChildrenOfRedNodes((RBNode<T>) root.getLeft())
               && verifyChildrenOfRedNodes((RBNode<T>) root.getRight());

      return true;
   }

   private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
      if (!node.isEmpty()) {
         if (node.getColour() == Colour.RED) {
            if (((RBNode<T>) node.getLeft()).getColour() != Colour.BLACK
                  || ((RBNode<T>) node.getRight()).getColour() != Colour.BLACK) {
               return false;
            }
         }
         return verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
               && verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
      }

      return true;
   }

   /**
    * Verifies the black-height property from the root. The method blackHeight
    * returns an exception if the black heights are different.
    */
   private boolean verifyBlackHeight() {
      if (root != null) {
         int leftBlackHeight = countBlackHeightNode((RBNode<T>) root.getLeft());
         int rightBlackHeight = countBlackHeightNode((RBNode<T>) root.getRight());
         return rightBlackHeight == leftBlackHeight;
      }

      return true;
   }

   protected int blackHeight() {
      if(root != null && !root.isEmpty()) return countBlackHeightNode((RBNode<T>) this.root);
      return 0;
   }

   public int countBlackHeightNode(RBNode<T> node) {
      if (node != null && !node.isEmpty()) {
         if (node.getColour() == Colour.BLACK) {
            return 1 + Math.max(countBlackHeightNode((RBNode<T>) node.getLeft()), countBlackHeightNode((RBNode<T>) node.getRight()));
         } else {
            return Math.max(countBlackHeightNode((RBNode<T>) node.getLeft()), countBlackHeightNode((RBNode<T>) node.getRight()));
         }
      } else
         return 0; // não contar o no como +1 na altura, coloquei -1 tirando do no vazio (quando e empty)
   }
   @Override
   public void insert(T value) {
      if (value != null) {
         RBNode<T> NIL = new RBNode<>();
         this.insert(NIL, (RBNode<T>) this.root, value);
      }
   }

   private void insert(RBNode<T> parent, RBNode<T> node, T element) {
      if (node.isEmpty()) {//se não tiver nada no nó, será inserido o valor do elemento nesse nó
         node.setData(element);
         node.setLeft(new RBNode<T>());
         node.setRight(new RBNode<T>());
         node.getLeft().setParent(node);
         node.getRight().setParent(node);
         if (!node.equals(root)) { // pai de root e null
            node.setParent(parent);
         }
         node.setColour(Colour.RED);

         fixUpCase1(node);
      } else {
         if ((node.getData().compareTo(element)) < 0) {
            insert(node, (RBNode<T>) node.getRight(), element);
         } else {
            insert(node, (RBNode<T>) node.getLeft(), element);
         }
      }
   }

   @SuppressWarnings("deprecation")
   @Override
   public RBNode<T>[] rbPreOrder() {
      ArrayList<RBNode<T>> aux = new ArrayList<>();
      //chamada para organizar.
      preOrder((RBNode<T>) this.root, aux); //bst

      RBNode<T>[] arrayRB = new RBNode[aux.size()];
      arrayRB = aux.toArray(arrayRB);
      return arrayRB;
   }

   private void preOrder(RBNode<T> node, ArrayList<RBNode<T>> aux) {
      //R,E,D
      if (!node.isEmpty()) {
         aux.add(node);
         preOrder((RBNode<T>) node.getLeft(), aux);
         preOrder((RBNode<T>) node.getRight(), aux);
      }
   }

   // FIXUP methods
   protected void fixUpCase1(RBNode<T> node) {
      if (node == root) {
         node.setColour(Colour.BLACK);
      } else {
         fixUpCase2(node);
      }
   }

   protected void fixUpCase2(RBNode<T> node) {
      if (((RBNode<T>) node.getParent()).getColour() == Colour.BLACK) {
         //ok
      } else {
         fixUpCase3(node);
      }
   }

   protected void fixUpCase3(RBNode<T> node) {
      RBNode<T> uncle = getNodeUncle(node);
      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandpa = (RBNode<T>) node.getParent().getParent();

      if (uncle.getColour() == Colour.RED) {
         parent.setColour(Colour.BLACK);
         uncle.setColour(Colour.BLACK);
         grandpa.setColour(Colour.RED);
         fixUpCase1(grandpa);
      } else {
         fixUpCase4(node);
      }
   }

   private RBNode<T> getNodeUncle(RBNode<T> node) {
      if (node.getParent() == node.getParent().getParent().getLeft()) { //o pai de node e filho a esquerda do avo de node
         return (RBNode<T>) node.getParent().getParent().getRight();
      } else { //o pai de node e filho a direita do avo de node
         return (RBNode<T>) node.getParent().getParent().getLeft();
      }
   }

   protected void fixUpCase4(RBNode<T> node) {
      RBNode<T> next = node;
      if (node == node.getParent().getRight() && node.getParent() == node.getParent().getParent().getLeft()) {
         leftRotation((RBNode<T>) node.getParent());
         next = (RBNode<T>) node.getLeft();
      } else if (node == node.getParent().getLeft() && node.getParent() == node.getParent().getParent().getRight()) {
         rightRotation((RBNode<T>) node.getParent());
         next = (RBNode<T>) node.getRight();
      }

      fixUpCase5(next);
   }

   protected void fixUpCase5(RBNode<T> node) {
      RBNode<T> parent = (RBNode<T>) node.getParent();// pai
      parent.setColour(Colour.BLACK);
      RBNode<T> grandpa = (RBNode<T>) node.getParent().getParent();
      grandpa.setColour(Colour.RED); // avo

      if (node == parent.getLeft()) {
         rightRotation(grandpa);
      } else {
         leftRotation(grandpa);
      }
   }

   //metodos auxiliares
   private void rightRotation(RBNode<T> node) {
      RBNode<T> auxNode = (RBNode<T>) Util.rightRotation(node);
      setFather(node, auxNode);
   }

   private void leftRotation(RBNode<T> node) {
      RBNode<T> auxNode = (RBNode<T>) Util.leftRotation(node);
      setFather(node, auxNode);
   }

   private void setFather(RBNode<T> node, RBNode<T> nodeMid) {
      if (node != root) { // verifica se o no e o root
         if (nodeMid.getParent().getLeft() == node) { // verifica se node era o filho a esquerda
            nodeMid.getParent().setLeft(nodeMid);
         } else { // verifica se node era filho a direita
            nodeMid.getParent().setRight(nodeMid);
         }
      } else {
         root = nodeMid;
      }
   }
}
