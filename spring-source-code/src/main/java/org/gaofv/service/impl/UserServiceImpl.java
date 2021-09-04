package org.gaofv.service.impl;

import org.gaofv.dao.UserDao;
import org.gaofv.entity.User;
import org.gaofv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description：
 * @Author：gaofei32419
 * @Date：2021/9/4 21:00
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser() {
        return userDao.selectUser();
    }

    @Override
    public String addUser() {
        return userDao.addUser();
    }


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
