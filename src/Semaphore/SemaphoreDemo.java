package Semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    private Semaphore sm = new Semaphore(1);
    private int count;

    public void increment() {
        try {
            sm.acquire();
            count++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sm.release();
        }
    }

    public static class Thr extends Thread {
        private SemaphoreDemo sd;

        public Thr(SemaphoreDemo sd) {
            this.sd = sd;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                sd.increment();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SemaphoreDemo sd = new SemaphoreDemo();
        Thr t1 = new Thr(sd);
        Thr t2 = new Thr(sd);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(sd.count);
    }
}
