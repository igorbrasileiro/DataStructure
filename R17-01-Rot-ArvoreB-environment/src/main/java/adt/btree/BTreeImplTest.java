package adt.btree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by igor on 27/09/16.
 */
public class BTreeImplTest {
    BTreeImpl<Integer> bt;
    @Before
    public void setUp() throws Exception {
        bt = new BTreeImpl<>(3);
    }

    @Test
    public void height() throws Exception {
        //23, 48, 12, 33, 40, 43, 45, 49, 70, 41, 42
        bt.insert(23);
        assertEquals(0, bt.height());

        bt.insert(48);
        assertEquals(0, bt.height());

        bt.insert(12);
        assertEquals(1, bt.height());
        //novo root e 23 agora
        assertEquals(23, bt.getRoot().getElements().get(0).intValue());

        bt.insert(33);
        assertEquals(1, bt.height());

        bt.insert(40);
        assertEquals(23, bt.getRoot().getElements().get(0).intValue());
        assertEquals(40, bt.getRoot().getElements().get(1).intValue());


        bt.insert(43);
        assertEquals(1, bt.height());

        // novo root  sera 40
        bt.insert(45);
        assertEquals(2,bt.height());

        bt.insert(49);
        assertEquals(2,bt.height());

        bt.insert(70);
        assertEquals(2,bt.height());

        bt.insert(41);
        assertEquals(2,bt.height());

        bt.insert(42);
        assertEquals(2,bt.height());
        assertEquals(40, bt.getRoot().getElements().get(0).intValue());
        assertEquals(45, bt.getRoot().getElements().get(1).intValue());
    }

    @Test
    public void depthLeftOrder() throws Exception {

    }

    @Test
    public void size() throws Exception {

        bt.insert(23);
        assertEquals(1, bt.size());

        bt.insert(48);
        assertEquals(2, bt.size());

        bt.insert(12);
        assertEquals(3, bt.size());
        //novo root e 23 agora
        assertEquals(23, bt.getRoot().getElements().get(0).intValue());

        bt.insert(33);
        assertEquals(4, bt.size());

        bt.insert(40);
        assertEquals(5,bt.size());
        assertEquals(23, bt.getRoot().getElements().get(0).intValue());
        assertEquals(40, bt.getRoot().getElements().get(1).intValue());


        bt.insert(43);
        assertEquals(6, bt.size());

        // novo root  sera 40
        bt.insert(45);
        assertEquals(7,bt.size());

        bt.insert(49);
        assertEquals(8,bt.size());

        bt.insert(70);
        assertEquals(9, bt.size());

        bt.insert(41);
        assertEquals(2,bt.height());
        assertEquals(10,bt.size());

        bt.insert(42);
        assertEquals(11,bt.size());
        assertEquals(40, bt.getRoot().getElements().get(0).intValue());
        assertEquals(45, bt.getRoot().getElements().get(1).intValue());
    }

    @Test
    public void search() throws Exception {

    }

    @Test
    public void insert() throws Exception {

    }
}
