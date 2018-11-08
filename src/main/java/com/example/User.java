package com.example;

import lombok.Data;

import java.io.Serializable;

@Data
public
class User implements Serializable {
    private int id;
    private String name;
    private String address;
    private short age;

    public User(){

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }

    public User(int id, String name, String address, short age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
    }
}