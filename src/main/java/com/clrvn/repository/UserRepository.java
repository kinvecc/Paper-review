package com.clrvn.repository;

import com.clrvn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否登录成功
     */
    User getUserByUsernameAndPassword(String username, String password);

}
