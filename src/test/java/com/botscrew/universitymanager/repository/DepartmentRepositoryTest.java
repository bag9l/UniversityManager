package com.botscrew.universitymanager.repository;

import com.botscrew.universitymanager.model.Degree;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.model.Lector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    DepartmentRepository underTest;

    @Autowired
    LectorRepository lectorRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindHeadByDepartmentId() {

        Lector lector = lectorRepository.save(new Lector(
                "temp",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(new Department())));

        Department department = underTest.save(new Department(
                "402880ee85d8b50c0185d8b6366d0001",
                "KRKS",
                lector,
                Set.of(lector)));

        Optional<Lector> result = underTest.findHeadByDepartmentId(department.getId());

        Optional<Lector> optionalOfLector = Optional.of(department.getHead());

        assertThat(result).isEqualTo(optionalOfLector);
    }

    @Test
    void shouldNotFindHeadByDepartmentId() {

        Lector lector = lectorRepository.save(new Lector(
                "temp",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(new Department())));

        Department department = underTest.save(new Department(
                "402880ee85d8b50c0185d8b6366d0001",
                "KRKS",
                lector,
                Set.of(lector)));

        Optional<Lector> result = underTest.findHeadByDepartmentId(department.getId());

        Optional<Lector> optionalOfLector = Optional.of(new Lector());

        assertThat(result).isNotEqualTo(optionalOfLector);
    }

    @Test
    void countLectorsByDepartmentIdAndLectorDegree() {

        List<Lector> lectors = lectorRepository.saveAll(List.of(
                new Lector(
                        "temp",
                        "firstname",
                        "lastname",
                        Degree.ASSISTANT,
                        new BigDecimal(10_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department())),
                new Lector(
                        "temp",
                        "firstname1",
                        "lastname1",
                        Degree.PROFESSOR,
                        new BigDecimal(10_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department())),
                new Lector(
                        "temp",
                        "firstname2",
                        "lastname2",
                        Degree.ASSISTANT,
                        new BigDecimal(10_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department()))
        ));

        Department department = underTest.save(new Department(
                "402880ee85d8b50c0185d8b6366d0001",
                "KRKS",
                lectors.get(0),
                new HashSet<>(lectors)));

        Integer numberOfAssistants = underTest.countLectorsByDepartmentIdAndLectorDegree(department.getId(), Degree.ASSISTANT);
        Integer numberOfProfessors = underTest.countLectorsByDepartmentIdAndLectorDegree(department.getId(), Degree.PROFESSOR);

        assertThat(numberOfAssistants).isEqualTo(2);
        assertThat(numberOfProfessors).isEqualTo(1);
    }

    @Test
    void calculateAvgSalaryById() {
        List<Lector> lectors = lectorRepository.saveAll(List.of(
                new Lector(
                        "temp",
                        "firstname",
                        "lastname",
                        Degree.ASSISTANT,
                        new BigDecimal(10_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department())),
                new Lector(
                        "temp",
                        "firstname1",
                        "lastname1",
                        Degree.PROFESSOR,
                        new BigDecimal(20_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department())),
                new Lector(
                        "temp",
                        "firstname2",
                        "lastname2",
                        Degree.ASSISTANT,
                        new BigDecimal(30_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department()))
        ));

        Department department = underTest.save(new Department(
                "402880ee85d8b50c0185d8b6366d0001",
                "KRKS",
                lectors.get(0),
                new HashSet<>(lectors)));

        BigDecimal result = underTest.calculateAvgSalaryById(department.getId());

        assertThat(result).isEqualTo(BigDecimal.valueOf(20_000.0));
    }

    @Test
    void countLectorsById() {
        List<Lector> lectors = lectorRepository.saveAll(List.of(
                new Lector(
                        "temp",
                        "firstname",
                        "lastname",
                        Degree.ASSISTANT,
                        new BigDecimal(10_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department())),
                new Lector(
                        "temp",
                        "firstname1",
                        "lastname1",
                        Degree.PROFESSOR,
                        new BigDecimal(20_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department())),
                new Lector(
                        "temp",
                        "firstname2",
                        "lastname2",
                        Degree.ASSISTANT,
                        new BigDecimal(30_000.0),
                        LocalDate.of(2003, 3, 7),
                        Set.of(new Department()))
        ));

        Department department = underTest.save(new Department(
                "402880ee85d8b50c0185d8b6366d0001",
                "KRKS",
                lectors.get(0),
                new HashSet<>(lectors)));

        Integer result = underTest.countLectorsById(department.getId());

        assertThat(result).isEqualTo(3);
    }

    @Test
    void findDepartmentByHeadId() {
        Lector lector = lectorRepository.save(new Lector(
                "temp",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(new Department())));

        Department department = underTest.save(new Department(
                "402880ee85d8b50c0185d8b6366d0001",
                "KRKS",
                lector,
                Set.of(lector)));

        Optional<Department> result = underTest.findDepartmentByHeadId(department.getHead().getId());

        assertThat(result).isEqualTo(Optional.of(department));
    }
}