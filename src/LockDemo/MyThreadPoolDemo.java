package LockDemo;

import java.util.concurrent.*;

/**
 * @program: JUC
 * @description: 线程池
 * @author: Mr.Wang
 * @create: 2019-09-06 11:02
 * <p>
 * Array  Arrays
 * Collection Collections
 * Executor Executors（辅助工具类）
 * <p>
 * 第4种获得使用java多线程的方式，通过线程池
 * 线程池的使用永远比关闭更重要
 **/
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()); //main线程创建的threadPool

        try {
            for (int i = 1; i <= 11; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }


    }

    private static void threadPoolInit() {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池多（5个）线程 === 类比银行的5个柜台
        // ExecutorService threadPool2 = Executors.newSingleThreadExecutor();//一池1线程 === 类比银行只有1个柜台
        //ExecutorService threadPool3 = Executors.newCachedThreadPool();//一池N个处理线程

        try {
            //模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
            for (int i = 0; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务 ");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
