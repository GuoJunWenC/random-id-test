package org.example.api.config;

public class LogAspect  {
    public void beforeDeleteUser() {
        System.out.println("Before Add User...");
    }

    public void afterDeleteUser() {
        System.out.println("After Delete User...");
    }
    public void aroundDeleteUser() {
        System.out.println("Around Delete User...");
    }
    public void afterReturnAddUser(String userName) {
        System.out.println("After Return Delete User..."+userName);
    }
    public void afterThrowingDeleteUser(Exception ex) {
        System.out.println("After  ThrowingDelete User...");
    }

}
