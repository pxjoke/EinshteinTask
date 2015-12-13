package einstein;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.JFactory;
import net.sf.javabdd.MicroFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ginva_000 on 05.12.2015.
 */
public class EinsteinBase {
    int N;
    int M;
    int LOG_N;
    int N_VAR;
    int rows;
    int columns;
    int K_SIZE;
    int K;
//    BDDFactory factory = MicroFactory.init(10000000, 10000000);
    BDDFactory factory;
    BDD[][][] p;
    BDD task;
    BDD[] k;

    public void init(int size) {
        if(size == 0) factory = JFactory.init(10000, 1000);
        else factory = JFactory.init(10000000, 10000000);
        factory.autoReorder(factory.REORDER_WIN3ITE);
        initVars();
        factory.setVarNum(N_VAR);
        task = factory.one();
        int I = 0;
        for (int item = 0; item < N; item++) {
            for (int value = 0; value < N; value++) {
                for (int property = 0; property < M; property++) {
                    p[property][item][value] = factory.one();
                    for (int k = 0; k < LOG_N; k++) {
                        int i = I + k + LOG_N * property;
                        BDD tmp = LBAfterRSh(value, k) ? factory.ithVar(i) : factory.nithVar(i);
                        p[property][item][value].andWith(tmp);
                    }
                }
            }
            I += LOG_N * M;
        }

    }

    public boolean LBAfterRSh(int x, int shift) {
        int tmp = 0;
        for (int i = 0; i <= shift; i++) {
            tmp = x % 2;
            x /= 2;
        }
        return !(tmp == 0);
    }

    public void limit6() {

        for (int item = 0; item < N; item++) {
            BDD[] tmp = new BDD[M];
            for (int property = 0; property < M; property++) {
                tmp[property] = factory.zero();
            }
            for (int value = 0; value < N; value++) {
                for (int property = 0; property < M; property++) {
                    tmp[property] = tmp[property].or(p[property][item][value]);
                }
            }
            for (BDD bdd : tmp) task.andWith(bdd);
        }
    }

    public void limit5() {
        for (int value = 0; value < N; value++) {
            for (int item = 0; item < N - 1; item++) {
                for (int anotherItem = item + 1; anotherItem < N; anotherItem++) {
                    for (int property = 0; property < M; property++) {
                        BDD tmp = p[property][item][value].imp(p[property][anotherItem][value].not());
                        task.andWith(tmp);
                    }
                }
            }
        }
    }

    public void limit1(int property, int item, int value) {
        task = task.and(p[property][item][value]);
    }

    public void limit2(int property1, int value1, int property2, int value2) {
        for (int item = 0; item < N; item++) {
            BDD tmp = (p[property1][item][value1].xor(p[property2][item][value2])).not();
            task.andWith(tmp);
        }
    }

    public BDD locationLimit(int property1, int value1, int property2, int value2, int row, int column) {
        BDD res = factory.one();
        if (row > 0) {
            for (int i = 0; i < columns; i++) {
                BDD tmp = p[property2][index(0, i)][value2].not().andWith(p[property1][index(rows - 1, i)][value1].not());
                res.andWith(tmp);
            }
        } else if (row < 0) {
            for (int i = 0; i < columns; i++) {
                BDD tmp = p[property1][index(0, i)][value1].not().andWith(p[property2][index(rows - 1, i)][value2].not());
                res.andWith(tmp);
            }
        }
        if (column > 0) {
            for (int i = 0; i < rows; i++) {
                BDD tmp = p[property2][index(i, 0)][value2].not().andWith(p[property1][index(i, columns - 1)][value1].not());
                res.andWith(tmp);
            }
        } else if (column < 0) {
            for (int i = 0; i < rows; i++) {
                BDD tmp = p[property1][index(i, 0)][value1].not().andWith(p[property2][index(i, columns - 1)][value2].not());
                res.andWith(tmp);
            }
        }
        for (int i = begin(row); i < rows - end(row); i++) {
            for (int j = begin(column); j < columns - end(column); j++) {
                BDD tmp = (p[property1][index(i, j)][value1].xor(p[property2][index(i + row, j + column)][value2])).not();
                res.andWith(tmp);
            }
        }
        return res;
    }

    public void rightLimit(int property1, int value1, int property2, int value2) {
        task.andWith(locationLimit(property1, value1, property2, value2, 0, 1));
    }

    public void besideLimit(int property1, int value1, int property2, int value2) {
        BDD tmp1 = locationLimit(property1, value1, property2, value2, 0, 1);
        BDD tmp2 = locationLimit(property1, value1, property2, value2, 0, -1);
        task.andWith(tmp1.orWith(tmp2));
    }

    public int begin(int x) {
        return x > 0 ? 0 : Math.abs(x);
    }

    public int end(int x) {
        return x > 0 ? x : 0;
    }

    public int index(int row, int column) {
        return columns * row + column;
    }

    public List<int[][]> getSolution() {
        List<byte[]> solutions = task.allsat();
        List<int[][]> res = new ArrayList<int[][]>(solutions.size());
        for (byte[] solution : solutions) {
            int[][] tmp = new int[N][M];
            int base = 1;
            int property = 0;
            int item = 0;
            int value = 0;
            for (int i = 0; i < N_VAR; i++) {
                value += base * solution[i];
                base *= 2;
                if (i % LOG_N == LOG_N - 1) {
                    tmp[item][property] = value;
                    value = 0;
                    base = 1;
                    if (property % M == M - 1) {
                        property = -1;
                        item++;
                    }
                    property++;
                }
            }
            res.add(tmp);
        }
        return res;
    }

    public void printSolutions() {
        List<int[][]> solutions = getSolution();
        for (int[][] solution : solutions) {
            for (int i = 0; i < N; i++) {
                if (i % columns == 0) System.out.println("");
                System.out.print(i + ":");
                for (int j = 0; j < M; j++) {
                    System.out.print(" "+solution[i][j]);
                }
                System.out.print(";   ");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println(solutions.size() + " solutions");
    }

    public BDD[] sum(BDD[] a, BDD[] b) {
        BDD s = factory.zero();
        BDD sNext = factory.zero();
        BDD[] c = new BDD[K_SIZE];
        for (int i = 0; i < K_SIZE; i++) {
            c[i] = factory.zero();
        }
        for (int i = 0; i < K_SIZE; i++) {
            s = sNext;
            c[i] = a[i].xor(b[i]).xor(s);
            sNext = (a[i].and(s)).or(a[i].not().and(b[i]).and(s)).or(a[i].and(b[i]).and(s.not()));
        }
        return c;
    }

    public void toBinary(BDD[] a, int x) {
        for (int i = 0; i < K_SIZE; i++) {
            if (x % 2 == 1) a[i] = factory.one();
            else a[i] = factory.zero();
            x /= 2;
        }
    }

    public BDD compare(BDD[] a, BDD[] b) {
        BDD res = factory.one();
        BDD[] x = new BDD[K_SIZE];
        for (int i = 0; i < K_SIZE; i++) {
            x[i] = a[i].xor(b[i]).not();
            res = res.and(x[i]);
        }
        for (int i = K_SIZE - 1; i >= 0; i--) {
            BDD tmp = a[i].and(b[i].not());
            for (int j = i + 1; j < K_SIZE; j++) {
                tmp = tmp.and(x[j]);
            }
            res.orWith(tmp);
        }
        return res;
    }

    public void limit7() {
        //type 2
        BDD[] tmp = new BDD[K_SIZE];
        BDD[] sum = new BDD[K_SIZE];
        toBinary(sum, 0);
        toBinary(tmp, 0);
        for (int item = 0; item < N; item++) {
            for (int property = 0; property < M; property++) {
                for (int i = 0; i < LOG_N; i++) {
                    tmp[i] = factory.ithVar((M * item + property) * LOG_N+i);
                }
                sum = sum(sum, tmp);
            }
            task.andWith(compare(k,sum));
            toBinary(sum,0);
        }
    }

    public void setK(int K) {
        this.K = K;
        toBinary(k, K);
    }

    public void setN(int n) {
        N = n;
    }

    public void setM(int m) {
        M = m;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    private void initVars() {
        LOG_N = (int)(Math.log(N)/Math.log(2))+1;
        N_VAR = N*M*LOG_N;
        p = new BDD[M][N][N];
        K_SIZE = N_VAR;
        k = new BDD[K_SIZE];
    }

    public int getK_SIZE() {
        return K_SIZE;
    }

    public BDD getTask() {
        return task;
    }

    public BDD[][][] getP() {
        return p;
    }

    //should be done: make getters safe, improve location limit, improve 7 limit
}
