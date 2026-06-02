package com.stu.controller;


import com.stu.entity.StuEntity;
import com.stu.model.Student;
import com.stu.service.StuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;



@RestController
public class StuController {

    @Autowired
    StuServiceImpl stsi;







    @GetMapping("/stu/{id}")
    public String getById(@PathVariable Long id) {

        return stsi.getById(id);
    }
    @PostMapping("/stu")
    public String create(@RequestBody Student s){
        return stsi.create(s);
    }
@PutMapping("/stu/{id}")
    public String update(@PathVariable long id,@RequestBody Student s){
        return stsi.update(id,s);
}
@DeleteMapping("/stu/{id}")
    public String delete(@PathVariable long id,@RequestBody Student s){
        return  stsi.delete(id);
}
@GetMapping("/pass")
    public String getPassedStudents(){
        return  stsi.getPassedStudents();
}
@GetMapping("avg")
    public String getAverageMark(){
        return stsi.getAverageMark();
}
@GetMapping("top")
    public String getTopStudent(){
        return stsi.getTopStudent();
}
}
