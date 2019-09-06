package LockDemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: JUC
 * @description: 3.0版本生产者阻塞队列
 * @author: Mr.Wang
 * @create: 2019-09-05 21:56
 * 知识点串一波：      valatile/CAS/atomicInteger/BlockQueue/线程交互/原子引用
 **/

class MyResource {
    private volatile boolean FlAG = true; //默认开启，进行生产消费，用volatile修饰是因为保证线程之间的可见性，也就是说我生产一个，立马通知其他线程过来消费
    //此处不应该用number++ 传统的里面是因为你有lock锁住了，此处并没有锁，应该用原子引用的atomicInteger
    private AtomicInteger atomicInteger = new AtomicInteger(); //默认值是0

    //传递应该传接口，因为不确定具体哪个类  spring的注入有两种 构造注入 set注入
    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    //生产
    public void myProd() throws Exception {
        String data = null;
        boolean retValue;//默认是false
        while (FlAG) {
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if(retValue){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"成功");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t 大老板叫停了，表示Flag = false,生产动作结束");
    }

    //消费
    public void myConsumer() throws Exception{
        String result = null;
            while(FlAG){
                result = blockingQueue.poll(2,TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName()+"\t 消费队列"+result+"成功");
                if(result == null || result.equalsIgnoreCase("")){
                       FlAG = false;
                    System.out.println(Thread.currentThread().getName()+"\t 超过2s,消费退出");
                    return;
                }
                System.out.println(Thread.currentThread().getName()+"\t 消费队列成功"+result);
            }
    }
}

public class ProdConsumer_BlockQueueDemo {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

}
