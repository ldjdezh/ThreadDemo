package condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }

            items[putptr] = x;
            if (++putptr == items.length) {
                putptr = 0;
            }

            ++count;
            notEmpty.signal();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 放东西");
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();

        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object x = items[takeptr];

            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --count;
            notFull.signal();
            System.out.println(Thread.currentThread().getName() + " 取东西 " + x);
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static class PutThr extends Thread {
        private ConditionDemo obj;

        public PutThr(ConditionDemo obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            Integer x = (int)(Math.random() * 100);
            try {
                obj.put(x);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class TakeThr extends Thread {
        private ConditionDemo obj;

        public TakeThr(ConditionDemo obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " 我要取东西");
                Integer x = (Integer)obj.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo cd = new ConditionDemo();


        for(int i=0;i<20;i++) {
            new TakeThr(cd).start();
        }

        Thread.sleep(2000);

        for(int i=0;i<20;i++) {
            new PutThr(cd).start();
        }
    }
}
