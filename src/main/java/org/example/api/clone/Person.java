package org.example.api.clone;


import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;

public class Person implements Cloneable, Serializable{
    private static final long serialVersionUID = 415435892452073417L;
    private String name;
    private Integer age;
    private Address address;

    public Person(String name, Integer age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
    public Person deepCopy() {
        return (Person) SerializationUtils.clone(this);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // Getters and setters...
    public static void main(String[] args) {
     Integer a = new Integer(1);
     Integer b = 1;
        System.out.println(a == b);
    }
}

