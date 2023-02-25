package com.botscrew.universitymanager.service.impl;

import com.botscrew.universitymanager.dto.LectorDTO;
import com.botscrew.universitymanager.exception.EntityNotExistsException;
import com.botscrew.universitymanager.mapper.LectorMapper;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectorServiceImplTest {

    @Mock
    private LectorRepository lectorRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private LectorMapper lectorMapper;

    @InjectMocks
    private LectorServiceImpl underTest;

    @Test
    void canGetAllLectors() {
        List<Lector> lectors = List.of(
                new Lector(
                        "402880ee85d0ac290185d0ac40a30000",
                        "firstname",
                        "lastname",
                        Degree.ASSISTANT,
                        new BigDecimal(10_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department())),
                new Lector(
                        "402880ee85d0ac290185d0ac40a30000",
                        "firstname",
                        "lastname",
                        Degree.ASSISTANT,
                        new BigDecimal(10_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department()))
        );

        when(lectorRepository.findAll()).thenReturn(lectors);

        List<Lector> result = underTest.getAllLectors();

        verify(lectorRepository).findAll();

        assertThat(result).isEqualTo(lectors);
    }

    @Test
    void canAddLector() {
        Lector lector = new Lector(
                "402880ee85d0ac290185d0ac40a30000",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(new Department()));
        LectorDTO dto = new LectorDTO(
                "402880ee85d0ac290185d0ac40a30000",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                null);

        when(lectorRepository.save(lector)).thenReturn(lector);
        when(lectorMapper.dtoToLector(dto, departmentRepository)).thenReturn(lector);

        Lector result = underTest.addLector(dto);

        verify(lectorRepository).save(lector);

        assertThat(result).isEqualTo(lector);
    }

    @Test
    void shouldReturnLectorById() {
        Lector lector = new Lector(
                "402880ee85d0ac290185d0ac40a30000",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(new Department()));

        when(lectorRepository.findById("402880ee85d0ac290185d0ac40a30000")).thenReturn(Optional.of(lector));

        Lector result = underTest.getLectorById("402880ee85d0ac290185d0ac40a30000");

        verify(lectorRepository).findById("402880ee85d0ac290185d0ac40a30000");

        assertThat(result).isEqualTo(lector);
    }

    @Test()
    void shouldThrowExceptionWhenLectorById() {
        when(lectorRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotExistsException.class, () -> {
            underTest.getLectorById("402880ee85d0ac290185d0ac40a30000");
        });

        verify(lectorRepository).findById(anyString());
    }

    @Test
    void shouldUpdateLector() {
        Lector lector = new Lector(
                "402880ee85d0ac290185d0ac40a30000",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(new Department()));
        LectorDTO dto = new LectorDTO(
                null,
                "newFirstname",
                "newLastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                null);

        when(lectorRepository.save(lector)).thenReturn(lector);
        when(lectorRepository.findById("402880ee85d0ac290185d0ac40a30000")).thenReturn(Optional.of(lector));
        when(lectorMapper.dtoToLector(dto, departmentRepository)).thenReturn(lector);

        Lector result = underTest.updateLector(dto, "402880ee85d0ac290185d0ac40a30000");

        lector.setFirstname("newFirstname");
        lector.setLastname("newLastname");

        verify(lectorRepository).save(lector);
        verify(lectorRepository).findById("402880ee85d0ac290185d0ac40a30000");
        verify(lectorMapper).dtoToLector(dto, departmentRepository);

        assertThat(result).isEqualTo(lector);
    }

    @Test
    void shouldThrowExceptionWhenUpdateLectorCauseLectorIsNotExists() {
        LectorDTO dto = new LectorDTO(
                null,
                "newFirstname",
                "newLastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                null);

        when(lectorRepository.findById("402880ee85d0ac290185d0ac40a30000")).thenReturn(Optional.empty());

        assertThrows(EntityNotExistsException.class, () -> {
                    underTest.updateLector(dto, "402880ee85d0ac290185d0ac40a30000");
                }
        );

        verify(lectorRepository).findById("402880ee85d0ac290185d0ac40a30000");
    }

    @Test
    void canDeleteLectorById() {
        underTest.deleteLectorById(anyString());
        verify(lectorRepository).deleteById(anyString());
    }

    @Test
    void canFindLectorsByTemplate() {
        List<Lector> lectors = List.of(
                new Lector(
                        "402880ee85d0ac290185d0ac40a30000",
                        "firstname",
                        "lastname",
                        Degree.ASSISTANT,
                        new BigDecimal(10_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department())),
                new Lector(
                        "402880ee85d0ac290185d0ac40a30000",
                        "firstname",
                        "lastname",
                        Degree.ASSISTANT,
                        new BigDecimal(10_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department()))
        );

        when(lectorRepository.findLectorsContainsTemplate(anyString())).thenReturn(lectors);

        List<Lector> result = underTest.findLectorsByTemplate("fi");

        verify(lectorRepository).findLectorsContainsTemplate(anyString());

        assertThat(result).isEqualTo(lectors);
    }
}