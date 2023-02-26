package com.botscrew.universitymanager.service.impl;

import com.botscrew.universitymanager.dto.DepartmentDTO;
import com.botscrew.universitymanager.exception.EntityNotExistsException;
import com.botscrew.universitymanager.mapper.DepartmentMapper;
import com.botscrew.universitymanager.model.Degree;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.repository.DepartmentRepository;
import com.botscrew.universitymanager.repository.LectorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    LectorRepository lectorRepository;

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    DepartmentMapper departmentMapper;

    @InjectMocks
    DepartmentServiceImpl underTest;

    @Test
    void canGetAllDepartments() {
        List<Department> departments = List.of(
                new Department(
                        "402880ee85d8b50c0185d8b6366d0001",
                        "KRKS",
                        new Lector(),
                        Set.of(new Lector())),
                new Department(
                        "402880ee85d8b50c0185d8b6366d0002",
                        "KRKS",
                        new Lector(),
                        Set.of(new Lector()))
        );

        when(departmentRepository.findAll()).thenReturn(departments);

        List<Department> result = underTest.getAllDepartments();

        assertThat(result).isEqualTo(departments);

        verify(departmentRepository).findAll();
    }

    @Test
    void canAddDepartment() {
        Lector lector = new Lector(
                "402880ee85d0ac290185d0ac40a30000",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(new Department()));

        DepartmentDTO dto = new DepartmentDTO(
                "402880ee85d8b50c0185d8b6366d0001",
                "KRKS",
                "402880ee85d0ac290185d0ac40a30000",
                Set.of("402880ee85d0ac290185d0ac40a30000"));
        Department department = new Department(
                "402880ee85d8b50c0185d8b6366d0001",
                "KRKS",
                lector,
                Set.of(lector));

        when(departmentMapper.dtoToDepartment(dto, lectorRepository, departmentRepository)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = underTest.addDepartment(dto);

        assertThat(result).isEqualTo(department);

        verify(departmentRepository).save(department);
    }

    @Test
    void canGetDepartmentById() {
        Department department = new Department(
                "402880ee85d8b50c0185d8b6366d0001",
                "KRKS",
                new Lector(),
                Set.of(new Lector()));

        when(departmentRepository.findById("402880ee85d8b50c0185d8b6366d0001")).thenReturn(Optional.of(department));

        Department result = underTest.getDepartmentById("402880ee85d8b50c0185d8b6366d0001");

        assertThat(result).isEqualTo(department);

        verify(departmentRepository).findById("402880ee85d8b50c0185d8b6366d0001");
    }

    @Test
    void shouldThrowExceptionWhenDepartmentIsNotFoundWhenGetDepartmentById() {

        when(departmentRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotExistsException.class, () -> {
            underTest.getDepartmentById("402880ee85d8b50c0185d8b6366d0001");
        });

        verify(departmentRepository).findById("402880ee85d8b50c0185d8b6366d0001");
    }

    @Test
    void canUpdateDepartment() {
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
                "402880ee85d8b50c0185d8b6366d0001",
                "KRKS",
                lector,
                Set.of(lector));

        when(departmentMapper.dtoToDepartment(dto, lectorRepository, departmentRepository)).thenReturn(department);
        when(departmentRepository.findById("402880ee85d8b50c0185d8b6366d0002")).thenReturn(Optional.of(department));
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = underTest.updateDepartment(dto, "402880ee85d8b50c0185d8b6366d0002");

        assertThat(result).isEqualTo(department);

        verify(departmentRepository).findById("402880ee85d8b50c0185d8b6366d0002");
        verify(departmentRepository).save(department);
    }

    @Test
    void shouldThrowExceptionWhenDepartmentNotExistsWhenUpdateDepartment() {

        DepartmentDTO dto = new DepartmentDTO(
                "402880ee85d8b50c0185d8b6366d0002",
                "KRKS",
                "402880ee85d0ac290185d0ac40a30000",
                null);

        when(departmentRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotExistsException.class, () -> {
            underTest.updateDepartment(dto, "402880ee85d8b50c0185d8b6366d0002");
        });

        verify(departmentRepository).findById("402880ee85d8b50c0185d8b6366d0002");
    }

    @Test
    void canDeleteDepartmentById() {
        underTest.deleteDepartmentById("402880ee85d8b50c0185d8b6366d0002");
        verify(departmentRepository).deleteById("402880ee85d8b50c0185d8b6366d0002");
    }

    @Test
    void shouldGetHeadByDepartmentId() {
        Lector head = new Lector(
                "402880ee85d0ac290185d0ac40a30000",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(new Department()));

        when(departmentRepository.findHeadByDepartmentId(anyString())).thenReturn(Optional.of(head));

        Lector result = underTest.getHeadByDepartmentId("402880ee85d8b50c0185d8b6366d0002");

        assertThat(result).isEqualTo(head);

        verify(departmentRepository).findHeadByDepartmentId("402880ee85d8b50c0185d8b6366d0002");
    }

    @Test
    void shouldThrowExceptionWhenDepartmentNotExistsWhenGetHeadByDepartmentId() {

        when(departmentRepository.findHeadByDepartmentId(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotExistsException.class, () -> {
            underTest.getHeadByDepartmentId("402880ee85d8b50c0185d8b6366d0002");
        });

        verify(departmentRepository).findHeadByDepartmentId("402880ee85d8b50c0185d8b6366d0002");
    }

    @Test
    void cadGetLectorsStatisticOfDepartment() {
        Map<String, Integer> statistics = new HashMap<>();
        statistics.put(Degree.ASSISTANT.getValue(), 3);
        statistics.put(Degree.ASSOCIATE_PROFESSOR.getValue(), 8);
        statistics.put(Degree.PROFESSOR.getValue(), 0);

        when(departmentRepository.countLectorsByDepartmentIdAndLectorDegree(
                "402880ee85d8b50c0185d8b6366d0002",
                Degree.ASSISTANT)).thenReturn(3);
        when(departmentRepository.countLectorsByDepartmentIdAndLectorDegree(
                "402880ee85d8b50c0185d8b6366d0002",
                Degree.ASSOCIATE_PROFESSOR)).thenReturn(8);

        Map<String, Integer> result = underTest.getLectorsStatisticOfDepartment("402880ee85d8b50c0185d8b6366d0002");

        assertThat(result).isEqualTo(statistics);

        verify(departmentRepository, times(1)).countLectorsByDepartmentIdAndLectorDegree(
                "402880ee85d8b50c0185d8b6366d0002",
                Degree.ASSISTANT
        );
        verify(departmentRepository, times(1)).countLectorsByDepartmentIdAndLectorDegree(
                "402880ee85d8b50c0185d8b6366d0002",
                Degree.ASSOCIATE_PROFESSOR
        );
        verify(departmentRepository, times(1)).countLectorsByDepartmentIdAndLectorDegree(
                "402880ee85d8b50c0185d8b6366d0002",
                Degree.PROFESSOR
        );
    }

    @Test
    void canCalculateAvgSalaryForTheDepartment() {
        when(departmentRepository.calculateAvgSalaryById("402880ee85d8b50c0185d8b6366d0002"))
                .thenReturn(BigDecimal.valueOf(20000.0));

        BigDecimal result = underTest.calculateAvgSalaryForTheDepartment("402880ee85d8b50c0185d8b6366d0002");

        assertThat(result).isEqualTo(BigDecimal.valueOf(20000.0));

        verify(departmentRepository).calculateAvgSalaryById("402880ee85d8b50c0185d8b6366d0002");
    }

    @Test
    void canGetNumberOfEmployeesForTheDepartment() {
        when(departmentRepository.countLectorsById("402880ee85d8b50c0185d8b6366d0002")).thenReturn(17);

        Integer result = underTest.getNumberOfEmployeesForTheDepartment("402880ee85d8b50c0185d8b6366d0002");

        assertThat(result).isEqualTo(17);

        verify(departmentRepository).countLectorsById("402880ee85d8b50c0185d8b6366d0002");
    }
}