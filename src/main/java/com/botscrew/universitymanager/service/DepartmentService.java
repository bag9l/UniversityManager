package com.botscrew.universitymanager.service;

import com.botscrew.universitymanager.dto.DepartmentDTO;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department addDepartment(DepartmentDTO department);
    Department getDepartmentById(String id);
    Department updateDepartment(DepartmentDTO department, String id);
    void deleteDepartmentById(String id);

    Lector getHeadByDepartmentId(String id);
}
