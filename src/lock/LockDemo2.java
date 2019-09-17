package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo2 {

    private Lock lock = new ReentrantLock();
    private int count;

    public void increment() {
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                try {
                    System.out.println(Thread.currentThread().getName() + "开始干活了");
                    Thread.sleep(2000);
                    count++;
                    System.out.println(Thread.currentThread().getName() + "干完活了 " + count);
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + "被人中断了");
        }
    }

    public static class Thr implements Runnable {
        private LockDemo2 obj;
        private Thread t;

        public Thr(LockDemo2 obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            obj.increment();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo2 obj = new LockDemo2();
        Thread t1 = new Thread(new Thr(obj));
        Thread t2 = new Thread(new Thr(obj));

        t1.start();
        Thread.sleep(500);
        t2.start();

        t2.interrupt();

        t1.join();
        t2.join();

        System.out.println(obj.count);
    }
}
