package com.lan.bing.annotation.other;

/**
 * Created by 蓝兵 on 2018/4/17.
 */

@StudentAction(read = "xiaoming21")
public class Person {

    @StudentAction(read = "xiaoming")
    @PersonAction(1)
    void eat() {

    }

    @PersonAction(2)
    void run() {
    }
    //
    //@PersonAction(3)
    //void drink() {
    //}
}
