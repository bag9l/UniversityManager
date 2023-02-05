package com.botscrew.universitymanager.service.impl;

import com.botscrew.universitymanager.dto.DepartmentDTO;
import com.botscrew.universitymanager.exception.EntityNotExistsException;
import com.botscrew.universitymanager.exception.LectorIsAlreadyHeadException;
import com.botscrew.universitymanager.model.Degree;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.repository.DepartmentRepository;
import com.botscrew.universitymanager.repository.LectorRepository;
import com.botscrew.universitymanager.service.DepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.botscrew.universitymanager.helper.NullPropertyFinder.getNullPropertyNames;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final LectorRepository lectorRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, LectorRepository lectorRepository) {
        this.departmentRepository = departmentRepository;
        this.lectorRepository = lectorRepository;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department addDepartment(DepartmentDTO dto) {
        Department department = new Department();
        BeanUtils.copyProperties(dto, department);

        if (dto.getLectorsIds() != null) {
            department.setLectors(findLectorsByIds(dto.getLectorsIds()));
        }

        setHeadOfDepartment(department, dto.getHeadId());

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
                    BeanUtils.copyProperties(departmentSource, department, getNullPropertyNames(departmentSource));
                    if (departmentSource.getLectorsIds() != null) {
                        department.setLectors(findLectorsByIds(departmentSource.getLectorsIds()));
                    }

                    setHeadOfDepartment(department, departmentSource.getHeadId());

                    return departmentRepository.save(department);
                }).orElseThrow(() ->
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
                                departmentRepository.countLectorsByIdAndLectorDegree(departmentId, degree)));

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

    private Set<Lector> findLectorsByIds(String[] ids) {
        List<String> ListOfIds = List.of(ids);
        Set<Lector> lectors = lectorRepository.findAllById(ListOfIds).stream()
                .collect(Collectors.toSet());
        return lectors;
    }

    private Lector findHeadById(String id) {
        Lector head = lectorRepository.findById(id).orElseThrow(() ->
                new EntityNotExistsException("Lector with id:" + id + " not found"));
        return head;
    }

    private void setHeadOfDepartment(Department department, String headId) {
        if (headId != null) {

            if (departmentRepository.findDepartmentByHeadId(headId).orElse(null) != null) {
                throw new LectorIsAlreadyHeadException();
            }

            Lector head = findHeadById(headId);

            Set<Department> departments = head.getDepartments();
            departments.add(department);
            head.setDepartments(departments);

            department.setHead(head);
        }
    }
}
