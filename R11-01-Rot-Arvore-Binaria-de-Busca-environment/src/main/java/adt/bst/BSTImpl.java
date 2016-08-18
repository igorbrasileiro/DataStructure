package adt.bst;

import java.util.ArrayList;


import adt.bt.BTNode;

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
		if(node.isEmpty()) {
			return -1; // caso seja vazio
		} else {
			return 1 + Math.max(height((BSTNode<T>) node.getLeft()), height((BSTNode<T>) node.getRight()));
		}
	}

	@Override
	public BSTNode<T> search(T element) {
		if(element != null)	return search(element, root);
		else return null;
	}

	private BSTNode<T> search(T element, BSTNode<T> node) {

		if(!node.isEmpty()){
			// caso base
			if(element.equals(node.getData())) return node;
			else {
				if(element.compareTo(node.getData()) < 0) {
					return search(element, (BSTNode<T>) node.getLeft());
				} else if(element.compareTo(node.getData()) > 0) {
					return search(element, (BSTNode<T>) node.getRight());
				}
			}
		} else return node; // retorna node NIL
		
		// questao de compilacao else if, porem professor nao insere elemento igual
		// nunca chegara aqui pelo que professor disse
		return new BSTNode<T>();
		
	}

	@Override
	public void insert(T element) {
		if(element != null) {
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
		if(node.isEmpty()) {
			node.setData(element);

			node.setLeft(new BSTNode<T>());
			node.getLeft().setParent(node);

			node.setRight(new BSTNode<T>());
			node.getRight().setParent(node);
		} else {
			if(element.compareTo(node.getData()) < 0) {
				insert(element, (BSTNode<T>) node.getLeft());
			} else if(element.compareTo(node.getData()) > 0) {
				insert(element, (BSTNode<T>) node.getRight());
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		BSTNode<T> auxNode = null;
		if(!root.isEmpty()) {
			auxNode = root;
			while(!auxNode.getRight().isEmpty()) {
				auxNode = (BSTNode<T>) auxNode.getRight();
			}
		}

		return auxNode;
	}

	@Override
	public BSTNode<T> minimum() {
		BSTNode<T> auxNode = null;
		if(!root.isEmpty()) {
			auxNode = root;
			while(!auxNode.getLeft().isEmpty()) {
				auxNode = (BSTNode<T>) auxNode.getLeft();
			}
		}

		return auxNode;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		if(element != null) {
			
			BSTNode<T> auxNode = search(element);
			//fazer verificar se element é o ultimo
			if(auxNode.equals(maximum())) return null;
			
			// verifica se auxNode e diferente NIL
			if(!auxNode.isEmpty()) {
				//verifica se direita existe direita
				if(!auxNode.getRight().isEmpty()) {
					auxNode = (BSTNode<T>) auxNode.getRight();
					// buscar o menor do elemento a direia
					while(!auxNode.getLeft().isEmpty()) {
						auxNode = (BSTNode<T>) auxNode.getLeft();
					} // auxNode e o menor
					return auxNode;
					
				} else { // segunda parte do algoritmo
					// procurar pelo pai
					BSTNode<T> auxRight = auxNode;
					auxNode = (BSTNode<T>) auxNode.getParent();					
					// pai nao pode ser null
					while( auxNode != null && auxRight.equals(auxNode.getRight())) {
						auxRight = auxNode;
						auxNode = (BSTNode<T>) auxNode.getParent();
					} // auxNode e o sucessor
					return auxNode;
				}
				
			} else return null;  // se node for nil
			
			
		} return null; // caso elemento seja null
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		if(element != null) {
			BSTNode<T> auxNode = search(element);
			//verificar se e o menor elemento
			if(auxNode.equals(minimum())) return null;
			
			//verifica se auxNode e diferente nil
			if(!auxNode.isEmpty()) {
				// verifica se esquerda existe
				if(!auxNode.getLeft().isEmpty()) {
					auxNode = (BSTNode<T>) auxNode.getLeft();
					// verifica se right existe
					while(!auxNode.getRight().isEmpty()) {
						auxNode = (BSTNode<T>) auxNode.getRight();
					} // auxNode e o maior
					return auxNode;
				} else { //segunda parte algoritmo
					// procura pelo pai
					BSTNode<T> auxLeft = auxNode;
					auxNode = (BSTNode<T>) auxNode.getParent();
					// pai nunca pode ser null
					while(auxNode != null && auxLeft.equals(auxNode.getLeft())) {
						auxLeft = auxNode;
						auxNode = (BSTNode<T>) auxNode.getParent();
					} // auxNode e antecessor
					return auxNode;
				}
			} else return null; // se node for nil
			
		} else return null; //caso element seja null
		
	}

	@Override
	public void remove(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
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
		if(!node.isEmpty()) {
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

		if(!node.isEmpty()) {
			preOrder(result, (BSTNode<T>) node.getLeft());
			result.add(node.getData());
			preOrder(result, (BSTNode<T>) node.getRight());
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
		if(!node.isEmpty()) {
			preOrder(result, (BSTNode<T>) node.getLeft());
			result.add(node.getData());
			preOrder(result, (BSTNode<T>) node.getRight());
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
			result = 1 + size((BSTNode<T>) node.getLeft())
			+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}
	
	private T[] makeArray(ArrayList<T> aux) {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Comparable[aux.size()];
		for(int i = 0; i < aux.size(); i++) {
			result[i] = aux.get(i);
		}
		
		return result;
	}

}
