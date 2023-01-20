package com.botscrew.universitymanager.service;

import com.botscrew.universitymanager.repository.LectorRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class LectorService {

    private final LectorRepository lectorRepository;

    @Autowired
    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }
}