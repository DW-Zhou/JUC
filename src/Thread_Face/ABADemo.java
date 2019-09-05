package Thread_Face;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @program: JVM-JUC-Core-master
 * @description:
 * @author: Mr.Wang
 * @create: 2019-09-05 00:16
 **/
public class ABADemo { // ABA问题的解决   AtomicStampedReference

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        System.out.println("=======================下面是对ABA问题的产生===========");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "T1").start();

        new Thread(() -> {
            //T2线程暂停1s中，保证上面的T1线程先执行，完成ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2019) + "\t" + atomicReference.get());
        }, "T2").start();
        //先暂停一会线程
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=======================下面是对ABA问题的解决===========");

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次获得的版本号是："+stamp);
            //暂停1s t3线程
            try{ TimeUnit.SECONDS.sleep(1); }catch(InterruptedException e){ e.printStackTrace();}
            //参数值：期望值，更新值，当前版本号，更新版本号
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第2次获得的版本号是："+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第3次获得的版本号是："+atomicStampedReference.getStamp());
        }, "T3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次获得的版本号是："+stamp);
            //暂停3s t4线程,保证上面的t3线程完成一次ABA操作
            try{ TimeUnit.SECONDS.sleep(3); }catch(InterruptedException e){ e.printStackTrace();}
            boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);// 100,2019,1,2  实际版本号3 不等
            System.out.println(Thread.currentThread().getName()+"\t修改成功否："+result+"--当前最新实际的版本号--"+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t当前实际最新值："+atomicStampedReference.getReference());
        }, "T4").start();
    }
}
