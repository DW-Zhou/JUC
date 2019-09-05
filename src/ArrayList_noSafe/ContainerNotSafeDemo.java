package ArrayList_noSafe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @program: JVM-JUC-Core-master
 * @description: 集合类线程不安全的问题
 * @author: Mr.Wang
 * @create: 2019-09-05 10:03
 * <p>
 * ArrayList Map Set 线程不安全
 **/
public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        Map<String,String> map = new  ConcurrentHashMap<>();//new HashMap();
        for(int i = 1;i <= 30; i++){
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
             },String.valueOf(i)).start();
        }
    }

    private static void SetNoSafe() {
        Set<String> set = new CopyOnWriteArraySet<>();//Collections.synchronizedSet(new HashSet<>());// new HashSet<>();
        for(int i = 1;i <= 30; i++){
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
             },String.valueOf(i)).start();
        }
        new HashSet<>().add("a");
    }

    private static void listNoSafe() {
        List<String> list = new CopyOnWriteArrayList<>();//Collections.synchronizedList(new ArrayList<>());//Vector<>();//ArrayList<>();
        /*
        *   Collection  -- 集合的接口
        *
        *   Collections ---- 辅助工具类
        *
        * */
        for(int i = 1;i <= 30; i++){
            new Thread(() -> {
               list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
             },String.valueOf(i)).start();
        }
        // java.util.Concurrent ModificationException (并发修改异常)
        /*
        * 1，故障现象
        *       java.util.Concurrent ModificationException (并发修改异常)
        * 2.导致的原因
        *   并发争抢修改导致，参考花名册情况
        *       一个人正在写入，另一个同学过来争抢夺，导致数据不一致异常，并发修改异常。
        *
        *
        * 3、解决方案
        *   3.1  使用Vector(),可以解决安全问题，但是线程并发性下降，不推荐使用，   public synchronized void addElement(E obj) {}
        *   3.2  使用  Collections.synchronizedList(new ArrayList<>());
        *   3.3   JUC  new CopyOnWriteArrayList();  import java.util.concurrent.CopyOnWriteArrayList; //写时复制
        * 4、优化的建议
        *
        * */
    }
}
