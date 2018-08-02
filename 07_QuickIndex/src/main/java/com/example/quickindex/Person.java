package com.example.quickindex;

/**
 * @author SYSTEM  on  2018/07/31 at 19:32
 * 邮箱：913305160@qq.com
 */
public class Person {

    private String name;
    private String pinYin;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinYin() {
        if (name != null) {
            return PinYinUtils.getPinYin(name);
        }
        if (pinYin != null) {
            return pinYin;
        }
        return null;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }
}
