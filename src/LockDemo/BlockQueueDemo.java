package LockDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @program: JUC
 * @description: 阻塞队列
 * @author: Mr.Wang
 * @create: 2019-09-05 17:00
 * <p>
 * <p>
 * ArrayBlockingQueue  : 是一个基于数组结构的有界阻塞队列，此队列按照FIFO（先进先出） 原则对元素进行排序
 * <p>
 * LinkedBlockingQueue: 是一个基于链表结构的有界阻塞队列，此队列按照FIFO（先进先出） 原则对元素进行排序，吞吐量通常高于ArrayBlockingQueue
 * <p>
 * SynchronousQueue : 是一个不存储元素的阻塞对列，每个插入操作必须等到另一个线程调用移除，否则插入操作一直处于阻塞状态，吞吐量通常高于LinkedBlockingQueue
 * <p>
 * <p>
 * 1 队列
 * <p>
 * 2 阻塞队列
 * 2.1  阻塞队列有没有好的一面
 * <p>
 * <p>
 * 2.2  不得不阻塞，你如何管理
 **/
public class BlockQueueDemo {
    public static void main(String[] args) throws Exception {
        //  List list = new ArrayList();

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

      /*  System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
     */
        // System.out.println(blockingQueue.add("d"));

        /*    System.out.println(blockingQueue.remove());
         */
       /* System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));

        System.out.println(blockingQueue.offer("x"));

        System.out.println(blockingQueue.peek());*/
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");

        blockingQueue.put("x");
        System.out.println(blockingQueue.peek());
    }
}
