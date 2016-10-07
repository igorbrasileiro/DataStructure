package adt.bt;

import adt.bst.BSTNode;
import adt.rbtree.RBNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir o seu filho a direita e retorna-lo em seguida
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(RBNode<T> node) {

		BTNode<T> nodeMid = node.getRight();// 	OS

		node.setRight(nodeMid.getLeft());//RS
		nodeMid.getLeft().setParent(node); // 
		nodeMid.setLeft(node);
		nodeMid.setParent(node.getParent());
		node.setParent(nodeMid);
		
		return (BSTNode<T>) nodeMid;
	}

	/**
	 * A rotacao a direita em node deve subir seu filho a esquerda s retorna-lo em seguida
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(RBNode<T> node) {
		BTNode<T> nodeMid = node.getLeft(); // NULL POINTER, OLHAR NO SEGUNDO IFF

		node.setLeft(nodeMid.getRight()); // OS
		nodeMid.getRight().setParent(node); // RS parente
		nodeMid.setRight(node); // passar o antigo no pai, para direita
		nodeMid.setParent(node.getParent()); // pega o pai do no e seta como pai do no do meio
		node.setParent(nodeMid); // seta o pai do no que foi para direita como sendo o no que subiu, nodeMid

		
		return (BSTNode<T>) nodeMid;
	}

}
