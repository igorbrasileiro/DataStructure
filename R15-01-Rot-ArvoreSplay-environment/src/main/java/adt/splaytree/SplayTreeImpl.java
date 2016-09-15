package adt.splaytree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;
import adt.bt.Util;

public class SplayTreeImpl<T extends Comparable<T>> extends BSTImpl<T>
		implements SplayTree<T> {

	private void splay(BSTNode<T> node) {
		while(node != root) {

            // caso 1: pai e raiz
            if(node.getParent() == root) {
                // node filho a esquerda, zig a direita
                if (node.getParent().getLeft() == node) {
                    zig_right(node);
                } else if (node.getParent().getRight() == node) { // filho a direita, zig a esquerda
                    zig_left(node);
                }
            } else { // pai nao e raiz
                // caso 2: neto - pai - avo do mesmo lado
                // ambos do lado esquerdo -> zig_zig_direita
                if(node.getParent().getLeft() == node && node.getParent().getParent().getLeft() == node.getParent()) {
                    zig_zig_right(node);
                } else if(node.getParent().getRight() == node && node.getParent().getParent().getRight() == node.getParent()) {
                    zig_zig_left(node);
                } else if(node.getParent().getRight() == node && node.getParent().getParent().getLeft() == node.getParent()) {
                    // caso 3: neto e pai filhos de lados apostos
                    // joelho a esquerda

                    //roda a esquerda deixa todos do mesmo lad
                    zig_left(node);
                    // roda a direita para subir
                    zig_right(node);
                } else if(node.getParent().getLeft() == node && node.getParent().getParent().getRight() == node.getParent()) {
                    // Joelho a direita
                    // roda a direita deixa todos do mesmo lado
                    zig_right(node);
                    // roda a esquerda para subir
                    zig_left(node);
                }
            }
        }
	}

	// insercao remocao e busca

	@Override
	public void insert(T element) {
		if (element != null) {
			BSTNode<T> NIL = new BSTNode<T>();
			insert(NIL, this.root, element);
		}
	}

	private void insert(BSTNode<T> parent, BSTNode<T> node, T element) {
		if (node.isEmpty()) {//se não tiver nada no nó, será inserido o valor do elemento nesse nó
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
            node.getLeft().setParent(node);
            node.getRight().setParent(node);
			if (!node.equals(root)) { // pai de root e null
				node.setParent(parent);
			}
            splay(node);
		} else {
			if ((node.getData().compareTo(element)) < 0) {
				insert(node, (BSTNode<T>) node.getRight(), element);
			} else {
				insert(node, (BSTNode<T>) node.getLeft(), element);
			}
		}
	}

    public void remove(T element) {
        if(element == null || root == null) return;

        BSTNode<T> node = super.search(root,element); // retorna o nil abaixo do parent ou o elemento que quer remover
        BSTNode<T> nodeParent = (BSTNode<T>) node.getParent();

            if(node.isEmpty() && nodeParent != null) splay((BSTNode<T>) node.getParent());
            else if(!node.isEmpty()){

                super.removeRecursive(node); // remove da BST
                if(nodeParent != null) splay((BSTNode<T>) nodeParent);
            }

    }

    @Override
    public BSTNode<T> search(T element) {
        if (element == null || this.root.isEmpty()) {
            return new BSTNode<T>();
        } else {
            BSTNode<T> node = super.search(this.root, element); // utiliza search de BST que retorna nil ligado a parent ou node
            if(node.isEmpty()) {
                splay((BSTNode<T>) node.getParent());
            } else {
                splay(node);
            }
            return node;
        }
    }

    private void zig_right(BSTNode<T> node) {
        rightRotation((BSTNode<T>) node.getParent());
    }

    private void zig_left(BSTNode<T> node) {
        leftRotation((BSTNode<T>) node.getParent());
    }

    private void zig_zig_right(BSTNode<T> node) {
        rightRotation((BSTNode<T>) node.getParent().getParent());
        rightRotation((BSTNode<T>) node.getParent());
    }

    private void zig_zig_left(BSTNode<T> node) {
        leftRotation((BSTNode<T>) node.getParent().getParent());
        leftRotation((BSTNode<T>) node.getParent());
    }

    private void rightRotation(BSTNode<T> node) {
        BTNode<T> nodeMid = Util.rightRotation(node); // retorna nodeMid
        setFather(node, nodeMid);
    }

    private void leftRotation(BSTNode<T> node) {
        BTNode<T> nodeMid = Util.leftRotation(node); // retorna nodeMid
        setFather(node, nodeMid);
    }

    private void setFather(BSTNode<T> node, BTNode<T> nodeMid) {
        if (node != root) { // verifica se o no e o root
            if (nodeMid.getParent().getLeft() == node) { // verifica se node era o filho a esquerda
                nodeMid.getParent().setLeft(nodeMid);
            } else { // verifica se node era filho a direita
                nodeMid.getParent().setRight(nodeMid);
            }
        } else {
            root = (BSTNode<T>) nodeMid;
        }
    }
}