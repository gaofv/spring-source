package org.gaofv.controller;

import org.gaofv.entity.User;
import org.gaofv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：
 * @Author：gaofei32419
 * @Date：2021/9/4 20:56
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("getUser")
    public User getUser(String userId) {
        System.out.println("userId = " + userId);
        return userService.getUser();
    }

}
