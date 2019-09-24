package ReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache<K,V> {
    final Map<K,V> m = new HashMap<>();
    final ReadWriteLock rwl = new ReentrantReadWriteLock();
    final Lock r = rwl.readLock();
    final Lock w = rwl.writeLock();

    V get(K key) {
        r.lock();
        try {
            return m.get(key);
        } finally {
            r.unlock();
        }
    }

    V put(K key,V value) {
        w.lock();
        try {
            return m.put(key,value);
        } finally {
            w.unlock();
        }
    }

    static class ReadThread extends Thread {
        Cache<String,String> c;

        public ReadThread(Cache<String,String> c) {
            this.c = c;
        }

        @Override
        public void run() {
            String key = c.get("key");
            System.out.println("read  " + key);
        }
    }

    static class WriteThread extends Thread {
        Cache<String,String> c;

        public WriteThread(Cache<String,String> c) {
            this.c = c;
        }

        @Override
        public void run() {
            String value = c.put("key","哈哈");
            System.out.println("write  " + value);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Cache<String,String> c = new Cache<String, String>();

        WriteThread wt = new WriteThread(c);
        wt.start();
        Thread.sleep(1000);

        for(int i=0;i<10;i++) {
            new ReadThread(c).start();
        }
    }
}
