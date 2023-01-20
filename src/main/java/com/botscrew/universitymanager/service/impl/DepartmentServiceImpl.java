package com.botscrew.universitymanager.service.impl;

import com.botscrew.universitymanager.dto.DepartmentDTO;
import com.botscrew.universitymanager.exception.EntityNotExistsException;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.repository.DepartmentRepository;
import com.botscrew.universitymanager.service.DepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.botscrew.universitymanager.helper.NullPropertyFinder.getNullPropertyNames;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department addDepartment(DepartmentDTO dto) {
        Department department = new Department();
        BeanUtils.copyProperties(dto, department);
        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id).orElseThrow(() ->
                new EntityNotExistsException("Department with id:" + id + " not found"));
    }

    @Override
    public Department updateDepartment(DepartmentDTO departmentSource, String id) {
        return departmentRepository.findById(id)
                .map(department -> {
                    BeanUtils.copyProperties(departmentSource, department, getNullPropertyNames(departmentSource));
                    return departmentRepository.save(department);
                }).orElseThrow(() ->
                        new EntityNotExistsException("Department with id:" + id + " not found"));
    }

    @Override
    public void deleteDepartmentById(String id) {
        departmentRepository.deleteById(id);
    }
}
