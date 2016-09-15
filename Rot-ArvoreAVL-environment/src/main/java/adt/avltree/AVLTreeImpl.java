package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		if (!node.isEmpty()) {
			return super.height((BSTNode<T>) node.getRight()) - super.height((BSTNode<T>) node.getLeft()); // altura esquerda - direita
		} else {
			return 0;
		}
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		int balance = calculateBalance(node);
		if (balance > 1) { // direita maior que esquerda
			if (calculateBalance((BSTNode<T>) node.getRight()) < 0) { // joelho a direita
				doubleLeftRotation(node);
			} else {
				leftRotation(node);
			}
		} else if (balance < -1) { // esquerda maior do que direita
			if (calculateBalance((BSTNode<T>) node.getLeft()) > 0) { // joelho a esquerda
				doubleRightRotation(node);
			} else
				rightRotation(node);
		}
	}

	private void doubleLeftRotation(BSTNode<T> node) {
		rightRotation((BSTNode<T>) node.getRight());
		leftRotation(node);
	}

	private void doubleRightRotation(BSTNode<T> node) {
		leftRotation((BSTNode<T>) node.getLeft());
		rightRotation(node);
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		// rebalancear de baixo pra cima
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (parent != null) {
			rebalance(parent);
			parent = (BSTNode<T>) parent.getParent();
		}
	}

	// AUXILIARY
	protected void leftRotation(BSTNode<T> node) {
		BTNode<T> nodeMid = Util.leftRotation(node); // retorna nodeMid

		if (node != root) {
			if (nodeMid.getParent().getLeft() == node) { // verifica se node era o filho a esquerda
				nodeMid.getParent().setLeft(nodeMid);
			} else { // verifica se node era filho a direita
				nodeMid.getParent().setRight(nodeMid);
			}
		} else {
			root = (BSTNode<T>) nodeMid;
		}
	}

	// AUXILIARY
	protected void rightRotation(BSTNode<T> node) {
		BTNode<T> nodeMid = Util.rightRotation(node);

		if (node != root) {
			if (nodeMid.getParent().getLeft() == node)
				nodeMid.getParent().setLeft(nodeMid);
			else
				nodeMid.getParent().setRight(nodeMid);
		} else {
			root = (BSTNode<T>) nodeMid;
		}
	}

	//sobrescrever metodos da BST
	@Override
	public void insert(T element) {

		if (isEmpty() && element != null) {
			root.setData(element);
			root.setLeft(new BSTNode<T>());
			root.setRight(new BSTNode<T>());
		} else if (element != null)
			insert((BTNode<T>) this.getRoot(), element);

	}

	private void insert(BTNode<T> node, T element) {

		if (element.compareTo(node.getData()) < 0) {

			if (node.getLeft().isEmpty()) {
				node.getLeft().setData(element);

				node.getLeft().setParent(node);
				node.getRight().setParent(node);

				node.getLeft().setLeft(new BSTNode<T>());
				node.getLeft().setRight(new BSTNode<T>());
			} else {
				insert(node.getLeft(), element);
				rebalance((BSTNode<T>) node);// fazer rebalance de baixo pra cima, garante que ate root ta balancead
			}

		} else if (element.compareTo(node.getData()) > 0) {

			if (node.getRight().isEmpty()) {

				node.getRight().setData(element);

				node.getLeft().setParent(node);
				node.getRight().setParent(node);

				node.getRight().setLeft(new BSTNode<T>());
				node.getRight().setRight(new BSTNode<T>());

			} else {
				insert(node.getRight(), element);
				rebalance((BSTNode<T>) node);// fazer rebalance de baixo pra cima, garante que ate root ta balanceado
			}
		}
	}

	@Override
	public void remove(T element) {
		if (element != null) {

			BSTNode<T> node = search(element);
			if (!node.isEmpty()) {

				if (node == root && size() == 1) {
					root = new BSTNode<T>();

				} else if (node == root) {
					BSTNode<T> auxNode = sucessor(node.getData());

					if (auxNode == null) {
						auxNode = predecessor(node.getData());
					}

					T aux = auxNode.getData();
					auxNode.setData(node.getData());
					node.setData(aux);

					remove(auxNode);

				} else
					remove(node);
			}
		}
	}

	private void remove(BSTNode<T> node) {

		boolean flagParent = false;

		if (node.getParent() != null) {
			flagParent = node == node.getParent().getLeft();
		}
		// case base, sem filho algum
		if (node.getLeft().isEmpty() && node.getRight().isEmpty()) {

			node.setData(null);
			node.setRight(null);
			node.setLeft(null);

			rebalanceUp(node);// rebalancear de baixo para cima o nota, sempre, para garantir que esta balancead e os nos de cima
		} else if (node.getLeft().isEmpty()) { //filho a direita

			if (node.getParent() != null) {
				if (flagParent) {
					node.getParent().setLeft(node.getRight());
				} else
					node.getParent().setRight(node.getRight());
			}
			node.getRight().setParent(node.getParent());
			rebalanceUp(node);

		} else if (node.getRight().isEmpty()) { // filho a esquerda

			if (node.getParent() != null) {
				if (flagParent) {
					node.getParent().setLeft(node.getLeft());
				} else {
					node.getParent().setRight(node.getLeft());
				}
			}
			node.getLeft().setParent(node.getParent());
			rebalanceUp(node);

		} else { // 2 filhos
			BSTNode<T> sucessor = sucessor(node.getData());
			T aux = sucessor.getData();
			sucessor.setData(node.getData());
			node.setData(aux);
			remove(sucessor);
		}
	}

}
