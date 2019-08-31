package ch01;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadMXBeanDemo {
    public static void main(String[] args) {
        //虚拟机线程管理的接口
        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMxBean.dumpAllThreads(true,true);
        for(ThreadInfo threadInfo:threadInfos) {
            System.out.println(threadInfo.getThreadName());
        }
    }
}
