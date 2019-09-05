package Bean;

/**
 * @program: JVM-JUC-Core-master
 * @description:
 * @author: Mr.Wang
 * @create: 2019-09-05 11:48
 **/
public class Person {
    private Integer id;
    private String personName;

    public Person(String personName){
        this.personName = personName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
