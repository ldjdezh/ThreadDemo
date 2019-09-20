package Semaphore;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class ObjPool<T, R> {
    final List<T> pool;
    final Semaphore sem;

    public ObjPool(int size,T t) {
        pool = new Vector<>();
        for(int i=0;i<size;i++) {
            pool.add(t);
        }

        sem = new Semaphore(size);
    }

    public R exec(Function<T,R> func) {
        T t = null;
        try {
            sem.acquire();
            t = pool.remove(0);
            return func.apply(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.add(t);
            sem.release();
        }
        return null;
    }

    public static void main(String[] args) {
        ObjPool<Long,String> pool = new ObjPool<Long, String>(10, (long) 2);
        pool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }
}
