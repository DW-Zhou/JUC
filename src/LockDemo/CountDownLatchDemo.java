package LockDemo;

import java.util.concurrent.CountDownLatch;

/**
 * @program: JUC
 * @description: 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。java.util.concurrent
 * @author: Mr.Wang
 * @create: 2019-09-05 15:57
 **/
public class CountDownLatchDemo {
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t 国,被灭了");
                countDownLatch.countDown();
            },CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        countDownLatch.await();
        //main线程
        System.out.println(Thread.currentThread().getName()+"\t *****************大秦帝国，一统天下");

        System.out.println();
        System.out.println(CountryEnum.SIX.getRetMessage());
    }

    private static void closeDemo() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t 上完自习，离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        //main线程
        System.out.println(Thread.currentThread().getName()+"\t *****************888啦，班长最后关门走人");

       /*
        原始操作： 会出现 班长直接走了，把6个线程关在教室

       for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t 上完自习，离开教室");
            }, String.valueOf(i)).start();
        }
        //main线程
        System.out.println(Thread.currentThread().getName()+"\t *****************888啦，班长最后关门走人");*/
    }
}
