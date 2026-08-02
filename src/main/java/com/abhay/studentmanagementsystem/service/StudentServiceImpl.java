package com.abhay.studentmanagementsystem.service;

import com.abhay.studentmanagementsystem.entity.Student;
import com.abhay.studentmanagementsystem.exception.DuplicateResourceException;
import com.abhay.studentmanagementsystem.exception.ResourceNotFoundException;
import com.abhay.studentmanagementsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public Student createStudent(Student student) {
        studentRepository.findByEmail(student.getEmail()).ifPresent(s -> {
            throw new DuplicateResourceException("A student with email " + student.getEmail() + " already exists");
        });
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, Student incoming) {
        Student existing = getStudentById(id);

        studentRepository.findByEmail(incoming.getEmail()).ifPresent(s -> {
            if (!s.getId().equals(id)) {
                throw new DuplicateResourceException("Another student already uses email " + incoming.getEmail());
            }
        });

        existing.setName(incoming.getName());
        existing.setEmail(incoming.getEmail());
        existing.setPhone(incoming.getPhone());
        existing.setCourse(incoming.getCourse());
        existing.setDepartment(incoming.getDepartment());
        existing.setGender(incoming.getGender());
        existing.setDob(incoming.getDob());
        existing.setAddress(incoming.getAddress());
        existing.setStatus(incoming.getStatus());

        return studentRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student existing = getStudentById(id);
        studentRepository.delete(existing);
    }

    @Override
    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllStudents();
        }
        return studentRepository.searchByKeyword(keyword.trim());
    }

    @Override
    public List<Student> filterByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Student> filterByCourse(String course) {
        return studentRepository.findByCourseContainingIgnoreCase(course);
    }

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", studentRepository.count());
        stats.put("activeStudents", studentRepository.countByStatus("ACTIVE"));
        stats.put("newAdmissions", studentRepository.countNewAdmissionsSince(LocalDateTime.now().minusDays(30)));
        return stats;
    }
}
