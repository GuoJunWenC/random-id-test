package org.example.api.service;

public interface UserService {
    String addUser(User user);

    void deleteUser(int id);

    /**
     * 更新用户信息
     * 
     * @param user 用户对象
     */
    void updateUser(User user);

}
