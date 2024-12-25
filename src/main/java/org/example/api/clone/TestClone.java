package org.example.api.clone;

import org.springframework.beans.BeanUtils;

public class TestClone {
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
    public static void main(String[] args) {
        Address address = new Address("Beijing");
        Person person = new Person("Alice", 30, address);
        Person person1 = PersonMapper.INSTANCE.sourceToTarget(person);
        System.out.println(person1 == person);
        System.out.println(person1.getAddress() == person.getAddress());
    }
}
