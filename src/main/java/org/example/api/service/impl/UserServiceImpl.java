package org.example.api.service.impl;

import org.example.api.service.User;
import org.example.api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    public String addUser(User user) {
        System.out.println("Add User: " + user.getName());
        return user.getName();
    }

    public void deleteUser(int id) {

        System.out.println("Delete User: " + id);
        //throw new RuntimeException();
    }
}
