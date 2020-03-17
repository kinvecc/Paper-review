package com.clrvn.service.impl;

import com.clrvn.entity.User;
import com.clrvn.repository.UserRepository;
import com.clrvn.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(User user) {
        return userRepository.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public User getUserByUId(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    @Transactional
    public User register(User user) {
        return userRepository.save(user);
    }

}
