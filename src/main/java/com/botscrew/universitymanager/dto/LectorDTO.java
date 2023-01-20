package com.botscrew.universitymanager.dto;

import com.botscrew.universitymanager.model.Degree;
import com.botscrew.universitymanager.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectorDTO {
    private String id;

    private String firstname;

    private String lastName;

    private Degree degree;

    private BigDecimal salary;

    private LocalDate dateOfBirth;

    private Set<Department> departments;

}
