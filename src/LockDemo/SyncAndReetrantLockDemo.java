package LockDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: JUC
 * @description: synchronized 和ReetrantLock之间的区别
 * @author: Mr.Wang
 * @create: 2019-09-05 21:16
 * <p>
 * <p>
 * 题目： 多线程之间按顺序调用， 实现A--->B----->C 三个线程启动，要求如下：
 * AA打印5次  BB打印10次，CC打印15次
 * <p>
 * 紧接着
 * AA打印5次  BB打印10次，CC打印15次
 * -----
 * 来10次
 **/

class ShareResoure {
    private int number = 1; //A:1;B:2; C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            //1.判断
            while (number != 1) {
                c1.await();
            }
            //2.干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3.通知
            number = 2; // 修改标志位
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            //1.判断
            while (number != 2) {
                c2.await();
            }
            //2.干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3.通知
            number = 3; // 修改标志位
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            //1.判断
            while (number != 3) {
                c3.await();
            }
            //2.干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3.通知
            number = 1; // 修改标志位
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

public class SyncAndReetrantLockDemo {
    public static void main(String[] args) {
        ShareResoure shareResoure = new ShareResoure();
        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                shareResoure.print5();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                shareResoure.print10();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                shareResoure.print15();
            }
        }, "C").start();
    }
}
