package my;

/**
 * 这好像就是CountDownLatch
 */
public class MyCountDownLatch {
    private int count;

    public MyCountDownLatch(int count) {
        this.count = count;
    }

    public synchronized void await() throws InterruptedException {
        while (count > 0) {
            wait();
        }
    }

    public synchronized void countDown() {
        count--;
        notifyAll();
    }
}
