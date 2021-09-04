package org.gaofv;

import org.junit.Test;

import java.io.InputStream;
import java.net.URL;

/**
 * @Description：
 * @Author：gaofei32419
 * @Date：2021/9/4 13:50
 */
public class Test01 {

    @Test
    public void test01() {

//        URL resource = Test01.class.getClassLoader().getResource("");
//        System.out.println(resource.getPath());
        URL resource1 = Test01.class.getResource("/Test02.java");
//        URL resource2 = Test01.class.getResource("/application.properties");
        System.out.println(resource1.getPath());
//        System.out.println(resource2.getPath());
    }
}
