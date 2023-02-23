package com.botscrew.universitymanager.service.impl;

import com.botscrew.universitymanager.dto.DepartmentDTO;
import com.botscrew.universitymanager.exception.EntityNotExistsException;
import com.botscrew.universitymanager.mapper.DepartmentMapper;
import com.botscrew.universitymanager.model.Degree;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.repository.DepartmentRepository;
import com.botscrew.universitymanager.repository.LectorRepository;
import com.botscrew.universitymanager.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final LectorRepository lectorRepository;

    private final DepartmentMapper departmentMapper;


    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department addDepartment(DepartmentDTO dto) {
        Department department = departmentMapper.dtoToDepartment(dto, lectorRepository, departmentRepository);

        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id).orElseThrow(() ->
                new EntityNotExistsException("Department with id:" + id + " not found"));
    }

    @Transactional
    @Override
    public Department updateDepartment(DepartmentDTO departmentSource, String id) {
        return departmentRepository.findById(id)
                .map(department -> {
                            department = departmentMapper.dtoToDepartment(departmentSource, lectorRepository, departmentRepository);
                            return departmentRepository.save(department);
                        }
                ).orElseThrow(() ->
                        new EntityNotExistsException("Department with id:" + id + " not found"));
    }

    @Override
    public void deleteDepartmentById(String id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Lector getHeadByDepartmentId(String id) {
        return departmentRepository.findHeadByDepartmentId(id).orElseThrow(() ->
                new EntityNotExistsException("Head not found"));
    }

    @Override
    public Map<String, Integer> getLectorsStatisticOfDepartment(String departmentId) {
        Map<String, Integer> statistics = new LinkedHashMap<>();

        Arrays.stream(Degree.values())
                .forEach(degree ->
                        statistics.put(degree.getValue(),
                                departmentRepository.countLectorsByDepartmentIdAndLectorDegree(departmentId, degree)));

        return statistics;
    }

    @Override
    public BigDecimal calculateAvgSalaryForTheDepartment(String departmentId) {
        return departmentRepository.calculateAvgSalaryById(departmentId);
    }

    @Override
    public Integer getNumberOfEmployeesForTheDepartment(String departmentId) {
        return departmentRepository.countLectorsById(departmentId);
    }
}
