package com.li.bean;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 17:25
 */
@Data
public class User {

    private String name;
    private Integer age;
    private String roles;

    public User() {
    }

    public User(String name, Integer age, String roles) {
        this.name = name;
        this.age = age;
        this.roles = roles;
    }
}
