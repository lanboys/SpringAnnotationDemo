package com.lan.bing.annotation.combination;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * https://blog.csdn.net/chs007chs/article/details/78594540
 *
 * @author lan_bing
 * @date 2019-03-27 18:55
 */

@Combination(test = "cname", value = "aa")
public class CombinationTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CombinationTest.class);

        String[] beanNamesForType = context.getBeanNamesForType(CombinationTest.class);

        //当 @Combination 加上@AliasFor时, 输出"cname"
        //当 @Combination 去掉@AliasFor注解后, 输出"combinationTest"
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  " + Arrays.toString(beanNamesForType));

        context.close();
    }
}