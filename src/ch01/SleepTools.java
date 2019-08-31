package ch01;

import java.util.concurrent.TimeUnit;

/**
 * 线程休眠辅助工具类
 */
public class SleepTools {

    /**
     * 按秒休眠
     * @param seconds
     */
    public static final void second(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {

        }
    }

    /**
     * 按毫秒数休眠
     * @param ms
     */
    public static final void ms(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {

        }
    }
}
