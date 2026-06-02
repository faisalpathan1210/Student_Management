package com.stu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

@Table(name = "s_stu")
public class StuEntity {
@Id

@GeneratedValue(
        strategy = GenerationType.IDENTITY
)
    private long id;
    private String name;
    private String email;
    private int age;
    private int marks;

}
