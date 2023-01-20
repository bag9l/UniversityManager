package com.botscrew.universitymanager.dto;

import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.validator.NameConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private String id;

    @NotNull(message = "must not be null")
    @NameConstraint
    private String name;

    private Set<Lector> lectors;
}