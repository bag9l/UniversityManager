package com.botscrew.universitymanager.controller;

import com.botscrew.universitymanager.service.LectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/lectors")
public class LectorController {

    private final LectorService lectorService;

    @Autowired
    public LectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }
}
