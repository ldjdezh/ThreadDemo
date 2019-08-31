package ch01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadDemo {
    private static class UseRun implements Runnable {

        @Override
        public void run() {
            System.out.println(" run ");
        }
    }

    private static class UseCall implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println(" call ");
            return "result";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UseRun useRun = new UseRun();
        new Thread(useRun).start();

        UseCall useCall = new UseCall();
        FutureTask<String> futureTask = new FutureTask<>(useCall);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
