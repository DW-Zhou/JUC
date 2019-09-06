package LockDemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @program: JUC
 * @description: 阻塞队列之 SynchronousQueue
 * @author: Mr.Wang
 * @create: 2019-09-05 20:26
 **/
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+"\t put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName()+"\t put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName()+"\t put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        new Thread(() -> {
            try {
               try{ TimeUnit.SECONDS.sleep(5); }catch(InterruptedException e){ e.printStackTrace();}
                System.out.println(Thread.currentThread().getName()+"\t 获取得值："+blockingQueue.take());
                try{ TimeUnit.SECONDS.sleep(5); }catch(InterruptedException e){ e.printStackTrace();}
                System.out.println(Thread.currentThread().getName()+"\t 获取得值："+blockingQueue.take());
                try{ TimeUnit.SECONDS.sleep(5); }catch(InterruptedException e){ e.printStackTrace();}
                System.out.println(Thread.currentThread().getName()+"\t 获取得值："+blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();

    }
}
