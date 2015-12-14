package task;

import einstein.EinsteinTask6_2;
import einstein.EinsteinTask9_1;

/**
 * Created by ginva_000 on 05.12.2015.
 */
public class Task2 {

    public static void main(String[] args) {
        EinsteinTask9_1 e = new EinsteinTask9_1();
        e.setN(9);
        e.setM(4);
        e.setRows(3);
        e.setColumns(3);
        e.init(1);
        e.setK(42);
        System.out.println("init");
        e.limit1(0,0,0);
        e.limit1(3,4,4);

        e.limit1(1,0,0);
        e.limit1(1,2,2);
        e.limit1(2,2,2);
        e.limit1(3,6,6);
        e.limit1(2,0,5);
        e.limit1(0,8,2);

//        e.limit1(3,8,1);
//        e.limit1(2,7,5);
//        e.limit1(3,3,0);
//        e.limit1(0,1,2);
        System.out.println("limit1");
//        e.printSolutions();

        e.limit2(0,3, 1,3);
        e.limit2(0,0, 1,0);
        e.limit2(2,1, 3,1);
        e.limit2(3,5, 0,5);
        e.limit2(1,7, 3,7);
        e.limit2(1,6, 3,6);
        System.out.println("limit2");
//        e.printSolutions();

        e.upperLimit(3,4, 3,2);
        e.upperLimit(2,6, 2,4);
        e.upperLimit(1,3, 1,1);
        e.lowerLimit(0,0, 0,4);
        e.lowerLimit(1,1, 1,5);
        e.lowerLimit(2,4, 2,8);


        e.lowerLimit(3,4, 2,8);
        e.lowerLimit(1,0, 1,4);



//        e.rightLimit(1,2,0,2);
//        e.rightLimit(2,6,2,7);

        System.out.println("left-right");
//        e.printSolutions();

        e.besideLimit(0,7, 0,5);
        System.out.println("1");
        e.besideLimit(2,3, 1,7);
        System.out.println("2");
        e.besideLimit(2,4, 2,2);
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
