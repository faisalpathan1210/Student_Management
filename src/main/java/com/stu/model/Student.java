package com.stu.model;

import lombok.Data;

@Data
public class Student {
    private long id;
    private String name;
    private String email;
    private int age;
    private double marks;
}
