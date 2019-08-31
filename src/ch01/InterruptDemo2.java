package ch01;

/**
 * 线程中断
 */
public class InterruptDemo2 {

    private static class UseRun implements Runnable {

        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            while(!thread.isInterrupted()) {
                System.out.println(thread.getName() + " ----- " + thread.isInterrupted());
            }
            System.out.println("end ----- " + thread.isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new UseRun());
        thread.start();
        Thread.sleep(500);
        thread.interrupt();

    }
}
