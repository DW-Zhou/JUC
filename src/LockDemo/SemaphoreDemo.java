package LockDemo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @program: JUC
 * @description: 信号量
 * @author: Mr.Wang
 * @create: 2019-09-05 16:50
 **/
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);// 模拟3个停车位

        //利用6个线程去模拟6部车
       for(int i = 1;i <= 6; i++){
           new Thread(() -> {
               try {
                   semaphore.acquire();
                   System.out.println(Thread.currentThread().getName()+"\t 抢到了车位！(*^▽^*)");
                   //暂停线程，模拟停车5分钟
                   try{ TimeUnit.SECONDS.sleep(3); }catch(InterruptedException e){ e.printStackTrace();}
                   System.out.println(Thread.currentThread().getName()+"\t 停车完毕 ，准备离开！(*^▽^*)");
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }finally {
                   //释放你的车位，也就是信号量
                    semaphore.release();
               }
           },String.valueOf(i)).start();
       }
    }
}
