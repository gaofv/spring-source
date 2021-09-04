package org.gaofv.dao.impl;

import org.gaofv.dao.UserDao;
import org.gaofv.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @Description：
 * @Author：gaofei32419
 * @Date：2021/9/4 20:57
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public User selectUser() {
        return new User("1", "gaofv", 24);
    }

    @Override
    public String addUser() {
        return "insert into user values ...";
    }
}
