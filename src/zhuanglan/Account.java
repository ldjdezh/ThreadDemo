package zhuanglan;

public class Account {
    // 锁：保护账户余额
    private final Object balLock = new Object();
    // 账户余额
    private Integer balance;
    // 锁：保护账户密码
    private final Object pwLock = new Object();
    // 账户密码
    private String password;

    /**
     * 取款
     *
     * @param amt
     */
    void withdraw(Integer amt) {
        synchronized (balLock) {
            if (this.balance > amt) {
                this.balance -= amt;
            }
        }
    }

    /**
     * 查看余额
     *
     * @return
     */
    Integer getBalance() {
        synchronized (balLock) {
            return balance;
        }
    }

    /**
     * 更改密码
     *
     * @param pw
     */
    void updatePassword(String pw) {
        synchronized (pwLock) {
            this.password = pw;
        }
    }

    /**
     * 查看密码
     *
     * @return
     */
    String getPassword() {
        synchronized (pwLock) {
            return password;
        }
    }

    public static void main(String[] args) {
        Account account = new Account();

        new Thread(() -> {
            account.withdraw(50);
            Integer balance = account.getBalance();
            Thread.currentThread().setName("第二个线程");
            System.out.println(Thread.currentThread().getName() + " --> " + balance);
        }).start();

        new Thread(() -> {
            account.balance = 200;
            Integer balance = account.getBalance();
            Thread.currentThread().setName("第一个线程");
            System.out.println(Thread.currentThread().getName() + " --> " + balance);
        }).start();
    }
}
