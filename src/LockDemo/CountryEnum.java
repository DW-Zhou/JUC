package LockDemo;

/**
 * @program: JUC
 * @description: 枚举国家在 CountDownLatchDemo中的应用
 * @author: Mr.Wang
 * @create: 2019-09-05 16:12
 **/
public enum CountryEnum {

    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "韩"), FIVE(5, "赵"), SIX(6, "魏");

    private Integer retCode;
    private String retMessage;

    public Integer getRetCode() {
        return retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum forEach_CountryEnum(int index) {
        CountryEnum[] values = CountryEnum.values();
        for (CountryEnum element : values) {
            if (index == element.getRetCode()) {
                return element;
            }
        }
        return null;
    }
}
/*
CountryEnum 类比于 数据库

mysql  DBName= CountryEnum

ONE TWO =  类比 表名

table
ONE
ID  username age  bith userEmail
1    齐



*/
