package com.stu.controller;

import com.stu.model.Student;
import com.stu.service.StuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.data.domain.Page;
import com.stu.entity.StuEntity;

import java.util.List;

@Controller
public class HomeController {





    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("totalStudents", service.getTotalStudents());
        model.addAttribute("passedStudents", service.getPassedCount());
        model.addAttribute("failedStudents", service.getFailedStudents());
        model.addAttribute("averageMarks", service.getAverageMark());
        model.addAttribute("topStudent", service.getTopStudent());

        return "index";
    }


    @Autowired
    StuServiceImpl service;

    @GetMapping("/students")
    public String students(Model model) {

        return studentsByPage(1, model);

    }

    @GetMapping("/students/page/{pageNo}")
    public String studentsByPage(@PathVariable int pageNo,
                                 Model model) {

        Page<StuEntity> page = service.getStudentsByPage(pageNo);

        model.addAttribute("students", page.getContent());

        model.addAttribute("currentPage", pageNo);

        model.addAttribute("totalPages", page.getTotalPages());

        model.addAttribute("totalItems", page.getTotalElements());

        return "students";
    }

    @GetMapping("/addStudent")
    public String addStudent(Model model) {

        model.addAttribute("student", new Student());

        return "addstudent";
    }
    @PostMapping("/saveStudent")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "addstudent";
        }

        service.create(student);

        redirectAttributes.addFlashAttribute(
                "success",
                "Student added successfully!"
        );

        return "redirect:/students";
    }


    @GetMapping("/editStudent/{id}")
    public String editStudent(@PathVariable Long id, Model model) {

        Student student = service.getStudentById(id);

        model.addAttribute("student", student);

        return "editstudent";
    }
    @PostMapping("/updateStudent")
    public String updateStudent(@Valid @ModelAttribute("student") Student student,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "editstudent";
        }

        service.update(student.getId(), student);

        redirectAttributes.addFlashAttribute(
                "success",
                "Student updated successfully!"
        );

        return "redirect:/students";
    }
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {

        service.delete(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Student deleted successfully!"
        );

        return "redirect:/students";
    }
    @GetMapping("/search")
    public String searchStudent(@RequestParam("name") String name,
                                Model model) {

        List<Student> students = service.searchStudents(name);

        model.addAttribute("students", students);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", 1);
        model.addAttribute("totalItems", students.size());

        return "students";
    }
    @GetMapping("/viewStudent/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {

        Student student = service.getStudentById(id);

        model.addAttribute("student", student);

        return "viewstudent";
    }
    @GetMapping("/students/sort")
    public String sortStudents(@RequestParam String field,
                               Model model) {

        model.addAttribute("students",
                service.getAllStudentsSorted(field));

        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", 1);
        model.addAttribute("totalItems",
                service.getAllStudents().size());

        return "students";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}