package com.example.netty.test.pojo;

import java.io.Serializable;

/**
 * @author: wangdk
 * @create: 2020-06-09 09:40
 * @description:
 **/
public class Student implements Serializable {
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
