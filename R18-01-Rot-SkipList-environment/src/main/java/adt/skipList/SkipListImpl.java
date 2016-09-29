package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int height;
	protected int maxHeight;

	protected boolean USE_MAX_HEIGHT_AS_HEIGHT;
	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		if (USE_MAX_HEIGHT_AS_HEIGHT) {
			this.height = maxHeight;
		} else {
			this.height = 1;
		}
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		if(USE_MAX_HEIGHT_AS_HEIGHT) {
			int index = maxHeight-1;
			while(root.getForward()[index] == null) {
				root.getForward()[index] = NIL;
				index--;
			}
		} else {
			root.getForward()[0] = NIL;
		}
	}

	/**
	 * Metodo que gera uma altura aleatoria para ser atribuida a um novo no no
	 * metodo insert(int,V)
	 */
	private int randomLevel() {
		int randomLevel = 1;
		double random = Math.random();
		while (Math.random() <= PROBABILITY && randomLevel < maxHeight) {
			randomLevel = randomLevel + 1;
		}
		return randomLevel;
	}

	@Override
	public void insert(int key, T newValue, int height) {
		if(newValue != null) {
			SkipListNode<T>[] update = new SkipListNode[height];
			SkipListNode<T> auxNode = root;

			//pesquisa local, igual search
			for(int i = height-1; i >= 0; i--) {
				while(auxNode.getForward(i) != null && auxNode.getForward(i).getKey() < key) {
					auxNode = auxNode.getForward(i);
				}
				update[i] = auxNode; // atualiza onde passou
			}

			auxNode = auxNode.getForward(0);

			if(auxNode.getKey() == key) {
				auxNode.setValue(newValue);
			} else {

				ajustRootToNIL(height, update);

				auxNode = new SkipListNode<T>(key,height,newValue);

				changingOldForwards(height, update, auxNode);
			}
		}
	}

	// metodos utilizados na inserção

	/**
	 * change old forwards, from existing nodes, to new node
	 * @param height
	 * @param update
	 * @param newNode
	 */
	private void changingOldForwards(int height, SkipListNode<T>[] update, SkipListNode<T> newNode) {
		for(int i = 0; i < height; i++) {

			if(update[i].getForward(i) == null) {
				newNode.getForward()[i] = NIL; // para nao apontar para null, que é o forward de nil e tambem nao apontar o forward de nill pra node
			} else {
				newNode.forward[i] = update[i].forward[i];
				update[i].forward[i] = newNode;
			}

		}
	}

	/**
	 * connect root forward to nil, for new height and updateArray
	 * @param height
	 * @param updateArray
	 */
	private void ajustRootToNIL(int height, SkipListNode<T>[] updateArray) {
		if(height > this.height) {
			for(int i = this.height; i < height; i++) {
				root.getForward()[i] = NIL;
			}

			this.height = height;
		}
	}
	// acaba aqui


	@Override
	public void remove(int key) {

		SkipListNode<T>[] update = new SkipListNode[height];
		SkipListNode<T> auxNode = root;

		//pesquisa local, igual search
		for(int i = height-1; i >= 0; i--) {
			while(auxNode.getForward(i) != null && auxNode.getForward(i).getKey() < key) {
				auxNode = auxNode.getForward(i);
			}
			update[i] = auxNode; // atualiza onde passou
		}

		auxNode = auxNode.getForward(0);

		if(auxNode.getKey() == key) {
			// remover os ponteiros
			for(int i = 0; i < height; i++) {
				if(!update[i].getForward()[i].equals(auxNode)) {
					break; /// TRANSFORMAR EM WHILE
				}
				update[i].getForward()[i] = auxNode.getForward()[i];
			}

			// ajeitar a altura
			while(this.height-1 > 0 && root.getForward()[height-1] == NIL) {
				height--;
			}
		}
	}

	@Override
	public int height() {
		int height = this.height;
		while(height >= 0 && root.getForward(height-1) == NIL) {
			height--;
		}

		return height;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> auxNode = this.root;

		for(int i = height-1; i >= 0; i--){
			while(auxNode.getForward(i) != null && auxNode.getForward(i).getKey() < key) {
				auxNode = auxNode.getForward(i);
			}
		}

		SkipListNode<T> result = auxNode.getForward(0);

		if(result.getKey() != key) {
			result = null;
		}

		return result;
	}

	@Override
	public int size() {
		int count = 0;
		SkipListNode<T> auxNode = this.root.getForward(0);
		while(auxNode != NIL) {
			count++;
			auxNode = auxNode.getForward(0);
		}

		return count;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		if(USE_MAX_HEIGHT_AS_HEIGHT) {
			connectRootToNil();
		}

		int size = size() +2;// incluir o root e nil do final
		SkipListNode<T>[] result = new SkipListNode[size];

		SkipListNode<T> auxNode = this.root;
		int index = 0;

		while(index < size) { // incluir o root e nil, por isso menor igual
			result[index] = auxNode;
			index++;
			auxNode = auxNode.getForward(0);
		}

		return result;
	}

}
