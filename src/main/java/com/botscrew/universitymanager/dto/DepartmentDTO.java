package com.botscrew.universitymanager.dto;

import com.botscrew.universitymanager.validator.NameConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private String id;

    @NotNull(message = "must not be null")
    @NameConstraint
    private String name;

    private String headId;

    private String[] lectorsIds;
}
