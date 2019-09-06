package LockDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: JUC
 * @description: 2.0版本生产消费者模型
 * @author: Mr.Wang
 * @create: 2019-09-05 20:41
 *
 *多线程情况下的操作：
 *      高并发 高内聚 低耦合的情况下， 线程    操纵   资源类
 *                                 判断     干活  通知
 *
 *                                 严防多线程并发情况下的虚假唤醒
 *
 *
 * 题目： 一个初始值为0 的变量，两个线程替操作，一个加1 一个减1 ，操作5次
 *
 *   java 万物皆对象
 *    操作变量应该是   先操作对象，再去操作其中的变量
 **/
class ShareData{ //资源类
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception{
        lock.lock();
        try {
            //1. 判断
            while(number != 0){
                //等待，不能生产
                condition.await();
            }
            //2 干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);

            //3.通知唤醒
            condition.signalAll();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void decreament() throws Exception{
        lock.lock();
        try {
            //1. 判断
            while(number == 0){
                //等待，不能生产
                condition.await();
            }
            //2 干活
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);

            //3.通知唤醒
            condition.signalAll();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

public class ProdConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 0;i <= 5;i++){
                try {
                    shareData.increment();
                    try{ TimeUnit.SECONDS.sleep(1); }catch(InterruptedException e){ e.printStackTrace();}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(() -> {
            for (int i = 0;i <= 5;i++){
                try {
                    shareData.decreament();
                    try{ TimeUnit.SECONDS.sleep(1); }catch(InterruptedException e){ e.printStackTrace();}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();
    }

}
