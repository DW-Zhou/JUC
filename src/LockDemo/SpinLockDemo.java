package LockDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: JUC
 * @description: 实现一个自旋锁
 * @author: Mr.Wang
 * @create: 2019-09-05 14:42
 * <p>
 * 题目： 实现一个自旋锁
 * 自旋锁的好处是： 循环比较获取直到成功为止，没有类似wait的阻塞
 * <p>
 * 通过CAS操作完成自旋锁，A线程进来先调用myLock方法自己持有锁5秒钟，B随后进来发现
 * 当前有线程持有锁，不是null，所以只能通过自旋，直到A释放锁，B随后抢到
 **/
public class SpinLockDemo {
    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference(); // 装的是线程的引用，默认初始为null

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t come in O(∩_∩)O哈哈~");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void myUnclock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t invoked myUnLock()");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.myLock();
            //暂停一会线程
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnclock();
        }, "AA").start();

        //保证T1线程一定先完成
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.myLock();
            //暂停一会线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnclock();
        }, "BB").start();
    }
}
