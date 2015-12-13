package einstein;

import net.sf.javabdd.BDD;

/**
 * Created by ginva_000 on 11.12.2015.
 */
public class EinsteinTask6_2 extends EinsteinBase{
    @Override
    public void rightLimit(int property1, int value1, int property2, int value2) {
        task.andWith(locationLimit(property1, value1, property2, value2, -1, 1));
    }

    public void leftLimit(int property1, int value1, int property2, int value2) {
        task.andWith(locationLimit(property1, value1, property2, value2, 1, -1));
    }

    @Override
    public void besideLimit(int property1, int value1, int property2, int value2) {
        BDD tmp1 = locationLimit(property1, value1, property2, value2, -1, 1);
        System.out.println("tmp1");
        BDD tmp2 = locationLimit(property1, value1, property2, value2, 1, -1);
        System.out.println("tmp2");
        tmp1.orWith(tmp2);
        System.out.println("or");
        task.andWith(tmp1);

    }
}
