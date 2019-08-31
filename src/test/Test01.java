package test;

public class Test01 {
    private long count = 0;

    private void add10K() {
        int idx = 0;
        while(idx++ < 10000) {
            count += 1;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Test01 test01 = new Test01();

        Thread th1 = new Thread(() -> {
           test01.add10K();
        });

        Thread th2 = new Thread(() -> {
            test01.add10K();
        });

        th1.start();
        th2.start();

        th1.join();
        th2.join();

        System.out.println(test01.count);
    }
}
