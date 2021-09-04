package org.gaofv.service.impl;

import org.gaofv.dao.UserDAO;
import org.gaofv.service.UserService;

/**
 * @Description：
 * @Author：gaofei32419
 * @Date：2021/9/4 18:04
 */
public class UserServiceImpl implements UserService {

    private UserDAO userDao;

    public UserDAO getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public String addUser() {
        return userDao.insert();
    }

    @Override
    public String getUser() {
        return userDao.select();
    }
}
