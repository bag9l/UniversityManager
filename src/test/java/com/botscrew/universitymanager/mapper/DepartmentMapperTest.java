package com.botscrew.universitymanager.mapper;

import com.botscrew.universitymanager.dto.DepartmentDTO;
import com.botscrew.universitymanager.model.Degree;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.repository.DepartmentRepository;
import com.botscrew.universitymanager.repository.LectorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentMapperTest {

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    LectorRepository lectorRepository;

    DepartmentMapper underTest = Mappers.getMapper(DepartmentMapper.class);

    @Test
    void canConvertDepartmentToDto() {
        Lector lector = new Lector(
                "402880ee85d0ac290185d0ac40a30000",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(new Department()));

        DepartmentDTO dto = new DepartmentDTO(
                "402880ee85d8b50c0185d8b6366d0002",
                "KRKS",
                "402880ee85d0ac290185d0ac40a30000",
                Set.of("402880ee85d0ac290185d0ac40a30000"));
        Department department = new Department(
                "402880ee85d8b50c0185d8b6366d0002",
                "KRKS",
                lector,
                Set.of(lector));

        DepartmentDTO result = underTest.departmentToDto(department);

        assertDtoEqualsFieldByField(result, dto);
    }

    @Test
    void shouldConvertDtoToDepartment() {
        Lector lector1 = new Lector();
        Lector lector2 = new Lector();
        Lector lector3 = new Lector();

        String[] lectorsIds = {
                "402880ee85d0ac290185d0ac40a30001",
                "402880ee85d0ac290185d0ac40a30002",
                "402880ee85d0ac290185d0ac40a30003"};

        lector1.setId(lectorsIds[0]);
        lector2.setId(lectorsIds[1]);
        lector3.setId(lectorsIds[2]);
        lector1.setDepartments(new HashSet<>());
        lector2.setDepartments(new HashSet<>());
        lector3.setDepartments(new HashSet<>());

        List<Lector> lectors = List.of(lector1, lector2, lector3);

        DepartmentDTO dto = new DepartmentDTO(
                "402880ee85d8b50c0185d8b6366d0002",
                "KRKS",
                lectorsIds[0],
                Set.of("402880ee85d0ac290185d0ac40a30001",
                        "402880ee85d0ac290185d0ac40a30002",
                        "402880ee85d0ac290185d0ac40a30003"));
        Department department = new Department(
                "402880ee85d8b50c0185d8b6366d0002",
                "KRKS",
                lector1,
                Set.of(lector1, lector2, lector3));

        when(lectorRepository.findAllById(dto.lectorsIds())).thenReturn(lectors);
        when(lectorRepository.findById(dto.headId())).thenReturn(Optional.of(lector1));
        when(departmentRepository.findDepartmentByHeadId(dto.headId())).thenReturn(Optional.empty());

        Department result = underTest.dtoToDepartment(dto, lectorRepository, departmentRepository);

        assertThat(result).isEqualTo(department);

        verify(lectorRepository).findAllById(dto.lectorsIds());
        verify(lectorRepository).findById(dto.headId());
        verify(departmentRepository).findDepartmentByHeadId(dto.headId());
    }

    private void assertDtoEqualsFieldByField(DepartmentDTO actual, DepartmentDTO expected) {
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.name()).isEqualTo(expected.name());
        assertThat(actual.headId()).isEqualTo(expected.headId());
        assertThat(actual.lectorsIds()).isEqualTo(actual.lectorsIds());
    }
}