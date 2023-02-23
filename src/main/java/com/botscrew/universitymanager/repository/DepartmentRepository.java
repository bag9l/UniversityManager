package com.botscrew.universitymanager.repository;

import com.botscrew.universitymanager.model.Degree;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    @Query("SELECT d.head FROM Department d WHERE d.id =:id")
    Optional<Lector> findHeadByDepartmentId(@Param("id") String id);

    @Query("SELECT COUNT(l) FROM Department d JOIN d.lectors l WHERE d.id =:id AND l.degree =:degree")
    Integer countLectorsByDepartmentIdAndLectorDegree(@Param("id") String departmentId, @Param("degree") Degree degree);

    @Query("SELECT AVG(l.salary) FROM Department d JOIN d.lectors l WHERE d.id =:id")
    BigDecimal calculateAvgSalaryById(@Param("id") String departmentId);

    @Query("SELECT COUNT(l) FROM Department d JOIN d.lectors l WHERE d.id =:id")
    Integer countLectorsById(@Param("id") String departmentId);

    @Query("SELECT d FROM Department d JOIN d.head h WHERE h.id =:id")
    Optional<Department> findDepartmentByHeadId(@Param("id") String headId);
}
