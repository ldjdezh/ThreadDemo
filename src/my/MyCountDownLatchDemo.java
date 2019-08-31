package my;

import ch01.SleepTools;

public class MyCountDownLatchDemo {

    public static MyCountDownLatch myCountDownLatch;

    public static class Run implements Runnable {
        private MyCountDownLatch myCountDownLatch;

        public Run(MyCountDownLatch myCountDownLatch) {
            this.myCountDownLatch = myCountDownLatch;
        }

        @Override
        public void run() {
            int num = (int) (Math.random() * 10);
            SleepTools.second(num);
            System.out.println(Thread.currentThread().getName() + " 执行了 " + num);
            myCountDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        myCountDownLatch = new MyCountDownLatch(20);

        for (int i = 0; i < 20; i++) {
            new Thread(new Run(myCountDownLatch), i + "").start();
        }

        myCountDownLatch.await();

        System.out.println("主线程执行了");
    }
}
