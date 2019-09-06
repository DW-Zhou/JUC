package LockDemo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: JUC
 * @description: 读写锁
 * @author: Mr.Wang
 * @create: 2019-09-05 15:13
 * <p>
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取该共享资源应该可以同时进行
 * <p>
 * 但是
 * 如果有一个线程想去写共享资源，就不应该再有其它线程可以对该资源读或者写
 * <p>
 * 小总结：
 * 读 ---- 读可以共存
 * 读 -----写不能共存
 * 写----- 写不能共存
 * <p>
 * 写操作：原子 + 独占 中间整个过程必须是一个统一的完整体，不许被分割或者打断
 **/
class MyCache { //资源类  多个读 单个写
    private volatile Map<String, Object> map = new HashMap<>();
    //可重复读写锁
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    //private Lock lock = new ReentrantLock();
    // 写资源类
    public void put(String key, Object value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);
            //模拟真实情况，暂停一会儿线程
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成：");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
     /*
            你的业务逻辑
     System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);
        //模拟真实情况，暂停一会儿线程
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "\t 写入完成：");*/
    }

    // 读资源类
    public void get(String key) {
        rwLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t 正在读入：");
            //模拟真实情况，暂停一会儿线程
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读入完成：" + result);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            rwLock.readLock().unlock();
        }
    /*
            业务逻辑 --- 原始的存在读写并发的问题
    System.out.println(Thread.currentThread().getName() + "\t 正在读入：");
        //模拟真实情况，暂停一会儿线程
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object result = map.get(key);
        System.out.println(Thread.currentThread().getName() + "\t 读入完成：" + result);*/
    }

    //模拟清除缓存
    public void clearMap(){
        map.clear();
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        //线程操作资源类
        MyCache myCache = new MyCache();
        //5个线程去写
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt + " ");
            }, String.valueOf(i)).start();
        }

        //5个线程去读
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }
    }
}
