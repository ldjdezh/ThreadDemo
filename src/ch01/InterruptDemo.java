package ch01;

/**
 * 线程中断
 */
public class InterruptDemo {

    private static class UseRun extends Thread {

        @Override
        public void run() {
            while(!isInterrupted()) {
                Thread thread = Thread.currentThread();
                System.out.println(thread.getName() + " ----- " + isInterrupted());
            }
            System.out.println("end ------ " + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseRun useRun = new UseRun();
        useRun.start();
        Thread.sleep(500);
        useRun.interrupt();
    }
}
