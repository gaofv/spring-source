package org.gaofv.controller;

import org.gaofv.service.UserService;

/**
 * @Description：
 * @Author：gaofei32419
 * @Date：2021/9/4 18:05
 */
public class UserController {
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public String login() {
        return userService.getUser();
    }

    public String register() {
        return userService.addUser();
    }
}
