package com.abhay.studentmanagementsystem.service;

import com.abhay.studentmanagementsystem.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudentById(Long id);

    List<Student> getAllStudents();

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    List<Student> searchStudents(String keyword);

    List<Student> filterByName(String name);

    List<Student> filterByCourse(String course);

    Map<String, Object> getDashboardStats();
}
