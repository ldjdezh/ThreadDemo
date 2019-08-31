package ch01;

public class UseJoin {

    static class JumpQueue implements Runnable {
        private Thread thread;

        public JumpQueue(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                System.out.println(thread.getName() + " 将插队 " + Thread.currentThread().getName());
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 已完成");
        }
    }

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new JumpQueue(previous), String.valueOf(i));
            System.out.println(previous.getName() + "要插队了 " + thread.getName());
            thread.start();
            previous = thread;
        }

        SleepTools.second(2);
        System.out.println(Thread.currentThread().getName() + " 已完成");
    }
}
