package com.stu.service;

import com.stu.entity.StuEntity;
import com.stu.model.Student;
import com.stu.repository.StuRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class StuServiceImpl implements Stuservice{
    @Autowired
    StuRepo strepo;
    public StuServiceImpl(){

    }

    @Override
    public List<Student> getAllStudents() {

        List<StuEntity> le = this.strepo.findAll();

        List<Student> l = new ArrayList<>();

        for (StuEntity stentity : le) {

            Student s = new Student();

            s.setId(stentity.getId());
            s.setName(stentity.getName());
            s.setEmail(stentity.getEmail());

            l.add(s);
        }

        return l;
    }

    @Override
    public String getById(Long id) {
        StuEntity se=strepo.findById(id).get();


      return se.toString();
    }

    @Override
    public String create(Student s) {
StuEntity se1=new StuEntity();
        BeanUtils.copyProperties(s,se1);
strepo.save(se1);
        return "Saved";
    }

    @Override
    public String update(Long id, Student s) {
        StuEntity old=strepo.findById(id).get();

        old.setName(s.getName());
        old.setEmail(s.getEmail());
        old.setAge(s.getAge());
        old.setMarks((int) s.getMarks());
        strepo.save(old);


        return "Updated";
    }

    @Override
    public String delete(Long id) {
        StuEntity del=strepo.findById(id).get();
strepo.delete(del);
        return "Deleted";
    }

    @Override
    public String getPassedStudents() {

        List<StuEntity> passedStudents = strepo.findByMarksGreaterThanEqual(35);

        return passedStudents.toString();
    }

    @Override
    public String getAverageMark() {

        double avg = strepo.findAll()
                .stream()
                .mapToInt(StuEntity::getMarks)
                .average()
                .orElse(0.0);

        return "Average Marks: " + avg;
    }
    @Override
    public String getTopStudent() {

        StuEntity top = strepo.findTopByOrderByMarksDesc();

        return top.getName();
    }
}
