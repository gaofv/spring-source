package org.gaofv.dao.impl;

import org.gaofv.dao.UserDAO;

/**
 * @Description：
 * @Author：gaofei32419
 * @Date：2021/9/4 18:02
 */
public class UserDAOImpl implements UserDAO {
    @Override
    public String select() {
        return "select * from user";
    }

    @Override
    public String insert() {
        return "insert into user values....";
    }
}
