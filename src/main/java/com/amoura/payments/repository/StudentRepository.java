package com.amoura.payments.repository;

import com.amoura.payments.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Student findByCode(String code);
    List<Student> findByProgramId(String programId);

}