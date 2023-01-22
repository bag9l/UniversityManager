package com.botscrew.universitymanager.service;

import com.botscrew.universitymanager.dto.LectorDTO;
import com.botscrew.universitymanager.model.Lector;

import java.util.List;

public interface LectorService {
    List<Lector> getAllLectors();

    Lector addLector(LectorDTO lector);

    Lector getLectorById(String id);

    Lector updateLector(LectorDTO lector, String id);

    void deleteLectorById(String id);

    List<Lector> findLectorsByTemplate(String template);
}
