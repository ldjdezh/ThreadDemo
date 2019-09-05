package zhuanglan.test;

import java.util.ArrayList;
import java.util.List;

public class Allocator {
    private List<Object> als = new ArrayList<>();

    /**
     * 一次性申请两个资源
     *
     * @param from
     * @param to
     * @return
     */
    public synchronized boolean apply(Object from, Object to) {
        if (als.contains(from) || als.contains(to)) {
            return false;
        } else {
            als.add(from);
            als.add(to);
            return true;
        }
    }

    /**
     * 释放资源
     *
     * @param from
     * @param to
     */
    public synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
    }
}
