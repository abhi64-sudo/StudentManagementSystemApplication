package com.abhay.studentmanagementsystem.controller;

import com.abhay.studentmanagementsystem.entity.Student;
import com.abhay.studentmanagementsystem.exception.DuplicateResourceException;
import com.abhay.studentmanagementsystem.exception.ResourceNotFoundException;
import com.abhay.studentmanagementsystem.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentWebController {

    private final StudentService studentService;

    @GetMapping
    public String listStudents(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("students", studentService.searchStudents(keyword));
        model.addAttribute("keyword", keyword);
        return "students/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/add";
    }

    @PostMapping
    public String addStudent(@Valid @ModelAttribute("student") Student student,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students/add";
        }
        try {
            studentService.createStudent(student);
            redirectAttributes.addFlashAttribute("successMessage", "Student added successfully.");
            return "redirect:/students";
        } catch (DuplicateResourceException ex) {
            result.rejectValue("email", "duplicate", ex.getMessage());
            return "students/add";
        }
    }

    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "students/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "students/edit";
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id,
                                 @Valid @ModelAttribute("student") Student student,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students/edit";
        }
        try {
            studentService.updateStudent(id, student);
            redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully.");
            return "redirect:/students";
        } catch (DuplicateResourceException ex) {
            result.rejectValue("email", "duplicate", ex.getMessage());
            return "students/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully.");
        return "redirect:/students";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }
}
