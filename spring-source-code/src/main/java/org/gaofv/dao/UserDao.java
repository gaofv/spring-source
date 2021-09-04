package org.gaofv.dao;

import org.gaofv.entity.User;

public interface UserDao {
    User selectUser();

    String addUser();
}
