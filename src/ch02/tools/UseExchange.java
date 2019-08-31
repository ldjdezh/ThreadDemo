package ch02.tools;

import java.util.Set;
import java.util.concurrent.Exchanger;

public class UseExchange {
    private static final Exchanger<Set<String>> exchange = new Exchanger<>();
    private static volatile int num = 0;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 1 b ---> " + num);
//                num = 100;
//                System.out.println(Thread.currentThread().getName() + " 1 e ---> " + num);
            }
        }).start();
//        Thread.sleep(500);
//        num = 500;
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 2 b ---> " + num);
//                num = 200;
//                System.out.println(Thread.currentThread().getName() + " 2 e ---> " + num);
            }
        }).start();
        num = 700;
    }
}
