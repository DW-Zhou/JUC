package Thread_Face;

/**
 * @program: JVM-JUC-Core-master
 * @description: 单例模式
 * @author: Mr.Wang
 * @create: 2019-09-04 20:10
 **/
public class SingletonDemo {
    private static volatile SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t 我是构造方法SingletonDemo()");
    }

    /*  public static  SingletonDemo getInstance(){
          if(instance == null){
              instance = new SingletonDemo();
          }
          return instance;
      }*/
    //DCL模式(Double check Lock双端检锁机制)
    /*潜在隐患：  线程上十万次时，也不保险，因为代码的底层会出现指令的重排
    *
    * */
    public static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class){
                if (instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //单线程下的单例模式
        //  System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        //并发系统下，情况会发生变化
        for (int i = 1; i <= 10000; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
