package Thread_Face;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: JVM-JUC-Core-master
 * @description: CAS
 * @author: Mr.Wang
 * @create: 2019-09-04 20:40
 **/
/*
*    1          CAS是什么？=====> compareAndSet  比较和交换
*
*               比较并交换
*
* */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);//5 就是线程从主物理内存拷贝过来的真实值
        //main do thing---
        //如果线程的期望值 和 物理内存的真实值一样，就修改更新值，否则重新获取主物理内存的真实值

        System.out.println( atomicInteger.compareAndSet(5,2019)+"\t current data----"+atomicInteger.get());

        System.out.println( atomicInteger.compareAndSet(5,1024)+"\t current data----"+atomicInteger.get());

        atomicInteger.getAndIncrement();//解决了类似i++多线程下的安全问题
    }
}
