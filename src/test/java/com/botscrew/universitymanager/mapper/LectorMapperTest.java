package com.botscrew.universitymanager.mapper;

import com.botscrew.universitymanager.dto.LectorDTO;
import com.botscrew.universitymanager.model.Degree;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectorMapperTest {

    @Mock
    DepartmentRepository departmentRepository;

    LectorMapper underTest = Mappers.getMapper(LectorMapper.class);

    @Test
    void shouldConvertLectorToDto() {
        Department department = new Department(
                "402880ee85d8b50c0185d8b6366d0002",
                "KRKS",
                new Lector(),
                Set.of(new Lector()));
        Lector lector = new Lector(
                "402880ee85d0ac290185d0ac40a30000",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(department));
        String[] departmentIds = {"402880ee85d8b50c0185d8b6366d0002"};
        LectorDTO dto = new LectorDTO(
                "402880ee85d0ac290185d0ac40a30000",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                departmentIds);

        LectorDTO result = underTest.lectorToDto(lector);

        assertDtoFieldByField(result, dto);
    }

    @Test
    void shouldConvertDtoToLector() {
        Department department = new Department(
                "402880ee85d8b50c0185d8b6366d0002",
                "KRKS",
                new Lector(),
                Set.of(new Lector()));
        Lector lector = new Lector(
                "402880ee85d0ac290185d0ac40a30000",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(department));
        String[] departmentIds = {"402880ee85d8b50c0185d8b6366d0002"};
        LectorDTO dto = new LectorDTO(
                "402880ee85d0ac290185d0ac40a30000",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                departmentIds);
        when(departmentRepository.findAllById(List.of(dto.departmentsIds()))).thenReturn(List.of(department));

        Lector result = underTest.dtoToLector(dto, departmentRepository);

        assertThat(result).isEqualTo(lector);

        verify(departmentRepository).findAllById(List.of(dto.departmentsIds()));
    }


    private void assertDtoFieldByField(LectorDTO actual, LectorDTO expected) {
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.firstname()).isEqualTo(expected.firstname());
        assertThat(actual.lastname()).isEqualTo(expected.lastname());
        assertThat(actual.degree()).isEqualTo(expected.degree());
        assertThat(actual.salary()).isEqualTo(expected.salary());
        assertThat(actual.dateOfBirth()).isEqualTo(expected.dateOfBirth());
        assertArrayEquals(actual.departmentsIds(), expected.departmentsIds());
    }
}