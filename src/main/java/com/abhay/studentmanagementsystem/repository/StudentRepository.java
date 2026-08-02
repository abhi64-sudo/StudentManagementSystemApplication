package com.abhay.studentmanagementsystem.repository;

import com.abhay.studentmanagementsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByCourseContainingIgnoreCase(String course);

    Optional<Student> findByEmail(String email);

    List<Student> findByStatus(String status);

    long countByStatus(String status);

    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.course) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.createdDate >= :since")
    long countNewAdmissionsSince(@Param("since") java.time.LocalDateTime since);
}
