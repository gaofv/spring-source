package org.gaofv.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：
 * @Author：gaofei32419
 * @Date：2021/9/4 14:50
 */
@RestController
public class HelloControlller {

    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }
}
