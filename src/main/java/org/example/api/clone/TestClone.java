package org.example.api.clone;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class TestClone {
    @Autowired
    private AuthenticationConfig authenticationConfig;;
    /*    public static void main(String[] args) {
            Address address = new Address("Beijing");
            Person person1 = new Person("Alice", 30, address);
    *//*
            Person person2 = (Person) person1.clone();
*//*
        Person clone = person1.deepCopy();
        System.out.println(person1.equals(clone)); // false
        System.out.println(person1.getAddress() == clone.getAddress()); // true
    }*/
 /*   public static void main(String[] args) {
        String appId = authenticationConfig.getAppId();
    }*/
    @GetMapping("/getAppId")
    public  String getAppId() {
        System.out.println(authenticationConfig.getAppId());
        return authenticationConfig.getAppId();
    }
}
