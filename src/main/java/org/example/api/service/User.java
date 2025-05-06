package org.example.api.service;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = -231305275544413140L;
    private String name;
    private String age;
    private String address;
}
