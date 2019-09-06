package LockDemo;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @program: JUC
 * @description: 多线程第3种获得线程的方式
 * @author: Mr.Wang
 * @create: 2019-09-06 09:46
 * 运用了适配器模式
 * 返回值
 * 是否抛出异常
 **/

class MyThread2 implements  Runnable{

    @Override
    public void run() {

    }
}

class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("********comin in Callable");
        return 1024;
    }
}
public class CallableDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //两个线程 一个mian 主线程  一个 AA futureTask

        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());

        while(!futureTask.isDone()){

        }
        int result2 = futureTask.get();//要求获得Callable线程获得的计算结果放在最后，如果没有计算完成回去强求，会导致堵塞，知道计算完成
        Thread t1 = new Thread(futureTask,"AAA");
        t1.start();
        System.out.println("****result"+futureTask.get());
    }
}
