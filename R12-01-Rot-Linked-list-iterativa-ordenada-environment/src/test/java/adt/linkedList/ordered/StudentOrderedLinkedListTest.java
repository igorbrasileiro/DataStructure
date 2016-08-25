package adt.linkedList.ordered;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StudentOrderedLinkedListTest {

	private OrderedLinkedList<Integer> lista1;
	private OrderedLinkedList<Integer> lista2;

	@Before
	public void setUp() throws Exception {

		getImplementations();

		// Lista com 3 elementos.
		lista1.insert(3);
		lista1.insert(1);
		lista1.insert(2);

	}

	private void getImplementations() {
		// TODO O aluno deve ajustar aqui para instanciar sua implementação
		Comparator<Integer> comparador = null;
		lista1 = new OrderedSingleLinkedListImpl<>();
		lista2 = new OrderedDoubleLinkedListImpl<>((o1, o2) -> (o1.compareTo(o2)));
	}

	@Test
	public void testIsEmpty() {
		Assert.assertFalse(lista1.isEmpty());
		Assert.assertTrue(lista2.isEmpty());
	}

	@Test
	public void testSize() {
		Assert.assertEquals(3, lista1.size());
		Assert.assertEquals(0, lista2.size());
	}

	@Test
	public void testSearch() {
		Assert.assertTrue(2 == lista1.search(2));
		Assert.assertNull(lista1.search(4));
		Assert.assertFalse(3 == lista1.search(2));
	}

	@Test
	public void testInsert() {
		Assert.assertEquals(3, lista1.size());
		lista1.insert(5);
		lista1.insert(7);
		Assert.assertEquals(5, lista1.size());

		Assert.assertEquals(0, lista2.size());
		lista2.insert(4);
		lista2.insert(7);
		Assert.assertEquals(2, lista2.size());
	}

	@Test
	public void testRemove() {
		Assert.assertEquals(3, lista1.size());
		//((OrderedDoubleLinkedListImpl<Integer>) lista1).removeFirst(); // 2-3
		//((OrderedDoubleLinkedListImpl<Integer>) lista1).removeLast(); // 2
		lista1.remove(2);
		lista1.remove(1);
		Assert.assertEquals(1, lista1.size());
	}

	@Test
	public void testToArray() {
		Assert.assertArrayEquals(new Integer[] {}, lista2.toArray());
		Assert.assertArrayEquals(new Integer[] { 1, 2, 3 }, lista1.toArray());
	}
	
	@Test
	public void testMinimum() {
		Assert.assertNull(lista2.minimum());
		Assert.assertEquals(new Integer(1), lista1.minimum());
	}
	@Test
	public void testMaximum() {
		Assert.assertNull(lista2.maximum());
		Assert.assertEquals(new Integer(3), lista1.maximum());
	}
}