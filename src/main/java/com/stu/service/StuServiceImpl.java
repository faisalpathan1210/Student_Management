package com.stu.service;

import com.stu.entity.StuEntity;
import com.stu.exception.StudentNotFoundException;
import com.stu.model.Student;
import com.stu.repository.StuRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StuServiceImpl implements Stuservice {

    @Autowired
    private StuRepo strepo;

    @Override
    public List<Student> getAllStudents() {

        List<StuEntity> entities = strepo.findAll();

        List<Student> students = new ArrayList<>();

        for (StuEntity entity : entities) {

            Student student = new Student();

            BeanUtils.copyProperties(entity, student);

            students.add(student);
        }

        return students;
    }

    @Override
    public String getById(Long id) {

        StuEntity entity = strepo.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with ID: " + id));

        return entity.toString();
    }

    public Student getStudentById(Long id) {

        StuEntity entity = strepo.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with ID: " + id));

        Student student = new Student();

        BeanUtils.copyProperties(entity, student);

        return student;
    }

    @Override
    public String create(Student student) {

        StuEntity entity = new StuEntity();

        BeanUtils.copyProperties(student, entity);

        strepo.save(entity);

        return "Saved";
    }

    @Override
    public String update(Long id, Student student) {

        StuEntity oldStudent = strepo.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with ID: " + id));

        oldStudent.setName(student.getName());
        oldStudent.setEmail(student.getEmail());
        oldStudent.setAge(student.getAge());
        oldStudent.setMarks((int) student.getMarks());

        strepo.save(oldStudent);

        return "Updated";
    }

    @Override
    public String delete(Long id) {

        StuEntity student = strepo.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with ID: " + id));

        strepo.delete(student);

        return "Deleted";
    }

    @Override
    public String getPassedStudents() {

        List<StuEntity> passedStudents =
                strepo.findByMarksGreaterThanEqual(35);

        return passedStudents.toString();
    }

    @Override
    public double getAverageMark() {

        return strepo.findAll()
                .stream()
                .mapToInt(StuEntity::getMarks)
                .average()
                .orElse(0.0);
    }

    @Override
    public String getTopStudent() {

        StuEntity top = strepo.findTopByOrderByMarksDesc();

        return top != null ? top.getName() : "No Students";
    }

    public long getTotalStudents() {

        return strepo.count();
    }

    public long getPassedCount() {

        return strepo.findByMarksGreaterThanEqual(35).size();
    }

    public long getFailedStudents() {

        return strepo.count() -
                strepo.findByMarksGreaterThanEqual(35).size();
    }

    public List<Student> searchStudents(String name) {

        List<StuEntity> entities =
                strepo.findByNameContainingIgnoreCase(name);

        List<Student> students = new ArrayList<>();

        for (StuEntity entity : entities) {

            Student student = new Student();

            BeanUtils.copyProperties(entity, student);

            students.add(student);
        }

        return students;
    }

    public Page<StuEntity> getStudentsByPage(int pageNo) {

        Pageable pageable = PageRequest.of(pageNo - 1, 5);

        return strepo.findAll(pageable);
    }

    public List<Student> getAllStudentsSorted(String field) {

        List<StuEntity> entities =
                strepo.findAll(Sort.by(field));

        List<Student> students = new ArrayList<>();

        for (StuEntity entity : entities) {

            Student student = new Student();

            BeanUtils.copyProperties(entity, student);

            students.add(student);
        }

        return students;
    }
}