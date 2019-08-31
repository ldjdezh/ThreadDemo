package ch01;

/**
 * 演示对象锁和类锁
 */
public class SynClzAndInst {

    /**
     * 使用类锁
     */
    private static class SynClass extends Thread {
        @Override
        public void run() {
            System.out.println("类锁 ... ");
            synClass();
        }
    }

    private static synchronized void synClass() {
        SleepTools.second(1);
        System.out.println("类锁 进行中 ...");
        SleepTools.second(1);
        System.out.println("类锁 结束了 ...");
    }

    private static class InstanceSyn implements Runnable {
        private SynClzAndInst synClzAndInst;

        public InstanceSyn(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("对象锁 ...");
            synClzAndInst.instance();
        }
    }

    private synchronized void instance() {
        SleepTools.second(3);
        System.out.println("对象锁 运行中 ...");
        SleepTools.second(3);
        System.out.println("对象锁 结束 ...");
    }

    private static class Instance2Syn implements Runnable {
        private SynClzAndInst synClzAndInst;

        public Instance2Syn(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("对象锁2 ...");
            synClzAndInst.instance2();
        }
    }

    private synchronized void instance2() {
        SleepTools.second(3);
        System.out.println("对象锁2 运行中 ...");
        SleepTools.second(3);
        System.out.println("对象锁2 结束 ...");
    }

    public static void main(String[] args) {
//        SynClass synClass = new SynClass();
//        synClass.start();
//        SleepTools.second(1);

        SynClzAndInst synClzAndInst = new SynClzAndInst();
        InstanceSyn instanceSyn = new InstanceSyn(synClzAndInst);
        new Thread(instanceSyn).start();

        SynClzAndInst synClzAndInst2 = new SynClzAndInst();
        Instance2Syn instance2Syn = new Instance2Syn(synClzAndInst2);
        new Thread(instance2Syn).start();
    }
}
