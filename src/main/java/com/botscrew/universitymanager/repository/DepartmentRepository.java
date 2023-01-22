package com.botscrew.universitymanager.repository;

import com.botscrew.universitymanager.model.Degree;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    @Query("SELECT d.head FROM Department d WHERE d.id =:id")
    Optional<Lector> findHeadByDepartmentId(@Param("id") String id);

    @Query("SELECT COUNT(l) FROM Department d JOIN d.lectors l WHERE d.id =:id AND l.degree =:degree")
    Integer countLectorsByIdAndLectorDegree(@Param("id") String id, @Param("degree") Degree degree);
}
