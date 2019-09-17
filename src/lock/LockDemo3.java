package lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo3 {

    private Lock lock = new ReentrantLock();
    private int count;

    public void increment() {
        try {
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " 开始干活");
            Thread.sleep(2000);
            count++;
            System.out.println(Thread.currentThread().getName() + " 干完活了 " + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + " 我被人中断了...");
        } finally {
            System.out.println(Thread.currentThread().getName() + " 锁不要了！！！");
            lock.unlock();
        }
    }

    public static class Thr implements Runnable {

        private LockDemo3 obj;

        public Thr(LockDemo3 obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            obj.increment();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo3 obj = new LockDemo3();
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
