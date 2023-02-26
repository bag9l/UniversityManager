package com.botscrew.universitymanager.dto;

import com.botscrew.universitymanager.validator.NameConstraint;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record DepartmentDTO(
        String id,
        @NotNull(message = "must not be null") @NameConstraint String name,
        String headId,
        Set<String> lectorsIds
) {
}
