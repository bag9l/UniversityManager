package com.botscrew.universitymanager.dto;

import com.botscrew.universitymanager.model.Degree;
import com.botscrew.universitymanager.validator.NameConstraint;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record LectorDTO(
        String id,
        @NotNull(message = "must not be null") @NameConstraint String firstname,
        @NotNull(message = "must not be null") @NameConstraint String lastname,
        @NotNull(message = "must not be null") Degree degree,
        BigDecimal salary,
        @NotNull(message = "must not be null") LocalDate dateOfBirth,
        Set<String> departmentsIds
) {
}
