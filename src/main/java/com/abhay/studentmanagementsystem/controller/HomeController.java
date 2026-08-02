package com.abhay.studentmanagementsystem.controller;

import com.abhay.studentmanagementsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final StudentService studentService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("stats", studentService.getDashboardStats());
        return "index";
    }
}
