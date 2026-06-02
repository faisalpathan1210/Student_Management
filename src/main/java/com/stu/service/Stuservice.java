package com.stu.service;

import com.stu.entity.StuEntity;
import com.stu.model.Student;

import java.util.List;

public interface Stuservice {

   public List<Student> getAllStudents();
   public String getById(Long id) ;
   public String  create(Student s);
   public String update(Long id, Student s);
   public String  delete(Long id);
   public String getPassedStudents();// — marks >= 40
   public String getAverageMark() ;//— using streams
   public String getTopStudent();// — highest marks using streams
}
