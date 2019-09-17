package lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Lock的tryLock来实现非阻塞的获取锁
 */
public class LockDemo {

    private Lock lock = new ReentrantLock();

    private AtomicInteger ai = new AtomicInteger(0);

    private int count;

    public void increament() {
        /**
         * tryLock()一调用就会立即返回，如果拿到锁   --> true <br>
         *                            如果拿不到锁 --> false
         */
        if (lock.tryLock()) {
            try {
                count++;
//                System.out.println(Thread.currentThread().getName() + " --> " + count);
            } finally {
                lock.unlock();
            }
        }

        // 虽然结果未必会是2000，但调用该的次数一定会是2000
        ai.incrementAndGet();
    }

    public static class Thr implements Runnable {

        private LockDemo ld;

        public Thr(LockDemo ld) {
            this.ld = ld;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                ld.increament();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo ld = new LockDemo();
        Thread t1 = new Thread(new Thr(ld));
        Thread t2 = new Thread(new Thr(ld));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(ld.count);
        System.out.println("end " + ld.ai);
    }
}
