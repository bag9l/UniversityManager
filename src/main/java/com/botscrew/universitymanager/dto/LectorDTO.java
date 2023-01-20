package com.botscrew.universitymanager.dto;

import com.botscrew.universitymanager.model.Degree;
import com.botscrew.universitymanager.model.Department;
import com.botscrew.universitymanager.validator.NameConstraint;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "must not be null")
    @NameConstraint
    private String firstname;

    @NotNull(message = "must not be null")
    @NameConstraint
    private String lastName;

    @NotNull(message = "must not be null")
    private Degree degree;

    private BigDecimal salary;

    @NotNull(message = "must not be null")
    private LocalDate dateOfBirth;

    private Set<Department> departments;

}
