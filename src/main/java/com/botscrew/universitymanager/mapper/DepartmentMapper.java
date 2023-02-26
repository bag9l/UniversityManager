package com.botscrew.universitymanager.mapper;

import com.botscrew.universitymanager.dto.DepartmentDTO;
import com.botscrew.universitymanager.exception.EntityNotExistsException;
import com.botscrew.universitymanager.exception.LectorIsAlreadyHeadException;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.repository.DepartmentRepository;
import com.botscrew.universitymanager.repository.LectorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class DepartmentMapper {

    @Mapping(target = "lectorsIds",
            expression = "java(department.getLectors().stream()\n" +
                    "                .map(com.botscrew.universitymanager.model.Lector::getId)\n" +
                    "                .collect(java.util.stream.Collectors.toSet()))")
    @Mapping(target = "headId", source = "head.id")
    public abstract DepartmentDTO departmentToDto(Department department);


    public Department dtoToDepartment(DepartmentDTO dto,
                                      LectorRepository lectorRepository,
                                      DepartmentRepository departmentRepository) {
        if (dto == null && lectorRepository == null) {
            return null;
        }

        Department department = new Department();

        if (dto != null) {
            department.setId(dto.id());
            department.setName(dto.name());
            department.setLectors(new HashSet<>(lectorRepository.findAllById(dto.lectorsIds())));
            setHeadOfDepartment(department, dto.headId(), departmentRepository, lectorRepository);
        }

        return department;
    }

    private void setHeadOfDepartment(Department department,
                                     String headId,
                                     DepartmentRepository departmentRepository,
                                     LectorRepository lectorRepository) {
        if (headId != null) {

            if (departmentRepository.findDepartmentByHeadId(headId).orElse(null) != null) {
                throw new LectorIsAlreadyHeadException();
            }

            Lector head = lectorRepository.findById(headId).orElseThrow(() ->
                    new EntityNotExistsException("Lector with id:" + headId + " not found"));

            Set<Department> departments = head.getDepartments();
            departments.add(department);
            head.setDepartments(departments);

            department.setHead(head);
        }
    }
}
