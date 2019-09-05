package Thread_Face;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: FaceCode
 * @description: 关键字之 Volatile
 * @author: Mr.Wang
 * @create: 2019-09-04 15:36
 **/

class MyData{

    volatile  int number = 0;//增加了关键字volatile
    // int number = 0;
    public void addTo60(){
        this.number = 60;
    }
    //number已经有了volatile关键字修饰，不保证原子性
    public void addPlusPlus(){
       number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }
}

/*验证volatile的可见性
    可见性是一种即时通知的机制
*   1.1 加入int number = 0； number变量之前根本没有添加volatile关键字修饰,没有可见性
*   1.2 添加了Volatile，可以解决可见性问题
*
*   验证volatile不保证原子性？
*    2.1 原子性
*           不可分割 完整性  意味着某个线程在做某个业务时，中间不可以被加塞或者被分割，需要完整性，要么同时成功，要么同时失败
*    2.2 是否保证原子性？
*
*
* */
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for(int i = 1;i <= 20; i++){
            new Thread(() -> {
                for(int j = 1; j <= 1000; j++){
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
             },String.valueOf(i)).start();
        }
        //等上面20个线程操作完成后，再用main线程获得结果值
        /*
        *   加入volatile保证了原子性，那么main获得结果应为：20*1000
        * */
      while(Thread.activeCount() > 2){
          Thread.yield();
      }

        System.out.println(Thread.currentThread().getName()+"\t int type,finally  main 获得的值"+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t AtomicInterger type,finally  main 获得的值"+myData.atomicInteger);
    }
    //可以保证可见性，及时通知其他线程，主物理内存的值已经发生改变了
    public static void seeOkByVolatile() {
        MyData myData = new MyData();//资源类
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t com in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName()+"\t updated number value:"+myData.number);
        },"AAA").start();

        //第2个线程 main线程
        while (myData.number == 0){
            //main线程就一直在这里等待循坏，一直到number不等于0

        }
        System.out.println(Thread.currentThread().getName()+"\t mission is over");
    }



}
