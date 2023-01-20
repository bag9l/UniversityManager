package com.botscrew.universitymanager.dto;

import com.botscrew.universitymanager.model.Lector;
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

    private String name;

    private Set<Lector> lectors;
}
