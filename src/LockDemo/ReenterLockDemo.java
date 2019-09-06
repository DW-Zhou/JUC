package LockDemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: JUC
 * @description: 可重入锁（又称为递归锁）
 * @author: Mr.Wang
 * @create: 2019-09-05 14:05
 * <p>
 * 指的是同一个线程外层函数获得锁之后，内层的递归函数仍然可以获得该锁的代码，
 * 在同一线程在最外层获取锁的时候，在进入内层方法会自动获取锁
 * <p>
 * 也就是说，线程可以进入任何一个它已经拥有锁所同步的代码块
 * <p>
 * case 1 Synchronized 是一个可重入锁
 * 11	 invoked sendSMS()
 * 11	 ##### invoked sendEmail()
 * 12	 invoked sendSMS()
 * 12	 ##### invoked sendEmail()
 * <p>
 * case 2 ReentrantLock也是一个可重入锁
 **/

class Phone implements Runnable {

    public synchronized void sendSMS() throws Exception {
        System.out.println(Thread.currentThread().getId() + "\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception {
        System.out.println(Thread.currentThread().getId() + "\t ##### invoked sendEmail()");
    }

    Lock lock = new ReentrantLock();//不填，默认是false，非公平锁

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t @@@@invoked get()");
            set();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t @@@@invoked set()");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}

public class ReenterLockDemo {
    public static void main(String[] args) throws Exception {
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "T1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "T2").start();

        System.out.println("==============ReetrantLock======");

        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        t3.start();
        t4.start();
    }
}
