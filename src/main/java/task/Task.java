package task;

import einstein.EinsteinBase;
import einstein.EinsteinTask6_2;

/**
 * Created by ginva_000 on 05.12.2015.
 */
public class Task {

    public static void main(String[] args) {
        EinsteinTask6_2 e = new EinsteinTask6_2();
        e.setN(9);
        e.setM(4);
        e.setRows(3);
        e.setColumns(3);
        e.init(1);
        e.setK(17);
        System.out.println("init");
        e.limit1(0,0,0);
        e.limit1(3,4,4);
        e.limit1(2,2,2);
        e.limit1(3,6,6);

        e.limit1(3,8,1);
        e.limit1(2,7,5);
        e.limit1(3,3,0);
        System.out.println("limit1");
//        e.printSolutions();

        e.limit2(0,3, 1,3);
        e.limit2(0,0, 1,0);
        e.limit2(2,1, 3,1);
        e.limit2(3,5, 0,5);
        e.limit2(1,7, 3,7);
        System.out.println("limit2");
//        e.printSolutions();

        e.rightLimit(3,4, 2,2);
        e.leftLimit(0,4, 3,6);
        e.leftLimit(2,4, 0,6);

        System.out.println("left-right");
//        e.printSolutions();

        e.besideLimit(0,1, 3,3);
        System.out.println("1");
        e.besideLimit(2,2, 1,4);
        System.out.println("beside");
//        e.printSolutions();

        e.limit7();
        System.out.println("limit7");
//        e.printSolutions();

        e.limit6();
        System.out.println("limit6");
//        e.printSolutions();

        e.limit5();
        System.out.println("limit5");
        e.printSolutions();
    }


}
