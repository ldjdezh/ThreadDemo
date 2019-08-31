package ch01.wn;

public class Express {
    public final static String CITY = "ShangHai";
    private int km;
    private String site;

    public Express() {
    }

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public synchronized void changeKm() {
        this.km = 101;
        notifyAll();
    }

    public synchronized void changeSite() {
        this.site = "BeiJing";
        notifyAll();
    }

    public synchronized void waitKm() {
        while(this.km <= 100) {
            try {
                System.out.println("km 等待中 ... " + Thread.currentThread().getName());
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("km 不等了 干活" + Thread.currentThread().getName());
    }

    public synchronized void waitSite() {
        while(CITY.equals(this.site)) {
            try {
                System.out.println("site 等待中 ... " + Thread.currentThread().getName());
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("site 不等了 干活" + Thread.currentThread().getName());
    }
}
