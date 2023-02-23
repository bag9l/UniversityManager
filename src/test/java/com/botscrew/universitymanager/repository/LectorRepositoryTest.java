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
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class LectorRepositoryTest {

    @Autowired
    private LectorRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindLectorsContainsTemplate() {

        Lector lector = underTest.save(new Lector(
                "temp",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(new Department())
        ));

        List<Lector> result = underTest.findLectorsContainsTemplate("fi");
        assertThat(result).isEqualTo(List.of(lector));
    }

    @Test
    void shouldNotFindLectorsContainsTemplate() {
        Lector lector = underTest.save(new Lector(
                "temp",
                "firstname",
                "lastname",
                Degree.ASSISTANT,
                new BigDecimal(10_000.0),
                LocalDate.of(2003, 3, 7),
                Set.of(new Department())
        ));

        List<Lector> result = underTest.findLectorsContainsTemplate("fila");
        assertThat(result).isNotEqualTo(List.of(lector));
    }
}