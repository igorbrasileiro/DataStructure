package adt.btree;

import java.util.Collections;
import java.util.LinkedList;

public class BNode<T extends Comparable<T>> {
	protected LinkedList<T> elements; //PODERIA TRABALHAR COM ARRAY TAMBEM
	protected LinkedList<BNode<T>> children; //PODERIA TRABALHAR COM ARRAY TAMBEM
	protected BNode<T> parent;
	protected int maxKeys;
	protected int maxChildren;

	public BNode(int order){
		this.maxChildren = order;
		this.maxKeys = order - 1;
		this.elements = new LinkedList<T>();
		this.children = new LinkedList<BNode<T>>();
	}
	@Override
	public String toString() {
		return this.elements.toString();
	}

	@Override
	public boolean equals(Object obj) {
		boolean resp = false;
		if(obj != null){
			if(obj instanceof BNode){
				if(this.size() == ((BNode<T>) obj).size()){
					resp = true;
					int i = 0;
					while(i<this.size() && resp){
						resp = resp && this.getElementAt(i).equals(((BNode<T>) obj).getElementAt(i));
						i++;
					}
				}
			}
		}
		return resp;
	}
	public boolean isEmpty(){
		return this.size() == 0;
	}
	public int size(){
		return this.elements.size();
	}
	public boolean isLeaf(){
		return this.children.size() == 0;
	}
	public boolean isFull(){
		return this.size()== maxKeys;
	}
	public void addElement(T element){
		this.elements.add(element);
		Collections.sort(elements);
	}
	public void removeElement(T element){
		this.elements.remove(element);
	}
	public void removeElement(int position){
		this.elements.remove(position);
	}
	public void addChild(int position, BNode<T> child){
		this.children.add(position, child);
		child.parent = this;
	}
	public void removeChild(BNode<T> child){
		this.children.remove(child);
	}
	public int indexOfChild(BNode<T> child){
		return this.children.indexOf(child);
	}
	public T getElementAt(int index){
		return this.elements.get(index);
	}
	protected void split(){
		// criar dois novos nos left e right
		// add os nos menores do que a media e maior que a mediana


		int numberOfKeys = this.getMaxChildren();
		int medianIndex = numberOfKeys / 2;
		T medianValue = this.getElementAt(medianIndex);

		BNode<T> left = new BNode<T>(getMaxChildren());
		for (int i = 0; i < medianIndex; i++) {
			left.addElement(this.getElements().get(i));
		}
		if (this.getChildren().size() > 0) {
			for (int j = 0; j <= medianIndex; j++) {
				BNode<T> c = this.getChildren().get(j);
				left.addChild(j,c);
			}
		}

		BNode<T> right = new BNode<T>(getMaxChildren());
		for (int i = medianIndex + 1; i < numberOfKeys; i++) {
			right.addElement(this.getElementAt(i));
		}
		if (this.getChildren().size() > 0) {
			for (int j = medianIndex + 1; j < this.getChildren().size(); j++) {
				BNode<T> c = this.getChildren().get(j);
				right.addChild(j,c);
			}
		}

		if (this.parent == null) {
			// new root, height of tree is increased
			BNode<T> newRoot = new BNode<T>(getMaxChildren());
			newRoot.addElement(medianValue);
			this.parent = newRoot;
			//root = newRoot;
			//this = root;
			medianIndex = getParent().getElements().indexOf(medianValue);
			this.getParent().addChild(medianIndex,left);
			this.addChild(medianIndex+1,right);
		} else {
			// Move the median value up to the parent
			BNode<T> parent = this.parent;
			parent.addElement(medianValue);
			parent.removeChild(this);
			medianIndex = getParent().getElements().indexOf(medianValue);
			parent.addChild(medianIndex,left);
			parent.addChild(medianIndex+1,right);

			//if (parent.numberOfKeys() > maxKeySize) split(parent);
		}
	}

	protected void promote(T element, BNode<T> left, BNode<T> right){
		if (this.parent == null) {
			// new root, height of tree is increased
			BNode<T> newRoot = new BNode<T>(getMaxChildren());
			newRoot.addElement(element);
			this.parent = newRoot;
			//root = newRoot;
			//this = root;
			int medianIndex = getParent().getElements().indexOf(element);
			this.getParent().addChild(medianIndex,left);
			this.addChild(medianIndex+1,right);
		} else {
			// Move the median value up to the parent
			BNode<T> parent = this.parent;
			parent.addElement(element);
			parent.removeChild(this);
			int medianIndex = getParent().getElements().indexOf(element);
			parent.addChild(medianIndex,left);
			parent.addChild(medianIndex+1,right);

			//if (parent.numberOfKeys() > maxKeySize) split(parent);
		}
	}
	public LinkedList<T> getElements() {
		return elements;
	}
	public void setElements(LinkedList<T> elements) {
		this.elements = elements;
	}
	public LinkedList<BNode<T>> getChildren() {
		return children;
	}
	public void setChildren(LinkedList<BNode<T>> children) {
		this.children = children;
	}
	public BNode<T> copy(){
		BNode<T> result = new BNode<T>(maxChildren);
		result.parent = parent;
		for (int i = 0; i < this.elements.size(); i++) {
			result.addElement(this.elements.get(i));
		}
		for (int i = 0; i < this.children.size(); i++) {
			result.addChild(i,((BNode<T>)this.children.get(i)).copy());
		}

		return result;
	}
	public BNode<T> getParent() {
		return parent;
	}
	public void setParent(BNode<T> parent) {
		this.parent = parent;
	}
	public int getMaxKeys() {
		return maxKeys;
	}
	public void setMaxKeys(int maxKeys) {
		this.maxKeys = maxKeys;
	}
	public int getMaxChildren() {
		return maxChildren;
	}
	public void setMaxChildren(int maxChildren) {
		this.maxChildren = maxChildren;
	}


}
