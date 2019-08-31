package ch01.pool;

import java.sql.Connection;
import java.util.LinkedList;

public class DBPool {

    private static LinkedList<Connection> pool = new LinkedList<>();

    public DBPool(int initalSize) {
        if(initalSize > 0) {
            for(int i=0;i<initalSize;i++) {
                pool.addLast(SqlConnectImpl.fetchConnection());
            }
        }
    }

    public Connection fetchConn(long mills) throws InterruptedException {
        synchronized(pool) {
            if(mills < 0) {
                while(pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long overtime = System.currentTimeMillis() + mills;
                long remain = mills;

                while(pool.isEmpty() && remain > 0) {
                    pool.wait(remain);
                    remain = overtime - System.currentTimeMillis();
                }

                Connection result = null;

                if(!pool.isEmpty()) {
                    result = pool.removeFirst();
                }

                return result;
            }
        }
    }

    public void releaseConn(Connection conn) {
        if(conn != null) {
            synchronized (pool) {
                pool.addLast(conn);
                pool.notifyAll();
            }
        }
    }
}
