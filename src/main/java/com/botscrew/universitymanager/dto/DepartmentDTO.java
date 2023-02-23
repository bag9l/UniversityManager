package com.botscrew.universitymanager.dto;

import com.botscrew.universitymanager.validator.NameConstraint;
import jakarta.validation.constraints.NotNull;

public record DepartmentDTO(
        String id,
        @NotNull(message = "must not be null") @NameConstraint String name,
        String headId,
        String[] lectorsIds
) { }
