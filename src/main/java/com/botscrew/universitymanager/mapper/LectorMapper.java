package com.botscrew.universitymanager.mapper;

import com.botscrew.universitymanager.dto.LectorDTO;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.repository.DepartmentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LectorMapper {

    @Mapping(target = "departmentsIds",
            expression = "java(lector.getDepartments().stream()\n" +
                    "                .map(com.botscrew.universitymanager.model.Department::getId)\n" +
                    "                .collect(java.util.stream.Collectors.toSet()))")
    LectorDTO lectorToDto(Lector lector);

    @Mapping(target = "departments",
            expression = "java(new java.util.HashSet<>(" +
                    "departmentRepository.findAllById(dto.departmentsIds())))")
    Lector dtoToLector(LectorDTO dto, DepartmentRepository departmentRepository);

}
