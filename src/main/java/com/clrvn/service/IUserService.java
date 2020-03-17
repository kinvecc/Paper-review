package com.clrvn.service;

import com.clrvn.entity.User;

public interface IUserService {

    User login(User user);

    User getUserByUId(Integer id);


    User register(User user);

}
