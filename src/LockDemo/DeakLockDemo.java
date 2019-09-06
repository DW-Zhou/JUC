package LockDemo;

import java.util.concurrent.TimeUnit;

/**
 * @program: JUC
 * @description: 死锁
 * @author: Mr.Wang
 * @create: 2019-09-06 20:09
 *
 * 死锁 是指两个或者两个以上的进程在执行过程中，因争抢资源而造成一种互相等待的现象，如果无外力的干涉那么它们将一直等待下去
 **/
class HoldLockThread implements Runnable{
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t自己持有：" + lockA + "\t尝试获取：" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t自己持有：" + lockB + "\t尝试获取：" + lockA);
             }
        }
    }
}
public class DeakLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA,lockB),"ThreadAAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"ThreadBBB").start();
    }
}
