package ch01;

public class InterruptDemo3 {

    private static class UseRun extends Thread {
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            while(!isInterrupted()) {
                System.out.println(thread.getName() + " ------ " + isInterrupted());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 抛出InterruptedException时，若想中断，需调用interrupt()方法
                    interrupt();
                    System.out.println("sleep ------ " + isInterrupted());
                }
            }
            System.out.println("end ----- " + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseRun useRun = new UseRun();
        useRun.start();
        Thread.sleep(500);
        useRun.interrupt();
    }
}
