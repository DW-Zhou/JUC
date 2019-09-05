package Thread_Face;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: JVM-JUC-Core-master
 * @description: ABA问题之 原子引用
 * @author: Mr.Wang
 * @create: 2019-09-04 23:26
 **/
class User{
    String username;
    int age;

    public User(String username,int age){
        this.age = age;
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User z3 = new User("z3",22);
        User li4 = new User("li4",23);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3,li4)+"\t"+atomicReference.get().toString());
    }
}
