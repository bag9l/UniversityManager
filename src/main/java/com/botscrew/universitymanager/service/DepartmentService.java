package com.botscrew.universitymanager.service;

import com.botscrew.universitymanager.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
}
