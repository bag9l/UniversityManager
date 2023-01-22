package com.botscrew.universitymanager.repository;

import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    @Query("SELECT d.head FROM Department d WHERE d.id = ?1")
    Optional<Lector> findHeadByDepartmentId(String id);
}
