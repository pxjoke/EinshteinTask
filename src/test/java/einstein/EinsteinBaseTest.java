package einstein;

import net.sf.javabdd.BDD;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EinsteinBaseTest {
    EinsteinBase e;

    @Before
    public void before() {
        e = new EinsteinBase();
        e.setColumns(3);
        e.setRows(1);
        e.setN(3);
        e.setM(3);
        e.init(0);
    }

    @Test
    public void testInit() throws Exception {
        System.out.println(e.getP()[2][1][1]);
        List<byte[]> list = e.getP()[2][1][1].allsat();
        int[] arr = e.getP()[2][1][1].varProfile();
        assertEquals(list.get(0)[10], 1);
        assertEquals(list.get(0)[11], 0);

    }

    @Test
    public void testLBAfterRSh() throws Exception {
        assertTrue(!e.LBAfterRSh(4, 0));
        assertTrue(!e.LBAfterRSh(4, 1));
        assertTrue(e.LBAfterRSh(4, 2));

        assertTrue(e.LBAfterRSh(5, 0));
        assertTrue(!e.LBAfterRSh(5, 1));
        assertTrue(e.LBAfterRSh(5, 2));

    }

    @Test
    public void testLimit6() throws Exception {
        e.limit6();
//        System.out.println(e.task.nodeCount());
        assertEquals(e.getTask().nodeCount(), 18);
//        e.task.printDot();
//        System.out.println(e.task);
    }

    @Test
    public void testLimit5() throws Exception {
        e.limit6();
        e.limit5();
//        System.out.println(e.task);
//        System.out.println(e.task.nodeCount());
        assertEquals(e.getTask().nodeCount(), 320);
    }

    @Test
    public void testLimit1() throws Exception {
        e.limit6();
        e.limit5();
        e.limit1(0, 0, 0);
//        System.out.println(e.task);
//        System.out.println(e.task.nodeCount());
//        System.out.println(e.task.satCount());
        assertEquals(e.getTask().nodeCount(), 193);
    }

    @Test
    public void testLimit2() throws Exception {
        e.limit6();
        e.limit5();
        e.limit1(0, 0, 0);
        e.limit2(1, 2, 2, 0);
//        System.out.println(e.task);
//        System.out.println(e.task.nodeCount());
//        System.out.println(e.task.satCount());
        assertEquals(e.getTask().nodeCount(), 119);
    }

    @Test
    public void testRightLimit() throws Exception {
        e.limit6();
        e.limit5();
        e.limit1(0, 0, 0);
        e.limit2(1, 2, 2, 0);
        e.rightLimit(1, 0, 0, 1);
//        System.out.println(e.task);
//        System.out.println(e.task.nodeCount());
//        System.out.println(e.task.satCount());
        assertEquals(e.getTask().nodeCount(), 73);
    }

    @Test
    public void testBesideLimit() throws Exception {
        e.limit6();
        e.limit5();
        e.limit1(0, 0, 0);
        e.limit2(1, 2, 2, 0);
        e.rightLimit(1, 0, 0, 1);
        e.besideLimit(2, 2, 1, 2);
        System.out.println(e.getTask());
        System.out.println(e.getTask().nodeCount());
        System.out.println(e.getTask().satCount());
        assertEquals(e.getTask().nodeCount(), 60);
        e.printSolutions();
        e.limit2(0, 2, 1, 2);
        e.printSolutions();
        e.limit2(0, 1, 2, 1);
//        e.printSolutions();
    }

    @Test
    public void testSum() throws Exception {
        BDD[] a = new BDD[e.getK_SIZE()];
        BDD[] b = new BDD[e.getK_SIZE()];
        BDD[] c;
        e.toBinary(a, 5);
        e.toBinary(b, 3);
        c = e.sum(a, b);
        for (int i = e.getK_SIZE() -1; i >= 0 ; i--) {
            if (c[i].isOne()) System.out.print("1");
            else System.out.print("0");
        }
        assertTrue(c[3].isOne());
        assertTrue(c[2].isZero());
        assertTrue(c[1].isZero());
        assertTrue(c[0].isZero());
        assertTrue(c[4].isZero());
    }

    @Test
    public void testCompare() throws Exception {
        BDD[] a = new BDD[e.getK_SIZE()];
        BDD[] b = new BDD[e.getK_SIZE()];
        BDD c;
        e.toBinary(a, 5);
        e.toBinary(b, 3);
        c = e.compare(a, b);
        assertTrue(c.isOne());
        e.toBinary(a, 8);
        e.toBinary(b, 9);
        c = e.compare(a, b);
        assertTrue(c.isZero());
        e.toBinary(a, 7);
        e.toBinary(b, 7);
        c = e.compare(a, b);
        assertTrue(c.isOne());
    }

    @Test
    public void testLimit7() throws Exception {
        e.setK(4);
        e.limit6();
        e.limit5();
        e.limit1(0, 0, 0);
        e.limit2(1, 2, 2, 0);
        e.rightLimit(1, 0, 0, 1);
        e.besideLimit(2, 2, 1, 2);
        e.limit7();
        System.out.println(e.getTask());
        System.out.println(e.getTask().nodeCount());
        System.out.println(e.getTask().satCount());
        assertEquals(e.getTask().nodeCount(), 51);
        e.printSolutions();
    }
}