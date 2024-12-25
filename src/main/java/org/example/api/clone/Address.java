package org.example.api.clone;

import lombok.Data;

import java.io.Serializable;
@Data
public class Address implements Serializable {
    private static final long serialVersionUID = -4801102361607231382L;
    private String city;

    public Address(String city) {
        this.city = city;
    }
    public Address() {
    }
    // Getters and setters...
}
